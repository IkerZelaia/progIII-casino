package main;

import javax.swing.SwingUtilities;

public class Main {
	public final static int REFRESH_RATE = 40;
	// variable hecha solo para probar sin tiempo de carga, poner a True cuando se haga push
	private final static boolean DEBUG_LOADTIME = true; 
	private final static boolean DEBUG_PERFECT_HORSES = false;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Window());
	}
	
	public static boolean debugLoadtime() {
		return DEBUG_LOADTIME;
	}
	public static boolean debugPerfectHorses() {
		return DEBUG_PERFECT_HORSES;
	}
}
