package menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MainMenu extends JPanel{

	private static final Color DORADO = new Color(212, 175, 55);
	
	private JPanel panelBotones = new JPanel();
	
	public MainMenu() {
		super(new BorderLayout());
		
		JPanel cabecera = new JPanel(new GridLayout(3,1));
		cabecera.setOpaque(false);
		cabecera.setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0));
		
		JLabel palos = new JLabel("\u2660  \u2665  \u2663  \u2666", SwingConstants.CENTER);
		palos.setFont(new Font(Font.SERIF, Font.PLAIN, 36));
		palos.setForeground(DORADO);
		
		JLabel titulo = new JLabel("CASINO DEUSTO", SwingConstants.CENTER);
		titulo.setFont(new Font(Font.SERIF, Font.BOLD, 68));
		titulo.setForeground(DORADO);
		
		JLabel eslogan = new JLabel("Donde la suerte saca matr\u00edcula de honor", SwingConstants.CENTER);
		eslogan.setFont(new Font(Font.SERIF, Font.ITALIC, 24));
		eslogan.setForeground(Color.WHITE);
		
		cabecera.add(palos);
		cabecera.add(titulo);
		cabecera.add(eslogan);
		
		
		panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10));
		panelBotones.setOpaque(false);
		panelBotones.setBorder(BorderFactory.createEmptyBorder(40,0,0,0));
		
		add(cabecera, BorderLayout.NORTH);
		add(panelBotones, BorderLayout.CENTER);
	
	}
	
	public void anadirJuego(String nombre, Runnable accion) {
		BotonJuego boton = new BotonJuego(nombre);
		boton.addActionListener(e -> accion.run());
		panelBotones.add(boton);
		panelBotones.revalidate();
	}
	
	
	
	
	
}
