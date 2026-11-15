package table;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import card.Card;

final class CardPainter { //Asistencia con IA (toda la clase)

	private static final Color BACK_RED = new Color(150, 20, 35);
	private static final Color GOLD = new Color(212, 175, 55);

	private CardPainter() {}


	static void paint(Graphics2D g, Card card, boolean faceUp, int x, int y, int w, int h) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		int arc = Math.max(6, w / 6);

		if (card == null) {
			g.setColor(new Color(255, 255, 255, 40));
			g.fillRoundRect(x, y, w, h, arc, arc);
			g.setColor(new Color(255, 255, 255, 110));
			g.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f, new float[] { 5f, 4f }, 0f));
			g.drawRoundRect(x, y, w - 1, h - 1, arc, arc);
			g.setStroke(new BasicStroke(1f));
			return;
		}

		if (!faceUp) {
			g.setColor(BACK_RED);
			g.fillRoundRect(x, y, w, h, arc, arc);
			g.setColor(Color.WHITE);
			g.drawRoundRect(x, y, w - 1, h - 1, arc, arc);
			g.setColor(GOLD);
			g.drawRoundRect(x + 3, y + 3, w - 7, h - 7, arc - 2, arc - 2);
			// trama de rombos (recortada al interior de la carta)
			java.awt.Shape oldClip = g.getClip();
			g.clipRect(x + 4, y + 4, w - 8, h - 8);
			g.setColor(new Color(255, 255, 255, 60));
			int step = Math.max(6, w / 5);
			for (int i = -h; i < w + h; i += step) {
				g.drawLine(x + i, y, x + i + h, y + h);
				g.drawLine(x + i + h, y, x + i, y + h);
			}
			g.setClip(oldClip);
			return;
		}

		g.setColor(Color.WHITE);
		g.fillRoundRect(x, y, w, h, arc, arc);
		g.setColor(new Color(120, 120, 120));
		g.drawRoundRect(x, y, w - 1, h - 1, arc, arc);

		Color col = card.getSuit().getColor();
		String rank = card.getRank().getSymbol();
		String suit = card.getSuit().getSymbol();
		g.setColor(col);

		Font rankFont = new Font(Font.SERIF, Font.BOLD, Math.max(10, (int) (h * 0.24)));
		g.setFont(rankFont);
		FontMetrics fm = g.getFontMetrics();
		g.drawString(rank, x + Math.max(3, w / 14), y + fm.getAscent() + 1);

		Font smallSuit = new Font(Font.SERIF, Font.PLAIN, Math.max(9, (int) (h * 0.2)));
		g.setFont(smallSuit);
		fm = g.getFontMetrics();
		g.drawString(suit, x + Math.max(3, w / 14), y + (int) (h * 0.24) + fm.getAscent() + 1);

		Font big = new Font(Font.SERIF, Font.PLAIN, (int) (h * 0.5));
		g.setFont(big);
		fm = g.getFontMetrics();
		g.drawString(suit, x + (w - fm.stringWidth(suit)) / 2 + w / 12, y + h - (int) (h * 0.16));
	}
}