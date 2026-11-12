package table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;

import card.Card;
import card.Deck;
import main.Main;
import player.Player;

public class TablePoker extends JPanel{
	
	private static final int CARDS_PER_PLAYER = 2;
	
	private JFrame window;
	private int playerNum;
	private JPanel up, left, right, mid, playerPanel, pHandPanel, gamePanel, betPanel;
	private Deck deck;
	private JButton startButton;
	private List<Player> players = new ArrayList<Player>();
	
	
	
	public TablePoker(int playerNum, JFrame window) {
		this.playerNum = playerNum;
		this.window = window;
		
		//VENTANA
		super(new BorderLayout(10, 10));
		this.playerNum = playerNum;
		this.window = window;
		this.setBackground(new Color(0, 120, 0));
		this.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		this.setSize(window.getSize());
		
		
		// PANELES
		JPanel midPanel = new JPanel();
		
		int numJugadorLado = Math.ceilDiv(playerNum, 4);
		int numJugadorTop = playerNum / 2;
		
		PanelSide
		upPanel = new PanelSide(2, numJugadorTop,  window.getHeight()/5),
		leftPanel = new PanelSide(1, numJugadorLado,  window.getHeight()/5),
		rightPanel = new PanelSide(3, numJugadorLado,  window.getHeight()/5);
		
		PanelSide downPanel = new PanelSide(4, 3, window.getHeight()/5);
		
		
		
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
		this.playerPanel = downPanel;
		this.left = leftPanel;
		this.right = rightPanel;
		this.mid = midPanel;
		
		addPlayers();
		
		//MAZO
		
		this.deck = new Deck();
		JLabel i = deck.getImage();
		i.setVerticalAlignment(JLabel.CENTER);
		
		Card c1, c2;
		c1 = deck.draw();
		c2 = deck.draw();
		System.out.println(c1 + " " + c2);
		c1.setShown(true);
		c2.setShown(true);
		pHandPanel.add(c1.getImage());
		
		pHandPanel.add(c2.getImage());
		
		// BOTON INICIO
		
		Dimension buttonSize = new Dimension(180, 50);
		startButton = new JButton("Inicio");
		startButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		startButton.setPreferredSize(buttonSize);
		startButton.setMaximumSize(buttonSize);
		startButton.setFocusPainted(false);
		startButton.addActionListener(e -> startGame());
		
		//ADD -> MAZO Y BOTON
		//mid.add(i);
		gamePanel.add(startButton);
		
	}
	
	
	private void startGame() {
		startButton.setEnabled(false);
		deck.shuffle();
		
	
	}
	
	
	
	public void addPlayers(){
		for (int i = 0; i < playerNum; i ++) {
			
			JPanel temp = new JPanel();
			temp.setOpaque(false);
			
			if (i == 0 ) {
				betPanel = new JPanel();
				gamePanel = new JPanel();
				
				betPanel.setOpaque(false);
				gamePanel.setOpaque(false);
				temp.setBorder(BorderFactory.createTitledBorder( BorderFactory.createLineBorder(this.getBackground().darker(), 7), "You", 0, 0, new Font(null, Font.BOLD, 20))); 
				pHandPanel = temp;
				
				playerPanel.add(betPanel);
				playerPanel.add(pHandPanel);
				playerPanel.add(gamePanel);
			}
			else{
				temp.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(this.getBackground(), 3),"Player" + i)) ;
				
				if (i % 2 == 0) {
					up.add(temp);
				}
				else {
					 if (i % 4 == 1) {
						 left.add(temp); 
					 }
					 else {
						 right.add(temp);
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

	public List<Player> getPlayers() {
		return players;
	}
	
}
