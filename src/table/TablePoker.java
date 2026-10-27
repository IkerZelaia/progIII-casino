package table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;

import card.Deck;

public class TablePoker extends JPanel{
	
	private JFrame window;
	private int playerNum;
	private JPanel up, down, left, right, mid;
	private Deck deck;
	
	public TablePoker(int playerNum, JFrame window) {
		this.playerNum = playerNum;
		this.window = window;
		
		//VENTANA
		super(new BorderLayout(10, 10));
		this.setBackground(new Color(0, 120, 0));
		this.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		this.setSize(window.getSize());
		
		
		// PANELES
		JPanel midPanel = new JPanel();
		
		PanelSide
		downPanel = new PanelSide(0, 1,  window.getHeight()/5),
		upPanel = new PanelSide(2, 1,  window.getHeight()/5),
		leftPanel = new PanelSide(1, 1,  window.getHeight()/5),
		rightPanel = new PanelSide(3, 1,  window.getHeight()/5);
		
		downPanel.setBackground(this.getBackground().brighter());
		upPanel.setBackground(this.getBackground().brighter());
		rightPanel.setBackground(this.getBackground().brighter());
		leftPanel.setBackground(this.getBackground().brighter());
		midPanel.setBackground(this.getBackground());
	
		
		this.add( downPanel, BorderLayout.SOUTH);
		this.add( upPanel, BorderLayout.NORTH);
		this.add(rightPanel, BorderLayout.EAST);
		this.add(leftPanel, BorderLayout.WEST);
		this.add(midPanel, BorderLayout.CENTER);
		
		BoxLayout f = new BoxLayout(midPanel, BoxLayout.X_AXIS);
	
		
		midPanel.setLayout(f);
		
		this.up = upPanel;
		this.down = downPanel;
		this.left = leftPanel;
		this.right = rightPanel;
		this.mid = midPanel;
		
		
		addPlayers();
		
		//MAZO
		
		this.deck = new Deck();
		JLabel i = deck.getImage();
		i.setVerticalAlignment(JLabel.CENTER);
		mid.add(i);
		
		
		System.out.println(mid.getComponentCount());
	}
	
	public void addPlayers(){
		for (int i = 0; i < playerNum; i ++) {
			
			JPanel temp = new JPanel();
			temp.setOpaque(false);
			
			if (i == 0 ) { 
				temp.setBorder(BorderFactory.createTitledBorder( BorderFactory.createLineBorder(this.getBackground().darker(), 7), "You", 0, 0, new Font(null, Font.BOLD, 20))); 
			}
			else{
				temp.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(this.getBackground(), 3),"Player" + i)) ;
			}
			
			if ( Math.floorDiv(i, 4) % 2 == 0) {
				switch (i % 4) {
				case 0: {
					down.add(temp);
					break;
				}
				case 1: {
					up.add(temp);	
					break;
				}
				case 2: {
					left.add(temp);
					break;
				}
				case 3: {
					right.add(temp);
					break;
				}
				default:{
					throw new IllegalArgumentException("Unexpected value: " + i % 4);
				}
				}
			}
			else {
				switch (i % 4) {
				case 0: {
					up.add(temp);
					break;
				}
				case 1: {
					down.add(temp);	
					break;
				}
				case 2: {
					up.add(temp);
					break;
				}
				case 3: {
					down.add(temp);
					break;
				}
				default:{
					throw new IllegalArgumentException("Unexpected value: " + i % 4);
				}
				}
			}
			
			
			
		}
	}
	
	public JFrame getWindow() {
		return this.window;
	}
	
	public int getPlayerNum() {
		return this.playerNum;
	}
}
