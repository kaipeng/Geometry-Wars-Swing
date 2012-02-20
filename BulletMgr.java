import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.Timer;


/**
 * Bullet Manager
 * Responsible for maintaining all bullets in the game.
 * First, holds all existing bullet instances in an ArrayList, 
 * 	and disposes them into garbage collection when they are destroyed
 * Second, keeps track of aiming of bullets by responding to arrow
 * 	keys pressed. Fast-response and always finds quickest direction to turn.
 * Third, sets rate of fire and deploys Bullet instances in given directions.
 * @param
 * @return
 */
public class BulletMgr{
	public double pointingTheta = Math.PI/2;
	protected double targetTheta = Math.PI/2;
	protected boolean isTurning = false;
	protected double turnRatio = 5;
	protected long aimInterval = 50;
	
	public ArrayList<Bullet> bullets;
	
	private int fireInterval = 150;
	
	public boolean firing = false;
	private boolean canFire = true;
	public boolean ceaseFire = false;
	public Timer repeat;
	public Timer aimer;
	
	public boolean upPressed = false;
	public boolean leftPressed = false;
	public boolean downPressed = false;
	public boolean rightPressed = false;
	
	/**
	 * BulletMgr Constructor
	 * Starts timer and creates arraylist
	 * @param
	 * @return
	 */
	public BulletMgr(){
		bullets = new ArrayList<Bullet>(100);
		timerSetup();
	}
	/**
	 * timerSetup
	 * Starts timer that reacts to arrow keys being pressed to turn the aim of the ship.
	 * @param
	 * @return
	 */
	public void timerSetup(){
	   	ActionListener repeatTimer = new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			canFire = true;
    		}
  	  	};  	  	
  	  	repeat = new Timer((int)fireInterval, repeatTimer);
  	  	repeat.setRepeats(false);
  	  	
	   	ActionListener aimTimer = new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
			    if(upPressed && leftPressed && !rightPressed && !downPressed)
			    	aim(5);
			    if(upPressed && !leftPressed && rightPressed && !downPressed)
			    	aim(7);
			    if(!upPressed && leftPressed && !rightPressed && downPressed)
			    	aim(3);
			    if(!upPressed && !leftPressed && rightPressed && downPressed)
			    	aim(1);
			    
			    if(upPressed && !leftPressed && !rightPressed && !downPressed)
			    	aim(6);
			    if(!upPressed && leftPressed && !rightPressed && !downPressed)
			    	aim(4);
			    if(!upPressed && !leftPressed && rightPressed && !downPressed)
			    	aim(0);
			    if(!upPressed && !leftPressed && !rightPressed && downPressed)
			    	aim(2);
			    
			    if(!upPressed && !leftPressed && !rightPressed && !downPressed){
			    	isTurning = false;
			    	//firing = false;
			    }
  			
			    //RESPONSIBLE FOR FINDING SHORTEST DISTANCE TO TURN AND TURNING
			    //KEY ABILITY TO NOT OVERSTEER BY DIVIDING DIFFERENCE RATHER THAN
			    //JUST ADDING
    			if(isTurning){
        			pointingTheta = pointingTheta % (2*Math.PI);
    				
    				double diff = pointingTheta - targetTheta;
    				if(diff == 0)
    					pointingTheta = targetTheta;
    				else if(diff > Math.PI)
    					pointingTheta += (targetTheta + 2*Math.PI - pointingTheta)/turnRatio;
    				else if(diff < -Math.PI)
    					pointingTheta -= (pointingTheta + 2*Math.PI - targetTheta)/turnRatio;    				
    				else
    					pointingTheta -= diff/turnRatio;    				
    			}
    			//CLEAN UP DEAD BULLETS
    			garbageCollect();    			
    		}
  	  	};  	  	
  	  	aimer = new Timer((int)aimInterval, aimTimer);
  	  	aimer.start();
	}
	
	/**
	 * Bullet aim
	 * Sets angle of aiming based on keys pressed. Also determines if
	 * turning is right to do - only turns if keys are being held
	 * @param
	 * @return
	 */
	public void aim(int direction){
		isTurning = true;
		targetTheta = (double)direction*Math.PI/4;
	}
	
	public void garbageCollect(){
		for(int i = 0; i < bullets.size(); i++){
			if(bullets.get(i).isDead() == true){
				bullets.remove(i);
			}
		}
	}
	
	public void setBounds(int width, int height){
		for(int i = 0; i < bullets.size(); i++){
			bullets.get(i).setBounds(width, height);
		}
	}
	
	/**
	 * Bullet Fire
	 * Deploys the bullets out in given direction of ship with relative velocity to the ship
	 * @param
	 * @return
	 */
	public void fire(int courtwidth, int courtheight, double shipX, double shipY, double shipVX, double shipVY, double pointingTheta) {
		if((firing == false || canFire == false) || ceaseFire)
			return;
		
		Bullet newB = new Bullet(courtwidth, courtheight, shipX, shipY, shipVX, shipVY, pointingTheta);
		bullets.add(newB);
		canFire = false;
		
		if(Sound.iBulletFire)
			Sound.BulletFire.play();

  	  	repeat.start();
	}
	/**
	 * Bullet TripleFire
	 * Shoots 3 at once at spray angle
	 * @param
	 * @return
	 */
	public void tripleFire(int courtwidth, int courtheight, double x, double y, double shipVX, double shipVY, double pointingTheta) {
		if((firing == false || canFire == false)  || ceaseFire)
			return;
		
		Bullet newB1 = new Bullet(courtwidth, courtheight, x, y, shipVX, shipVY, pointingTheta - .06);
		Bullet newB2 = new Bullet(courtwidth, courtheight, x, y, shipVX, shipVY, pointingTheta);
		Bullet newB3 = new Bullet(courtwidth, courtheight, x, y, shipVX, shipVY, pointingTheta + .06);		
		bullets.add(newB1);
		bullets.add(newB2);
		bullets.add(newB3);
		canFire = false;
		
		if(Sound.iBulletTripleFire)
			Sound.BulletTripleFire.play();

  	  	repeat.start();
	}
	
	public void draw(Graphics g){
		for(int i = 0; i < bullets.size(); i++){
			bullets.get(i).draw(g);
		}
	}
	
	public void move(){
		for(int i = 0; i < bullets.size(); i++){
			bullets.get(i).move();
		}
	}	
}
