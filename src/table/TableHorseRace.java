package table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import horse.Horse;
import horse.Race;
import main.Window;

public class TableHorseRace extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Color COLOR_DORADO = new Color(212, 175, 55);
	private static final Color COLOR_BURDEOS = new Color(110, 15, 30);
	private static final Color COLOR_BURDEOS_HOVER = new Color(145, 25, 45);
	private static final Color COLOR_TAPETE = new Color(25, 105, 52);
	
	private Window window;
	private JPanel panelRace, panelData, panelMenu, panelCentral, panelHorses, panelInicio;
	private JButton botonReturn, botonStart;
	private Race currentRace;
	
	public TableHorseRace(Window window) {
		
		super(new BorderLayout());
		this.setBackground(COLOR_TAPETE);
		this.window = window;
		
		JPanel relleno = new JPanel();
		relleno.setOpaque(false);
		
		panelCentral = new JPanel(new GridLayout(1, 2));
		panelRace = new JPanel();
		estilizarPanel(panelRace);
		panelData = new JPanel();
		estilizarPanel(panelData);
		panelMenu = new JPanel(new GridLayout(1, 3));
		estilizarPanel(panelMenu);
		
		panelInicio = new JPanel();
		botonStart = new JButton("Inicio");
		botonStart.addActionListener(e -> startRace());
		
		panelInicio.add(botonStart);
		
		panelData.add(panelInicio);
		
		botonReturn = new JButton("Menu principal");
		botonReturn.addActionListener( e -> this.window.mostrarMenu());
		botonReturn.setHorizontalAlignment(SwingConstants.LEFT);
		estilizarBoton(botonReturn, COLOR_BURDEOS, COLOR_BURDEOS_HOVER);
		JPanel contenedorReturn = new JPanel();
		contenedorReturn.add(botonReturn);
		contenedorReturn.setOpaque(false);
		
		panelMenu.add(contenedorReturn);
		panelMenu.add(new JLabel("CARRERAS DE CABALLOS", SwingConstants.CENTER));
		panelMenu.add(relleno);
		
		Horse h = new Horse();
		ImageIcon i = h.getImage();
		panelRace.add(new JLabel(i));
		
		panelCentral.add(panelRace);
		panelCentral.add(panelData);
		
		this.add(panelCentral, BorderLayout.CENTER);
		this.add(panelMenu, BorderLayout.NORTH);
	}
	
	
	private void startRace() {
		panelRace.removeAll();
		currentRace = new Race();
		int n = currentRace.getNumHorse();
		panelHorses = new JPanel(new GridLayout(n, 1, 0, 0));
		panelHorses.setOpaque(false);
		
		for ( Horse h : currentRace.getHorses()) {
			ImageIcon i = h.getImage();
			JLabel l = new JLabel (i);
			
			panelHorses.add(l);
		}
		panelRace.add(panelHorses);
		window.setContentPane(this);
		
	}
	
	
	
	
	private void estilizarPanel(JPanel p) {
		p.setBackground(COLOR_TAPETE);
		p.setBorder(BorderFactory.createLineBorder(COLOR_DORADO, 7));
	}
	// Copiado del Black Jack
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
	
}
