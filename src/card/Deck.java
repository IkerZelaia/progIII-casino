package card;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Stream;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Deck {
	public static final Dimension cardDimension = new Dimension(63, 89);
	public static double cardScale = 1.5;
	
	private LinkedList<Card> unusedCards = new LinkedList<Card>();
	private LinkedList<Card> usedCards = new LinkedList<Card>();
	private boolean hasJokers;
	private JLabel deckImage;
	
	public Deck(){
		for (Card.Suit s : Card.Suit.values()) {
			for (Card.Rank r : Card.Rank.values()) {
				unusedCards.add(new Card(  r,  s));

			}
		}
		
		
		
		
		
		
		
		Dimension size = new Dimension((int) (cardDimension.width*Deck.cardScale),(int) (cardDimension.height*Deck.cardScale));
		ImageIcon img = new ImageIcon( new ImageIcon("res/back_standard.jpg").getImage().getScaledInstance(size.width, size.height, Image.SCALE_SMOOTH) );
		//deckImage.setPreferredSize(size);
		deckImage = new JLabel(img);
		//deckImage.setSize(deckImage.getPreferredSize());
		
	}
	
	public void shuffle() {
		LinkedList<Card> c = new LinkedList<Card>();
		c.addAll(unusedCards);
		c.addAll(usedCards);
		unusedCards.clear();
		usedCards.clear();
		for (int i = c.size()- 1 ; i>=0 ; i --) {
			
			Card crd = c.get((int) (i*Math.random()));
			unusedCards.add(crd);
			c.remove(crd);
		}
	}
	
	public JLabel getImage() {
		return this.deckImage;
	}
	public LinkedList<Card> getCards(){
		LinkedList<Card> c = new LinkedList<Card>();
		c.addAll(unusedCards);
		c.addAll(usedCards);
		 return c;
		
	}
}
