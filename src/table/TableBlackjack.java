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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import card.Card;
import card.Deck;

import main.Window; 

public class TableBlackjack extends JPanel {

	private static final Color COLOR_TAPETE = new Color(25, 105, 52);
	private static final Color COLOR_BARRA = new Color(15, 65, 32);
	private static final Color COLOR_DORADO = new Color(212, 175, 55);
	private static final Color COLOR_BURDEOS = new Color(110, 15, 30);
	private static final Color COLOR_BURDEOS_HOVER = new Color(145, 25, 45);
	private static final Color COLOR_ACCION = new Color(0, 100, 0);
	private static final Color COLOR_ACCION_HOVER = new Color(0, 135, 0);
	
	
	
	
	private Window window; 
	private static final long serialVersionUID = 1L;
	private JLabel lblTitulo;
	private JButton btnVolverMenu;
	private JButton btnHistorial;
	
	private JPanel panelRepartidor;
	private JPanel panelJugador;
	
	private JLabel lblApuesta;
	private JLabel lblFichas;
	
	private JButton btnFicha20;
	private JButton btnFicha50;
	private JButton btnFicha100;
	private JButton btnPedir;
	private JButton btnPlantarse;
	
	//
	private int saldo = 1000;
	private int apuestaActual = 0;
	
	//barajas
	private Deck baraja;
	private List<Card> manoJugador = new ArrayList<>();
	private List<Card> manoRepartidor = new ArrayList<>();

	private JButton btnRepartir;

