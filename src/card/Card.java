package card;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Card {
	
	

	public enum Rank {
		Ace("A"),
		Two("2"),
		Three("3"),
		Four("4"),
		Five("5"),
		Six("6"),
		Seven("7"),
		Eight("8"),
		Nine("9"),
		Ten("10"),
		Jack("J"),
		Queen("Q"),
		King("K");
 
		private final String symbol;
 
		Rank(String symbol) {
			this.symbol = symbol;
		}
 
		public String getSymbol() {
			return symbol;
		}
	}
	
	 public enum Suit {
		Spades("\u2660", Color.black),
		Hearts("\u2665", Color.red),
		Clubs("\u2663", Color.black),
		Diamonds("\u2666", Color.red);
		 
		

		private final String symbol;
		private final Color color;
		
		private Suit(String symbol, Color color) {
			this.symbol = symbol;
			this.color = color;
		}
		
		
		public String getSymbol() {
			return symbol;
		}

		public Color getColor() {
			return color;
		}

	}
	
	 
	private JLabel backImage;
	private static boolean backLoaded = false;
	 
	private Suit suit;
	private Rank rank;
	private boolean shown;
	private JPanel back, front, image;
	
	
	public Card(Rank rank, Suit suit){
		this.suit = suit;
		this.rank = rank;
		this.shown = false;
		this.back = new JPanel();
		this.front = new JPanel(new BorderLayout());
		
		
		front.setPreferredSize(cardSize());
		front.setSize(cardSize());
		
		front.setBackground(Color.WHITE);
		JLabel frontText = new JLabel(this.suit.getSymbol() + " " + this.rank.getSymbol());
		frontText.setForeground(this.suit.getColor());
		front.add(frontText, BorderLayout.NORTH);
		
		
		ImageIcon img = new ImageIcon( new ImageIcon("res/back_standard.jpg").getImage().getScaledInstance(cardSize().width, cardSize().height, Image.SCALE_SMOOTH) );
		backImage = new JLabel(img);
		back.add(backImage);
	}
	
	
	public Rank getRank() {
		return this.rank;
	}
	public Suit getSuit() {
		return this.suit;
	}
	public boolean isShown() {
		return this.shown;
	}
	public void setShown(boolean visible) {
		this.shown = visible;
		refreshImage();
	}
	
	public JPanel getImage() {
		refreshImage();
		return this.image;
	}
	
	
	private static Dimension cardSize() {
		return new Dimension((int) (Deck.cardDimension.width * Deck.cardScale), (int) (Deck.cardDimension.height * Deck.cardScale));
	}
	
	private void refreshImage() {
		image = shown ? front : back;
	}
	
	@Override
	public String toString() {
		return rank + " of " + suit;
	}
	
	/*
	private void refreshImage() {
		if (back == null) {
			return;
		}
		back.removeAll();
		if (visible) {
			buildFace();
		} else {
			buildBack();
		}
		back.revalidate();
		back.repaint();
	}
	
	private void buildFace() {
		Color color = suit.isRed() ? new Color(200, 0, 0): Color.BLACK;
		
		back.setLayout(new GridLayout(2, 1));
		back.setBackground(Color.WHITE);
		back.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		JLabel rankLabel = new JLabel(rank.getSymbol(), SwingConstants.CENTER);
		rankLabel.setFont(new Font(Font.SERIF, Font.BOLD, 18));
		rankLabel.setForeground(color);
		
		JLabel suitLabel = new JLabel(rank.getSymbol(), SwingConstants.CENTER);
		suitLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 24));
		suitLabel.setForeground(color);
		
		back.add(rankLabel);
		back.add(suitLabel);
	}
	
	private void buildBack() {
		back.setLayout(new BorderLayout());
		
		ImageIcon icon = getBackIcon();
		if (icon != null) {
			back.setBorder(null);
			back.add(new JLabel(icon), BorderLayout.CENTER);
		} else {
			back.setBackground(new Color(25, 50, 130));
			back.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), BorderFactory.createLineBorder(Color.WHITE, 2)));
		}
	}
	
	private static ImageIcon getBackIcon() {
		if (!backLoaded) {
			backLoaded = true;
			ImageIcon original = new ImageIcon("res/back_standard.jpg");
			if(original.getIconWidth()> 0) {
					Dimension size = cardSize();
					backImage = new ImageIcon(original.getImage().getScaledInstance(size.width, size.height, Image.SCALE_SMOOTH));
			}
		}
		return backImage;
	}
	
	*/
}
