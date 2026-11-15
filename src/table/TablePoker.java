package table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import card.Deck;
import main.Window;
import poker.PokerAction;
import poker.PokerGame;
import poker.PokerListener;
import poker.PokerView;


public class TablePoker extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final Color COLOR_BARRA = new Color(15, 65, 32);
	private static final Color COLOR_DORADO = new Color(212, 175, 55);
	private static final Color COLOR_BURDEOS = new Color(110, 15, 30);
	private static final Color COLOR_BURDEOS_HOVER = new Color(145, 25, 45);
	private static final Color COLOR_ACCION = new Color(0, 100, 0);
	private static final Color COLOR_ACCION_HOVER = new Color(0, 135, 0);

	private final Window window;
	private final int playerNum;
	private final Deck deck = new Deck();

	private PokerGame game;
	private GameListener listener;

	private final PokerTableCanvas canvas;
	private final PokerBoardView boardView = new PokerBoardView();
	private final JLabel lblFase = new JLabel("", SwingConstants.CENTER);
	private final JLabel lblBote = new JLabel("", SwingConstants.CENTER);

	private final JLabel lblMano = new JLabel(" ");
	private final JLabel lblMensaje = new JLabel("Pulsa \u00abEmpezar partida\u00bb", SwingConstants.CENTER);
	private final JLabel lblTiempo = new JLabel(" ", SwingConstants.RIGHT);

	private final JButton btnVolverMenu = new JButton("Volver al Men\u00fa");
	private final JButton btnNueva = new JButton("Empezar partida");
	private final JButton btnFold = new JButton("Retirarse");
	private final JButton btnCheckCall = new JButton("Pasar");
	private final JButton btnRaise = new JButton("Subir");
	private final JButton btnAllIn = new JButton("All-in");
	private final JButton btnNext = new JButton("Siguiente mano");
	private final JSlider sliderRaise = new JSlider(0, 100, 0);

	private PokerView.HumanTurn humanTurn;
	private boolean wasMyTurn;
	private boolean started;

	public TablePoker(int playerNum, Window window) {
		super(new BorderLayout());
		this.window = window;
		this.playerNum = Math.max(2, Math.min(8, playerNum));

		
		JPanel top = new JPanel(new BorderLayout());
		top.setBackground(COLOR_BARRA);
		top.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 2, 0, COLOR_DORADO),
				BorderFactory.createEmptyBorder(8, 12, 8, 12)));

		estilizarBoton(btnVolverMenu, COLOR_BURDEOS, COLOR_BURDEOS_HOVER);
		btnVolverMenu.addActionListener(e -> volverAlMenu());

		JLabel titulo = new JLabel("\u2660  POKER \u00b7 TEXAS HOLD'EM  \u2665", SwingConstants.CENTER);
		titulo.setFont(new Font(Font.SERIF, Font.BOLD, 30));
		titulo.setForeground(COLOR_DORADO);

		estilizarBoton(btnNueva, COLOR_ACCION, COLOR_ACCION_HOVER);
		btnNueva.addActionListener(e -> onNuevaPartida());

		top.add(btnVolverMenu, BorderLayout.WEST);
		top.add(titulo, BorderLayout.CENTER);
		top.add(btnNueva, BorderLayout.EAST);

		
		lblFase.setFont(new Font(Font.SERIF, Font.ITALIC, 16));
		lblFase.setForeground(new Color(255, 255, 255, 200));
		lblBote.setFont(new Font(Font.SERIF, Font.BOLD, 22));
		lblBote.setForeground(COLOR_DORADO);
		Dimension centroLbl = new Dimension(300, 28);
		lblBote.setPreferredSize(centroLbl);
		lblBote.setMaximumSize(centroLbl);
		lblFase.setPreferredSize(new Dimension(300, 22));
		lblFase.setMaximumSize(new Dimension(300, 22));

		JPanel center = new JPanel();
		center.setOpaque(false);
		center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
		lblFase.setAlignmentX(CENTER_ALIGNMENT);
		lblBote.setAlignmentX(CENTER_ALIGNMENT);
		boardView.setAlignmentX(CENTER_ALIGNMENT);
		center.add(lblBote);
		center.add(Box.createVerticalStrut(6));
		center.add(boardView);
		center.add(Box.createVerticalStrut(6));
		center.add(lblFase);

		canvas = new PokerTableCanvas(this.playerNum, center);

		
		JPanel bottom = new JPanel(new BorderLayout(0, 4));
		bottom.setBackground(COLOR_BARRA);
		bottom.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(2, 0, 0, 0, COLOR_DORADO),
				BorderFactory.createEmptyBorder(6, 15, 8, 15)));

		Font fInfo = new Font(Font.SERIF, Font.BOLD, 16);
		lblMano.setFont(fInfo);
		lblMano.setForeground(Color.WHITE);
		lblMensaje.setFont(new Font(Font.SERIF, Font.ITALIC, 16));
		lblMensaje.setForeground(new Color(255, 240, 190));
		lblTiempo.setFont(fInfo);
		lblTiempo.setForeground(Color.WHITE);
		lblMano.setPreferredSize(new Dimension(290, 22));
		lblTiempo.setPreferredSize(new Dimension(70, 22));

		JPanel info = new JPanel(new BorderLayout());
		info.setOpaque(false);
		info.add(lblMano, BorderLayout.WEST);
		info.add(lblMensaje, BorderLayout.CENTER);
		info.add(lblTiempo, BorderLayout.EAST);

		estilizarBoton(btnFold, COLOR_BURDEOS, COLOR_BURDEOS_HOVER);
		estilizarBoton(btnCheckCall, COLOR_ACCION, COLOR_ACCION_HOVER);
		estilizarBoton(btnRaise, COLOR_ACCION, COLOR_ACCION_HOVER);
		estilizarBoton(btnAllIn, COLOR_BURDEOS, COLOR_BURDEOS_HOVER);
		estilizarBoton(btnNext, new Color(30, 80, 140), new Color(45, 110, 185));

		sliderRaise.setOpaque(false);
		sliderRaise.setPreferredSize(new Dimension(200, 30));
		sliderRaise.addChangeListener(e -> actualizarTextoSubir());

		JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 4));
		botones.setOpaque(false);
		botones.add(btnFold);
		botones.add(btnCheckCall);
		botones.add(sliderRaise);
		botones.add(btnRaise);
		botones.add(btnAllIn);
		botones.add(btnNext);

		bottom.add(info, BorderLayout.NORTH);
		bottom.add(botones, BorderLayout.CENTER);

		add(top, BorderLayout.NORTH);
		add(canvas, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);

		btnFold.addActionListener(e -> enviar(PokerAction.fold()));
		btnCheckCall.addActionListener(e -> {
			if (humanTurn != null) {
				enviar(humanTurn.toCall() == 0 ? PokerAction.check() : PokerAction.call());
			}
		});
		btnRaise.addActionListener(e -> enviar(PokerAction.raiseTo(sliderRaise.getValue())));
		btnAllIn.addActionListener(e -> {
			if (humanTurn != null) {
				enviar(PokerAction.raiseTo(humanTurn.maxRaiseTo()));
			}
		});
		btnNext.addActionListener(e -> {
			btnNext.setVisible(false);
			if (game != null) {
				game.continueToNextHand();
			}
		});

		crearPartida();
		aplicarVista(game.initialView());
		habilitarControles(false);
		btnNext.setVisible(false);
	}

	private void crearPartida() {
		if (listener != null) {
			listener.active = false;     
		}
		if (game != null) {
			game.shutdown();
		}
		listener = new GameListener();
		game = new PokerGame(playerNum, deck, listener);
	}

	private void onNuevaPartida() {
		if (started) {
			int r = JOptionPane.showConfirmDialog(this, "\u00bfEmpezar una partida nueva? Se perder\u00e1 la actual.",
					"Nueva partida", JOptionPane.YES_NO_OPTION);
			if (r != JOptionPane.YES_OPTION) {
				return;
			}
		}
		arrancarNueva();
	}

	private void arrancarNueva() {
		crearPartida();
		started = true;
		humanTurn = null;
		wasMyTurn = false;
		btnNext.setVisible(false);
		btnNueva.setText("Nueva partida");
		lblMensaje.setText("\u00a1Que empiece el juego!");
		aplicarVista(game.initialView());
		game.start();
	}

	private void volverAlMenu() {
		detener();
		window.mostrarMenu();
	}

	private void detener() {
		if (listener != null) {
			listener.active = false;
		}
		if (game != null) {
			game.shutdown();
		}
	}

	
	@Override
	public void removeNotify() {
		detener();
		super.removeNotify();
	}

	private class GameListener implements PokerListener {
		volatile boolean active = true;
//Asistencia de la IA
		@Override
		public void onUpdate(PokerView.Table view) {
			SwingUtilities.invokeLater(() -> {
				if (active) aplicarVista(view);
			});
		}

		@Override
		public void onMessage(String text) {
			SwingUtilities.invokeLater(() -> {
				if (active) lblMensaje.setText(text);
			});
		}

		@Override
		public void onGameOver(String text, boolean humanWon) {
			SwingUtilities.invokeLater(() -> {
				if (!active) return;
				lblMensaje.setText(text);
				habilitarControles(false);
				Object[] opciones = { "Nueva partida", "Volver al men\u00fa" };
				int r = JOptionPane.showOptionDialog(TablePoker.this, text,
						humanWon ? "\u00a1Victoria!" : "Fin de la partida", JOptionPane.DEFAULT_OPTION,
						humanWon ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE, null, opciones,
						opciones[0]);
				if (r == 0) {
					arrancarNueva();
				} else if (r == 1) {
					volverAlMenu();
				}
			});
		}
	}


	private void aplicarVista(PokerView.Table v) {
		List<PokerSeatView> views = canvas.getSeats();
		for (PokerView.Seat s : v.seats()) {
			if (s.index() < views.size()) {
				views.get(s.index()).setSeat(s);
			}
		}
		boardView.setCards(v.community());
		lblFase.setText(v.phase());
		lblBote.setText(v.pot() > 0 ? "Bote: " + v.pot() + " \u20ac" : " ");
		lblMano.setText(v.humanHand().isEmpty() ? " " : "Tu mano: " + v.humanHand());

		humanTurn = v.humanTurn();
		boolean myTurn = humanTurn != null;
		if (myTurn) {
			configurarTurno(humanTurn, !wasMyTurn);
			lblTiempo.setText("\u23f1 " + humanTurn.secondsLeft() + " s");
			lblTiempo.setForeground(humanTurn.secondsLeft() <= 5 ? new Color(255, 110, 90) : Color.WHITE);
		} else {
			lblTiempo.setText(" ");
		}
		wasMyTurn = myTurn;
		habilitarControles(myTurn);
		btnNext.setVisible(v.waitingNextHand());
	}

