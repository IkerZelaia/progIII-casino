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
		Spades("\u2660", false),
		Hearts("\u2665", true),
		Clubs("\u2663", false),
		Diamonds("\u2666", true);
		 
		

		private final String symbol;
		private final boolean red;
		
		private Suit(String symbol, boolean red) {
			this.symbol = symbol;
			this.red = red;
		}
		
		
		public String getSymbol() {
			return symbol;
		}

		public boolean isRed() {
			return red;
		}

	}
	
	 
	private static ImageIcon backIcon;
	private static boolean backLoaded = false;
	 
	private Suit suit;
	private Rank rank;
	private boolean visible;
	private JPanel cardImage;
	
	
	public Card(Rank rank, Suit suit){
		this.suit = suit;
		this.rank = rank;
		this.visible = false;
		
	}
	
	
	public Rank getRank() {
		return this.rank;
	}
	public Suit getSuit() {
		return this.suit;
	}
	public boolean isVisible() {
		return this.visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
		refreshImage();
	}
	
	public JPanel getImage() {
		if (this.cardImage == null) {
			this.cardImage = new JPanel();
			Dimension size = cardSize();
			cardImage.setPreferredSize(size);
			cardImage.setMinimumSize(size);
			cardImage.setMaximumSize(size);
			refreshImage();
		}
		
		return this.cardImage;
	}
	
	
	private static Dimension cardSize() {
		return new Dimension((int) (Deck.cardDimension.width * Deck.cardScale), (int) (Deck.cardDimension.height * Deck.cardScale));
	}
	
	private void refreshImage() {
		if (cardImage == null) {
			return;
		}
		cardImage.removeAll();
		if (visible) {
			buildFace();
		} else {
			buildBack();
		}
		cardImage.revalidate();
		cardImage.repaint();
	}
	
	private void buildFace() {
		Color color = suit.isRed() ? new Color(200, 0, 0): Color.BLACK;
		
		cardImage.setLayout(new GridLayout(2, 1));
		cardImage.setBackground(Color.WHITE);
		cardImage.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		JLabel rankLabel = new JLabel(rank.getSymbol(), SwingConstants.CENTER);
		rankLabel.setFont(new Font(Font.SERIF, Font.BOLD, 18));
		rankLabel.setForeground(color);
		
		JLabel suitLabel = new JLabel(rank.getSymbol(), SwingConstants.CENTER);
		suitLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 24));
		suitLabel.setForeground(color);
		
		cardImage.add(rankLabel);
		cardImage.add(suitLabel);
	}
	
	private void buildBack() {
		cardImage.setLayout(new BorderLayout());
		
		ImageIcon icon = getBackIcon();
		if (icon != null) {
			cardImage.setBorder(null);
			cardImage.add(new JLabel(icon), BorderLayout.CENTER);
		} else {
			cardImage.setBackground(new Color(25, 50, 130));
			cardImage.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), BorderFactory.createLineBorder(Color.WHITE, 2)));
		}
	}
	
	private static ImageIcon getBackIcon() {
		if (!backLoaded) {
			backLoaded = true;
			ImageIcon original = new ImageIcon("res/back_standard.jpg");
			if(original.getIconWidth()> 0) {
					Dimension size = cardSize();
					backIcon = new ImageIcon(original.getImage().getScaledInstance(size.width, size.height, Image.SCALE_SMOOTH));
			}
		}
		return backIcon;
	}
	
	@Override
	public String toString() {
		return rank + " of " + suit;
	}
}
