package horse;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import main.Main;

public class Horse implements Comparable<Horse>{
	private static final Color CHESTNUT = new Color(130, 110, 75);
	private static final Color WHITE = new Color(10, 10, 10);
	private static final Color DARK = new Color(25, 22, 20);
	private static final Color GRAY = new Color(175, 175, 175);
	private static final Color GINGER = new Color(170, 125, 45);
	private static final Color CREAM = new Color(215, 185, 140);
	
	private static final Color[] BASE_COLORS = {CHESTNUT, WHITE, DARK, GRAY, GINGER, CREAM};
	private static final Color[] MANE_COLORS = {WHITE, DARK, CHESTNUT, GRAY};
	private static final String[] NAMES1 = {"", "Sea", "Sunday", "American", "War", "Deep", "Gold", "Vaguely", "Forever", "Admire", "Chestnut", "Native", "Black", "Red"};
	private static final String[] NAMES2 = {"Biscuit", "Silence", "Pharaoh", "Admiral", "Secretariat", "Eclipse", "Assault", "Impact", "Arabian", "Turk", "Sultan", "Young", "Opera", "Tempo", "Soverignty", "City", "Dancer"};
	
	private Random horseSeed;
	
	private String name;
	private ImageIcon image;
	private BufferedImage iBase, iMane, iMaskMain, iMaskSec;
	private JProgressBar raceProgressBar;
	public int speed = 1500, accel = 30, stam = 750, potential;
	public double consistency;
	public int winningStreak = 0;
	public double distanceRun = 0;
	public double performance;
	public int targetSpeed, currentSpeed;
	public double hp;
	public boolean finished;
	public boolean isTired;
	public Style style;
	
	public enum Style{
		PUNTERO(1, 1, 1.3, 0.7),
		ACECHADOR(1, 1.2, 1, 1.5),
		CERRADOR(0.8, 2, 0.6, 2.5);
		
		public double accMultEarly, accMultLate, stmMult, spdMultLate;
		
		Style(double accMultEarly, double accMultLate, double stmMult , double spdMultLate){
			this.accMultEarly= accMultEarly;
			this.accMultLate = accMultLate;
			this.stmMult = stmMult;
			this.spdMultLate = spdMultLate;
		}
		
		
	}
	public Horse() {
		horseSeed = new Random();
		image = drawImage();
		
		
		
		
		if(!Main.debugPerfectHorses()) {
			speed += horseSeed.nextInt(0, 500);
			
			accel += horseSeed.nextInt(0, 20);
			
			stam += horseSeed.nextInt(0, 1250);
			
			potential = speed + accel*40 + stam;
			
			
			
			consistency = horseSeed.nextInt(75, 100)/100;
		}
		else {
			speed = 2000;
			
			accel = 50;
			
			stam = 2000;
			
			potential = speed + accel*40 + stam;
			
			
			
			consistency = 0.5;
		}
		
		
		style = Style.values()[horseSeed.nextInt(Style.values().length)];
		
		
		name = NAMES1[horseSeed.nextInt(NAMES1.length)] + " " + NAMES2[horseSeed.nextInt(NAMES2.length)];
	}
	
