package table;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

import javax.swing.JComponent;

import card.Card;
import poker.PokerView;


 public class PokerSeatView extends JComponent {

	static final int W = 116, H = 116;
	private static final Color GOLD = new Color(212, 175, 55);
	private static final Color WIN = new Color(90, 255, 130);

	private PokerView.Seat seat;

	public PokerSeatView() {
		setOpaque(false);
		setPreferredSize(new Dimension(W, H));
	}

	public void setSeat(PokerView.Seat seat) {
		this.seat = seat;
		repaint();
	}

	@Override //Ayuda con IA
	protected void paintComponent(Graphics g0) {
		if (seat == null) return;
		Graphics2D g = (Graphics2D) g0.create();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		boolean dim = seat.folded() || seat.out();
		if (dim) g.setComposite(AlphaComposite.SrcOver.derive(0.5f));

		int w = getWidth(), h = getHeight();
		g.setColor(new Color(15, 35, 20, 225));
		g.fillRoundRect(0, 0, w - 1, h - 1, 16, 16);
		Color border = seat.winner() ? WIN : seat.turn() ? GOLD : new Color(255, 255, 255, 90);
		g.setColor(border);
		g.setStroke(new BasicStroke(seat.winner() || seat.turn() ? 3f : 1.5f));
		g.drawRoundRect(1, 1, w - 3, h - 3, 16, 16);

	
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
		g.setColor(seat.human() ? new Color(255, 230, 140) : Color.WHITE);
		drawCentered(g, seat.name(), w / 2, 15, w - 30);

		
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		g.setColor(GOLD);
		drawCentered(g, seat.chips() + " \u20ac", w / 2, 29, w - 8);

		if (!seat.role().isEmpty()) {
			g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
			FontMetrics fm = g.getFontMetrics();
			int tw = fm.stringWidth(seat.role()) + 8;
			g.setColor(seat.role().startsWith("D") ? Color.WHITE : new Color(70, 130, 220));
			g.fillRoundRect(w - tw - 4, 3, tw, 14, 10, 10);
			g.setColor(seat.role().startsWith("D") ? Color.BLACK : Color.WHITE);
			g.drawString(seat.role(), w - tw, 14);
		}


		List<Card> cards = seat.cards();
		int cw = 38, ch = 54, gap = 4;
		int total = 2 * cw + gap;
		int x0 = (w - total) / 2, y0 = 34;
		for (int i = 0; i < cards.size(); i++) {
			CardPainter.paint(g, cards.get(i), seat.faceUp(), x0 + i * (cw + gap), y0, cw, ch);
		}
		if (cards.isEmpty()) {
			g.setFont(new Font(Font.SERIF, Font.ITALIC, 12));
			g.setColor(new Color(255, 255, 255, 150));
			drawCentered(g, seat.out() ? "fuera" : seat.folded() ? "sin cartas" : "", w / 2, y0 + 32, w - 8);
		}

		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
		g.setColor(seat.allIn() && !seat.folded() ? new Color(255, 120, 90) : Color.WHITE);
		drawCentered(g, seat.status(), w / 2, 102, w - 6);
		if (seat.bet() > 0) {
			g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
			g.setColor(new Color(255, 225, 120));
			drawCentered(g, "Apuesta: " + seat.bet(), w / 2, 113, w - 6);
		}
		g.dispose();
	}

	private void drawCentered(Graphics2D g, String text, int cx, int baseline, int maxWidth) {
		FontMetrics fm = g.getFontMetrics();
		String t = text;
		while (t.length() > 1 && fm.stringWidth(t) > maxWidth) {
			t = t.substring(0, t.length() - 1);
		}
		g.drawString(t, cx - fm.stringWidth(t) / 2, baseline);
	}
}
