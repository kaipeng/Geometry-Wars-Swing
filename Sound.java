import java.applet.*;
import java.net.MalformedURLException;
import java.net.URL;

public class Sound {

	static public AudioClip Music, Explode1, Explode2, Explode3, Explode4, MiniExplode, ShipExplode, BulletFire, BulletTripleFire, Appear, BeginLife, EndLife, MiniBoom, Life;
	static public boolean iMusic, iExplode1, iExplode2, iExplode3, iExplode4, iMiniExplode, iShipExplode, iBulletFire, iBulletTripleFire, iAppear, iBeginLife, iEndLife, iMiniBoom, iLife;

			
	static public void init(){
		try{
			Music = Applet.newAudioClip(new URL("file:sounds/Empire.wav"));
			iMusic = true;
		}
		catch(MalformedURLException e){iMusic = false;}
		
		try{
			Explode1 = Applet.newAudioClip(new URL("file:sounds/Explode1.wav"));
			iExplode1 = true;
		}
		catch(MalformedURLException e){iExplode1 = false;}
		
		try{
			Explode2 = Applet.newAudioClip(new URL("file:sounds/Explode2.wav"));
			iExplode2 = true;
		}
		catch(MalformedURLException e){iExplode2 = false;}
		
		try{
			Explode3 = Applet.newAudioClip(new URL("file:sounds/Explode3.wav"));
			iExplode3 = true;
		}
		catch(MalformedURLException e){iExplode3 = false;}
		
		try{
			Explode4 = Applet.newAudioClip(new URL("file:sounds/Explode4.wav"));
			iExplode4 = true;
		}
		catch(MalformedURLException e){iExplode4 = false;}
		
		try{
			MiniExplode = Applet.newAudioClip(new URL("file:sounds/MiniExplode.wav"));
			iMiniExplode = true;
		}
		catch(MalformedURLException e){iMiniExplode = false;}
		
		try{
			ShipExplode = Applet.newAudioClip(new URL("file:sounds/ShipExplode.wav"));
			iShipExplode = true;
		}
		catch(MalformedURLException e){iShipExplode = false;}
		
		try{
			BulletFire = Applet.newAudioClip(new URL("file:sounds/BulletFire.wav"));
			iBulletFire = true;
		}
		catch(MalformedURLException e){iBulletFire = false;}
		
		try{
			BulletTripleFire = Applet.newAudioClip(new URL("file:sounds/BulletTripleFire.wav"));
			iBulletTripleFire = true;
		}
		catch(MalformedURLException e){iBulletTripleFire = false;}
		
		try{
			Appear = Applet.newAudioClip(new URL("file:sounds/Appear.wav"));
			iAppear = true;
		}
		catch(MalformedURLException e){iAppear = false;}
		
		try{
			BeginLife = Applet.newAudioClip(new URL("file:sounds/BeginLife.wav"));
			iBeginLife = true;
		}
		catch(MalformedURLException e){iBeginLife = false;}
		
		try{
			EndLife = Applet.newAudioClip(new URL("file:sounds/EndLife.wav"));
			iEndLife = true;
		}
		catch(MalformedURLException e){iEndLife = false;}
		
		try{
			MiniBoom = Applet.newAudioClip(new URL("file:sounds/MiniBoom.wav"));
			iMiniBoom = true;
		}
		catch(MalformedURLException e){iMiniBoom = false;}
		
		try{
			Life = Applet.newAudioClip(new URL("file:sounds/Life.wav"));
			iLife = true;
		}
		catch(MalformedURLException e){iLife = false;}
	}
	
	public static void startMusic() {
		Music.loop();
	}
	public static void stopMusic() {
		Music.stop();
	}
}