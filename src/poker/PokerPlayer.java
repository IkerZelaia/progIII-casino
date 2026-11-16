package poker;

import java.util.ArrayList;
import java.util.List;

import card.Card;

public class PokerPlayer {

	final String name;
	final boolean human;
	final PokerBot bot;
	int chips;
	int bet;
	int contributed;
	boolean folded;
	boolean allIn;
	boolean acted;
	boolean out;
	boolean revealed;
	boolean winner;
	String lastAction = "";
	final List<Card> hole = new ArrayList<>();
	public PokerPlayer(String name, boolean human, int chips) {
		super();
		this.name = name;
		this.human = human;
		this.chips = chips;
		this.bot = human ? null: new PokerBot();
	}
	public void resetForHand() {
		out = chips <= 0;
		folded = out;
		allIn = false;
		bet = 0;
		contributed = 0;
		acted = false;
		revealed = false;
		winner = false;
		lastAction = "";
		hole.clear();
	}
	
	
	
}
