package main;

import java.util.Random;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import menu.MainMenu;
import table.SlotMachine;
import table.TablePoker;
import table.TableBlackjack;

public class Window extends JFrame{
	public Window() {
		
		this.setTitle("Casino");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(new Dimension(960, 600));
		this.setLocationRelativeTo(null);
		
		mostrarMenu();

		this.setVisible(true);
		
	}
	
	public void mostrarMenu() {
		MainMenu menu = new MainMenu();
		
		mostrarPantallaDeCarga(menu);
		
		menu.anadirJuego("Poker", this::mostrarPoker);

        menu.anadirJuego("Slots", this::mostrarSlots); 
        
		menu.anadirJuego("Blackjack", this::mostrarBlackjack);
		//cambiarPanel(menu);

	}
	
	
	
	public void mostrarPoker() {
		cambiarPanel(new DownloadScreen(this, new TablePoker(8, this), 80));
	}
	

	public void mostrarSlots() {
		cambiarPanel(new DownloadScreen(this, new SlotMachine(), 30));
	}
	public void mostrarBlackjack() {
		cambiarPanel(new DownloadScreen(this, new TableBlackjack(this), 10));
	}
	
	public void mostrarPantallaDeCarga(JPanel destino) {
		cambiarPanel(new DownloadScreen(this, destino, 50));
	}
	
	

	public void cambiarPanel(JPanel panel) {
		this.setContentPane(panel);
		this.revalidate();
		this.repaint();
	}
	
}
