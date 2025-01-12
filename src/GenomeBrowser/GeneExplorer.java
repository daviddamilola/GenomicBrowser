/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GenomeBrowser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 *
 * @author davidoluwasusi
 */
public class GeneExplorer extends javax.swing.JPanel {

    private String fastaFilePath;
    private Map<String, List<int[]>> parsedExonsFromGtf;

    /**
     * Creates new form GeneExplorer
     */
    public GeneExplorer() {
        initComponents();
    }

    private void setGenesAsOptions(String path) {
        // Use SwingWorker to perform the operation in the background
        SwingWorker<List<String>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<String> doInBackground() {
                // Perform the long-running task of getting gene names
                return getGeneNamesFromFasta(path);
            }

            @Override
            protected void done() {
                try {
                    // Update the UI on the Event Dispatch Thread
                    List<String> options = get();

                    // Remove all existing items
                    geneSelector.removeAllItems();

                    // Add a default option
                    geneSelector.addItem("Select gene to view");

                    // Add each gene name from the list to the JComboBox
                    for (String option : options) {
                        geneSelector.addItem(option);
                    }
                } catch (Exception e) {
                    // Handle exceptions (e.g., file not found, access issues)
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error loading gene names: " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        // Execute the SwingWorker
        worker.execute();
    }

    public static ArrayList<String> getGeneNamesFromFasta(String fastaFilePath) {
        ArrayList<String> geneNames = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fastaFilePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith(">")) {
                    // Extract the gene name from the header line
                    String geneName = getGeneIdFromLine(line);
                    geneNames.add(geneName);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading FASTA file: " + e.getMessage());
        }

        return geneNames;
    }

    private static String getGeneIdFromLine(String line) {
        String[] parts = line.split("\\|");
        return parts[0].substring(1);
    }

    public void setExplorerFastaFilePath(String filePath) {
        fastaFilePath = filePath;
        setGenesAsOptions(filePath);
    }

    public void setParsedExons(Map<String, List<int[]>> parsedExons) {
        parsedExonsFromGtf = parsedExons;
    }

    public static Map<String, Object> extractGeneInfo(
            String geneName,
            String fastaFilePath
    ) {
        Map<String, Object> geneInfo = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fastaFilePath))) {
            String line;
            StringBuilder sequenceBuilder = new StringBuilder();
            boolean isTargetGene = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith(">")) {
                    // If we encounter a new header, process the previous gene
                    if (isTargetGene) {
                        geneInfo.put("gene_sequence", sequenceBuilder.toString());
                        break; // Target gene processed, exit loop
                    }

                    // Check if this is the header for the target gene
                    if (line.contains(geneName)) {
                        isTargetGene = true;
                        sequenceBuilder = new StringBuilder(); // Reset for the new gene

                        // Parse header for chromosome, start, and end positions
                        String[] parts = line.split("\\|");
                        String chromInfo = parts[1]; // Extract "chr21:10521553-10606140"
                        String[] chromParts = chromInfo.split(":");
                        String chromosome = chromParts[0];
                        String[] positions = chromParts[1].split("-");

                        geneInfo.put("chromosome", chromosome);
                        geneInfo.put("start_position", Integer.parseInt(positions[0]));
                        geneInfo.put("end_position", Integer.parseInt(positions[1]));
                    }
                } else if (isTargetGene) {
                    // Append sequence data if we're in the target gene
                    sequenceBuilder.append(line);
                }
            }

            // Handle the case where the target gene is the last in the file
            if (isTargetGene && !geneInfo.containsKey("gene_sequence")) {
                geneInfo.put("gene_sequence", sequenceBuilder.toString());
            }

        } catch (IOException e) {
            System.err.println("Error reading FASTA file: " + e.getMessage());
        }

        return geneInfo;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ExplorePanel = new javax.swing.JPanel();
        geneSelectorPrompt = new javax.swing.JLabel();
        geneSelector = new javax.swing.JComboBox<>();
        showExonsButton = new javax.swing.JButton();
        clearExonsButton = new javax.swing.JButton();
        chromosomeLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        chromosomePane = new javax.swing.JTextPane();
        startPositionLabel = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        startPosPane = new javax.swing.JTextPane();
        endPositionLabel = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        endPosTextPane = new javax.swing.JTextPane();
        geneSequenceLabel = new javax.swing.JLabel();
        visualRepresentationLabel = new javax.swing.JLabel();
        visualizer2 = new GenomeBrowser.Visualizer();
        jScrollPane5 = new javax.swing.JScrollPane();
        geneSequencePane = new javax.swing.JTextPane();
        downloadButton = new javax.swing.JButton();

        geneSelectorPrompt.setText("Select gene to view:");

        geneSelector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        geneSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                geneSelectorActionPerformed(evt);
            }
        });

        showExonsButton.setText("Show Exons");
        showExonsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showExonsButtonActionPerformed(evt);
            }
        });

        clearExonsButton.setText("clear");
        clearExonsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearExonsButtonActionPerformed(evt);
            }
        });

        chromosomeLabel.setText("Chromosome:");

        jScrollPane2.setViewportView(chromosomePane);

        startPositionLabel.setText("Start pos: ");

        jScrollPane3.setViewportView(startPosPane);

        endPositionLabel.setText("End Pos:");

        jScrollPane4.setViewportView(endPosTextPane);

        geneSequenceLabel.setText("Gene sequence");

        visualRepresentationLabel.setText("Visual Representation of selected gene");

        visualizer2.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane5.setViewportView(geneSequencePane);

        downloadButton.setText("Download sequence");
        downloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ExplorePanelLayout = new javax.swing.GroupLayout(ExplorePanel);
        ExplorePanel.setLayout(ExplorePanelLayout);
        ExplorePanelLayout.setHorizontalGroup(
            ExplorePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ExplorePanelLayout.createSequentialGroup()
                .addGroup(ExplorePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ExplorePanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(visualRepresentationLabel))
                    .addGroup(ExplorePanelLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(geneSelectorPrompt, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(geneSelector, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(ExplorePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ExplorePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ExplorePanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane5)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ExplorePanelLayout.createSequentialGroup()
                        .addComponent(chromosomeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(startPositionLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(endPositionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(clearExonsButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(showExonsButton)
                        .addGap(46, 46, 46))
                    .addGroup(ExplorePanelLayout.createSequentialGroup()
                        .addComponent(visualizer2, javax.swing.GroupLayout.PREFERRED_SIZE, 902, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(ExplorePanelLayout.createSequentialGroup()
                        .addComponent(geneSequenceLabel)
                        .addGap(649, 649, 649)
                        .addComponent(downloadButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        ExplorePanelLayout.setVerticalGroup(
            ExplorePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ExplorePanelLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(ExplorePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(geneSelectorPrompt)
                    .addComponent(geneSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ExplorePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(geneSequenceLabel)
                    .addComponent(downloadButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ExplorePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(ExplorePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(startPositionLabel)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(chromosomeLabel)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ExplorePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(clearExonsButton)
                            .addComponent(showExonsButton)))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(endPositionLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(visualRepresentationLabel)
                .addGap(18, 18, 18)
                .addComponent(visualizer2, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ExplorePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(ExplorePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void clearExonsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearExonsButtonActionPerformed
        // TODO add your handling code here:
        geneSequencePane.setText("");
        endPosTextPane.setText("");
        startPosPane.setText("");
        chromosomePane.setText("");
        geneSelector.setSelectedIndex(0);
    }//GEN-LAST:event_clearExonsButtonActionPerformed

    private void geneSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_geneSelectorActionPerformed
        // TODO add your handling code here:
        if (evt.getSource() == geneSelector) {
            JComboBox<String> comboBox = (JComboBox<String>) evt.getSource();
            var selectedGene = (String) comboBox.getSelectedItem();

            if (selectedGene == null) {
                return;
            }

            if (!selectedGene.isEmpty() || !selectedGene.isBlank()) {
                Map<String, Object> geneInfo = extractGeneInfo(
                        selectedGene, fastaFilePath
                );

                if (!geneInfo.isEmpty()) {
                    String sequence = geneInfo.get("gene_sequence").toString();
                    StringBuilder htmlBuilder = new StringBuilder(
                            "<html><body><p style=\"width:500px;\">"
                    );
                    geneSequencePane.setContentType("text/html");
//                    geneSequencePane.setSize(500);
                    htmlBuilder.append(sequence);
                    htmlBuilder.append("</p></body></html>");
                    geneSequencePane.setText(htmlBuilder.toString());
                    endPosTextPane.setText(geneInfo.get("end_position").toString());
                    startPosPane.setText(geneInfo.get("start_position").toString());
                    chromosomePane.setText(geneInfo.get("chromosome").toString());
                    visualizer2.setGeneVisualization(
                            selectedGene,
                            parsedExonsFromGtf,
                            (int) geneInfo.get("start_position"),
                            (int) geneInfo.get("end_position"));
                }
            }
        }

    }//GEN-LAST:event_geneSelectorActionPerformed

    private void showExonsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showExonsButtonActionPerformed
        // TODO add your handling code here:
        String selectedGene = (String) geneSelector.getSelectedItem();
        if (!selectedGene.isEmpty() || !selectedGene.isBlank()) {
            Map<String, Object> geneInfo = extractGeneInfo(selectedGene, fastaFilePath);

            if (!geneInfo.isEmpty()) {

                String sequence = geneInfo.get("gene_sequence").toString();
                Integer startPos = (int) geneInfo.get("start_position");
                var exons = parsedExonsFromGtf.get(selectedGene);
                StringBuilder htmlBuilder = new StringBuilder("<html><body><p style=\"width:500px;\">");
                String highlightedSequence = SequenceViewer.highlightSequence(sequence, startPos, exons);
                geneSequencePane.setContentType("text/html");
                htmlBuilder.append(highlightedSequence);
                htmlBuilder.append("</p></body></html>");
                geneSequencePane.setText(htmlBuilder.toString());
            }
        }

    }//GEN-LAST:event_showExonsButtonActionPerformed

    private void downloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadButtonActionPerformed
        // TODO add your handling code here:
        GenomeBrowser.utilities.TextPaneUtil.downloadTextPaneContent(geneSequencePane, ExplorePanel);
    }//GEN-LAST:event_downloadButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ExplorePanel;
    private javax.swing.JLabel chromosomeLabel;
    private javax.swing.JTextPane chromosomePane;
    private javax.swing.JButton clearExonsButton;
    private javax.swing.JButton downloadButton;
    private javax.swing.JTextPane endPosTextPane;
    private javax.swing.JLabel endPositionLabel;
    private javax.swing.JComboBox<String> geneSelector;
    private javax.swing.JLabel geneSelectorPrompt;
    private javax.swing.JLabel geneSequenceLabel;
    private javax.swing.JTextPane geneSequencePane;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JButton showExonsButton;
    private javax.swing.JTextPane startPosPane;
    private javax.swing.JLabel startPositionLabel;
    private javax.swing.JLabel visualRepresentationLabel;
    private GenomeBrowser.Visualizer visualizer2;
    // End of variables declaration//GEN-END:variables
}
