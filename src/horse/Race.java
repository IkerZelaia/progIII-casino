package horse;

import java.util.Random;

public class Race {
	
	private static final int[] DISTANCES = {800, 1000, 1200, 1400, 1600, 1800, 2000, 2200, 2400, 2600, 3000};
	
	
	private Random raceSeed;
	private int numHorse;
	private int distance;
	private Weather weather;
	private Surfice surfice;
	private Horse[] horses;
	
	public enum Weather{
		SUNNY(1, 1, 0.9),
		CLOUDY(1, 1, 1),
		RAINY(0.9, 0.8, 0.8),
		STORMY(0.7, 0.7, 1),
		SNOWY(0.8, 0.9, 0.9);
		
		public double spdMult, accMult, stmMult;
		Weather(double spdMult, double accMult, double stmMult) {
			this.spdMult = spdMult;
			this.stmMult = stmMult;
			this.accMult = accMult;
		}
		
	}
	public enum Surfice{
		TURF(1, 1, 1.1),
		DIRT(1.1, 1.1, 0.7);
		
		public double spdMult, accMult, stmMult;
		Surfice(double spdMult, double accMult, double stmMult) {
			this.spdMult = spdMult;
			this.stmMult = stmMult;
			this.accMult = accMult;
		}
		
	}
	
	public Race() {
		raceSeed = new Random(System.nanoTime());
		
		surfice = Surfice.values()[raceSeed.nextInt(Surfice.values().length)];
		weather = Weather.values()[raceSeed.nextInt(Weather.values().length)];
		distance = DISTANCES[surfice == Surfice.TURF ? raceSeed.nextInt(DISTANCES.length) : raceSeed.nextInt(DISTANCES.length/2)];
		
		numHorse = raceSeed.nextInt(6, 14);
		horses = new Horse[numHorse];
		
		addHorses();
	}
	
	private void addHorses() {
		for (int i = 0; i < numHorse; i++) {
			horses[i]  = new Horse();
		}
	}
	
	public Horse[] getHorses() {
		return horses;
	}

	public int getNumHorse() {
		return numHorse;
	}

	public int getDistance() {
		return distance;
	}

	public Weather getWeather() {
		return weather;
	}

	public Surfice getSurfice() {
		return surfice;
	}
}
