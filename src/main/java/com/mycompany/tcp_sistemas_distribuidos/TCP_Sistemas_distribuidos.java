package com.mycompany.tcp_sistemas_distribuidos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.nio.file.Files;

public class TCP_Sistemas_distribuidos extends JFrame {

    private JTextArea textArea;
    private JButton btnDownload;
    private JButton btnView;
    private String filePath;

    public TCP_Sistemas_distribuidos() {
        setTitle("File Transfer App");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        btnDownload = new JButton("Descargar Archivo");
        btnView = new JButton("Ver Archivo");

        JPanel panel = new JPanel();
        panel.add(btnView);
        panel.add(btnDownload);

        add(new JScrollPane(textArea), BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        btnView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewFile();
            }
        });

        btnDownload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                downloadFile();
            }
        });
    }

    private void viewFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            filePath = file.getAbsolutePath();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                textArea.read(reader, null);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void downloadFile() {
        if (filePath == null || filePath.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Primero visualiza un archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar archivo");

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try {
                Files.copy(new File(filePath).toPath(), fileToSave.toPath());
                JOptionPane.showMessageDialog(this, "Archivo guardado exitosamente.");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TCP_Sistemas_distribuidos().setVisible(true);
            }
        });
    }
}