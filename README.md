# Genomics Viewer Technical Documentation

## By Oluwasusi David  
**Student Number:** 447435  

---

## Table of Contents

1. [Introduction](#introduction)
2. [Features](#features)
3. [Software Design and Structure](#software-design-and-structure)
   - [GTF Parsing](#gtf-parsing)
   - [Error Handling](#error-handling)
   - [GTF Table](#gtf-table)
   - [GTF Table Class Design](#gtf-table-class-design)
   - [Basic Statistics](#basic-statistics)
   - [Flow Charts](#flow-charts)
4. [Fasta File Parsing](#fasta-file-parsing)
   - [Handling Large Files](#handling-large-files)
   - [Exon Mapping](#exon-mapping)
   - [Error Handling](#error-handling-1)
   - [Basic Statistics](#basic-statistics-1)
5. [Sequence Exons Highlighting](#sequence-exons-highlighting)
6. [Individual Gene Selection](#individual-gene-selection)
7. [Exon Visualization](#exon-visualization)
8. [User Manual](#user-manual)
   - [Installation Steps](#installation-steps)
   - [Uploading Fasta and GTF Files](#uploading-fasta-and-gtf-files)
   - [Viewing Details](#viewing-details)
9. [Keynotes](#keynotes)
10. [References](#references)

---

## Introduction
Genomics Viewer is a software tool for understanding DNA sequence information. It visualizes exon positions and provides basic statistics such as the average number of genes, longest and shortest gene models, and gene sequence lengths from FASTA files.

---

## Features
- **GTF and FASTA file loading**: The GTF file is displayed in a table with relevant attributes. The FASTA file is loaded into a text pane and supports large files.
- **Basic statistics calculation**: Extracts statistics from GTF and FASTA files.
- **Gene Exploration**: Allows visualization of individual genes, highlighting exon positions.
- **Download of Highlighted Gene**: Users can download highlighted gene sequences as HTML files.

---

## Software Design and Structure
Genomics Viewer is built using **Java Swing**. It consists of a combination of panels and a single frame.

### GTF Parsing
- The GTF file is uploaded via **JFileChooser**.
- Validation is performed on file extension.
- Data is processed using **OpenCSV**.
- Extracted attributes: **Chromosome, Source, Feature, Start, End, Frame, Attributes**.

### Error Handling
- If an error occurs during parsing, the table remains empty and an error message is displayed.

### GTF Table
- The `parseGtfFile` function creates a **two-dimensional list** of extracted columns.
- Column headers are manually set.
- Data is passed to the **Java Swing TableModel**.
- The table is appended to the **TableViewer panel**.

### GTF Table Class Design
- The `TableViewer` panel provides a **getter method** (`getTablePane`) to allow updates.

### Basic Statistics
Statistics extracted from the GTF file include:
- **Average Exons Per Gene**
- **Longest Gene**
- **Shortest Gene**
- **Average Gene Length**

### Flow Charts
- **Basic Statistics Flow Chart**: Outlines how statistics are extracted.
- **GTF Parsing Flow Chart**: Shows how the file is processed upon upload.

---

## Fasta File Parsing
- The FASTA file is uploaded via **JFileChooser**.
- Validation is performed on file extension.
- The file is processed using **BufferedReader** and **StringBuilder**.
- **Basic statistics are calculated during file reading** to avoid reprocessing.

### Handling Large Files
- Parsing large FASTA files blocks the UI. To prevent this:
  - Processing is moved to a **new thread** using **SwingWorker**.
  - A **Loader UI** is displayed during processing.

### Exon Mapping
- Exons are mapped using gene names as **index keys**.
- **Mappings are created from the GTF file**.
- Drawback: **Not all FASTA files have standard gene headers**.

### Error Handling
- Errors are displayed for:
  - Incorrect file formats (non-`.fa` extension).
  - Corrupt FASTA files.

### Basic Statistics
- **GC Content Percentage**
- **Sequence Length Calculation**

---

## Sequence Exons Highlighting
- **Flow Chart**: Shows how the highlight process is triggered.
- `highlightExons` method handles the highlighting logic.
- `highlightSequence` method processes individual genes.
- Exons are **highlighted in red** and displayed in the text pane.

---

## Individual Gene Selection
- Available in the **Explore Genes tab**.
- Allows selection of specific genes.
- Automatically visualizes the selected gene.

---

## Exon Visualization
- Genes are visualized using a **rectangular representation**.
- Blue stripes denote exon positions.
- Implemented by overriding the **paintComponent** method of `JPanel`.

---

## User Manual

### Installation Steps
To run the **Genomics Viewer**:

#### MacOS
```sh
java -version
```
- If Java is not installed, download it from [Oracle Java](https://www.oracle.com/java/technologies/downloads/).
- Locate `GenomicsViewer.jar`, right-click, and open with **Java Runtime Environment**.

#### Windows
```sh
java -version
```
- If Java is not installed, download it from [Oracle Java](https://www.oracle.com/java/technologies/downloads/).
- Double-click `GenomicsViewer.jar` to run.

#### Linux
```sh
sudo apt install openjdk-17-jre
cd /path/to/file
java -jar GenomicsViewer.jar
```

---

### Uploading Fasta and GTF Files
1. Click **Upload .gtf File** button.
2. Click **Upload .fa File** button.
3. The FASTA header format must be:
   ```
   >GeneName|chr:start-end
   ```

### Viewing Details
#### Overview Tab
- Provides tools for uploading and exploring **GTF** and **FASTA** files.
- Once uploaded, the GTF file data loads into a table.

#### Basic Statistics
- Automatically calculated after successful file uploads:
  - **Average exons per gene**
  - **Longest/Shortest gene**
  - **Average gene length**
  - **GC content percentage**

#### Explore Genes
- Select a gene from the **dropdown menu**.
- Click **Highlight Sequence** to view it annotated.
- Click **Download Sequence** to save the sequence as an **HTML file**.

---

## Keynotes
- The **FASTA file header must follow the required format**.
- The **GTF file must be valid** and contain necessary columns.
- Use **Highlight Exons** for better visualization.
- **Basic statistics require both files to be uploaded**.

---

## References
- OpenCSV. (n.d.). *CSVReader (OpenCSV 4.0)*. [Documentation](https://javadoc.io/doc/com.opencsv/opencsv/4.0/com/opencsv/CSVReader.html)
- Oracle. (n.d.). *Creating a GUI with Swing*. [Oracle Docs](https://docs.oracle.com/javase/tutorial/uiswing/)

---