//Asistencia con IA
	private void configurarTurno(PokerView.HumanTurn t, boolean nuevoTurno) {
		btnCheckCall.setText(t.toCall() == 0 ? "Pasar"
				: t.toCall() >= t.chips() ? "Igualar (all-in " + t.toCall() + ")" : "Igualar " + t.toCall());

		boolean puedeSubir = t.minRaiseTo() < t.maxRaiseTo();
		if (puedeSubir && nuevoTurno) {
			sliderRaise.setMinimum(t.minRaiseTo());
			sliderRaise.setMaximum(t.maxRaiseTo());
			sliderRaise.setValue(t.minRaiseTo());
		}
		btnRaise.putClientProperty("puedeSubir", puedeSubir);
		actualizarTextoSubir();
	}

	private void actualizarTextoSubir() {
		if (humanTurn == null) return;
		btnRaise.setText((humanTurn.currentBet() == 0 ? "Apostar " : "Subir a ") + sliderRaise.getValue());
	}

	private void habilitarControles(boolean turno) {
		boolean puedeSubir = turno && Boolean.TRUE.equals(btnRaise.getClientProperty("puedeSubir"));
		btnFold.setEnabled(turno);
		btnCheckCall.setEnabled(turno);
		btnAllIn.setEnabled(turno);
		btnRaise.setEnabled(puedeSubir);
		sliderRaise.setEnabled(puedeSubir);
		if (!turno) {
			btnCheckCall.setText("Pasar");
			btnRaise.setText("Subir");
		}
	}

	private void enviar(PokerAction a) {
		if (game != null && humanTurn != null) {
			humanTurn = null;
			habilitarControles(false);   // evita dobles clics (IA)
			game.submitAction(a);
		}
	}

	

	private void estilizarBoton(JButton boton, Color fondo, Color hover) {
		boton.setFont(new Font(Font.SERIF, Font.BOLD, 16));
		boton.setForeground(Color.WHITE);
		boton.setBackground(fondo);
		boton.setFocusPainted(false);
		boton.setOpaque(true);
		boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		boton.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(COLOR_DORADO, 2),
				BorderFactory.createEmptyBorder(7, 14, 7, 14)));
		boton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (boton.isEnabled()) boton.setBackground(hover);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				boton.setBackground(fondo);
			}
		});
	}

	public Window getWindow() {
		return window;
	}

	public int getPlayerNum() {
		return playerNum;
	}
}