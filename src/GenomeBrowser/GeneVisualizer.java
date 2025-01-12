/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GenomeBrowser;

/**
 *
 * @author davidoluwasusi
 */
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class GeneVisualizer extends JPanel {

    private String geneName;
    private List<int[]> exons;
    private int geneStart;
    private int geneEnd;

    public GeneVisualizer(String geneName, Map<String, List<int[]>> exonData, int geneStart, int geneEnd) {
        this.geneName = geneName;
        this.exons = exonData.get(geneName); // Get exons for this gene
        this.geneStart = geneStart;
        this.geneEnd = geneEnd;
        setPreferredSize(new Dimension(800, 100)); // Set a fixed size for the visualizer
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Set background color
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Calculate scale factor (gene length to panel width)
        int geneLength = geneEnd - geneStart + 1;
        int padding = 20; // Padding from the edges
        int usableWidth = getWidth() - 2 * padding;
        double scale = (double) usableWidth / geneLength;

        int yPosition = getHeight() / 2;
        int rectHeight = 20; // Height of the rectangle
        int rectWidth = getWidth() - 2 * padding;

        g2d.setColor(Color.CYAN);
        g2d.fillRect(padding, yPosition - rectHeight / 2, rectWidth, rectHeight);

        // Draw exons
        if (exons != null) {
            g2d.setColor(Color.BLUE);
            for (int[] exon : exons) {
                int exonStart = exon[0];
                int exonEnd = exon[1];

                // Map exon positions to panel coordinates
                int startX = padding + (int) ((exonStart - geneStart) * scale);
                int endX = padding + (int) ((exonEnd - geneStart) * scale);

                // Draw exon as a filled rectangle
                g2d.fillRect(startX, yPosition - 10, endX - startX, 20);
            }
        }

        // Add labels for clarity
        g2d.setColor(Color.BLACK);
        g2d.drawString("Gene: " + geneName, padding, yPosition - 25);
        g2d.drawString("Start", padding, yPosition + 35);
        g2d.drawString("End", getWidth() - padding - 60, yPosition + 35);
    }
}
