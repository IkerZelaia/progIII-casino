package player;

import java.util.ArrayList;
import java.util.Collections;
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
		card.setVisible(human);
		panel.add(card.getImage());
		panel.revalidate();
		panel.repaint();
	}
	
	public void clearHand() {
		for (Card card: hand) {
			card.setVisible(false);
		}
		hand.clear();
		panel.removeAll();
		panel.revalidate();
		panel.repaint();
	}
	
	public String getName() {
		return name;
	}

	public boolean isHuman() {
		return human;
	}

	public JPanel getPanel() {
		return panel;
	}

	public List<Card> getHand() {
		return hand;
	}
	
}
