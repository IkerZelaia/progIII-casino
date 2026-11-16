package poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import card.Card;
import card.Deck;

public class PokerGame extends Thread{
	
	public static final int START_CHIPS = 1000;
	public static final int SMALL_BLIND = 10;
	public static final int BIG_BLIND = 20;
	public static final int TURN_SECONDS = 30;
	public static final int NEXT_HAND_SECONDS = 10;
	
	private static final String[] BOT_NAMES = {"Aitor", "Maite", "Joen", "Jon", "Iker", "Xabier", "Augusto"};

	private final List<PokerPlayer> players = new ArrayList<>();
	private final Deck deck;
	private final PokerListener listener;
	private final Random rnd = new Random();
	private final List<Card> board = new ArrayList<>();

	//Generado con Claude IA
	private final BlockingQueue<PokerAction> humanActions = new ArrayBlockingQueue<>(1);
	private final Semaphore handSignal = new Semaphore(0);
	//Hasta aqui
	private boolean running = true;
	private double delayScale = 1.0;
	
	private int dealer = -1, sbIdx = -1, bbIdx = -1;
	private int pot, currentBet, minRaise;
	private String phase = "Esperando inicio";
	private boolean handRunning;
	
	public PokerGame(int numPlayers, Deck deck, PokerListener listener) {
		super("PokerGame");
		setDaemon(true);   // si se cierra la ventana, el hilo no mantiene viva la JVM (IA)
		this.deck = deck;
		this.listener = listener;
		int n = Math.max(2, Math.min(8, numPlayers));
		players.add(new PokerPlayer("T\u00fa", true, START_CHIPS));
		for (int i = 1; i < n; i++) {
			players.add(new PokerPlayer(BOT_NAMES[(i - 1) % BOT_NAMES.length], false, START_CHIPS));
		}
		for (PokerPlayer p : players) {
			p.out = false;
		}
	}
	
	public void submitAction(PokerAction action) {
		humanActions.offer(action);
	}
	
	public void continueToNextHand() { //IA
		handSignal.release();
	}
	
	public void shutdown() {
		running = false;
		interrupt();
	}
	
	public void setDelayScale(double scale) {
		this.delayScale = scale;
	}
	public PokerView.Table initialView(){
		return snapshot(null, null);
	}
	public int totalChips() {
		int t = 0;
		for (PokerPlayer p: players)
			t += p.chips;
		return t;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			dealer = rnd.nextInt(players.size());
			while (running) {
				PokerPlayer me = players.get(0);
				int alive = 0;
				for (PokerPlayer p: players)
					if (p.chips > 0) {
						alive ++;
					}
				if (me.chips <= 0) {
					listener.onGameOver("Te has quedado sin fichas. \u00a1Fin de la partida!", false);
					return;
				}
				if (alive == 1) {
					listener.onGameOver("\u00a1Has ganado a todos los rivales! Te llevas la mesa.", true);
					return;
				}
				playHand();
				waitNextHand();
					
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();		
			}
	}
	
	private void pause (long ms) throws InterruptedException {
		long d = (long) (ms * delayScale);
		if (d > 0)
			Thread.sleep(d);
		else if (!running)
			throw new InterruptedException();
		
	}
	
	private void waitNextHand() throws InterruptedException{   //IA
		handSignal.drainPermits();
		PokerView.Table v = snapshot(null, null);
		listener.onUpdate(new PokerView.Table(v.seats(), v.community(), v.pot(), v.phase(), null,
				v.humanHand(), true));
		handSignal.tryAcquire((long) (NEXT_HAND_SECONDS * 1000 * delayScale), TimeUnit.MILLISECONDS);
		if (!running) throw new InterruptedException();
	}
	
