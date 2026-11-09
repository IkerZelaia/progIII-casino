package menu;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class BotonJuego extends JButton{


	private static final Color DORADO = new Color(212, 175, 55);
	private static final Color DORADO_CLARO = new Color(255, 215, 90);
	private static final Color BORDE = new Color(120, 90, 10);
	
	private boolean hover = false;
	
	public BotonJuego(String texto) {
		super(texto);
		
		setFont(new Font(Font.SERIF, Font.BOLD, 26));
		setForeground(Color.BLACK);
		setPreferredSize(new Dimension(220, 70));
		setFocusPainted(false);
		setBorderPainted(false);
		setContentAreaFilled(false);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				hover = true;
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				hover = false;
				repaint();
			}
				
	});
	
	}
	
	
}
