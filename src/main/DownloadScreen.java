package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class DownloadScreen extends JPanel { 

    private JProgressBar progressBar;

    public DownloadScreen(Window window, JPanel panelDestino) {
        setLayout(new BorderLayout(0, 20));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(220, 150, 220, 150));

        Color verdeCasino = new Color(0, 150, 0);

        JLabel statusLabel = new JLabel("Downloading :", SwingConstants.LEFT);
        statusLabel.setForeground(verdeCasino); 
        statusLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(false);
        progressBar.setForeground(verdeCasino); 
        progressBar.setBackground(Color.WHITE); 
        progressBar.setBorder(BorderFactory.createLineBorder(verdeCasino, 2)); 

        add(statusLabel, BorderLayout.NORTH);
        add(progressBar, BorderLayout.CENTER);

        iniciarHilo(window, panelDestino);
    }

    private void iniciarHilo(Window window, JPanel panelDestino) {
        Thread downloadThread = new Thread(() -> {
            for (int i = 0; i <= 100; i++) {
                final int currentProgress = i;

                SwingUtilities.invokeLater(() -> {
                    progressBar.setValue(currentProgress);
                });

                try {
                    Thread.sleep(30); 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

 
            SwingUtilities.invokeLater(() -> {
                window.cambiarPanel(panelDestino);
            });
        });

        downloadThread.start(); 
    }
}