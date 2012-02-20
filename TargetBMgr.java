import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.util.*;

import javax.swing.Timer;

public class TargetBMgr{	
	public ArrayList<TargetB> targetBs;
	
	private int seekInterval = 100;
	
	public Timer seeker;
	
	private double turnRatio = 10;
	
	private double ACEL_RATE;
	private double ESCAPE_ACC;
	private double ESCAPE_VEL;
	private double ESCAPE_ANG;
	private double SPEED_LIMIT;
	
	private double[][] poly = null;
	private int height = -1;
	private int width = -1;
	private Color color;
	
	private int hitsToKill = 1;
	
	public boolean ceaseFire = false;
		
	public TargetBMgr(double speedLimit, double accLimit, double escapeAcc, 
			double escapeVel, double escapeAng,
			int width, Color color, int hits){
		ACEL_RATE = accLimit;
		ESCAPE_ACC = escapeAcc;
		ESCAPE_VEL = escapeVel;
		ESCAPE_ANG = escapeAng;
		SPEED_LIMIT = speedLimit;
		this.width = width;
		this.color = color;
		this.hitsToKill = hits;
		targetBs = new ArrayList<TargetB>(100);
		timerSetup();
	}
	
	public TargetBMgr(double speedLimit, double accLimit, double escapeAcc, 
			double escapeVel, double escapeAng,
			double[][] poly, int height, int width, Color color, int hits){
		ACEL_RATE = accLimit;
		ESCAPE_ACC = escapeAcc;
		ESCAPE_VEL = escapeVel;
		ESCAPE_ANG = escapeAng;
		SPEED_LIMIT = speedLimit;
		this.poly = poly;
		this.height = height;
		this.width = width;
		this.color = color;
		this.hitsToKill = hits;
		targetBs = new ArrayList<TargetB>(100);
		timerSetup();
	}
		
	public void timerSetup(){
	   	ActionListener repeatTimer = new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    	   		garbageCollect();    	   		
    		}
  	  	};  	  	
  	  	seeker = new Timer(seekInterval, repeatTimer);
  	  	seeker.start();
	}
	
	public void seek(double shipX, double shipY, double aimTheta){
		for(int i = 0; i < targetBs.size(); i++){
			double pointingTheta = targetBs.get(i).theta;
			double tan = (targetBs.get(i).y -shipY)/(targetBs.get(i).x -shipX);
			
			double targetTheta = Math.atan(tan) % (2*Math.PI);
			
			if((targetBs.get(i).x -shipX > 0))
				targetTheta += Math.PI;
			
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
			
			targetTheta %= 2*Math.PI;
			aimTheta %= 2*Math.PI;				
			
			if((targetTheta < aimTheta && targetTheta + Math.PI < aimTheta + ESCAPE_ANG && targetTheta + Math.PI > aimTheta - ESCAPE_ANG)
				|| (targetTheta > aimTheta && targetTheta - Math.PI < aimTheta + ESCAPE_ANG && targetTheta - Math.PI > aimTheta - ESCAPE_ANG)){
				pointingTheta += Math.PI;
				targetBs.get(i).theta = pointingTheta;
				targetBs.get(i).setAccel(ESCAPE_ACC);
				targetBs.get(i).speedLimit = ESCAPE_VEL;
			}else{
				targetBs.get(i).theta = pointingTheta;
				targetBs.get(i).setAccel(ACEL_RATE);
				targetBs.get(i).speedLimit = targetBs.get(i).SPEED_LIMIT;
			}
		}
	}
	
	public void garbageCollect(){
		for(int i = 0; i < targetBs.size(); i++){
			if(targetBs.get(i).isDead() == true){
				targetBs.remove(i);
			}
		}
	}
	
	public void setBounds(int width, int height){
		for(int i = 0; i < targetBs.size(); i++){
			targetBs.get(i).setBounds(width, height);
		}
	}

	
	public void fire(double x, double y) {
		if(ceaseFire)
			return;
		
		TargetB newB1;
		if(poly == null)
			newB1 = new TargetB(x, y, SPEED_LIMIT, width, color, hitsToKill);
		else
			newB1 = new TargetB(x, y, SPEED_LIMIT, poly, height, width, color, hitsToKill);
		
		targetBs.add(newB1);
	}
	
	public void draw(Graphics g){
		for(int i = 0; i < targetBs.size(); i++){
			targetBs.get(i).draw(g);
		}
	}
	
	public void move(){
		for(int i = 0; i < targetBs.size(); i++){
			targetBs.get(i).move();
		}
	}
	
	public boolean explode(ArrayList<Bullet> bullets, ExplosionMgr expl, Ship ship) throws MalformedURLException {
		for(int j = 0; j < targetBs.size(); j++){
			Intersection inter = targetBs.get(j).intersects(ship);
			if(inter != Intersection.NONE){
				ship.explode(expl);
				ship.killSelf();
				
					if(Sound.iShipExplode)
						Sound.ShipExplode.play();
				
				targetBs.get(j).explode(expl);
				targetBs.get(j).killSelf();
				return true;
			}
		}
		
		for(int i = 0; i < bullets.size(); i++){
			for(int j = 0; j < targetBs.size(); j++){
				Intersection inter = targetBs.get(j).intersects(bullets.get(i));
				if(inter != Intersection.NONE){
					int score = (int) ((int) (3+targetBs.get(j).hitsToKill)*(3+targetBs.get(j).SPEED_LIMIT)/(.2*targetBs.get(j).width));
					
					bullets.get(i).killSelf();
					
					targetBs.get(j).hitsTaken++;
					targetBs.get(j).miniexplode(expl);
					if(Sound.iMiniExplode)
						Sound.MiniExplode.play();
					Gametracker.score += score;
					
					if(targetBs.get(j).hitsTaken >= targetBs.get(j).hitsToKill){
						targetBs.get(j).explode(expl);
						Color id = targetBs.get(j).color;
						
						if(id == Color.magenta){
							if(Sound.iExplode1)
								Sound.Explode1.play();
						}
						else if(id == Color.blue){
							if(Sound.iExplode2)
								Sound.Explode2.play();
						}
						else if(id == Color.green){
							if(Sound.iExplode3)
								Sound.Explode3.play();
						}
						else if(id == Color.red){
							if(Sound.iExplode4)
								Sound.Explode4.play();
						}
						
						targetBs.get(j).killSelf();

						Gametracker.score += score*targetBs.get(j).hitsToKill;
					}
					
				}
			}
		}
		return false;
	}
	
	public void explodeAll(ExplosionMgr expl, Ship ship){
		for(int j = 0; j < targetBs.size(); j++){
				targetBs.get(j).miniexplode(expl);
				targetBs.get(j).killSelf();
		}
	}
}