	private void playHand() throws InterruptedException {
		board.clear();
		pot = 0;
		currentBet = 0;
		minRaise = BIG_BLIND;
		for (PokerPlayer p: players)
			p.resetForHand();
		
		dealer = nextIn(dealer);
		int inHand= 0;
		for (PokerPlayer p: players) 
			if (!p.out)
				inHand ++;
			if (inHand == 2) {
				sbIdx = dealer;
				bbIdx = nextIn(dealer);
			}
			else {
				sbIdx = nextIn(dealer);
				bbIdx = nextIn(sbIdx);
			}
			handRunning = true;
			phase = "Pre-flop";

			synchronized (deck) {
				deck.shuffle();
			}
			postBlind(players.get(sbIdx), SMALL_BLIND, "Ciega peque\u00f1a");
			postBlind(players.get(bbIdx), BIG_BLIND, "Ciega grande");
			currentBet = BIG_BLIND;
			listener.onMessage("Nueva mano. " + players.get(dealer).name + " tiene el bot\u00f3n. Ciegas "
					+ SMALL_BLIND + "/" + BIG_BLIND + ".");
			publish(null, null);
			pause(500);
		
			for (int round = 0; round < 2; round++) {
				int idx = dealer;
				for (int i = 0; i < countIn(); i++) {
					idx = nextIn(idx);
					players.get(idx).hole.add(draw());
					publish(null,null);
					pause(180);
				}
			}
			
			int first = (countIn() == 2) ? sbIdx : nextIn(bbIdx);
			bettingRound(first);
			
			String[] phases = { "Flop", "Turn", "River" };
			int[] cardsToDeal = { 3, 1, 1 };
			for (int st = 0; st < 3 && contenders() > 1; st++) {
				newStreet();
				phase = phases[st];
				for (int k = 0; k < cardsToDeal[st]; k++) {
					board.add(draw());
					publish(null, null);
					pause(350);
				}
				if (canActCount() >= 2) {
					bettingRound(nextIn(dealer));
				} else {
					revealContenders();
					pause(900);
				}
			}

			showdown();
		}

		private void newStreet() {
			for (PokerPlayer p : players) {
				p.bet = 0;
				p.acted = false;
				if (!p.folded && !p.allIn) p.lastAction = "";
			}
			currentBet = 0;
			minRaise = BIG_BLIND;
			publish(null, null);
		}

		private Card draw() throws InterruptedException {
			if (!running) throw new InterruptedException();
			synchronized (deck) {
				return deck.draw();
			}
		}

		private void postBlind(PokerPlayer p, int amount, String label) {
			int pay = Math.min(amount, p.chips);
			putChips(p, pay);
			p.lastAction = p.allIn ? "All-in " + pay : label + " " + pay;
		}

		private void putChips(PokerPlayer p, int amount) {
			p.chips -= amount;
			p.bet += amount;
			p.contributed += amount;
			pot += amount;
			if (p.chips == 0) p.allIn = true;
		}

		private void bettingRound(int firstIdx) throws InterruptedException { //Asistencia con IA
			for (PokerPlayer p : players) p.acted = false;
			int raises = 0;
			int i = firstIdx;
			while (true) {
				if (contenders() <= 1 || roundOver()) 
					return;
				PokerPlayer p = players.get(i);
				if (needsToAct(p)) {
					PokerAction a = p.human ? askHuman(p) : askBot(p, raises);
					if (apply(p, a)) raises++;
					publish(null, null);
					listener.onMessage(p.name + ": " + p.lastAction);
					pause(250);
				}
				i = (i + 1) % players.size();
			}
		}

		private boolean needsToAct(PokerPlayer p) {
			return !p.folded && !p.allIn && (!p.acted || p.bet < currentBet);
		}

