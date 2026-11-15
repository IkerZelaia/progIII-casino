package table;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class PokerTableCanvas extends JPanel {

	private final List<PokerSeatView> seats = new ArrayList<>();
	private final JComponent center;

	PokerTableCanvas(int numSeats, JComponent center) {
		super(null);
		this.center = center;
		setBackground(new Color(18, 70, 38));
		for (int i = 0; i < numSeats; i++) {
			PokerSeatView s = new PokerSeatView();
			seats.add(s);
			add(s);
		}
		add(center);
	}

	List<PokerSeatView> getSeats() {
		return seats;
	}
	
	@Override //Ayuda con IA
	public void doLayout() {
		int w = getWidth(), h = getHeight();
		int n = seats.size();
		double cx = w / 2.0, cy = h / 2.0;
		double rx = (w - PokerSeatView.W) / 2.0 - 6;
		double ry = (h - PokerSeatView.H) / 2.0 - 4;
		for (int i = 0; i < n; i++) {
			// i = 0 abajo (el jugador); luego en sentido horario
			double a = Math.PI / 2 + 2 * Math.PI * i / n;
			int x = (int) Math.round(cx + rx * Math.cos(a)) - PokerSeatView.W / 2;
			int y = (int) Math.round(cy + ry * Math.sin(a)) - PokerSeatView.H / 2;
			seats.get(i).setBounds(x, y, PokerSeatView.W, PokerSeatView.H);
		}
		int cw = 330, ch = 150;
		center.setBounds((int) cx - cw / 2, (int) cy - ch / 2, cw, ch);
	}

	@Override //Ayuda con IA
	protected void paintComponent(Graphics g0) {
		super.paintComponent(g0);
		Graphics2D g = (Graphics2D) g0.create();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int mx = PokerSeatView.W / 2, my = PokerSeatView.H / 2 - 4;
		int w = getWidth() - 2 * mx, h = getHeight() - 2 * my;
		if (w <= 0 || h <= 0) {
			g.dispose();
			return;
		}
		g.setColor(new Color(70, 40, 20));
		g.fillOval(mx - 8, my - 8, w + 16, h + 16);
		g.setPaint(new GradientPaint(0, my, new Color(30, 110, 58), 0, my + h, new Color(14, 80, 40)));
		g.fillOval(mx, my, w, h);
		g.setColor(new Color(212, 175, 55, 140));
		g.drawOval(mx + 12, my + 12, w - 24, h - 24);
		g.dispose();
	}
}
