package horse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.TreeMap;

public class Race {
	
	private static final int[] DISTANCES = {800, 1000, 1200, 1400, 1600, 1800, 2000, 2200, 2400, 2600, 3000};
	private static final String[] NAMES1 = {"Kentucky", "Breeders'", "Deusto", "L'arc", "Japan", "Hong Kong", "Dubai", "Royal", "Italian"};
	private static final String[] NAMES2 = {"Derby", "Stakes", "Cup", "Oaks", "Grand Prix", "Guineas"};
	
	private String name;
	private Random raceSeed;
	private int numHorse;
	private int distance;
	private Weather weather;
	private Surfice surfice;
	private ArrayList<Horse> horses;
	
	public ArrayList<Horse> resultado;

	
	public enum Weather{
		SOLEADO(1, 1, 1.1),
		NUBLADO(1, 1, 1),
		LLUVIOSO(0.9, 0.8, 1.2),
		TORMENTA(0.7, 0.7, 1.2),
		NIEVE(0.8, 0.9, 0.9);
		
		public double spdMult, accMult, stmMult;
		Weather(double spdMult, double accMult, double stmMult) {
			this.spdMult = spdMult;
			this.stmMult = stmMult;
			this.accMult = accMult;
		}
		
	}
	public enum Surfice{
		CESPED(1, 1, 0.9),
		TIERRA(1.2, 1.2, 1.3);
		
		public double spdMult, accMult, stmMult;
		Surfice(double spdMult, double accMult, double stmMult) {
			this.spdMult = spdMult;
			this.stmMult = stmMult;
			this.accMult = accMult;
		}
		
	}
	
	public Race() {
		raceSeed = new Random(System.nanoTime());
		resultado = new ArrayList<Horse>();
		
		distance  = DISTANCES[raceSeed.nextInt(DISTANCES.length)];
		if (distance <= 2000) {
			surfice = raceSeed.nextBoolean() ? Surfice.CESPED : Surfice.TIERRA;
		}
		else {
			surfice = Surfice.CESPED;
		}
		
		weather = Weather.values()[raceSeed.nextInt(Weather.values().length)];
	
		name = NAMES1[raceSeed.nextInt(NAMES1.length)] + " " + NAMES2[raceSeed.nextInt(NAMES2.length)];
				
		numHorse = raceSeed.nextInt(6, 14);
		horses = new ArrayList<Horse>();
		
		addHorses();
	}
	
	public Race(Horse betH) {
		raceSeed = new Random(System.nanoTime());
		resultado = new ArrayList<Horse>();
		
		distance  = DISTANCES[raceSeed.nextInt(DISTANCES.length)];
		if (distance <= 2000) {
			surfice = raceSeed.nextBoolean() ? Surfice.CESPED : Surfice.TIERRA;
		}
		else {
			surfice = Surfice.CESPED;
		}
		
		weather = Weather.values()[raceSeed.nextInt(Weather.values().length)];
	
		name = NAMES1[raceSeed.nextInt(NAMES1.length)] + " " + NAMES2[raceSeed.nextInt(NAMES2.length)];
				
		numHorse = raceSeed.nextInt(6, 15);
		horses = new ArrayList<Horse>();
		
		addHorses();
		
		horses.set(0, betH);
		
	}

	private void addHorses() {
		int rep = 0;
		for (int i = 0; i < numHorse; i++) {
			horses.add(new Horse());
			for ( int j = 0; j < i; j++) {
				if (!horses.get(i).equals(horses.get(j)) && horses.get(i).getName().equals(horses.get(j).getName())) {
					rep ++;
					horses.get(i).setName("Unique Horse nº" + rep);
				}
			}
		}
	}
	
	public ArrayList<Horse> getHorses() {
		return horses;
	}

	public int getNumHorse() {
		return numHorse;
	}

	public int getDistance() {
		return distance;
	}
	
	public String getName() {
		return name;
	}
	public Weather getWeather() {
		return weather;
	}

	public Surfice getSurfice() {
		return surfice;
	}
	
	public Random getSeed() {
		return this.raceSeed;
	}
	
	@Override
	public String toString() {
		return Integer.toString(distance) + "m — " + surfice + " — " + weather;
	}
}