		private boolean roundOver() {
			int able = 0;
			boolean pending = false;
			PokerPlayer only = null;
			for (PokerPlayer p : players) {
				if (!p.folded && !p.allIn) {
					able++;
					only = p;
					if (needsToAct(p)) 
						pending = true;
				}
			}
			if (able == 0) 
				return true;
			if (able == 1) 
				return only.bet >= currentBet; 
			return !pending;
		}

		
		private boolean apply(PokerPlayer p, PokerAction a) { 
			int toCall = currentBet - p.bet;
			PokerAction.Type type = a.type();
			if (type == PokerAction.Type.CHECK && toCall > 0) 
				type = PokerAction.Type.CALL;
			if (type == PokerAction.Type.CALL && toCall <= 0) 
				type = PokerAction.Type.CHECK;

			boolean raised = false;
			switch (type) {
			case FOLD:
				p.folded = true;
				p.lastAction = "Se retira";
				break;
			case CHECK:
				p.lastAction = "Pasa";
				break;
			case CALL: {
				int pay = Math.min(toCall, p.chips);
				putChips(p, pay);
				p.lastAction = p.allIn ? "All-in " + p.bet : "Iguala " + p.bet;
				break;
			}
			default: { // RAISE, Asistencia con IA
				int maxTo = p.bet + p.chips;
				int raiseTo = Math.min(a.amount(), maxTo);
				if (raiseTo < currentBet + minRaise && raiseTo < maxTo) {
					raiseTo = Math.min(currentBet + minRaise, maxTo);
				}
				if (raiseTo <= currentBet) {   
					int pay = Math.min(toCall, p.chips);
					putChips(p, pay);
					p.lastAction = p.allIn ? "All-in " + p.bet : "Iguala " + p.bet;
					break;
				}
				boolean opening = currentBet == 0;
				int raiseSize = raiseTo - currentBet;
				putChips(p, raiseTo - p.bet);
				if (raiseSize >= minRaise) {
					minRaise = raiseSize;
					for (PokerPlayer o : players) {
						if (o != p && !o.folded && !o.allIn) o.acted = false;  
					}
				}
				currentBet = raiseTo;
				raised = true;
				p.lastAction = p.allIn ? "All-in " + p.bet : (opening ? "Apuesta " : "Sube a ") + p.bet;
			}
			}
			p.acted = true;
			return raised;
		}

		private PokerAction askBot(PokerPlayer p, int raises) throws InterruptedException {
			publish(p, null);
			pause(450 + rnd.nextInt(650));    // el bot "piensa"
			int toCall = Math.min(currentBet - p.bet, p.chips);
			int maxTo = p.bet + p.chips;
			int minRaiseTo = Math.min(currentBet + minRaise, maxTo);
			PokerAction a = p.bot.decide(p.hole, board, currentBet - p.bet, pot, p.chips, p.bet,
					currentBet, minRaiseTo, BIG_BLIND, raises);
			if (a.type() == PokerAction.Type.FOLD && toCall == 0) return PokerAction.check();
			return a;
		}

		private PokerAction askHuman(PokerPlayer p) throws InterruptedException { 
			humanActions.clear();
			int toCall = Math.min(currentBet - p.bet, p.chips);
			int maxTo = p.bet + p.chips;
			int minRaiseTo = Math.min(currentBet + minRaise, maxTo);
			listener.onMessage("Tu turno.");
			for (int s = TURN_SECONDS; s > 0; s--) {
				publish(p, new PokerView.HumanTurn(toCall, currentBet, minRaiseTo, maxTo, p.chips, s));
				PokerAction a = humanActions.poll(1000, TimeUnit.MILLISECONDS);
				if (!running) throw new InterruptedException();
				if (a != null) return a;
			}
			listener.onMessage("Tiempo agotado.");
			return toCall == 0 ? PokerAction.check() : PokerAction.fold();
		}

		private void revealContenders() {
			for (PokerPlayer p : players) {
				if (!p.folded) p.revealed = true;
			}
			publish(null, null);
		}

		private void showdown() throws InterruptedException {
			phase = "Showdown";
			for (PokerPlayer p : players) {
				p.bet = 0;
				if (!p.folded && !p.allIn) p.lastAction = "";
			}
			List<PokerPlayer> cont = new ArrayList<>();
			for (PokerPlayer p : players) if (!p.folded) cont.add(p);

			Map<PokerPlayer, Integer> won = new HashMap<>();
			Map<PokerPlayer, Long> scores = new HashMap<>();
			boolean contested = cont.size() > 1;

			if (!contested) {
				won.put(cont.get(0), pot);
			} else {
				for (PokerPlayer p : cont) {
					p.revealed = true;
					List<Card> all = new ArrayList<>(p.hole);
					all.addAll(board);
					scores.put(p, HandEvaluator.evaluate(all));
				}
				awardPots(cont, scores, won);
			}

			StringBuilder msg = new StringBuilder();
			for (PokerPlayer p : players) {
				Integer w = won.get(p);
				if (w != null && w > 0) {
					p.chips += w;
					p.winner = true;
					if (msg.length() > 0) msg.append("  |  ");
					msg.append(p.name).append(" gana ").append(w);
					if (contested) msg.append(" con ").append(HandEvaluator.describe(scores.get(p)));
					else msg.append(" (todos se retiran)");
				}
			}
			pot = 0;
			publish(null, null);
			listener.onMessage(msg.toString());
			pause(1200);
		}

		
		private void awardPots(List<PokerPlayer> cont, Map<PokerPlayer, Long> scores, Map<PokerPlayer, Integer> won) { //Generado con IA
			TreeSet<Integer> levels = new TreeSet<>();
			for (PokerPlayer p : players) if (p.contributed > 0) levels.add(p.contributed);

			int prev = 0, carry = 0;
			for (int level : levels) {
				int part = carry;
				for (PokerPlayer p : players) {
					part += Math.min(p.contributed, level) - Math.min(p.contributed, prev);
				}
				carry = 0;
				prev = level;

				List<PokerPlayer> eligible = new ArrayList<>();
				for (PokerPlayer p : cont) if (p.contributed >= level) eligible.add(p);
				if (eligible.isEmpty()) {
					carry = part;
					continue;
				}
				giveToBest(eligible, scores, won, part);
			}
			if (carry > 0) giveToBest(cont, scores, won, carry);
		}

