package com.anonymizer.layout;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Home {
    private JPanel painel;
    private JButton openDatasetButton;
    private JButton button2;
    private JTable datasetTable;
    private JTable anonimizedDatasetTable;
    private JScrollPane datasetPane;
    private JScrollPane attributesPanel;
    private JCheckBox dataFlyCheckbox;
    private JCheckBox incognitoCheckBox;
    private JCheckBox mondrianCheckBox;
    private JButton anonymizeButton;

    public Home() {
        openDatasetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel dtm = (DefaultTableModel) datasetTable.getModel();
                dtm.setRowCount(0);
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Arquivos CSV", "csv");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(null);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    String filePath = chooser.getSelectedFile().getPath();
                    File file = new File(filePath);
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        // get the first line
                        // get the columns name from the first line
                        // set columns name to the jtable model
                        String firstLine = br.readLine().trim();
                        String[] columnsName = firstLine.split(",");
                        addLabelsToAttributePanel(columnsName);
                        DefaultTableModel model = (DefaultTableModel)datasetTable.getModel();
                        model.setColumnIdentifiers(columnsName);
                        for (int i = 0;i < datasetTable.getColumnCount();i++){
                            datasetTable.getColumnModel().getColumn(i).setMinWidth(100);
                        }
                        // get lines from txt file
                        Object[] tableLines = br.lines().toArray();

                        // extratct data from lines
                        // set data to jtable model
                        for(int i = 0; i < tableLines.length; i++)
                        {
                            String line = tableLines[i].toString().trim();
                            String[] dataRow = line.split(",");
                            model.addRow(dataRow);
                        }

                    } catch (Exception ex) {
                        Logger.getLogger("Teste").log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Home");
        frame.setContentPane(new Home().painel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void addLabelsToAttributePanel(String[] attribNames){

        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        for(String attr : attribNames){
            JLabel label = new JLabel(attr);
            mainPanel.add(label);
        }
        mainPanel.setPreferredSize(new Dimension(mainPanel.getWidth(), mainPanel.getHeight()));
        attributesPanel.setPreferredSize(new Dimension(250,400));
        attributesPanel.setViewportView(mainPanel);
    }
}
