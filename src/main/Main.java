package main;

import javax.swing.SwingUtilities;

public class Main {
	public final static int REFRESH_RATE = 30;
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Window());
	}
}