	private ImageIcon drawImage() {
		iBase = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
		iMane = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
		File fBase = new File("res/horse_base.png");
		File fMane = new File("res/horse_mane.png");
		File fMask1 = new File("res/horse_mask_main.png");
		File fMask2 = new File("res/horse_mask_sec.png");
		
		try {
			iBase = ImageIO.read(fBase);
			iMane = ImageIO.read(fMane);
			iMaskMain = ImageIO.read(fMask1);
			iMaskSec = ImageIO.read(fMask2);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		

		Color bColor = BASE_COLORS[horseSeed.nextInt(BASE_COLORS.length)];
		Color mColor = MANE_COLORS[horseSeed.nextInt(MANE_COLORS.length)];
		
		Color dColor1 = new Color(horseSeed.nextInt(256), horseSeed.nextInt(256), horseSeed.nextInt(256));
		Color dColor2 = new Color(horseSeed.nextInt(256), horseSeed.nextInt(256), horseSeed.nextInt(256));
		
		colorHorse(bColor, iBase);
		colorHorse(mColor, iMane);
		colorHorse(dColor1, iMaskMain);
		colorHorse(dColor2, iMaskSec);
		
		overlay(iBase, iMane);
		overlay(iBase, iMaskMain);
		overlay(iBase, iMaskSec);
		
		raceProgressBar = new JProgressBar(0, 10000000);
		raceProgressBar.setForeground(dColor1);
		raceProgressBar.setOpaque(false);
		raceProgressBar.setValue(0);
		
		ImageIcon ic = new ImageIcon(iBase);
		return ic;
	}
	
	public void calculateTargetSpeed(Race r, boolean late) {
		ArrayList<Horse> horses = r.getHorses();
		
		if (late) {
			this.targetSpeed = this.speed;
			return;
		}
		
		if (style == Style.PUNTERO) {
			if (horses.getFirst().equals(this)) {
				this.targetSpeed = this.speed*2/3;
				return;
			}
			
			this.targetSpeed = this.speed;
			
		}
		else if (style == Style.CERRADOR ) {
			if (horses.getFirst().equals(this)) {
				this.targetSpeed = this.speed/3;
				return;
			}
			
			if (horses.getFirst().distanceRun - this.distanceRun > r.getDistance()/4) {
				this.targetSpeed = this.speed;
			}
			else {
				this.targetSpeed = this.speed/2;
			}
		}
		else {
			if (horses.getFirst().equals(this)) {
				this.targetSpeed = this.speed/2;
			}
			
			if (horses.getFirst().distanceRun - this.distanceRun > r.getDistance()/7) {
				this.targetSpeed = this.speed;
			}
			else {
				this.targetSpeed = this.speed/2;
			}
			
		}
	}
	
	private void colorHorse(Color c, BufferedImage i) {
		for(int y = 0; y < i.getHeight(); y++) {
		    for(int x = 0; x < i.getWidth(); x++)
		    {
		    	int colorNum  = i.getRGB(x, y);
		    	if ( colorNum != 0) {
		    		Color imageColor = multiplyColor(new Color(colorNum), c);
		    		i.setRGB(x, y, imageColor.getRGB());
		    	}
		        
		        
		    }
		}
	}
	
	private void overlay(BufferedImage i1, BufferedImage i2) {
		for(int y = 0; y < i2.getHeight(); y++) {
		    for(int x = 0; x < i2.getWidth(); x++)
		    {
		    	int colorNum  = i2.getRGB(x, y);
		    	if ( colorNum != 0) {
		    		Color imageColor = new Color(colorNum);
		    		i1.setRGB(x, y, imageColor.getRGB());
		    	}
		        
		        
		    }
		}
	}
	
	private Color multiplyColor(Color color1, Color color2) {
		float[] color1Components = color1.getRGBComponents(null);   
	    float[] color2Components = color2.getRGBColorComponents(null);
	    float[] newComponents = new float[3]; 

	    for(int i = 0; i < 3; i++)
	        newComponents[i] = color1Components[i] * color2Components[i];

	    return new Color(newComponents[0], newComponents[1], newComponents[2],
	        color1Components[3]);
	}
	
	public ImageIcon getImage() {
		return this.image;
	}
	
	public JProgressBar getProgress() {
		return this.raceProgressBar;
	}
	public void setProgress(JProgressBar p) {
		this.raceProgressBar = p;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String s) {
		this.name = s;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name + ": " + distanceRun +"m " + hp + "/" + stam + "hp " ;
	}

	@Override
	public int compareTo(Horse o) {
		
		return this.distanceRun <= o.distanceRun ? 1 : -1;
	}
}
