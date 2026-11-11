package main; // Debe coincidir exactamente con la primera línea de tu clase Window

import javax.swing.SwingUtilities;

public class Main_Bj {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Window window = new Window();
            
            // Carga directamente la pantalla de Blackjack sin pasar por el menú
            window.mostrarBlackjack();
        });
    }
}