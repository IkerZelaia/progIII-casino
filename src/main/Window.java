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
import table.TableHorseRace;

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
		
		cargar(menu, 50);
		
		menu.anadirJuego("Poker", this::mostrarPoker);

        menu.anadirJuego("Slots", this::mostrarSlots); 
        
		menu.anadirJuego("Blackjack", this::mostrarBlackjack);
		
		menu.anadirJuego("Caballos", this::mostrarCaballos);
		//cambiarPanel(menu);

	}
	
	
	
	public void mostrarPoker() {
		cargar(new TablePoker(8, this), 80);
	}
	public void mostrarSlots() {
		cargar(new SlotMachine(), 30);
	}
	public void mostrarBlackjack() {
		cargar(new TableBlackjack(this), 10);
	}
	
	public void mostrarCaballos() {
		cargar(new TableHorseRace(this), 30);
	}
	
	public void cargar(JPanel destino, int tiempo) {
		cambiarPanel(new DownloadScreen(this, destino, tiempo));
	}
	
	

	public void cambiarPanel(JPanel panel) {
		this.setContentPane(panel);
		this.revalidate();
		this.repaint();
	}
	
}
