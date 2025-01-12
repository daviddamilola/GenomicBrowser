package GenomeBrowser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author davidoluwasusi
 */
public class BasicStatisticsCalculator {

    public Map<String, Object> calculateGTFStats(Object[][] gtfData) {
        Map<String, Object> stats = new LinkedHashMap<>(); // Use LinkedHashMap to preserve insertion order
        Map<String, List<int[]>> geneExons = new HashMap<>();
        Map<String, Integer> geneLengths = new HashMap<>();

        try {
            for (Object[] row : gtfData) {
                String feature = (String) row[2];
                int start = (int) row[3];
                int end = (int) row[4];
                String attributes = (String) row[7];

                // Only process "exon" features
                if ("exon".equalsIgnoreCase(feature)) {
                    String geneName = getAttribute(attributes, "gene_name");
                    int length = end - start + 1;

                    // Track exons for each gene
                    geneExons.computeIfAbsent(geneName, k -> new ArrayList<>()).add(new int[]{start, end});

                    // Calculate gene length (sum of exon lengths)
                    geneLengths.put(geneName, geneLengths.getOrDefault(geneName, 0) + length);
                }
            }

            // Calculate required stats
            int totalExons = geneExons.values().stream().mapToInt(List::size).sum();
            int totalGenes = geneExons.size();
            double avgExonsPerGene = totalGenes == 0 ? 0 : roundToTwoDecimalPlaces((double) totalExons / totalGenes);

            String longestGeneName = geneLengths.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse(null);

            String shortestGeneName = geneLengths.entrySet().stream()
                    .min(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse(null);

            int longestGene = geneLengths.getOrDefault(longestGeneName, 0);
            int shortestGene = geneLengths.getOrDefault(shortestGeneName, 0);

            double avgGeneLength = totalGenes == 0
                    ? 0
                    : roundToTwoDecimalPlaces(geneLengths.values().stream().mapToInt(Integer::intValue).average().orElse(0));

            // Add results to stats map in specified order
            stats.put("Average Exons Per Gene", avgExonsPerGene);
            stats.put("Longest Gene Name", longestGeneName);
            stats.put("Longest Gene Length", longestGene);
            stats.put("Shortest Gene Name", shortestGeneName);
            stats.put("Shortest Gene Length", shortestGene);
            stats.put("Average Gene Length", avgGeneLength);

        } catch (Exception e) {
            System.out.println("Error calculating GTF statistics: " + e.getMessage());
        }

        return stats;
    }

    // Helper to extract attribute values from the attributes column
    public static String getAttribute(String attributes, String key) {
        String[] pairs = attributes.split(";");
        for (String pair : pairs) {
            String[] parts = pair.trim().split(" ");
            if (parts[0].equals(key)) {
                return parts[1].replace("\"", "");
            }
        }
        return "";
    }

    // Helper to round to two decimal places
    private static double roundToTwoDecimalPlaces(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
