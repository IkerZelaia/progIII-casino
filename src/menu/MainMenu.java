package menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MainMenu extends JPanel{

	private static final Color DORADO = new Color(212, 175, 55);
	private static final int NUM_CASILLAS = 4;
	private static final Color COLOR_ESLOGAN = new Color(40, 40, 40);
	private final BotonJuego[] casillas = new BotonJuego[NUM_CASILLAS];
	private int siguienteLibre = 0;
	
	public MainMenu() {
		super(new BorderLayout());
		setBackground(Color.WHITE);
		
		JPanel cabecera = new JPanel(new GridLayout(3,1));
		cabecera.setOpaque(false);
		cabecera.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
		
		JLabel palos = new JLabel("\u2660  \u2665  \u2663  \u2666", SwingConstants.CENTER);
		palos.setFont(new Font(Font.SERIF, Font.PLAIN, 36));
		palos.setForeground(DORADO);
		
		JLabel titulo = new JLabel("CASINO DEUSTO", SwingConstants.CENTER);
		titulo.setFont(new Font(Font.SERIF, Font.BOLD, 68));
		titulo.setForeground(DORADO);
		
		JLabel eslogan = new JLabel("Donde la suerte saca matr\u00edcula de honor", SwingConstants.CENTER);
		eslogan.setFont(new Font(Font.SERIF, Font.ITALIC, 24));
		eslogan.setForeground(COLOR_ESLOGAN);
		
		cabecera.add(palos);
		cabecera.add(titulo);
		cabecera.add(eslogan);
		
		
		JPanel panelBotones = new JPanel(new GridLayout(1, NUM_CASILLAS, 10, 0));
		panelBotones.setOpaque(false);
		panelBotones.setPreferredSize(new Dimension(880, 110));	
		
		
		for (int i = 0; i < NUM_CASILLAS; i++) {
			casillas[i] = new BotonJuego("Pr\u00f3ximamente");
			casillas[i].setEnabled(false);
			panelBotones.add(casillas[i]);
			
		}
		
		
		JPanel centro = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 50));
		centro.setOpaque(false);
		centro.add(panelBotones);
		
		add(cabecera, BorderLayout.NORTH);
		add(panelBotones, BorderLayout.CENTER);
	
	}
	
	public void anadirJuego(String nombre, Runnable accion) {
	    if (siguienteLibre >= NUM_CASILLAS) {
	        throw new IllegalStateException("No quedan casillas libres en el menu");
	    }
	    
	    BotonJuego casilla = casillas[siguienteLibre];
	    casilla.setText(nombre);
	    casilla.setEnabled(true);
	    casilla.addActionListener(e -> accion.run());
	    
	    siguienteLibre++;
	}
	
	
	
	
}
