package main;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import table.TablePoker;

public class Window extends JFrame{
	public Window() {
		
		this.setTitle("Casino");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	
		
		this.setPreferredSize(new Dimension(960, 600));
		this.setSize(new Dimension(960, 600));
		this.setVisible(true);
		
		
		this.add(new TablePoker(7, this));
		this.pack();
		
		this.setLocationRelativeTo(null);
	}
}
