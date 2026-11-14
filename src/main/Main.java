package main;

import javax.swing.SwingUtilities;

public class Main {
	public final static int REFRESH_RATE = 30;
	// variable hecha solo para probar sin tiempo de carga, poner a True cuando se haga push
	private final static boolean DEBUG_LOADTIME = false; 
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Window());
	}
	
	public static boolean isLoadtime() {
		return DEBUG_LOADTIME;
	}
}
