package poker;

public interface PokerListener {

	void onUpdate(PokerView.Table view);
	
	void onMessage(String text);
	
	void onGameOver(String text, boolean humanWon);
}
