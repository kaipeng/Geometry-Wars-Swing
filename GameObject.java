import java.awt.*;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;


/**
 * GameObject 
 * Superclass for inheritance by almost all objects in the game.
 * Contains ability to rotate, translate, draw polygons.
 * Allows acceleration, velocity control, angular acceleration.
 * Keeps track of lifetime and whether it is dead or not,
 * which is a flag for garbage disposal.
 * 
 * @param
 * @return
 */
public abstract class GameObject {
    public double x; // x and y coordinates upper left
    public double y;

    protected int width;
    protected int height;

    protected double velocityX; // Pixels to move each time move() is called.
    protected double velocityY;
    protected double accel = 0;

    protected double speed;
    public double speedLimit = 10;
    
    protected double velocityA;
    public double theta = -((Math.PI)/2);

    protected int rightBound; // Maximum permissible x, y values.
    protected int bottomBound;
    
    public boolean isDead = false;
    
    public Timer kill;
    
	protected Color color = Color.white;

    public GameObject(double x, double y, double velocityX, double velocityY, int width,
            int height) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.width = width;
        this.height = height;
    }
    
    public void startLife(long lifespan){
    	ActionListener killTimer = new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			killSelf();
    		}
  	  	};
  	  	kill = new Timer((int)lifespan, killTimer);
  	  	kill.setRepeats(false);
  	  	kill.start();
    }
    
    public void killSelf(){
    	isDead = true;
    }
    
    public boolean isDead(){
    	return isDead;
    }

    public void setBounds(int width, int height) {
        rightBound = width - this.width;
        bottomBound = height - this.height;
    }

    public void setVelocity(double velocityX, double velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }
    
    public void setAccel(double accel){	
		this.accel = accel;
    }
    
    public void accelerate(){
    	double acceldX = this.velocityX + accel*Math.cos(theta);
    	double acceldY = this.velocityY + accel*Math.sin(theta);
    	double speed = Math.sqrt(acceldX*acceldX + acceldY*acceldY);	
    	
		if(speed > speedLimit){
			acceldX = acceldX*(speedLimit/speed);
			acceldY = acceldY*(speedLimit/speed);
		}
    	
		this.velocityX = acceldX;
		this.velocityY = acceldY;
    }
    
    public void setAngVelocity(double velocityA) {
        this.velocityA = velocityA;
    }
    
    public void turn(){
    	this.theta += velocityA;
    }
    
	public void thrust(double speed){
		this.velocityX += speed*Math.cos(theta);
        this.velocityY += speed*Math.sin(theta);
	}

    // Move the object at the given velocity.
    public void move() {
        x += velocityX;
        y += velocityY;

        accelerate();
        bound();
        clip();
    }

    // Keep the object in the bounds of the court
    public void clip() {
        if (x < 0)
            x = 0;
        else if (x > rightBound)
            x = rightBound;

        if (y < 0)
            y = 0;
        else if (y > bottomBound)
            y = bottomBound;
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
    
    public double[] scale(double[] points, double factor){
    	int xlen = points.length;
    	
    	for(int i = 0; i < xlen; i++){
    		double xp = points[i];
    		points[i] = xp*factor;
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
    
    /**
     * Compute whether two GameObjects intersect.
     * 
     * @param other
     *            The other game object to test for intersection with.
     * @return NONE if the objects do not intersect. Otherwise, a direction
     *         (relative to <code>this</code>) which points towards the other
     *         object.
     */
    public Intersection intersects(GameObject other) {
        if (   other.x > x + width
            || other.y > y + height
            || other.x + other.width  < x
            || other.y + other.height < y)
            return Intersection.NONE;

        // compute the vector from the center of this object to the center of
        // the other
        double dx = other.x + other.width /2 - (x + width /2);
        double dy = other.y + other.height/2 - (y + height/2);

        double theta = Math.atan2(dy, dx);
        double diagTheta = Math.atan2(height, width);

        if ( -diagTheta <= theta && theta <= diagTheta )
            return Intersection.RIGHT;
        if ( diagTheta <= theta && theta <= Math.PI - diagTheta )
            return Intersection.DOWN;
        if ( Math.PI - diagTheta <= theta || theta <= diagTheta - Math.PI )
            return Intersection.LEFT;
        // if ( diagTheta - Math.PI <= theta && theta <= diagTheta)
            return Intersection.UP;
    }
    
	public abstract void explode(ExplosionMgr expl);
    
    public abstract void draw(Graphics g);
    
    public abstract void bound();
    
    
    
    
    
    
    
//    
//    
//    //UNUSED
//    public void setSpeed(double speed){
//    	this.speed = speed;
//    	calcVelocity(this.speed, theta);
//    }
//    
//    public void incSpeed(double inc){
//    	this.speed += inc;
//    	calcVelocity(this.speed, theta);
//
//    }
//    
//    public void calcVelocity(double speed, double theta){
//    	this.velocityX = speed*Math.cos(theta);
//        this.velocityY = speed*Math.sin(theta);
//    }
//    //UNUSED
}
