/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GenomeBrowser;

import javax.swing.JTable;

/**
 *
 * @author davidoluwasusi
 */
public class TableViewer extends javax.swing.JPanel {
    
    



    /**
     * Creates new form TableViewer
     */
    public TableViewer() {
        initComponents();
    }
    
    public JTable getTablePane() {
        return tablePane;
    }
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tablePaneTitle = new javax.swing.JLabel();
        tablePaneScrollPanel = new javax.swing.JScrollPane();
        tablePane = new javax.swing.JTable();

        setAutoscrolls(true);

        tablePaneTitle.setText("Annotation File");

        tablePane.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Chromosome", "Source", "Feature", "Start", "End", "Score", "Frame", "Attributes"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablePaneScrollPanel.setViewportView(tablePane);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tablePaneTitle)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(tablePaneScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tablePaneTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tablePaneScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable tablePane;
    private javax.swing.JScrollPane tablePaneScrollPanel;
    private javax.swing.JLabel tablePaneTitle;
    // End of variables declaration//GEN-END:variables
}