	public TableBlackjack(Window window) {
		this.window = window;
		
		setLayout(new BorderLayout());

		JPanel panelSuperior = new JPanel(new BorderLayout());
		panelSuperior.setBackground(COLOR_BARRA); 
		panelSuperior.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 2, 0, COLOR_DORADO),
				BorderFactory.createEmptyBorder(10, 15, 10, 15)
		));

		lblTitulo = new JLabel("♠  BLACKJACK  ♦", SwingConstants.CENTER);
		lblTitulo.setFont(new Font(Font.SERIF, Font.BOLD, 34));
		lblTitulo.setForeground(COLOR_DORADO);
		

		btnVolverMenu = new JButton("Volver al Menu");
		estilizarBoton(btnVolverMenu, COLOR_BURDEOS, COLOR_BURDEOS_HOVER);
		btnVolverMenu.addActionListener(e -> this.window.mostrarMenu());
		
		panelSuperior.add(btnVolverMenu, BorderLayout.WEST);
		panelSuperior.add(lblTitulo, BorderLayout.CENTER);
		panelSuperior.add(Box.createHorizontalStrut(btnVolverMenu.getPreferredSize().width), BorderLayout.EAST);
		
		btnHistorial = new JButton("Historial");
        estilizarBoton(btnHistorial, COLOR_ACCION, COLOR_ACCION_HOVER);
        btnHistorial.addActionListener(e -> new HistorialBlackjack().setVisible(true));

        panelSuperior.add(btnVolverMenu, BorderLayout.WEST);
        panelSuperior.add(lblTitulo, BorderLayout.CENTER);
        panelSuperior.add(btnHistorial, BorderLayout.EAST);

		JPanel panelCentro = new JPanel(new BorderLayout(0, 10));
		panelCentro.setBackground(COLOR_TAPETE);
		panelCentro.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

		JPanel zonaCartas = new JPanel(new GridLayout(2, 1, 0, 15));
		zonaCartas.setOpaque(false);

		panelRepartidor = crearZonaCartas("CARTAS DEL REPARTIDOR");
		panelJugador = crearZonaCartas("TUS CARTAS");

		zonaCartas.add(panelRepartidor);
		zonaCartas.add(panelJugador);
		
		

		panelCentro.add(zonaCartas, BorderLayout.CENTER);

		
		
		JPanel panelInferior = new JPanel(new GridLayout(2, 1));
		panelInferior.setBackground(COLOR_BARRA);
		panelInferior.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(2, 0, 0, 0, COLOR_DORADO),
				BorderFactory.createEmptyBorder(8, 15, 10, 15)
		));
		

		JPanel panelInfoApuesta = new JPanel(new FlowLayout(FlowLayout.CENTER, 35, 4));
		panelInfoApuesta.setOpaque(false);
		
		Font fuenteMarcador = new Font(Font.SERIF, Font.BOLD, 20);
		
		lblFichas = new JLabel();
		lblFichas.setFont(fuenteMarcador);
		lblFichas.setForeground(Color.WHITE);
		lblApuesta = new JLabel();
		lblApuesta.setForeground(Color.WHITE);
		lblApuesta.setFont(fuenteMarcador);

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
		
		Font fuenteValorFicha = new Font(Font.SERIF, Font.BOLD, 16);
		
		
		JLabel lblValor20 = new JLabel("20€");
		lblValor20.setForeground(Color.white);
		lblValor20.setFont(fuenteValorFicha);

		ImageIcon iconoFicha50 = cargarIconoEscalado("res/PokerChip_50.png", 100, 70);
		btnFicha50 = new JButton(iconoFicha50);
		btnFicha50.setBorderPainted(false);
		btnFicha50.setContentAreaFilled(false);
		btnFicha50.setFocusPainted(false);
		
		JLabel lblValor50 = new JLabel("50€");
		lblValor50.setForeground(Color.WHITE);
		lblValor50.setFont(fuenteValorFicha);

		ImageIcon iconoFicha100 = cargarIconoEscalado("res/PokerChip_100.png", 100, 70);
		btnFicha100 = new JButton(iconoFicha100);
		btnFicha100.setBorderPainted(false);
		btnFicha100.setContentAreaFilled(false);
		btnFicha100.setFocusPainted(false);
		
		JLabel lblValor100 = new JLabel("100€");
		lblValor100.setForeground(Color.white);
		lblValor100.setFont(fuenteValorFicha);

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
		
		
		btnRepartir = new JButton("Repartir");

		panelBotones.add(Box.createHorizontalStrut(30));
		panelBotones.add(btnRepartir);
		
		//estilo botones
		
		estilizarBoton(btnRepartir, COLOR_ACCION, COLOR_ACCION_HOVER);
		estilizarBoton(btnPedir, COLOR_ACCION, COLOR_ACCION_HOVER);
		estilizarBoton(btnPlantarse, COLOR_BURDEOS, COLOR_BURDEOS_HOVER);
				

		// llaman a metodo apostar al hacer clic
		btnFicha20.addActionListener(e -> apostar(20));
		btnFicha50.addActionListener(e -> apostar(50));
		btnFicha100.addActionListener(e -> apostar(100));

		// Evento de Repartir (cambia de fase)
		btnRepartir.addActionListener(e -> iniciarRonda());
		btnPedir.addActionListener(e -> pedirCarta());
		btnPlantarse.addActionListener(e -> plantarse());
		
		

		// Dejamos la mesa preparada en "Fase de Apuesta"
		actualizarTextosDinero();
		configurarFaseApuesta(true);
		
		
	}
	
	// mejorar estetica de botones
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
	
	
	//auxiliar para el tamaño de las imagenes
	private ImageIcon cargarIconoEscalado(String ruta, int ancho, int alto) {
	    ImageIcon iconoOriginal = new ImageIcon(ruta);
	    // Si la imagen existe y se carga correctamente
	    if (iconoOriginal.getIconWidth() > 0) {
	        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
	        return new ImageIcon(imagenEscalada);
	    }
	    return iconoOriginal;
	}
	// cambiar estetica de la zona de cartas
	private JPanel crearZonaCartas(String titulo) {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
		panel.setOpaque(false);

		TitledBorder borde = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(new Color(255, 255, 255, 140), 1),
				"  " + titulo + "  "
		);
		borde.setTitleFont(new Font(Font.SERIF, Font.BOLD, 16));
		borde.setTitleColor(COLOR_DORADO);
		panel.setBorder(borde);

		return panel;
	}
	
	
	
	// sumar dinero a la apuesta si tiene saldo
	private void apostar(int cantidad) {
	    if (saldo >= cantidad) {
	        saldo -= cantidad;
	        apuestaActual += cantidad;
	        actualizarTextosDinero();
	    } else {
	        JOptionPane.showMessageDialog(this, "No tienes suficiente saldo para esa ficha.", "Saldo insuficiente", JOptionPane.WARNING_MESSAGE);
	    }
	}

	// actualiza etiqueetas de saldo
	private void actualizarTextosDinero() {
	    lblFichas.setText("Fichas: " + saldo + "€");
	    lblApuesta.setText("Apuesta actual: " + apuestaActual + "€");
	}

	// controla que botones son utilizables
	private void configurarFaseApuesta(boolean esFaseApuesta) {
	    // si estamos apostando se activan fichas y repartir
	    btnFicha20.setEnabled(esFaseApuesta);
	    btnFicha50.setEnabled(esFaseApuesta);
	    btnFicha100.setEnabled(esFaseApuesta);
	    btnRepartir.setEnabled(esFaseApuesta);

	    // si no estamos apostando activa pedir y plantarse
	    btnPedir.setEnabled(!esFaseApuesta);
	    btnPlantarse.setEnabled(!esFaseApuesta);
	}
	
	private void iniciarRonda() {
		if (apuestaActual == 0) {
			JOptionPane.showMessageDialog(this, "Debes apostar al menos 20€ antes de repartir.",
					"Sin apuesta", JOptionPane.WARNING_MESSAGE);
			return;
		}

		configurarFaseApuesta(false);

		baraja = new Deck();
		baraja.shuffle();

		manoJugador.clear();
		manoRepartidor.clear();
		
		// 2 cartas boca arriba para jugador
				repartirCarta(manoJugador, true);
				repartirCarta(manoJugador, true);
				
		// 1 carta boca arriba para repartidor
				repartirCarta(manoRepartidor, true);
				repartirCarta(manoRepartidor, false);
				
				actualizarMesa();
	}
	
	private void repartirCarta(List<Card> mano, boolean bocaArriba) {
		Card carta = baraja.draw();
		carta.setShown(bocaArriba);
		mano.add(carta);
	}
	
	private void actualizarMesa() {
		panelJugador.removeAll();
		panelRepartidor.removeAll();

		for (Card c : manoJugador) {
			panelJugador.add(c.getImage());
		}
		for (Card c : manoRepartidor) {
			panelRepartidor.add(c.getImage());
		}

		Font fuenteBorde = new Font(Font.SERIF, Font.BOLD, 16);

		// puntos de jugador
		int ptsJugador = calcularPuntos(manoJugador);
		panelJugador.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.WHITE),
				" Tus Cartas (" + ptsJugador + " pts) ", 0, 0, fuenteBorde, COLOR_DORADO));

		// puntos de repartidor visibles
		List<Card> visiblesRepartidor = new ArrayList<>();
		for (Card c : manoRepartidor) {
			if (c.isShown()) {
				visiblesRepartidor.add(c);
			}
		}
		int ptsRepartidor = calcularPuntos(visiblesRepartidor);
		panelRepartidor.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.WHITE),
				" Cartas del Repartidor (" + ptsRepartidor + " pts) ", 0, 0, fuenteBorde, COLOR_DORADO));

		panelJugador.revalidate();
		panelJugador.repaint();
		panelRepartidor.revalidate();
		panelRepartidor.repaint();
	}
	
	private int calcularPuntos(List<Card> mano) {
		int total = 0;
		int ases = 0;

		for (Card carta : mano) {
			switch (carta.getRank()) {
				case Two:   total += 2; break;
				case Three: total += 3; break;
				case Four:  total += 4; break;
				case Five:  total += 5; break;
				case Six:   total += 6; break;
				case Seven: total += 7; break;
				case Eight: total += 8; break;
				case Nine:  total += 9; break;
				case Ten:
				case Jack:
				case Queen:
				case King:
					total += 10;
					break;
				case Ace:
					total += 11;
					ases++;
					break;
			}
		}

		while (total > 21 && ases > 0) {
			total -= 10;
			ases--;
		}

		return total;
	}
	
	private void pedirCarta() {
        repartirCarta(manoJugador, true);
        actualizarMesa();

        int puntos = calcularPuntos(manoJugador);
        if (puntos > 21) {
            int ptsRepartidor = calcularPuntos(manoRepartidor);
            HistorialBlackjack.guardarPartida(apuestaActual, puntos, ptsRepartidor, "DERROTA", -apuestaActual);
            finalizarRonda("Te has pasado", "Has sumado " + puntos + " puntos. Gana la banca.", 0);
        }
    }

    private void finalizarRonda(String titulo, String mensaje, int premio) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
        saldo += premio;
        apuestaActual = 0;
        actualizarTextosDinero();

        manoJugador.clear();
        manoRepartidor.clear();
        actualizarMesa();
        configurarFaseApuesta(true);
    }

    private void plantarse() {
        // cartas repartdor
        for (Card c : manoRepartidor) {
            c.setShown(true);
        }
        actualizarMesa();

        // sigue repartiendo hasta 17 o más
        while (calcularPuntos(manoRepartidor) < 17) {
            repartirCarta(manoRepartidor, true);
            actualizarMesa();
        }

        // compara puntuaciones
        int ptsJugador = calcularPuntos(manoJugador);
        int ptsRepartidor = calcularPuntos(manoRepartidor);

        if (ptsRepartidor > 21) {
            HistorialBlackjack.guardarPartida(apuestaActual, ptsJugador, ptsRepartidor, "VICTORIA", apuestaActual);
            finalizarRonda("¡Ganaste!", "La banca se pasó con " + ptsRepartidor + " puntos. Ganas " + (apuestaActual * 2) + "€.", apuestaActual * 2);
        } else if (ptsJugador > ptsRepartidor) {
            HistorialBlackjack.guardarPartida(apuestaActual, ptsJugador, ptsRepartidor, "VICTORIA", apuestaActual);
            finalizarRonda("¡Ganaste!", "Tus " + ptsJugador + " puntos superan los " + ptsRepartidor + " de la banca. Ganas " + (apuestaActual * 2) + "€.", apuestaActual * 2);
        } else if (ptsJugador == ptsRepartidor) {
            HistorialBlackjack.guardarPartida(apuestaActual, ptsJugador, ptsRepartidor, "EMPATE", 0);
            finalizarRonda("Empate", "Ambos tenéis " + ptsJugador + " puntos. Recuperas tu apuesta de " + apuestaActual + "€.", apuestaActual);
        } else {
            HistorialBlackjack.guardarPartida(apuestaActual, ptsJugador, ptsRepartidor, "DERROTA", -apuestaActual);
            finalizarRonda("Gana la banca", "La banca gana con " + ptsRepartidor + " puntos frente a tus " + ptsJugador + ".", 0);
        }
    }
	
	
	public Window getWindow() {
		return this.window;
	}
}