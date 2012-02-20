import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import javax.swing.*;

@SuppressWarnings("serial")
public class GeoCourt extends JPanel {
	private Ship ship;
	private BulletMgr bullets;
	private TailMgr tails;

	private TargetBMgr targetB1s;
	private TargetBMgr targetB2s;
	private TargetBMgr targetB3s;
	private TargetBMgr targetB4s;
		
	private ExplosionMgr expls;
	
	private int interval = 30; // Milliseconds between updates.
	private Timer timer; // Timer fires to animate one step.

	public final int COURTWIDTH = 800;
	public final int COURTHEIGHT = 600;
	
	public double SHIP_VEL = .5;
	public double ANG_VEL = .15;
		


	public GeoCourt() {
		setPreferredSize(new Dimension(COURTWIDTH, COURTHEIGHT));
		reset();
		setBorder(BorderFactory.createLineBorder(Color.WHITE));
		setBackground(Color.BLACK);
		timer = new Timer(interval, new TimerAction());
		
		Sound.init();
		if(Sound.iMusic)
			Sound.startMusic();
		
		setFocusable(true);
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {

			    if (e.getKeyCode() == KeyEvent.VK_1)
			        targetB1s.fire(Math.random()*getWidth(),Math.random()*getHeight());
			    if (e.getKeyCode() == KeyEvent.VK_2)
			        targetB2s.fire(Math.random()*getWidth(),Math.random()*getHeight());
			    if (e.getKeyCode() == KeyEvent.VK_3)
			        targetB3s.fire(Math.random()*getWidth(),Math.random()*getHeight());
			    if (e.getKeyCode() == KeyEvent.VK_4)
			        targetB4s.fire(Math.random()*getWidth(),Math.random()*getHeight());
			}
		});
		
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
			    if (e.getKeyCode() == KeyEvent.VK_P)
			    	Gametracker.isPaused = true;
			    else if (e.getKeyCode() == KeyEvent.VK_L)
			    	Gametracker.isPaused = false;
			}
		});
		
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
			    if (e.getKeyCode() == KeyEvent.VK_A)
			        ship.setAngVelocity(-ANG_VEL);
			    else if (e.getKeyCode() == KeyEvent.VK_D)
			    	ship.setAngVelocity(ANG_VEL);
			}
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D)
					ship.setAngVelocity(0);
			}
		});
		
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
			    if (e.getKeyCode() == KeyEvent.VK_W)
			        ship.setAccel(SHIP_VEL);
			    else if (e.getKeyCode() == KeyEvent.VK_S)
			        ship.setAccel(-SHIP_VEL);
			}
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S)
					ship.setAccel(0);
			}
		});
		
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
			    if (e.getKeyCode() == KeyEvent.VK_T){
			        ship.upPressed = true;
			    }
			    else if (e.getKeyCode() == KeyEvent.VK_F){
			        ship.leftPressed = true;
			    }
			    else if (e.getKeyCode() == KeyEvent.VK_G){
			        ship.downPressed = true;
			    }
			    else if (e.getKeyCode() == KeyEvent.VK_H){
			        ship.rightPressed = true;
			    }
			}
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_T){
					ship.upPressed = false;
					ship.setAccel(0);
				}
				if (e.getKeyCode() == KeyEvent.VK_H){
					ship.rightPressed = false;
					ship.setAccel(0);
				}
				if (e.getKeyCode() == KeyEvent.VK_F){
					ship.leftPressed = false;
					ship.setAccel(0);
				}
				if (e.getKeyCode() == KeyEvent.VK_G){
					ship.downPressed = false;
					ship.setAccel(0);
				}
			}
		});
		
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
			    if (e.getKeyCode() == KeyEvent.VK_UP){
			        //bullets.aim(6);
			        bullets.firing = true;
			        bullets.upPressed = true;
			    }
			    else if (e.getKeyCode() == KeyEvent.VK_LEFT){
			    	//bullets.aim(4);
			        bullets.firing = true;
			        bullets.leftPressed = true;
			    }
			    else if (e.getKeyCode() == KeyEvent.VK_DOWN){
			    	//bullets.aim(2);
			        bullets.firing = true;
			        bullets.downPressed = true;
			    }
			    else if (e.getKeyCode() == KeyEvent.VK_RIGHT){
			    	//bullets.aim(0);
			        bullets.firing = true;
			        bullets.rightPressed = true;
			    }
			}
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP){
					bullets.upPressed = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT){
					bullets.rightPressed = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_LEFT){
					bullets.leftPressed = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN){
					bullets.downPressed = false;
				}
			}
		});
		
		Gametracker.init();
	}
	
	Timer restart;
	public void newLife() {
	   	ActionListener restartTimer = new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			bullets.aimer.stop();
    			bullets.repeat.stop();
    			tails.repeat.stop();
    			targetB1s.seeker.stop();
    			targetB2s.seeker.stop();
    			targetB3s.seeker.stop();
       			targetB4s.seeker.stop();
    			expls.repeat.stop();
    			reset();
				Gametracker.canDie = true;
    		}
  	  	};  	  	
  	  	restart = new Timer(5000, restartTimer);
  	  	restart.setRepeats(false);
  	  	restart.start();
  	  	
	}

	private boolean isFirstRun = true;
	public void reset(){
		if(!isFirstRun){
			expls.repeat.stop();
			bullets.aimer.stop();
			ship.aimer.stop();
			tails.repeat.stop();
			targetB1s.seeker.stop();
			targetB2s.seeker.stop();
			targetB3s.seeker.stop();
		}
		
		ship = new Ship(COURTWIDTH, COURTHEIGHT);
		bullets = new BulletMgr();
		tails = new TailMgr();

		double[][] poly1 = {{-2, 0, 2},{-2, 2, -2}};
		targetB1s = new TargetBMgr(3, .3, 3, 2, .2, poly1, 5, 5, Color.magenta, 1);
		double[][] poly2 = {{-3, -3, 3, 3},{-3, 3, 3, -3}};
		targetB2s = new TargetBMgr(2, .1, 2, 2, .1, poly2, 7, 7, Color.blue, 3);
		double[][] poly3 = {{-3, -1, 0, 1, 3, 1, 0, -1},{0, 1, 3, 1, 0, -1, -3, -1}};
		targetB3s = new TargetBMgr(3, .6, 1,3,.1, poly3, 3, 3, Color.green, 1);

		targetB4s = new TargetBMgr(1, .05, 0,0,0, 10, Color.red, 15);
		
		expls = new ExplosionMgr();
		
		
		
		Gametracker.resetTargets(targetB1s, targetB2s, targetB3s, targetB4s);
		
		if(Sound.iBeginLife)
			Sound.BeginLife.play();
		
		isFirstRun = false;
		grabFocus();
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		super.paintComponent(g); // Paint background, border
		
		if(!ship.isDead()){
			g.setColor(Color.WHITE);
			ship.draw(g);
			
			g.setColor(Color.ORANGE);
			tails.draw(g);
		}
		
		g.setColor(Color.YELLOW);
		bullets.draw(g);
		
		targetB1s.draw(g);
		targetB2s.draw(g);
		targetB3s.draw(g);
		targetB4s.draw(g);
		
		expls.draw(g);
		
		//messages
		if(Gametracker.isOver){
			g.setColor(Color.red);
			g.drawString("GAME OVER, CORPORAL", getWidth()/2 - 80, getHeight()/2);
			g.drawString("SCORE: " + Gametracker.score, getWidth()/2 - 60, getHeight()/2 + 20);
			return;
		}
		
		if(Gametracker.isPaused){
			g.setColor(Color.red);
			g.drawString("GAME PAUSED - PRESS [L] TO RESUME", getWidth()/2 - 140, getHeight()/2);
			return;
		}
		
		g.setColor(Color.gray);
		g.drawString("Score: " + Gametracker.score, 5, 15);
		g.drawString("Lives: ", getWidth()/2 - 75, 15);
		for(int i = 0; i < Gametracker.lives; i++){
			g.drawString("/|_|\\", getWidth()/2+35*i -35, 15);
			g.drawString("\\_._/", getWidth()/2+35*i -35, 26);
		}
		g.drawString("Stage: " + Gametracker.stage, getWidth() - 60 , 15);

	}

	public void startTimer() { timer.start(); }


	/**
	 * All actions occur here
	 * From moving, turning, seeking, exploding, firing, to updating statuses - it all happens here
	 * @param
	 * @return
	 */
	class TimerAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(!Gametracker.isPaused){	
				grabFocus();
				
				bullets.setBounds(getWidth(), getHeight());
				bullets.move();
				if(Gametracker.weaponUpgrade)
					bullets.tripleFire(getWidth(), getHeight(), ship.x, ship.y, ship.velocityX, ship.velocityY, bullets.pointingTheta);
				else
					bullets.fire(getWidth(), getHeight(), ship.x, ship.y, ship.velocityX, ship.velocityY, bullets.pointingTheta);
		        
				tails.setBounds(getWidth(), getHeight());
				tails.move();
				tails.fire(getWidth(), getHeight(), ship.x, ship.y, ship.theta);
				
				
				targetB1s.setBounds(getWidth(), getHeight());
				targetB1s.seek(ship.x, ship.y, bullets.pointingTheta);
				targetB1s.move();
				targetB2s.setBounds(getWidth(), getHeight());
				targetB2s.seek(ship.x, ship.y, bullets.pointingTheta);
				targetB2s.move();
				targetB3s.setBounds(getWidth(), getHeight());
				targetB3s.seek(ship.x, ship.y, bullets.pointingTheta);
				targetB3s.move();
				targetB4s.setBounds(getWidth(), getHeight());
				targetB4s.seek(ship.x, ship.y, bullets.pointingTheta);
				targetB4s.move();
				
				ship.setBounds(getWidth(), getHeight());
				ship.move();
				ship.turn();
				
				try {
					if(targetB1s.explode((bullets.bullets), expls, ship) || 
					targetB2s.explode((bullets.bullets), expls, ship) || 
					targetB3s.explode((bullets.bullets), expls, ship) ||
					targetB4s.explode((bullets.bullets), expls, ship))
					{
						Gametracker.shipDied();
						ship.isDead();
						bullets.ceaseFire = true;
						
						targetB1s.explodeAll(expls, ship);
						targetB2s.explodeAll(expls, ship);
						targetB3s.explodeAll(expls, ship);
						targetB4s.explodeAll(expls, ship);
						
						targetB1s.ceaseFire = true;
						targetB2s.ceaseFire = true;
						targetB3s.ceaseFire = true;
						targetB4s.ceaseFire = true;

						newLife();
					}
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
	
				expls.setBounds(getWidth(), getHeight());
				expls.move();
			}
			
			if(targetB1s.targetBs.size() == 0 && 
					targetB2s.targetBs.size() == 0 && 
					targetB3s.targetBs.size() == 0 && 
					targetB4s.targetBs.size() == 0 &&
					!ship.isDead){
				Gametracker.subadv(getWidth(), getHeight());
				ActionListener waiter = new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Gametracker.waiting = false;
					}
				  	};
				  	Timer wait = new Timer(2000, waiter);
				  	wait.setRepeats(false);
				  	wait.start();
			}
				
			Gametracker.newLife();

			repaint(); // Repaint indirectly calls paintComponent.
		}
	}
	
	
}
