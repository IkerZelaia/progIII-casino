package player;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import card.Card;

public class Player {

	private final String name;
	private final boolean human;
	private final JPanel panel;
	private final List<Card> hand = new ArrayList<Card>();
	
	public Player(String name, boolean human, JPanel panel) {
		super();
		this.name = name;
		this.human = human;
		this.panel = panel;
	}
	
	public void receiveCard(Card card) {
		hand.add(card);
		
	}
	
}
