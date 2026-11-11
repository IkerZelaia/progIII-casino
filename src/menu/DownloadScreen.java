package menu;

import javax.swing.*;
import java.awt.*;
import table.TablePoker;

public class DownloadScreen extends JFrame {

    private JPanel loadingPanel;
    private JProgressBar progressBar;
    private JLabel statusLabel;
    
    private MainMenu mainMenu; 

    public DownloadScreen() {
        setTitle("Casino Deusto - Launcher");
        setSize(900, 600); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ==========================================
        // 1. CONSTRUIR EL PANEL DE CARGA (Reutilizable)
        // ==========================================
        loadingPanel = new JPanel(new BorderLayout(0, 20));
        loadingPanel.setBackground(Color.WHITE);
        loadingPanel.setBorder(BorderFactory.createEmptyBorder(220, 150, 220, 150));

        Color verdeCasino = new Color(0, 150, 0);

        statusLabel = new JLabel("Downloading :", SwingConstants.LEFT);
        statusLabel.setForeground(verdeCasino); 
        statusLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(false);
        progressBar.setForeground(verdeCasino); 
        progressBar.setBackground(Color.WHITE); 
        progressBar.setBorder(BorderFactory.createLineBorder(verdeCasino, 2)); 

        loadingPanel.add(statusLabel, BorderLayout.NORTH);
        loadingPanel.add(progressBar, BorderLayout.CENTER);

        // ==========================================
        // 2. INICIALIZAR EL MENÚ PRINCIPAL Y ENLAZAR
        // ==========================================
        mainMenu = new MainMenu();

        mainMenu.anadirJuego("Poker", () -> {
            // Cuando hacen clic en Poker, instanciamos el juego...
            TablePoker panelPoker = new TablePoker(4, this);
            // ...y llamamos a nuestro método reutilizable pasándole el juego
            iniciarPantallaDeCarga(panelPoker);
        });

        // ==========================================
        // 3. CARGA INICIAL (Al arrancar la app)
        // ==========================================
        // Llamamos al mismo método para que cargue el Menú Principal
        iniciarPantallaDeCarga(mainMenu);
    }

    // ==========================================
    // MÉTODO REUTILIZABLE CON HILOS CONCURRENTES
    // ==========================================
    private void iniciarPantallaDeCarga(JPanel panelDestino) {
        // 1. Limpiamos la ventana y ponemos el panel de carga a cero
        getContentPane().removeAll();
        progressBar.setValue(0);
        add(loadingPanel);
        revalidate();
        repaint();

        // 2. Creamos y lanzamos el hilo concurrente
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

            // 3. Transición final hacia el panel que nos hayan pasado por parámetro
            SwingUtilities.invokeLater(() -> {
                getContentPane().removeAll(); 
                add(panelDestino);        
                revalidate();         
                repaint();            
            });
        });

        downloadThread.start(); 
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DownloadScreen().setVisible(true);
        });
    }
}