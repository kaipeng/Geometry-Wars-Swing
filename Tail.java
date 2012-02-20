import java.awt.*;

public class Tail extends GameObject {
	final static int SCALE = 1;
	
	final static int HEIGHT = 2*SCALE;
	final static int WIDTH = 1*SCALE;
	
	final static double SPEED_LIMIT = 2;
	final static long LIFESPAN = 400;

	public Tail(int courtwidth, int courtheight, double shipX, double shipY, double theta) {
		super(shipX + 12, shipY + 8, 
				(-Math.cos(theta)*SPEED_LIMIT), 
				(-Math.sin(theta)*SPEED_LIMIT), 
				WIDTH, HEIGHT);
		super.speedLimit = SPEED_LIMIT;
		super.theta = theta;
		startLife(LIFESPAN);
	}
	
	public void bound(){	
		if ((x <= 1) || (x >= rightBound-1) || (y <= 1) || (y >= bottomBound-1))
			killSelf();
	}

	public void draw(Graphics g) {		
		Polygon bullet = new Polygon();
		double[] xp = new double[] {0,0};
		double[] yp = new double[] {-1,1};
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
	
	public void explode(ExplosionMgr expl){	}
}
