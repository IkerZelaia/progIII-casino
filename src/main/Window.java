package main;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import menu.MainMenu;
import table.TablePoker;

public class Window extends JFrame{
	public Window() {
		
		this.setTitle("Casino");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(new Dimension(960, 600));
		this.setLocationRelativeTo(null);
		
		this.setPreferredSize(new Dimension(960, 600));
		mostrarMenu();

		this.setVisible(true);
		
		this.add(new TablePoker(7, this));
		this.pack();	
	}
	
	public void mostrarMenu() {
		MainMenu menu = new MainMenu();
		menu.anadirJuego("Poker", this::mostrarPoker);
		cambiarPanel(menu);
	}
	
	public void mostrarPoker() {
		cambiarPanel(new TablePoker(7, this));
	}

	public void cambiarPanel(JPanel panel) {
		this.setContentPane(panel);
		this.revalidate();
		this.repaint();
	}
	
}
