package table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import main.Window; 

public class SlotMachine extends JPanel {

    // Constantes de colores extraídas de TableBlackjack para mantener la armonía
    private static final Color COLOR_TAPETE = new Color(25, 105, 52);
    private static final Color COLOR_BARRA = new Color(15, 65, 32);
    private static final Color COLOR_DORADO = new Color(212, 175, 55);
    private static final Color COLOR_BURDEOS = new Color(110, 15, 30);
    private static final Color COLOR_BURDEOS_HOVER = new Color(145, 25, 45);
    private static final Color COLOR_ACCION = new Color(0, 100, 0);
    private static final Color COLOR_ACCION_HOVER = new Color(0, 135, 0);

    
    private JLabel slot1, slot2, slot3;
    private JButton spinButton;
    private Random random;
    private Window window; 

    private ImageIcon[] arrayImagenes;

    public SlotMachine(Window window) {
        this.window = window;
        
        setLayout(new BorderLayout(20, 50));
        
       
        setBackground(COLOR_TAPETE);
        setBorder(BorderFactory.createEmptyBorder(50, 100, 100, 100));
        random = new Random();
        
        
        String[] rutasImagenes = {
            "res/figura1.jpg", 
            "res/figura2.jpg", 
            "res/figura3.jpg", 
            "res/figura4.jpg"
        };
        
        arrayImagenes = new ImageIcon[rutasImagenes.length];
        for (int i = 0; i < rutasImagenes.length; i++) {
            arrayImagenes[i] = cargarYRedimensionarImagen(rutasImagenes[i], 120, 120);
        }

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false); 

        JButton backButton = new JButton("Volver al Menu");
        estilizarBoton(backButton, COLOR_BURDEOS, COLOR_BURDEOS_HOVER);
        backButton.addActionListener(e -> window.mostrarMenu());

        JLabel titleLabel = new JLabel("SLOT MACHINE", SwingConstants.CENTER);
        titleLabel.setFont(new Font(Font.SERIF, Font.BOLD, 54));
        titleLabel.setForeground(COLOR_DORADO); 
        
        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        
        JPanel numbersPanel = new JPanel(new GridLayout(1, 3, 30, 0));
        numbersPanel.setOpaque(false);

        slot1 = crearCasillaImagen(arrayImagenes[0]);
        slot2 = crearCasillaImagen(arrayImagenes[0]);
        slot3 = crearCasillaImagen(arrayImagenes[0]);

        numbersPanel.add(slot1);
        numbersPanel.add(slot2);
        numbersPanel.add(slot3);

        spinButton = new JButton("SPIN");
        spinButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 36));
        estilizarBoton(spinButton, COLOR_ACCION, COLOR_ACCION_HOVER);
        spinButton.addActionListener(e -> tirarDeLaPalanca());

        add(topPanel, BorderLayout.NORTH); 
        add(numbersPanel, BorderLayout.CENTER);
        add(spinButton, BorderLayout.SOUTH);
    }

    
    private JLabel crearCasillaImagen(ImageIcon iconoInicial) {
        JLabel label = new JLabel(iconoInicial, SwingConstants.CENTER);
        label.setBorder(BorderFactory.createLineBorder(COLOR_DORADO, 8));
        
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        
        return label;
    }

    private void estilizarBoton(JButton boton, Color colorFondo, Color colorHover) {
        boton.setFont(new Font(Font.SERIF, Font.BOLD, 17));
        boton.setForeground(Color.WHITE);
        boton.setBackground(colorFondo);
        boton.setFocusPainted(false);
        boton.setOpaque(true);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_DORADO, 2),
                BorderFactory.createEmptyBorder(8, 18, 8, 18)
        ));

        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (boton.isEnabled()) {
                    boton.setBackground(colorHover);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (boton.isEnabled()) {
                    boton.setBackground(colorFondo);
                }
            }
        });
    }
// copiado de blackjack
    private ImageIcon cargarYRedimensionarImagen(String ruta, int width, int height) {
        java.io.File archivoImagen = new java.io.File(ruta);
        
        if (archivoImagen.exists()) {
            ImageIcon iconoOriginal = new ImageIcon(ruta);
            Image imagenModificada = iconoOriginal.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(imagenModificada);
        } else {
            System.err.println("No se encontró la imagen: " + ruta + " - Revisa la carpeta res.");
            return new ImageIcon(); 
        }
    }

    private void tirarDeLaPalanca() {
        spinButton.setEnabled(false);
        spinButton.setBackground(Color.GRAY);

        Thread hiloSpin = new Thread(() -> {
            ImageIcon img1 = null, img2 = null, img3 = null;
            
            for (int i = 0; i < 30; i++) {
                img1 = arrayImagenes[random.nextInt(arrayImagenes.length)];
                img2 = arrayImagenes[random.nextInt(arrayImagenes.length)];
                img3 = arrayImagenes[random.nextInt(arrayImagenes.length)];

                ImageIcon finalImg1 = img1;
                ImageIcon finalImg2 = img2;
                ImageIcon finalImg3 = img3;

                SwingUtilities.invokeLater(() -> {
                    slot1.setIcon(finalImg1);
                    slot2.setIcon(finalImg2);
                    slot3.setIcon(finalImg3);
                });

                try {
                    Thread.sleep(50); 
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            ImageIcon res1 = img1;
            ImageIcon res2 = img2;
            ImageIcon res3 = img3;

            SwingUtilities.invokeLater(() -> {
                spinButton.setEnabled(true);
                spinButton.setBackground(COLOR_ACCION);
                
                if (res1 == res2 && res2 == res3) {
                    JOptionPane.showMessageDialog(
                        SlotMachine.this, 
                        "¡JACKPOT! Has ganado el premio mayor.", 
                        "¡PREMIO!", 
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }
            });
        });

        hiloSpin.start();
    }
}