package poker;

public record PokerAction(Type type, int amount) {
	public enum Type { FOLD, RAISE, CHECK, CALL}
	
	public static PokerAction fold() {
		return new PokerAction(Type.FOLD, 0);
	}

	public static PokerAction raiseTo(int to) {
		return new PokerAction(Type.RAISE, to);
	}
	
	public static PokerAction check() {
		return new PokerAction(Type.CHECK, 0);
	}
	
	public static PokerAction call() {
		return new PokerAction(Type.CALL, 0);
	}
}
