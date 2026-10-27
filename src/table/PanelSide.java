package table;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PanelSide extends JPanel{
	
	public PanelSide(int direction, int num, int size) {
		
		if (direction % 2 == 0) {
			this.setPreferredSize(new Dimension(0, size));
			this.setLayout(new GridLayout(1, num));
		}
		else {
			this.setPreferredSize(new Dimension(size, 0));
			this.setLayout(new GridLayout(num, 1));
		}
		
		
		
		
		this.setSize(getPreferredSize());
		
		this.setBorder(BorderFactory.createLineBorder(Color.black, 1));
	}
	
}
