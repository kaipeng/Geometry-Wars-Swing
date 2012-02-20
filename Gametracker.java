	import java.awt.*;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
import javax.swing.Timer;


/**
 * Gametracker 
 * Responsible for maintaining flow of game from level to level, lives, score, weapon upgrades, etc
 * Deploys targets based on level.
 * @param
 * @return
 */
public class Gametracker {
	public static Timer kill, release;
	
	public static long score = 0;
	public static int stage = 0;
	public static int substage = 0;
	public static int tonext = 5;
	
	public static int lives = 3;
	public static int newlifescore = 2000;
	public static boolean canDie = true;
	
	public static boolean isPaused;
	public static boolean isOver;
	
	public static boolean waiting;
	
	public static boolean weaponUpgrade;
	
	private static TargetBMgr targetB1s;
	private static TargetBMgr targetB2s;
	private static TargetBMgr targetB3s;
	private static TargetBMgr targetB4s;
	
	public static void newLife(){
		if(score > newlifescore){
			lives++;
			if(Sound.iLife)
				Sound.Life.play();
			newlifescore += newlifescore + 1000;

		}
	}
	
	public static void resetTargets(TargetBMgr targetB1,TargetBMgr targetB2,TargetBMgr targetB3,TargetBMgr targetB4){
		targetB1s = targetB1;
		targetB2s = targetB2;
		targetB3s = targetB3;
		targetB4s = targetB4;		
	}
	
