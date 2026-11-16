package poker;

import java.util.List;

import card.Card;

public final class PokerView {

	private PokerView() {}
	// Asistencia con IA
	public record Seat(int index, String name, boolean human, int chips, int bet,
			boolean folded, boolean allIn, boolean out, boolean turn, boolean winner,
			String role, String status, List<Card> cards, boolean faceUp) {}


	public record HumanTurn(int toCall, int currentBet, int minRaiseTo, int maxRaiseTo,
			int chips, int secondsLeft) {}

	public record Table(List<Seat> seats, List<Card> community, int pot, String phase,
			HumanTurn humanTurn, String humanHand, boolean waitingNextHand) {}
}
