package poker;

import java.util.ArrayList;
import java.util.List;

import card.Card;

// Generado con IA, utilizado para ver quien gana en cada caso de partida.
 final class HandEvaluator {

	public enum Category {
		HIGH_CARD, PAIR, TWO_PAIR, TRIPS, STRAIGHT, FLUSH, FULL_HOUSE, QUADS, STRAIGHT_FLUSH
	}

	private static final String[] SING = { "", "", "Dos", "Tres", "Cuatro", "Cinco", "Seis", "Siete",
			"Ocho", "Nueve", "Diez", "Jota", "Reina", "Rey", "As" };
	private static final String[] PLUR = { "", "", "Doses", "Treses", "Cuatros", "Cincos", "Seises", "Sietes",
			"Ochos", "Nueves", "Dieces", "Jotas", "Reinas", "Reyes", "Ases" };

	private HandEvaluator() {}

	
	public static int value(Card c) {
		int o = c.getRank().ordinal();   // Ace = 0, Two = 1 ... King = 12
		return o == 0 ? 14 : o + 1;
	}

	
	public static long evaluate(List<Card> cards) {
		if (cards.size() < 5) {
			throw new IllegalArgumentException("Hacen falta al menos 5 cartas");
		}
		long[] best = { -1 };
		combine(cards, 0, new Card[5], 0, best);
		return best[0];
	}

	private static void combine(List<Card> cards, int start, Card[] pick, int n, long[] best) {
		if (n == 5) {
			long s = score5(pick);
			if (s > best[0]) {
				best[0] = s;
			}
			return;
		}
		for (int i = start; i <= cards.size() - (5 - n); i++) {
			pick[n] = cards.get(i);
			combine(cards, i + 1, pick, n + 1, best);
		}
	}

	private static long score5(Card[] h) {
		int[] cnt = new int[15];
		boolean flush = true;
		for (Card c : h) {
			cnt[value(c)]++;
			if (c.getSuit() != h[0].getSuit()) {
				flush = false;
			}
		}

		
		List<Integer> order = new ArrayList<>();
		for (int k = 4; k >= 1; k--) {
			for (int v = 14; v >= 2; v--) {
				if (cnt[v] == k) {
					order.add(v);
				}
			}
		}

		int straightHigh = 0;
		if (order.size() == 5) {
			int hi = order.get(0), lo = order.get(4);
			if (hi - lo == 4) {
				straightHigh = hi;
			} else if (hi == 14 && order.get(1) == 5 && lo == 2) {
				straightHigh = 5;   
			}
		}

		int c0 = cnt[order.get(0)];
		int c1 = order.size() > 1 ? cnt[order.get(1)] : 0;
		Category cat;
		if (straightHigh > 0 && flush) cat = Category.STRAIGHT_FLUSH;
		else if (c0 == 4)              cat = Category.QUADS;
		else if (c0 == 3 && c1 == 2)   cat = Category.FULL_HOUSE;
		else if (flush)                cat = Category.FLUSH;
		else if (straightHigh > 0)     cat = Category.STRAIGHT;
		else if (c0 == 3)              cat = Category.TRIPS;
		else if (c0 == 2 && c1 == 2)   cat = Category.TWO_PAIR;
		else if (c0 == 2)              cat = Category.PAIR;
		else                           cat = Category.HIGH_CARD;

		List<Integer> tb = new ArrayList<>();
		if (straightHigh > 0) {
			tb.add(straightHigh);
		} else {
			tb.addAll(order);
		}
		long v = 0;
		for (int i = 0; i < 5; i++) {
			v = (v << 4) | (i < tb.size() ? tb.get(i) : 0);
		}
		return ((long) cat.ordinal() << 20) | v;
	}

	public static Category category(long score) {
		return Category.values()[(int) (score >>> 20)];
	}

	
	static int tie(long score, int i) {
		return (int) ((score >> (4 * (4 - i))) & 0xF);
	}

	
	public static String describe(long score) {
		int a = tie(score, 0), b = tie(score, 1);
		switch (category(score)) {
		case STRAIGHT_FLUSH: return a == 14 ? "Escalera real" : "Escalera de color al " + SING[a];
		case QUADS:          return "P\u00f3ker de " + PLUR[a];
		case FULL_HOUSE:     return "Full (" + PLUR[a] + " y " + PLUR[b] + ")";
		case FLUSH:          return "Color al " + SING[a];
		case STRAIGHT:       return "Escalera al " + SING[a];
		case TRIPS:          return "Tr\u00edo de " + PLUR[a];
		case TWO_PAIR:       return "Doble pareja (" + PLUR[a] + " y " + PLUR[b] + ")";
		case PAIR:           return "Pareja de " + PLUR[a];
		default:             return "Carta alta (" + SING[a] + ")";
		}
	}

	
	public static String describePreflop(List<Card> hole) {
		int a = value(hole.get(0)), b = value(hole.get(1));
		if (a == b) {
			return "Pareja de " + PLUR[a];
		}
		return "Carta alta (" + SING[Math.max(a, b)] + ")";
	}
}