package poker;

import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import card.Card;

public class PokerBot {

	private final Random rnd = new Random();
	private final double aggression;   
	private final double bluffChance;  

	//Generado con IA 
	PokerBot() {
		aggression = 0.7 + rnd.nextDouble() * 0.7;
		bluffChance = 0.03 + rnd.nextDouble() * 0.08;
	}

	PokerAction decide(List<Card> hole, List<Card> board, int toCall, int pot, int chips,
			int myBet, int currentBet, int minRaiseTo, int bigBlind, int raisesThisRound) {

		double s = strength(hole, board) + rnd.nextGaussian() * 0.05;
		boolean bluff = rnd.nextDouble() < bluffChance;
		int maxTo = myBet + chips;
		boolean canRaise = maxTo > currentBet && raisesThisRound < 4;
		double potOdds = toCall == 0 ? 0 : (double) toCall / (pot + toCall);

		
		if (toCall == 0) {
			if (canRaise && ((s > 0.62 && rnd.nextDouble() < 0.55 * aggression) || bluff)) {
				return sizedRaise(s, pot, chips, myBet, currentBet, minRaiseTo, bigBlind);
			}
			return PokerAction.check();
		}

	
		if (toCall >= chips) {
			return s >= potOdds + 0.10 ? PokerAction.call() : PokerAction.fold();
		}

		if (canRaise && s > 0.82 && rnd.nextDouble() < 0.55 * aggression) {
			return sizedRaise(s, pot, chips, myBet, currentBet, minRaiseTo, bigBlind);
		}
		if (canRaise && bluff && s < 0.35 && rnd.nextDouble() < 0.5) {
			return sizedRaise(0.6, pot, chips, myBet, currentBet, minRaiseTo, bigBlind);
		}

		double stackPressure = (double) toCall / (chips + toCall);
		double need = potOdds + 0.10 + stackPressure * 0.25;
		boolean cheap = toCall <= bigBlind && s > 0.28;
		return (s >= need || cheap) ? PokerAction.call() : PokerAction.fold();
	}

	private PokerAction sizedRaise(double s, int pot, int chips, int myBet, int currentBet,
			int minRaiseTo, int bigBlind) {
		int maxTo = myBet + chips;
		double frac = 0.4 + 0.6 * s * (0.7 + 0.6 * rnd.nextDouble());
		int raiseTo = Math.max(minRaiseTo, currentBet + (int) (pot * frac));
		int step = Math.max(1, bigBlind / 2);
		raiseTo = ((raiseTo + step - 1) / step) * step;
		if (raiseTo > maxTo * 0.65) {
			raiseTo = maxTo;  
		}
		return PokerAction.raiseTo(Math.min(raiseTo, maxTo));
	}


	private double strength(List<Card> hole, List<Card> board) {
		if (board.isEmpty()) {
			return preflopStrength(hole);
		}
		List<Card> all = new java.util.ArrayList<>(hole);
		all.addAll(board);
		if (all.size() < 5) {
			return preflopStrength(hole);
		}
		long score = HandEvaluator.evaluate(all);
		HandEvaluator.Category cat = HandEvaluator.category(score);
		int h1 = HandEvaluator.value(hole.get(0)), h2 = HandEvaluator.value(hole.get(1));
		int t0 = HandEvaluator.tie(score, 0), t1 = HandEvaluator.tie(score, 1);
		int maxBoard = 0;
		for (Card c : board) {
			maxBoard = Math.max(maxBoard, HandEvaluator.value(c));
		}
		boolean holeInT0 = h1 == t0 || h2 == t0;
		boolean holeInT1 = h1 == t1 || h2 == t1;

		double s;
		switch (cat) {
		case STRAIGHT_FLUSH: s = 1.0; break;
		case QUADS:          s = holeInT0 ? 0.99 : 0.35; break;
		case FULL_HOUSE:     s = (holeInT0 || holeInT1) ? 0.97 : 0.40; break;
		case FLUSH:          s = 0.93; break;
		case STRAIGHT:       s = 0.90; break;
		case TRIPS:          s = holeInT0 ? 0.85 : 0.28; break;
		case TWO_PAIR:       s = (holeInT0 || holeInT1) ? 0.78 : 0.25; break;
		case PAIR:
			if (!holeInT0)            s = 0.18 + Math.max(h1, h2) / 14.0 * 0.08;  
			else if (t0 > maxBoard)   s = 0.72;                                   
			else if (t0 == maxBoard)  s = 0.60;                                    
			else                      s = 0.42;
			break;
		default:
			s = 0.10 + Math.max(h1, h2) / 14.0 * 0.08;
		}

		if (board.size() < 5 && cat.ordinal() < HandEvaluator.Category.STRAIGHT.ordinal()) {
			s += drawBonus(all, hole);
		}
		return Math.min(1.0, s);
	}

	private double drawBonus(List<Card> all, List<Card> hole) {
		double bonus = 0;
		int[] suits = new int[4];
		for (Card c : all) {
			suits[c.getSuit().ordinal()]++;
		}
		for (int su = 0; su < 4; su++) {
			if (suits[su] == 4 && (hole.get(0).getSuit().ordinal() == su || hole.get(1).getSuit().ordinal() == su)) {
				bonus += 0.15;
			}
		}
		TreeSet<Integer> vals = new TreeSet<>();
		for (Card c : all) {
			int v = HandEvaluator.value(c);
			vals.add(v);
			if (v == 14) {
				vals.add(1);
			}
		}
		for (int lo = 1; lo <= 10; lo++) {
			int present = 0;
			for (int v = lo; v < lo + 5; v++) {
				if (vals.contains(v)) present++;
			}
			if (present >= 4) {
				bonus += 0.10;
				break;
			}
		}
		return bonus;
	}

	private double preflopStrength(List<Card> hole) {
		int a = HandEvaluator.value(hole.get(0));
		int b = HandEvaluator.value(hole.get(1));
		int hi = Math.max(a, b), lo = Math.min(a, b);
		double s;
		if (hi == lo) {
			s = 0.55 + (hi - 2) / 12.0 * 0.45;
		} else {
			s = ((hi - 2) + 0.5 * (lo - 2)) / 18.0 * 0.85;
			if (hi - lo == 1) s += 0.04;
			else if (hi - lo == 2) s += 0.02;
		}
		if (hole.get(0).getSuit() == hole.get(1).getSuit()) {
			s += 0.05;
		}
		return Math.max(0, Math.min(1, s));
	}
}