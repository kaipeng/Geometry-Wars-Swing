	import java.awt.*;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 * Explosion
 * Much like all other object classes only this doesnt inherit from GameObject to
 * reduce bulkiness of unused methods, variables, since exlosions are just simple,
 * non-accelerating objects.
 * @param
 * @return
 */
public class Explosion {
	final static int SCALE = 1;
	
	final static int HEIGHT = 2*SCALE;
	final static int WIDTH = 1*SCALE;
	
	double size;
	
	   public double x; // x and y coordinates upper left
	    public double y;

	    protected int width;
	    protected int height;

	    protected double velocityX; // Pixels to move each time move() is called.
	    protected double velocityY;

	    protected double speed;
	    public double speedLimit = 10;
	    
	    protected double velocityA;
	    public double theta = -((Math.PI)/2);

	    protected int rightBound; // Maximum permissible x, y values.
	    protected int bottomBound;
	    
	    public boolean isDead = false;
	    
	    public Timer kill;
	    
		protected Color color = Color.white;
		
	
	public Explosion(int courtwidth, int courtheight, double originX, double originY, double size, double theta, double radius, long lifeSpan, Color color) {
        this.x = originX;
        this.y = originY;
        this.velocityX = (-Math.cos(theta)*(radius/(lifeSpan/100.)));
        this.velocityY = (-Math.sin(theta)*(radius/(lifeSpan/100.)));
        this.width = WIDTH;
        this.height = HEIGHT;
		this.speedLimit = 6;
		this.theta = theta;
		startLife((long)lifeSpan);
		this.color = color;
		this.size = size;
	}
	
	    
	    public void startLife(long lifespan){
	    	ActionListener killTimer = new ActionListener() {
	    		public void actionPerformed(ActionEvent e) {
	    			killSelf();
	    		}
	  	  	};
	  	  	kill = new Timer((int)lifespan, killTimer);
	  	  	kill.start();
	    }
	    
	    public void killSelf(){
	    	kill.stop();
	    	isDead = true;
	    }
	    
	    public boolean isDead(){
	    	return isDead;
	    }

	    public void setBounds(int width, int height) {
	        rightBound = width - this.width;
	        bottomBound = height - this.height;
	    }
	    

	    // Move the object at the given velocity.
	    public void move() {
	        x += velocityX;
	        y += velocityY;

	        bound();
	    }

	    
	    public double[][] rotate(double[][] points, double theta){
	    	if(points.length < 2)
	    		return points;
	    	
	    	int xlen = points[0].length;
	    	int ylen = points[1].length;
	    	
	    	if(xlen!=ylen)
	    		return points;
	    	
	    	for(int i = 0; i < xlen; i++){
	    		double xp = points[0][i];
	    		double yp = points[1][i];
	    		points[0][i] = xp*Math.cos(theta) - yp*Math.sin(theta);
	    		points[1][i] = xp*Math.sin(theta) + yp*Math.cos(theta);
	    	}
			return points;
	    }
	    
	    public double[] translate(double[] points, double shift){
	    	int xlen = points.length;
	    	
	    	for(int i = 0; i < xlen; i++){
	    		double xp = points[i];
	    		points[i] = xp+shift;
	    	}
			return points;
	    }
	    
	    public int[] round(double[] coords){
	    	int points = coords.length;
	    	
	    	int[] rpoints = new int[points];
			
			for(int i = 0; i < points; i++){
				rpoints[i] = (int) Math.round(coords[i]);
			}
			return rpoints;
	    }

	
	public void bound(){	
		if ((x <= 1) || (x >= rightBound-1) || (y <= 1) || (y >= bottomBound-1))
			killSelf();
	}

	public void draw(Graphics g) {
		g.setColor(this.color);
		
		Polygon bullet = new Polygon();
		double[] xp = new double[] {0,0};
		double[] yp = new double[] {-1*size,1*size};
		int points = xp.length;
		int[] transxp, transyp;
		
		double[][] coords = new double[][] {xp, yp};
		coords = rotate(coords, (theta+(Math.PI)/2));
		coords[0] = translate(coords[0], x+WIDTH/2);
		coords[1] = translate(coords[1], y+HEIGHT/2);
		transxp = round(coords[0]);
		transyp = round(coords[1]);
		
		bullet.xpoints = transxp;
		bullet.ypoints = transyp;
		bullet.npoints = points;
		g.drawPolygon(bullet);
	}
	
	public void explode(ExplosionMgr expl){}
}