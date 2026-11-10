package menu;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class BotonJuego extends JButton{


	private static final Color COLOR_NORMAL = new Color(0, 100, 0);  // verde oscuro
	private static final Color COLOR_HOVER = new Color(100, 10, 25);    //burdeos oscuro
	private static final Color COLOR_DESACTIVADO = new Color(205, 205, 205);
	private static final Color TEXTO_DESACTIVADO = new Color(120, 120, 120);
	private static final int GROSOR_BORDE = 5;
	
	private boolean activo;
			
	
	public BotonJuego(String texto) {
		super(texto);
		
		setFont(new Font(Font.SERIF, Font.BOLD, 28));
		setHorizontalAlignment(SwingConstants.CENTER);
		setVerticalAlignment(SwingConstants.CENTER);
		setBorder(BorderFactory.createLineBorder(Color.BLACK, GROSOR_BORDE));
		setFocusPainted(false);
		setContentAreaFilled(true);
		setOpaque(true);
		
		setActivo(true);
		
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				if (activo) {
					setBackground(COLOR_HOVER);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				if (activo) {
					setBackground(COLOR_NORMAL);
				}
			}
				
	});
	
	}
	
	public void setActivo(boolean activo) {
		this.activo = activo;
		setBackground(activo ? COLOR_NORMAL : COLOR_DESACTIVADO);
		setForeground(activo ? Color.WHITE : TEXTO_DESACTIVADO);
		setCursor(Cursor.getPredefinedCursor(activo ? Cursor.HAND_CURSOR : Cursor.DEFAULT_CURSOR ));
	}
	
	
	public boolean isActivo() {
		return activo;
	}
}
