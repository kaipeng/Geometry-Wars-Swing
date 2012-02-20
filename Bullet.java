import java.awt.*;


/**
 * Bullet Class
 * Extends GameObject to make bullet objects. 
 */
public class Bullet extends GameObject {
	final static int SCALE = 1;
	
	final static int HEIGHT = 9*SCALE;
	final static int WIDTH = 3*SCALE;
	
	final static double SPEED_LIMIT = 12;
	final static long LIFESPAN = 3000;

	/**
	 * Bullet Constructor
	 * Takes on position and velocity of the ship and the direction to fire in - given by the manager
	 * @param
	 * @return
	 */
	public Bullet(int courtwidth, int courtheight, double x, double y, double shipVX, double shipVY, double theta) {
		super(x + 12, y + 8, 
				(shipVX + Math.cos(theta)*SPEED_LIMIT), 
				(shipVY + Math.sin(theta)*SPEED_LIMIT), 
				WIDTH, HEIGHT);
		super.speedLimit = SPEED_LIMIT;
		super.theta = theta;
		startLife(LIFESPAN);
	}
	
	public void bound(){	
		if ((x <= 1) || (x >= rightBound-1) || (y <= 1) || (y >= bottomBound-1))
			killSelf();
	}

	/**
	 * Bullet Draw
	 * Makes small chevrons
	 * @param
	 * @return
	 */
	public void draw(Graphics g) {
		Polygon bullet = new Polygon();
		double[] xp = new double[] {0,2, 0,-2};
		double[] yp = new double[] {4,2,-4, 2};
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
		
		bullet.xpoints = transxp;
		bullet.ypoints = transyp;
		bullet.npoints = points;
		g.drawPolygon(bullet);
	}
	
	/**
	 * Bullet Explode
	 * Bullets do not explode due to too much system requirements
	 * @param
	 * @return
	 */
	public void explode(ExplosionMgr expl){
		isDead = true;
	}
}
