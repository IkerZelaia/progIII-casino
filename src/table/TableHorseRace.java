package table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;



import horse.Horse;
import horse.Horse.Style;
import horse.Race;
import main.Main;
import main.Window;

public class TableHorseRace extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Color COLOR_DORADO = new Color(212, 175, 55);
	private static final Color COLOR_BURDEOS = new Color(110, 15, 30);
	private static final Color COLOR_BURDEOS_HOVER = new Color(145, 25, 45);
	private static final Color COLOR_TAPETE = new Color(25, 105, 52);
	private static final Color COLOR_BEIGE =new Color(230, 217, 195);
	
	private Window window;
	private JPanel panelRace, panelData, panelMenu, panelCentral, panelHorses, panelInfoRace;
	private JPanel[] panelParticipant;
	private JButton botonReturn, botonStart;
	private Race currentRace;
	private Horse betHorse;
	private int dinero, apuestaActual;
	private JLabel labelDinero;
	
	private HiloCarrera	hilo;
	
	public TableHorseRace(Window window) {
		
		super(new BorderLayout());
		this.setBackground(COLOR_TAPETE);
		this.window = window;
		
		JPanel relleno = new JPanel();
		relleno.setOpaque(false);
		
		panelCentral = new JPanel(new GridLayout(1, 2));
		panelRace = new JPanel();
		estilizarPanel(panelRace);
		panelData = new JPanel(new GridLayout());
		estilizarPanel(panelData);
		panelMenu = new JPanel(new BorderLayout());
		estilizarPanel(panelMenu);
		
		botonStart = new JButton("Inicio");
		estilizarBoton(botonStart, COLOR_BURDEOS, COLOR_BURDEOS_HOVER);
		botonStart.addActionListener(e -> loadRace(null));
		panelData.add(botonStart);
		
		botonReturn = new JButton("Menu principal");
		botonReturn.addActionListener( e -> {
			
			try {
				hilo.interrupt();
			}
			catch (Exception e2) {
				
			}
			this.window.mostrarMenu();
		});
		botonReturn.setHorizontalAlignment(SwingConstants.LEFT);
		estilizarBoton(botonReturn, COLOR_BURDEOS, COLOR_BURDEOS_HOVER);
		JPanel contenedorReturn = new JPanel();
		contenedorReturn.add(botonReturn);
		contenedorReturn.setOpaque(false);
		
		panelMenu.add(contenedorReturn, BorderLayout.WEST);
		JLabel lTitulo = new JLabel("CARRERAS DE CABALLOS", SwingConstants.CENTER);
		
		lTitulo.setForeground(COLOR_DORADO);
		lTitulo.setFont(new Font("Times New Roman", Font.BOLD, 30));
		panelMenu.add(lTitulo, BorderLayout.CENTER);
		
		dinero = 2000;
		labelDinero = new JLabel("Dinero: " + dinero, SwingConstants.LEADING);
		labelDinero.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		labelDinero.setForeground(COLOR_DORADO);
		panelMenu.add(labelDinero, BorderLayout.EAST);
		
		panelCentral.add(panelRace);
		panelCentral.add(panelData);
		
		this.add(panelCentral, BorderLayout.CENTER);
		this.add(panelMenu, BorderLayout.NORTH);
		
		
		
		
	}
	
	
	private void loadRace(Horse betH) {
		panelRace.removeAll();
		panelData.removeAll();
		panelRace.setLayout(new GridLayout(1, 1 , 0, 50));
		hilo = null;
		
		if ( betH != null) {
			currentRace = new Race(betH);
		}
		else {
			currentRace = new Race();
		}
		
		
			
		
		
		
		int n = currentRace.getNumHorse();
		int horseSize = panelRace.getHeight()*9/(n*10);
		panelParticipant = new JPanel[n];
		
		panelHorses = new JPanel(new GridLayout(n, 1, 0, 0));
		panelHorses.setOpaque(false);
		
		
		
		for (int x = 0; x < currentRace.getHorses().size(); x ++) {
			
			Horse h = currentRace.getHorses().get(x);
			
			JPanel temp = new JPanel( new BorderLayout(5, 0));
			ImageIcon i = new ImageIcon(h.getImage().getImage().getScaledInstance(horseSize,horseSize , Image.SCALE_SMOOTH));
			JButton b = new JButton (i);
			JProgressBar p = h.getProgress();
			
			
			
			b.setOpaque(false);
			b.setContentAreaFilled(false);
			b.setBorderPainted(false);
			b.addActionListener(e -> loadHorse(h));
			
			temp.setOpaque(false);
			temp.setBorder(BorderFactory.createLineBorder(COLOR_TAPETE.darker()));
			temp.add(b, BorderLayout.WEST);
			temp.add(h.getProgress(), BorderLayout.CENTER);
			panelParticipant[x] = temp;
			
			panelHorses.add(temp);
		}
		
		panelInfoRace = new JPanel(new BorderLayout());
		panelInfoRace.setBorder(BorderFactory.createLineBorder(COLOR_BEIGE, 30, false));
		
		Font fPeriodico = new Font("Times New Roman", Font.BOLD, 20);
		
		JLabel lTitular = new JLabel("<html>"+currentRace.getName().toUpperCase()+"<br/><font size = '2' face = 'Arial'>"+currentRace.toString()+"</font>"+"</html>");
		JLabel lInst = new JLabel("Haz clic en en caballo para apostar");
		lTitular.setFont(fPeriodico);
		lTitular.setHorizontalAlignment(SwingConstants.CENTER);
		lInst.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		panelInfoRace.add(lTitular, BorderLayout.NORTH);
		panelInfoRace.add(lInst);
		
		panelData.add(panelInfoRace);
		panelRace.add(panelHorses);
		window.setContentPane(this);
		
	}
	
	
	private void loadHorse(Horse h) {
		
		panelInfoRace.remove(1);
		
		JPanel panelH = new JPanel(new BorderLayout());
		panelH.setBorder(BorderFactory.createLineBorder(COLOR_BEIGE, 3, true));
		panelH.add(new JLabel(h.getName(), SwingConstants.CENTER), BorderLayout.NORTH);
		
		JPanel panelS = new JPanel(new GridLayout(5, 1));
		JProgressBar pbSPD = new JProgressBar();
		pbSPD.setString("VELOCIDAD: " + h.speed);
		pbSPD.setStringPainted(true);
		JProgressBar pbACC = new JProgressBar();
		pbACC.setString("ACCELERACION: " + h.accel);
		pbACC.setStringPainted(true);
		JProgressBar pbSTM = new JProgressBar();
		pbSTM.setString("RESISTENCIA: " + h.stam);
		pbSTM.setStringPainted(true);
		
		pbSPD.setValue(h.speed*100/2000);
		pbACC.setValue(h.accel*100/50);
		pbSTM.setValue(h.stam*100/2000);
		
		JPanel panelS2 = new JPanel(new GridLayout(1, 2));
		JLabel lStyle = new JLabel("", SwingConstants.CENTER);
		
		if (h.style == Horse.Style.CERRADOR) {
			lStyle.setText("Estilo: Cerrador");
		}
		if (h.style == Horse.Style.PUNTERO) {
			lStyle.setText("Estilo: Puntero");
		}
		if	(h.style == Horse.Style.ACECHADOR) {
			lStyle.setText("Estilo: Acechador");
		}
			
		JLabel lPotencial = new JLabel("Potencial: " + h.potential, SwingConstants.CENTER);
		
		panelS2.add(lStyle);
		panelS2.add(lPotencial);
		
		JPanel panelApuesta = new JPanel(new GridLayout(1, 2));
		
		JButton bApuesta = new JButton("Apostar");
		JSpinner sApuesta = new JSpinner(new SpinnerNumberModel(0, 0, 1000000, 5));
		
		bApuesta.addActionListener(e -> {
			int apuesta = (Integer) sApuesta.getValue();
			
			if ( apuesta > 0 && apuesta <= dinero) {
				betHorse = h;
				panelApuesta.setVisible(false);
				apuestaActual = apuesta;
				dinero -= apuestaActual;
				labelDinero.setText("Dinero: " + dinero);
				startRace();
			}
		});
		
		panelApuesta.add(bApuesta);
		panelApuesta.add(sApuesta);
		
		if (hilo != null) {
			panelApuesta.setVisible(false);
		}
		
		
		panelS.add(new JLabel(h.getImage(), SwingConstants.CENTER));
		panelS.add(pbSPD);
		panelS.add(pbACC);
		panelS.add(pbSTM);
		panelS.add(panelS2);
		
		panelH.add(panelS, BorderLayout.CENTER);
		panelH.add(panelApuesta, BorderLayout.SOUTH);
		
		panelInfoRace.add(panelH, BorderLayout.CENTER);
		window.setContentPane(this);
	}
	
	private void startRace() {
		//panelData.removeAll();
		window.setContentPane(this);
		hilo = new HiloCarrera();
		hilo.start();
	}
	private void endRace() {
		panelData.removeAll();
		JPanel panelResult = new JPanel(new GridLayout(5, 1));
		Horse h =  currentRace.getHorses().get(0);
		Horse h2 =  currentRace.getHorses().get(1);
		Horse h3 =  currentRace.getHorses().get(2);
		
		if (h.equals(betHorse)) {
			dinero += apuestaActual*2;
		}else if (h2.equals(betHorse)) {
			dinero += apuestaActual*1.5;
		}else if (h3.equals(betHorse)) {
			dinero += apuestaActual;
		}
		
		JPanel panelPodio = new JPanel(new BorderLayout());
		panelPodio.add(new JLabel(h2.getImage(), SwingConstants.CENTER), BorderLayout.WEST);
		panelPodio.add(new JLabel(h3.getImage(), SwingConstants.CENTER), BorderLayout.EAST);
		panelPodio.add(new JLabel("2º y 3º puesto : " +h2.getName() + " y " + h3.getName(), SwingConstants.CENTER), BorderLayout.CENTER);
		
		JPanel panelBotones = new JPanel(new GridLayout(1, 2));
		
		JButton bMantener = new JButton("SI");
		JButton bDeshechar = new JButton("NO");
		bMantener.addActionListener(e -> loadRace(betHorse));
		bDeshechar.addActionListener(e -> loadHorse(null));
		
		panelBotones.add(bMantener);
		panelBotones.add(bDeshechar);
		
		
		panelResult.add(new JLabel("Caballo ganador: " + h.getName() , SwingConstants.CENTER));
		panelResult.add(new JLabel(h.getImage(), SwingConstants.CENTER));
		panelResult.add(panelPodio);
		panelResult.add(new JLabel("Quieres mantener con el mismo caballo para la siguiente carrera?", SwingConstants.CENTER));
		panelResult.add(panelBotones);
		
		
		
		
		panelData.add(panelResult);
		
		window.setContentPane(this);
	}
	
	private class HiloCarrera extends Thread{
		
		
		
		public HiloCarrera() {
			try {
				hilo.interrupt();
			}
			catch (Exception e) {
				
			}
		}
		@Override
		public void run() {
			
			int timer = 0;
			double raceAcc = currentRace.getSurfice().accMult*currentRace.getWeather().accMult;
			double raceSpd = currentRace.getSurfice().spdMult*currentRace.getWeather().spdMult;
			double raceStm = currentRace.getSurfice().stmMult*currentRace.getWeather().stmMult;
			for (Horse h : currentRace.getHorses()) {
				h.distanceRun = 0;
				if (!Main.debugPerfectHorses()) {
					h.performance = currentRace.getSeed().nextDouble(h.consistency + 0.25, 1);
				}
				else {
					h.performance = 1;
				}
				
				h.finished = false;
				h.hp = h.stam;
				h.isTired = false;
				
			}
			
			while(!isInterrupted()) {
				try {
					for(Horse h : currentRace.getHorses()) {
						if(!h.finished) {
							boolean isLateRace = h.distanceRun >= 2*currentRace.getDistance()/3;
							
							h.calculateTargetSpeed(currentRace, isLateRace);
							
							double topSpeed = h.targetSpeed*raceSpd*h.performance;
							
							topSpeed *= isLateRace ? h.style.spdMultLate : 1;
							
							double actualAcc = h.accel*h.performance*raceAcc;
							
							actualAcc *= isLateRace ? h.style.accMultLate : h.style.accMultEarly;
							
							if (h.currentSpeed > topSpeed) {
								
								h.currentSpeed -= actualAcc;
							}
							else {
								h.currentSpeed += actualAcc;
							}
							
							
							
							if (!h.isTired) {
								h.hp -= 4*(raceStm)*(h.currentSpeed)/(h.speed)*h.style.stmMult;
								if (h.hp <= 0 ) {
									h.isTired = true;
								}
							}
							else {
								h.currentSpeed *= 0.95;
								h.hp += h.stam/200;
								if (h.hp >=  h.stam/2) {
									h.isTired = false;
								}
								
							}
							
							
							h.distanceRun += h.currentSpeed/400;
							
							
							JProgressBar pb = h.getProgress();
							if (h.isTired) {
								pb.setString("CANSADO");
								pb.setStringPainted(true);
							}
							else {
								pb.setStringPainted(false);
							}
							int v = (int) (h.distanceRun*pb.getMaximum()/currentRace.getDistance());
							pb.setValue(v);
							h.setProgress(pb);
							
							if ( h.distanceRun >= currentRace.getDistance()) {
								h.distanceRun = currentRace.getDistance();
								System.out.println(h + "-" + timer + "s");
								
								h.finished = true;
								currentRace.resultado.add(h);
								pb.setString(currentRace.resultado.size() + "º");
								pb.setStringPainted(true);
								if (currentRace.resultado.size() == currentRace.getNumHorse()) {
									this.interrupt();
									Collections.sort(currentRace.getHorses());
									endRace();
								}
							}
						}
					}
					Collections.sort(currentRace.getHorses());
					//System.out.println(horses);
					timer ++;
					//System.out.println(timer);
					Thread.sleep(Main.REFRESH_RATE);
				} catch (InterruptedException e) {
					
					this.interrupt();
				}
			}
			
		}
	}
	
	
	
	private void estilizarPanel(JPanel p) {
		p.setBackground(COLOR_TAPETE);
		p.setBorder(BorderFactory.createLineBorder(COLOR_DORADO, 7));
	}
	// Copiado del Black Jack
	private void estilizarBoton(JButton boton, Color colorFondo, Color colorHover) {
		boton.setFont(new Font(Font.SERIF, Font.BOLD, 17));
		boton.setForeground(Color.WHITE);
		boton.setBackground(colorFondo);
		boton.setFocusPainted(false);
		boton.setOpaque(true);
		boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		boton.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(COLOR_DORADO, 2),
				BorderFactory.createEmptyBorder(8, 18, 8, 18)
		));

		boton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (boton.isEnabled()) {
					boton.setBackground(colorHover);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (boton.isEnabled()) {
					boton.setBackground(colorFondo);
				}
			}
		});
	}
	
}
