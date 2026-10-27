package card;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Card {
	
	
	private JPanel cardImage;
	private boolean visible;
	public enum Rank {
		Ace,
		Two,
		Three,
		Four,
		Five,
		Six,
		Seven,
		Eight,
		Nine,
		Ten,
		Jack,
		Queen,
		King;
		
	
		

	}
	public enum Suit {
		Spades,
		Hearts,
		Clubs,
		Diamonds;

	}
	
	private Suit suit;
	private Rank rank;
	
	public Card(Rank rank, Suit suit){
		this.suit = suit;
		this.rank = rank;
		
		this.visible = false;
		this.cardImage = new JPanel();
		Dimension size = new Dimension((int) (Deck.cardDimension.width*Deck.cardScale),(int) (Deck.cardDimension.height*Deck.cardScale));
		cardImage.setPreferredSize(size);
		cardImage.setSize(cardImage.getPreferredSize());
		
		ImageIcon img = new ImageIcon("/res/back_mtg.jpg");
		cardImage.add(new JLabel(img));
		
		
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
	public JPanel getImage() {
		return this.cardImage;
	}
	
	public String toString() {
		return rank + " of " + suit;
	}
}
