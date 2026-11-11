package table;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;


public class SlotMachine extends JPanel {

    private JLabel slot1, slot2, slot3;
    private JButton spinButton;
    private Random random;

    public SlotMachine() {
        setLayout(new BorderLayout(20, 50));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(50, 100, 100, 100));
        random = new Random();
        
        JLabel titleLabel = new JLabel("SLOT MACHINE", SwingConstants.CENTER);
        titleLabel.setFont(new Font(Font.SERIF, Font.BOLD, 54));
        titleLabel.setForeground(new Color(0, 150, 0)); // Verde casino
        
        JPanel numbersPanel = new JPanel(new GridLayout(1, 3, 30, 0));
        numbersPanel.setOpaque(false);

        Font fontNumbers = new Font(Font.SANS_SERIF, Font.BOLD, 120);
        Color verdeCasino = new Color(0, 150, 0);

        slot1 = crearCasilla("7", fontNumbers, verdeCasino);
        slot2 = crearCasilla("7", fontNumbers, verdeCasino);
        slot3 = crearCasilla("7", fontNumbers, verdeCasino);

        numbersPanel.add(slot1);
        numbersPanel.add(slot2);
        numbersPanel.add(slot3);

        spinButton = new JButton("SPIN");
        spinButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 36));
        spinButton.setFocusPainted(false);
        spinButton.addActionListener(e -> tirarDeLaPalanca());

        add(titleLabel, BorderLayout.NORTH);
        add(numbersPanel, BorderLayout.CENTER);
        add(spinButton, BorderLayout.SOUTH);
    }

    private JLabel crearCasilla(String texto, Font fuente, Color color) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setFont(fuente);
        label.setForeground(color);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        return label;
    }

    private void tirarDeLaPalanca() {
        spinButton.setEnabled(false);

        Thread hiloSpin = new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                
                String n1 = String.valueOf(random.nextInt(10));
                String n2 = String.valueOf(random.nextInt(10));
                String n3 = String.valueOf(random.nextInt(10));

                SwingUtilities.invokeLater(() -> {
                    slot1.setText(n1);
                    slot2.setText(n2);
                    slot3.setText(n3);
                });

                try {
                    Thread.sleep(50); 
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            SwingUtilities.invokeLater(() -> {
                spinButton.setEnabled(true);
            });
        });

        hiloSpin.start();
    }
}