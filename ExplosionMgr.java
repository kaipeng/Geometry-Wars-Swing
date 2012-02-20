import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.Timer;

/**
 * Explosion Mgr 
 * Like other managers, only this one shoots out Explosions in all directions
 * @param
 * @return
 */
public class ExplosionMgr{	
	public ArrayList<Explosion> explosions;
	
	private int fireInterval = 100;
	
	public Timer repeat;
	
	public ExplosionMgr(){
		explosions = new ArrayList<Explosion>(100);
		timerSetup();
	}
	
	public void timerSetup(){
	   	ActionListener repeatTimer = new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    	   		garbageCollect();
    		}
  	  	};  	  	
  	  	repeat = new Timer((int)fireInterval, repeatTimer);
  	  	repeat.start();
	}
	
	public void garbageCollect(){
		for(int i = 0; i < explosions.size(); i++){
			if(explosions.get(i).isDead() == true){
				explosions.remove(i);
			}
		}
	}
	
	public void setBounds(int width, int height){
		for(int i = 0; i < explosions.size(); i++){
			explosions.get(i).setBounds(width, height);
		}
	}

	/**
	 * Bullet Constructor
	 * Based on position of object, color, radius, fragment size, # of fragments,
	 * duration of explosion, the explosion firing fires in all directions
	 * at random angles.
	 * @param
	 * @return
	 */
	public void fire(int courtwidth, int courtheight, double x, double y, 
			int fragments, double fragSize, double radius, long lifeSpan, Color color) {		
		int layers = 2;
		
		Color[] colorSet = {color, Color.white, color, Color.orange, color, Color.white, color, Color.yellow};
		
		for(int j = 0; j < layers; j++){
			for(int i = 0; i < fragments; i++){

				Color fragCol = colorSet[(int)(Math.random()*((double)(colorSet.length)))];

				Explosion newB = new Explosion(courtwidth, courtheight, x, y, fragSize, Math.random() + 2*Math.PI*((double)i)/(double)fragments, 15+((double)j/layers)*radius, lifeSpan + 10*j, fragCol);
				explosions.add(newB);
			}
		}
	}
	
	public void draw(Graphics g){
		for(int i = 0; i < explosions.size(); i++){
			explosions.get(i).draw(g);
		}
	}
	
	public void move(){
		for(int i = 0; i < explosions.size(); i++){
			explosions.get(i).move();
		}
	}	
}
