import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


@SuppressWarnings("serial")
public class Splash extends JFrame {
	private boolean isFirst;
	Geo game;
	
	public Splash(boolean firstTime){
		super("Welcome to Geometry Wars");
		setPreferredSize(new Dimension(550, 745));
		setLocation(350, 30);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.BLACK);
		setForeground(Color.WHITE);
		
		isFirst = firstTime;

		final JPanel    panel = new JPanel();
	
		final JButton start = new JButton("Godspeed, soldier.");
		final JTextArea instructs = new JTextArea("     \t\t       INSTRUCTIONS:\n" +
				"************************************************************************************************\n"+
				"\tStern\n"+
				"\t/|_|\\ \n" +
				"\t\\_._/    <--Your Ship: The Euclidian 2010.\n" +
				"\t Aft\t            Polygons, watch out!\n\n"+
				"************************************************************************************************\n"+
				" MANEUVERING: You have a choice of 2 options:\n"+
				"________________________________________________________________________________________________\n"+
				" 1) Asteroids style:\n" +
				"\t     [W]    = Accelerate ship forward in direction it's facing.\n" +
				"        [W]\t     [S]    = Accelerate ship backward in opposite direction.\n" +
				"     [A][S][D]\t     [A]    = Rotate ship left (counterclockwise).\n"+
				"\t     [D]    = Rotate ship right (clockwise).\n\n" +
				"              *Basically, just hold [W], and steer with [A] and [D], and enjoy the physics.\n"+
				"________________________________________________________________________________________________\n"+
				" 2) Joystick style:\n" +
				"\t     [T]    = Accelerate ship Up.\n" +
				"        [T]\t     [G]    = Accelerate ship Down.\n" +
				"     [F][G][H]\t     [F]    = Accelerate ship Left.\n"+
				"\t     [H]    = Accelerate ship Right.\n\n" +
				"              *Hold combinations of buttons to go diagonally.\n\n"+
				"************************************************************************************************\n"+
				" SHOOTING: Aim your Plasma Turret with the Arrow Keys.\n" +
				"________________________________________________________________________________________________\n"+
				"\t     [UP]       = Turn Turret towards Up.\n" +
				"\t     [DOWN]     = Turn Turret towards Down.\n" +
				"\t     [LEFT]     = Turn Turret towards Left.\n"+
				"\t     [RIGHT] = Turn Turret towards Right.\n\n" +
				"              *Hold combinations of arrows to aim diagonally.\n" +
				"              *Tap buttons to nudge turret in a given direction.\n\n"+
				"************************************************************************************************\n"+
				" Commander, you have 3 lives. Get lucky enough, and you might get more!\n" +
				" YOUR OBJECTIVE IS TO SHOOT UP ALL THOSE SHAPES UNTIL THEY BLOW. SIMPLE.\n"+
				"\tPress [P] to pause the game at any time and [L] to resume.\n"+
				"\tYou may stretch/shrink the window as big/small as you like.\n");
		panel.add(start);

		pack();
		add(panel, BorderLayout.CENTER);
		add(instructs, BorderLayout.NORTH);
		instructs.setBackground(Color.black);
		instructs.setForeground(Color.white);
		
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isFirst){
					game = new Geo();
				}
				Gametracker.isPaused = false;
				setVisible(false);
			}
		});

        setVisible(true);
	}
	
}
