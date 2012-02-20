import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class Geo extends JFrame {

	public GeoCourt court;
	public Splash splash;
	boolean firstTime = true;
	Geo() {
		super("Geometry Wars");
		setLocation(270,100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JPanel    panel = new JPanel();
		final JButton   reset = new JButton("Let me start over.");
		final JButton   help = new JButton("I'm lost - HELP!.");
		
		court = new GeoCourt();

		panel.add(reset);
		panel.add(help);
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Gametracker.init();
				court.reset();
			}
		});
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(firstTime){
					splash = new Splash(false);
					firstTime = false;
				}
				else
					splash.setVisible(true);
				
				Gametracker.isPaused = true;
			}
		});

		add(panel, BorderLayout.NORTH);
		add(court, BorderLayout.CENTER);

        pack();

        setVisible(true);
		court.startTimer();
		court.grabFocus();
		
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Splash(true);
			}
		});
	}

}
