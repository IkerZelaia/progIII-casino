package table;

import java.util.List;

import javax.swing.SwingUtilities;

import card.Card;
import card.Deck;
import player.Player;

public class Dealer extends Thread {
	
	private final Deck deck;
	private final List<Player> players;
	private final int cardsPerPlayer;
	private final Runnable finished;
	
	public Dealer (Deck deck, List<Player> players, int cardsPerPlayer, Runnable finished) {
		super("Dealer");
		this.deck = deck;
		this.players = players;
		this.cardsPerPlayer = cardsPerPlayer;
		this.finished = finished;
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			deck.shuffle();
			for (int round = 0; round < cardsPerPlayer; round++) {
				for (Player player : players) {
					Card card = deck.draw();
					SwingUtilities.invokeLater(() -> player.receiveCard(card));
					Thread.sleep(400);
					
				}
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} finally {
			SwingUtilities.invokeLater(finished);
		}
	}
	
}