	public static void startStage(int width, int height){
		if(stage == 0){
			weaponUpgrade =  false;
			if(substage == 1){
				for(int i = 0; i < 1; i++)
					targetB1s.fire(Math.random()*width,Math.random()*height);				
			}
			if(substage == 2){
				for(int i = 0; i < 2; i++)
					targetB2s.fire(Math.random()*width,Math.random()*height);
			}
			if(substage == 3){
				for(int i = 0; i < 2; i++)
					targetB3s.fire(Math.random()*width,Math.random()*height);
				for(int i = 0; i < 2; i++)
					targetB1s.fire(Math.random()*width,Math.random()*height);
			}
			if(substage == 4){
				for(int i = 0; i < 3; i++)
					targetB1s.fire(Math.random()*width,Math.random()*height);
				for(int i = 0; i < 3; i++)
					targetB2s.fire(Math.random()*width,Math.random()*height);
				for(int i = 0; i < 1; i++)
					targetB4s.fire(Math.random()*width,Math.random()*height);
			}
			if(substage == 5){
				for(int i = 0; i < 5; i++)
					targetB3s.fire(Math.random()*width,Math.random()*height);
				for(int i = 0; i < 5; i++)
					targetB2s.fire(Math.random()*width,Math.random()*height);
			}
		}
		if(stage == 1){
			weaponUpgrade =  true;
			if(substage == 1){
				for(int i = 0; i < 5; i++){
					targetB1s.fire(50*i,50*i);
				}
			}
			if(substage == 2){
				for(int i = 0; i < 4; i++){
					targetB3s.fire(30*i,30*i);
				}
				for(int i = 0; i < 4; i++){
					targetB3s.fire(width-30*i,height-30*i);
				}
				for(int i = 0; i < 4; i++){
					targetB3s.fire(30*i,height-30*i);
				}
				for(int i = 0; i < 4; i++){
					targetB3s.fire(width-30*i,30*i);
				}
			}
			if(substage == 3){
				weaponUpgrade =  false;
				for(int i = 0; i < 5; i++){
					targetB1s.fire(20*i,20*i);
				}
				for(int i = 0; i < 5; i++){
					targetB2s.fire(width-20*i,height-20*i);
				}
				for(int i = 0; i < 5; i++){
					targetB2s.fire(20*i,height-20*i);
				}
				for(int i = 0; i < 5; i++){
					targetB1s.fire(width-20*i,20*i);
				}
			}
			if(substage == 4){
				for(int i = 0; i < 3; i++)
					targetB3s.fire(Math.random()*width,Math.random()*height);
				for(int i = 0; i < 5; i++)
					targetB2s.fire(20*i,height/2);
				
				for(int i = 0; i < 5; i++)
					targetB2s.fire(width-20*i,height/2);
				
				for(int i = 0; i < 1; i++)
					targetB4s.fire(Math.random()*width,Math.random()*height);
			}
			if(substage == 5){
				for(int i = 0; i < 5; i++)
					targetB3s.fire(Math.random()*width,Math.random()*height);
				for(int i = 0; i < 5; i++)
					targetB2s.fire(Math.random()*width,Math.random()*height);
				for(int i = 0; i < 3; i++)
					targetB4s.fire(Math.random()*width,Math.random()*height);
			}
		}
		if(stage == 2){
			weaponUpgrade =  false;
			if(substage == 1){
				for(int i = 0; i < 3; i++){
					targetB3s.fire(30*i,height/2);
				}
				for(int i = 0; i < 3; i++){
					targetB3s.fire(width-30*i,height/2);
				}
			}
			if(substage == 2){				
				for(int i = 0; i < 3; i++)
					targetB3s.fire(width/3,0);
				
				for(int i = 0; i < 3; i++)
					targetB3s.fire(2*width/3,0);
				
			}
			if(substage == 3){				
				weaponUpgrade =  true;
				for(int i = 0; i < 4; i++)
				targetB1s.fire(width/3,0);
				for(int i = 0; i < 3; i++)
					targetB2s.fire(width-20*i,height/2);
				for(int i = 0; i < 4; i++)
				targetB1s.fire(2*width/3,0);
				for(int i = 0; i < 2; i++)
					targetB4s.fire(Math.random()*width,Math.random()*height);
			}
			if(substage == 4){
				for(int i = 0; i < 3; i++)
					targetB1s.fire(Math.random()*width,Math.random()*height);
				for(int i = 0; i < 4; i++)
					targetB1s.fire(width/3,0);
				for(int i = 0; i < 3; i++)
					targetB2s.fire(width-20*i,height/2);
				for(int i = 0; i < 4; i++)
				targetB1s.fire(2*width/3,0);
				for(int i = 0; i < 2; i++)
					targetB4s.fire(Math.random()*width,Math.random()*height);
			}
			if(substage == 5){
				for(int i = 0; i < 5; i++)
					targetB3s.fire(Math.random()*width,Math.random()*height);
				for(int i = 0; i < 5; i++)
					targetB2s.fire(Math.random()*width,Math.random()*height);
			}
		}
		if(stage == 3){
			weaponUpgrade =  true;
			if(substage == 1){
				for(int i = 0; i < 4; i++){
					targetB2s.fire(10*i,10*i);
				}
				for(int i = 0; i < 4; i++){
					targetB2s.fire(width-10*i,height-10*i);
				}
				for(int i = 0; i < 4; i++){
					targetB1s.fire(10*i,height-10*i);
				}
				for(int i = 0; i < 4; i++){
					targetB1s.fire(width-10*i,10*i);				
				}
					for(int i = 0; i < 3; i++)
						targetB4s.fire(Math.random()*width,Math.random()*height);
			}
			if(substage == 2){
				for(int i = 0; i < 8; i++)
					targetB3s.fire(10*i,10*i);
				for(int i = 0; i < 8; i++)
					targetB3s.fire(width-10*i,height-10*i);
				for(int i = 0; i < 8; i++)
					targetB3s.fire(10*i,height-10*i);
				for(int i = 0; i < 8; i++)
					targetB3s.fire(width-10*i,10*i);
			}
			if(substage == 3){
				for(int i = 0; i < 2; i++)
					targetB3s.fire(Math.random()*width,Math.random()*height);
				for(int i = 0; i < 2; i++)
					targetB1s.fire(Math.random()*width,Math.random()*height);
			}
			if(substage == 4){
				weaponUpgrade =  false;
				for(int i = 0; i < 6; i++)
					targetB1s.fire(Math.random()*width,Math.random()*height);
				for(int i = 0; i < 9; i++)
					targetB2s.fire(Math.random()*width,Math.random()*height);
				for(int i = 0; i < 1; i++)
					targetB4s.fire(Math.random()*width,Math.random()*height);
			}
			if(substage == 5){
				for(int i = 0; i < 5; i++)
					targetB3s.fire(Math.random()*width,Math.random()*height);
				for(int i = 0; i < 5; i++)
					targetB2s.fire(Math.random()*width,Math.random()*height);
			}
		}
		if(stage >= 4){
			weaponUpgrade =  false;
			if(substage == 1){
				for(int i = 0; i < 15; i++)
					targetB1s.fire(Math.random()*width,Math.random()*height);
				
			}
			if(substage == 2){
				for(int i = 0; i < 2; i++)
					targetB2s.fire(Math.random()*width,Math.random()*height);
				for(int i = 0; i < 3; i++)
					targetB1s.fire(Math.random()*width,Math.random()*height);
				for(int i = 0; i < 3; i++)
					targetB2s.fire(Math.random()*width,Math.random()*height);
				for(int i = 0; i < 5; i++)
					targetB4s.fire(Math.random()*width,Math.random()*height);
			}
			if(substage == 3){
				for(int i = 0; i < 3; i++)
					targetB3s.fire(20*i,height/2);
				
				for(int i = 0; i < 3; i++)
					targetB3s.fire(width-20*i,height/2);
				
					for(int i = 0; i < 2; i++)
						targetB2s.fire(Math.random()*width,Math.random()*height);
					for(int i = 0; i < 10; i++)
						targetB4s.fire(Math.random()*width,Math.random()*height);
				
			}
			if(substage == 4){
				for(int i = 0; i < 4; i++)
					targetB1s.fire(Math.random()*width,Math.random()*height);
				for(int i = 0; i < 2; i++)
					targetB2s.fire(Math.random()*width,Math.random()*height);
				for(int i = 0; i < 10; i++)
					targetB4s.fire(Math.random()*width,Math.random()*height);
			}
			if(substage == 5){
				for(int i = 0; i < 20; i++)
					targetB3s.fire(Math.random()*width,Math.random()*height);
				for(int i = 0; i < 20; i++)
					targetB2s.fire(Math.random()*width,Math.random()*height);
			}
		}
		if(Sound.iAppear)
			Sound.Appear.play();
		

	}
	//make waiting false at end of each new level released
	//start release as well
	
	public static void subadv(int width, int height){
		if(!waiting){
			waiting = true;

			substage++;
			if(substage >= tonext){
				stage++;
				substage = 0;
			}
			startStage(width, height);
		}
	}
	
	public static void goBack(){
		if(substage > 0)
			substage--;
		if(substage == 0 && stage > 0){
			substage = 5;
			stage--;
		}
	}
	
	public static void init(){
		isOver = false;
		isPaused = false;
		lives = 3;
		score = 0;
		stage = 0;
		substage = 0;
	}
	
	public static void shipDied(){
		if(canDie){
			lives--;
			canDie = false;
		}
		if(lives <= 0)
			isOver = true;
		
		ActionListener killTimer = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Sound.iEndLife)
					Sound.EndLife.play();
				return;
			}
		  	};
		  	kill = new Timer(2000, killTimer);
		  	kill.setRepeats(false);
		  	kill.start();
	}
}