import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.Timer;

public class TailMgr{	
	public ArrayList<Tail> tails;
	
	private int fireInterval = 30;
	
	private boolean canFire = true;
	public Timer repeat;
	
	public TailMgr(){
		tails = new ArrayList<Tail>(200);
		timerSetup();
	}
	
	public void timerSetup(){
	   	ActionListener repeatTimer = new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    	   		garbageCollect();
    	   		canFire = true;
    		}
  	  	};  	  	
  	  	repeat = new Timer((int)fireInterval, repeatTimer);
  	  	repeat.start();
	}
	
	public void garbageCollect(){
		for(int i = 0; i < tails.size(); i++){
			if(tails.get(i).isDead() == true){
				tails.remove(i);
			}
		}
	}
	
	public void setBounds(int width, int height){
		for(int i = 0; i < tails.size(); i++){
			tails.get(i).setBounds(width, height);
		}
	}

	
	public void fire(int courtwidth, int courtheight, double x, double y, double pointingTheta) {
		if(canFire == false)
			return;
		
		Tail newB1 = new Tail(courtwidth, courtheight, x-12*Math.cos(pointingTheta), y-12*Math.sin(pointingTheta), pointingTheta - .1);
		Tail newB2 = new Tail(courtwidth, courtheight, x-12*Math.cos(pointingTheta), y-12*Math.sin(pointingTheta), pointingTheta);
		Tail newB3 = new Tail(courtwidth, courtheight, x-12*Math.cos(pointingTheta), y-12*Math.sin(pointingTheta), pointingTheta + .1);
		Tail newB4 = new Tail(courtwidth, courtheight, x-12*Math.cos(pointingTheta), y-12*Math.sin(pointingTheta), pointingTheta + .04);
		Tail newB5 = new Tail(courtwidth, courtheight, x-12*Math.cos(pointingTheta), y-12*Math.sin(pointingTheta), pointingTheta -.04);
		tails.add(newB1);
		tails.add(newB2);
		tails.add(newB3);
		tails.add(newB4);
		tails.add(newB5);
		canFire = false;
	}
	
	public void draw(Graphics g){
		for(int i = 0; i < tails.size(); i++){
			tails.get(i).draw(g);
		}
	}
	
	public void move(){
		for(int i = 0; i < tails.size(); i++){
			tails.get(i).move();
		}
	}	
}
