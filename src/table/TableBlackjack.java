package table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.Window; // Asegúrate de importar tu clase Window

public class TableBlackjack extends JPanel {

	private Window window; // Cambiado a Window en lugar de JFrame genérico
	private static final long serialVersionUID = 1L;
	private JLabel lblTitulo;
	private JButton btnVolverMenu;
	
	private JPanel panelRepartidor;
	private JPanel panelJugador;
	
	private JLabel lblApuesta;
	private JLabel lblFichas;
	
	private JButton btnFicha20;
	private JButton btnFicha50;
	private JButton btnFicha100;
	private JButton btnPedir;
	private JButton btnPlantarse;

	public TableBlackjack(Window window) {
		this.window = window;
		
		setLayout(new BorderLayout());

		JPanel panelSuperior = new JPanel(new BorderLayout());
		panelSuperior.setBackground(new Color(20, 80, 40)); 

		lblTitulo = new JLabel("BLACKJACK", SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 40));
		lblTitulo.setForeground(Color.RED.darker());

		btnVolverMenu = new JButton("Volver al Menu");
		btnVolverMenu.addActionListener(e -> this.window.mostrarMenu());
		
		panelSuperior.add(btnVolverMenu, BorderLayout.WEST);
		panelSuperior.add(lblTitulo, BorderLayout.CENTER);

		JPanel panelCentro = new JPanel(new GridLayout(2, 1));
		panelCentro.setBackground(new Color(35, 120, 60)); 

		panelRepartidor = new JPanel();
		panelRepartidor.setOpaque(false); 
		panelRepartidor.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.WHITE), "Cartas del Repartidor", 0, 0, null, Color.WHITE));

		panelJugador = new JPanel();
		panelJugador.setOpaque(false);
		panelJugador.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.WHITE), "Tus Cartas", 0, 0, null, Color.WHITE));

		panelCentro.add(panelRepartidor);
		panelCentro.add(panelJugador);

		JPanel panelInferior = new JPanel(new GridLayout(2, 1));
		panelInferior.setBackground(new Color(20, 80, 40));

		JPanel panelInfoApuesta = new JPanel();
		panelInfoApuesta.setOpaque(false);
		lblFichas = new JLabel("Fichas: 0€   | ");
		lblFichas.setForeground(Color.WHITE);
		lblApuesta = new JLabel("Apuesta actual: 0€");
		lblApuesta.setForeground(Color.WHITE);
		panelInfoApuesta.add(lblFichas);
		panelInfoApuesta.add(lblApuesta);

		JPanel panelBotones = new JPanel();
		panelBotones.setOpaque(false);
		panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));

		ImageIcon iconoFicha20 = cargarIconoEscalado("res/PokerChip_20.png", 100, 70);
		btnFicha20 = new JButton(iconoFicha20);
		btnFicha20.setBorderPainted(false);
		btnFicha20.setContentAreaFilled(false);
		btnFicha20.setFocusPainted(false);
		
		JLabel lblValor20 = new JLabel("20€");
		lblValor20.setForeground(Color.GREEN.darker());
		lblValor20.setFont(new Font("Arial", Font.BOLD, 14));

		ImageIcon iconoFicha50 = cargarIconoEscalado("res/PokerChip_50.png", 100, 70);
		btnFicha50 = new JButton(iconoFicha50);
		btnFicha50.setBorderPainted(false);
		btnFicha50.setContentAreaFilled(false);
		btnFicha50.setFocusPainted(false);
		
		JLabel lblValor50 = new JLabel("50€");
		lblValor50.setForeground(Color.RED);
		lblValor50.setFont(new Font("Arial", Font.BOLD, 14));

		ImageIcon iconoFicha100 = cargarIconoEscalado("res/PokerChip_100.png", 100, 70);
		btnFicha100 = new JButton(iconoFicha100);
		btnFicha100.setBorderPainted(false);
		btnFicha100.setContentAreaFilled(false);
		btnFicha100.setFocusPainted(false);
		
		JLabel lblValor100 = new JLabel("100€");
		lblValor100.setForeground(Color.BLACK.brighter());
		lblValor100.setFont(new Font("Arial", Font.BOLD, 14));

		btnPedir = new JButton("Pedir Carta");
		btnPlantarse = new JButton("Plantarse");

		panelBotones.add(btnFicha20);
		panelBotones.add(lblValor20);
		panelBotones.add(Box.createHorizontalStrut(10));
		
		panelBotones.add(btnFicha50);
		panelBotones.add(lblValor50);
		panelBotones.add(Box.createHorizontalStrut(10));
		
		panelBotones.add(btnFicha100);
		panelBotones.add(lblValor100);
		
		panelBotones.add(Box.createHorizontalStrut(30));
		panelBotones.add(btnPedir);
		panelBotones.add(btnPlantarse);

		panelInferior.add(panelInfoApuesta);
		panelInferior.add(panelBotones);

		add(panelSuperior, BorderLayout.NORTH);
		add(panelCentro, BorderLayout.CENTER);
		add(panelInferior, BorderLayout.SOUTH);
	}
	
	//auxiliar para el tamaño de las imagenes (IA)
	private ImageIcon cargarIconoEscalado(String ruta, int ancho, int alto) {
	    ImageIcon iconoOriginal = new ImageIcon(ruta);
	    // Si la imagen existe y se carga correctamente
	    if (iconoOriginal.getIconWidth() > 0) {
	        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
	        return new ImageIcon(imagenEscalada);
	    }
	    return iconoOriginal;
	}
	
	
	
	public Window getWindow() {
		return this.window;
	}
}