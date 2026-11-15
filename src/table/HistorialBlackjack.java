package table;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class HistorialBlackjack extends JDialog {

    private static ArrayList<PartidaBlackjack> listaPartidas = new ArrayList<>();

    private DefaultTableModel modeloTabla;
    private JLabel lblTotal;
    private JLabel lblVictorias;
    private JLabel lblBalance;

    public HistorialBlackjack() {
    		setTitle("Historial de Blackjack");
        setModal(true);
        setSize(650, 420);
        setLocationRelativeTo(null); 
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(20, 60, 35));

        JPanel panelStats = new JPanel(new GridLayout(1, 3, 10, 10));
        panelStats.setBackground(new Color(15, 45, 25));

        lblTotal = crearLabelStat("Partidas: 0");
        lblVictorias = crearLabelStat("Victorias: 0");
        lblBalance = crearLabelStat("Balance: 0 €");

        panelStats.add(lblTotal);
        panelStats.add(lblVictorias);
        panelStats.add(lblBalance);
        add(panelStats, BorderLayout.NORTH);


        String[] columnas = {"Ronda", "Apuesta", "Tus Puntos", "Crupier", "Resultado", "Ganancia"};
        modeloTabla = new DefaultTableModel(columnas, 0);

        JTable tabla = new JTable(modeloTabla);
        tabla.setRowHeight(25);
        tabla.setEnabled(false); // Para que no se puedan editar las celdas haciendo doble clic

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setOpaque(false);
        
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        


        JButton btnBorrar = new JButton("Borrar historial");
        btnBorrar.addActionListener(e -> {
            listaPartidas.clear();
            cargarDatos();
        });

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());

        panelBotones.add(btnBorrar);
        panelBotones.add(btnCerrar);
        add(panelBotones, BorderLayout.SOUTH);

        cargarDatos();
    }



    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        int victorias = 0;
        int balanceTotal = 0;

        for (PartidaBlackjack p : listaPartidas) {
            if (p.getResultado().equals("VICTORIA")) {
                victorias++;
            }
            balanceTotal += p.getGanancia();

            Object[] fila = {
                p.getRonda(),
                p.getApuesta() + " €",
                p.getPuntosJugador(),
                p.getPuntosCrupier(),
                p.getResultado(),
                p.getGanancia() + " €"
            };
            modeloTabla.addRow(fila);
        }

        lblTotal.setText("Partidas: " + listaPartidas.size());
        lblVictorias.setText("Victorias: " + victorias);
        lblBalance.setText("Balance: " + balanceTotal + " €");
    }

    private JLabel crearLabelStat(String texto) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setForeground(new Color(230, 190, 60));
        label.setFont(new Font("SansSerif", Font.BOLD, 15));
        label.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        return label;
    }
    
    // guardar partida
    public static void guardarPartida(int apuesta, int puntosJugador, int puntosCrupier, String resultado, int ganancia) {
        int numeroRonda = listaPartidas.size() + 1;
        PartidaBlackjack nueva = new PartidaBlackjack(numeroRonda, apuesta, puntosJugador, puntosCrupier, resultado, ganancia);
        listaPartidas.add(nueva);
    }
}