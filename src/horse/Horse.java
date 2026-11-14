package horse;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Horse {
	private static final Color CHESTNUT = new Color(130, 110, 75);
	private static final Color WHITE = new Color(10, 10, 10);
	private static final Color DARK = new Color(25, 22, 20);
	private static final Color GRAY = new Color(175, 175, 175);
	private static final Color GINGER = new Color(170, 125, 45);
	private static final Color CREAM = new Color(215, 185, 140);
	
	private static final Color[] BASE_COLORS = {CHESTNUT, WHITE, DARK, GRAY, GINGER, CREAM};
	private static final Color[] MANE_COLORS = {WHITE, DARK, CHESTNUT, GRAY};
	private Random horseSeed;
	
	private ImageIcon image;
	private BufferedImage iBase, iMane, iMaskMain, iMaskSec;
	
	public int speed, accel, stam;
	public double style, consistency;
	
	public Horse() {
		horseSeed = new Random();
		image = drawImage();
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
		
		ImageIcon ic = new ImageIcon(iBase);
		return ic;
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
}
