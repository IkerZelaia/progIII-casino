package table;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JComponent;

import card.Card;


public class PokerBoardView extends JComponent {

	static final int CW = 56, CH = 78, GAP = 6;
	private List<Card> cards = List.of();

	PokerBoardView() {
		setOpaque(false);
		setPreferredSize(new Dimension(5 * CW + 4 * GAP, CH));
	}

	void setCards(List<Card> cards) {
		this.cards = cards;
		repaint();
	}

	@Override //Ayuda IA
	protected void paintComponent(Graphics g0) {
		Graphics2D g = (Graphics2D) g0.create();
		int total = 5 * CW + 4 * GAP;
		int x0 = (getWidth() - total) / 2;
		for (int i = 0; i < 5; i++) {
			Card c = i < cards.size() ? cards.get(i) : null;
			CardPainter.paint(g, c, true, x0 + i * (CW + GAP), 0, CW, CH);
		}
		g.dispose();
	}
}
