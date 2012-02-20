import java.awt.*;

public class TargetB extends GameObject {
	final static int SCALE = 3;
	
	final static int HEIGHT = 5*SCALE;
	final static int WIDTH = 5*SCALE;
		
	protected double[][] poly = null;
	
	public double SPEED_LIMIT = 4;
	
	public int hitsTaken = 0;
	
	public int hitsToKill = 1;

	public TargetB(double x, double y, double speed,
			int width, Color color, int hits) {
		super(x, y, 0, 0, SCALE*width, SCALE*width);
		super.speedLimit = speed;
		this.color = color;
		this.SPEED_LIMIT = speed;
		hitsToKill = hits;
	}
	
	public TargetB(double x, double y, double speed, double[][] poly, int height, int width, Color color, int hits) {
		super(x, y, 0, 0, SCALE*width, SCALE*height);
		super.speedLimit = speed;
		this.poly = poly;
		this.color = color;
		this.SPEED_LIMIT = (int)speed;
		hitsToKill = hits;
	}
	

	public void bound(){	
		if (x < 0 || x > rightBound)
			velocityX = 0;
		if (y < 0 || y > bottomBound)
			velocityY = 0;		
	}

	public void draw(Graphics g) {
		Polygon target = new Polygon();
		double[] xp, yp;
		if(poly == null){
			g.setColor(color);
			g.drawOval((int)Math.round(x), (int)Math.round(y), Math.round(width), Math.round(height));
			return;
		}
		else
		{
			xp = poly[0].clone();
			yp = poly[1].clone();
			g.setColor(color);
		}
	
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
		
		target.xpoints = transxp;
		target.ypoints = transyp;
		target.npoints = points;
		
		
		g.drawPolygon(target);
	}
	
	public void explode(ExplosionMgr expl){
		expl.fire(rightBound, bottomBound, x, y, 7, 2, width*2, 500, super.color);
		isDead = true;
	}
	
	public void miniexplode(ExplosionMgr expl){
		expl.fire(rightBound, bottomBound, x, y, 3, 1, 10, 200, super.color);
	}
}
