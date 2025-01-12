/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GenomeBrowser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.filechooser.*;

/**
 *
 * @author davidoluwasusi
 */
public class UploadButtons extends JPanel implements ActionListener {

    private JLabel l;
    private JLabel statusLabel;
    private JButton gtfButton;
    private JButton fastaButton;
    
     private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
//        button.setPreferredSize(new Dimension(150, 50));
//        button.setFont(new Font("Arial", Font.PLAIN, 14));
//        button.setFocusPainted(false);
//        button.setBackground(new Color(240, 240, 240)); // Light gray
//        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
//
//        // Add an icon to the button
////        ImageIcon icon = new ImageIcon(getClass().getResource("/path/to/upload_icon.png")); // Replace with the correct path
////        button.setIcon(icon);
//        button.setHorizontalTextPosition(SwingConstants.CENTER);
//        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        return button;
    }

    public UploadButtons() {
        // Initialize the buttons and label
           // Create a panel for the buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        gtfButton = createStyledButton("upload .gtf file");
        gtfButton.setActionCommand("GTF_UPLOAD");

        gtfButton.addActionListener(this);
        
        fastaButton = createStyledButton("upload .fasta file");
        fastaButton.setActionCommand("FASTA_UPLOAD");
        fastaButton.addActionListener(this);

        buttonPanel.add(gtfButton);
        buttonPanel.add(fastaButton);
        
        statusLabel = new JLabel("ONLY GTF AND FASTA FILES ARE SUPPORTED");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        statusLabel.setHorizontalAlignment(JLabel.CENTER);

        // Add action listeners
        gtfButton.addActionListener(this);
        fastaButton.addActionListener(this);

        // Add components to the panel
        this.add(buttonPanel, BorderLayout.CENTER);
        this.add(statusLabel, BorderLayout.SOUTH);
//        this.add(l);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        String com = evt.getActionCommand();

        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
  

        int result;
        
        result = fileChooser.showOpenDialog(null);
        

        if (result == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        String fileName = selectedFile.getName().toLowerCase();

        // Validate file extension
        if (fileName.endsWith(".gtf") && com.contains(".gtf")) {
            statusLabel.setText("Selected GTF file: " + selectedFile.getName());
        } else if (fileName.endsWith(".fasta") && com.contains(".fasta")) {
            statusLabel.setText("Selected FASTA file: " + selectedFile.getName());
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Invalid file type. Please select a valid .gtf or .fasta file.",
                "File Selection Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    } else {
        statusLabel.setText("No file selected.");
    }
    }
}