		private void giveToBest(List<PokerPlayer> eligible, Map<PokerPlayer, Long> scores,
				Map<PokerPlayer, Integer> won, int amount) {
			long best = Long.MIN_VALUE;
			for (PokerPlayer p : eligible) best = Math.max(best, scores.get(p));
			List<PokerPlayer> winners = new ArrayList<>();
			int idx = dealer;
			for (int k = 0; k < players.size(); k++) {
				idx = (idx + 1) % players.size();
				PokerPlayer p = players.get(idx);
				if (eligible.contains(p) && scores.get(p) == best) 
					winners.add(p);
			}
			int share = amount / winners.size();
			int rem = amount % winners.size();
			for (int k = 0; k < winners.size(); k++) {
				won.merge(winners.get(k), share + (k < rem ? 1 : 0), Integer::sum);
			}
		}


		private int nextIn(int from) {
			int i = from;
			for (int k = 0; k < players.size(); k++) {
				i = (i + 1) % players.size();
				if (!players.get(i).out) return i;
			}
			return from;
		}

		private int countIn() {
			int n = 0;
			for (PokerPlayer p : players) if (!p.out) n++;
			return n;
		}

		private int contenders() {
			int n = 0;
			for (PokerPlayer p : players) if (!p.folded) n++;
			return n;
		}

		private int canActCount() {
			int n = 0;
			for (PokerPlayer p : players) if (!p.folded && !p.allIn) n++;
			return n;
		}


		private void publish(PokerPlayer turn, PokerView.HumanTurn ht) {
			listener.onUpdate(snapshot(turn, ht));
		}

		private PokerView.Table snapshot(PokerPlayer turn, PokerView.HumanTurn ht) { //Asistencia con IA
			List<PokerView.Seat> seats = new ArrayList<>();
			for (int i = 0; i < players.size(); i++) {
				PokerPlayer p = players.get(i);
				String role = "";
				if (handRunning) {
					if (i == dealer && i == sbIdx) role = "D\u00b7SB";
					else if (i == dealer) role = "D";
					else if (i == sbIdx) role = "SB";
					else if (i == bbIdx) role = "BB";
				}
				boolean dead = p.out || (p.chips == 0 && "Showdown".equals(phase));
				String status;
				if (dead) status = "Eliminado";
				else if (p.folded) status = "Retirado";
				else status = p.lastAction;
				List<Card> cards = (p.folded || p.out) ? List.of() : List.copyOf(p.hole);
				seats.add(new PokerView.Seat(i, p.name, p.human, p.chips, p.bet, p.folded, p.allIn, dead,
						p == turn, p.winner, role, status, cards, p.human || p.revealed));
			}
			PokerPlayer me = players.get(0);
			String hand = "";
			if (handRunning && !me.folded && me.hole.size() == 2) {
				if (board.size() >= 3) {
					List<Card> all = new ArrayList<>(me.hole);
					all.addAll(board);
					hand = HandEvaluator.describe(HandEvaluator.evaluate(all));
				} else {
					hand = HandEvaluator.describePreflop(me.hole);
				}
			}
			return new PokerView.Table(Collections.unmodifiableList(seats), List.copyOf(board), pot, phase, ht,
					hand, false);
		}
	
	}

