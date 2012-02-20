import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Ship extends GameObject {
	final static int SCALE = 3;
	
	final static int HEIGHT = 8*SCALE;
	final static int WIDTH = 8*SCALE;
	
	final static double SPEED_LIMIT = 4;	

	public Ship(int courtwidth, int courtheight) {
		super((courtwidth - WIDTH) / 2, courtheight - HEIGHT - 20, 0, 0, WIDTH, HEIGHT);
		super.speedLimit = SPEED_LIMIT;
		timerSetup();
	}
	

	public void bound(){	
		if (x < 0 || x > rightBound)
			velocityX = 0;
		if (y < 0 || y > bottomBound)
			velocityY = 0;
	}

	public void draw(Graphics g) {
		Polygon ship = new Polygon();
		double[] xp = new double[] {-2, -4, -4, -3, -2, -3, 0, 3,  2,  3, 4, 4, 2};
		double[] yp = new double[] { 4,  1,  0, -3, -4,  0, 2, 0, -4, -3, 0, 1, 4};

		int points = xp.length;
		int[] transxp, transyp;
		
		double[][] coords = new double[][] {xp, yp};
		coords = rotate(coords, (theta+(Math.PI)/2));
		coords[0] = scale(coords[0], SCALE);
		coords[1] = scale(coords[1], SCALE);
		coords[0] = translate(coords[0], x+WIDTH/2);
		coords[1] = translate(coords[1], y+HEIGHT/2);
		transxp = round(coords[0]);
		transyp = round(coords[1]);
		
		ship.xpoints = transxp;
		ship.ypoints = transyp;
		ship.npoints = points;
		
		g.fillPolygon(ship);
	}
	
	public void explode(ExplosionMgr expl){
		expl.fire(rightBound, bottomBound, x, y, 30, 5, 400, 3000, Color.white);
		expl.fire(rightBound, bottomBound, x, y, 25, 4, 450, 2000, Color.red);
		isDead = true;
	}
	
	
	public double pointingTheta = Math.PI/2;
	protected double targetTheta = Math.PI/2;
	protected boolean isTurning = false;
	protected double turnRatio = 5;
	protected long aimInterval = 50;
	
	public Timer aimer;
	
	public boolean upPressed = false;
	public boolean leftPressed = false;
	public boolean downPressed = false;
	public boolean rightPressed = false;

	public void timerSetup(){
  	  	
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
			    }
  			
    			if(isTurning){
    				pointingTheta = theta;
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
    				
    				theta = pointingTheta;//careful
    			}   			
    		}
  	  	};  	  	
  	  	aimer = new Timer((int)aimInterval, aimTimer);
  	  	aimer.start();
	}
	
    // 1=up, 2=left, 3=down, 4=right
	public void aim(int direction){
		setAccel(.5);
		isTurning = true;
		targetTheta = (double)direction*Math.PI/4;
	}
}
