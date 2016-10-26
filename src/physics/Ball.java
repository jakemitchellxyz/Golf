package physics;
// Data from: https://www.wolframalpha.com/
	

public class Ball {

	public final double MASS = 0.04593; // mass of a standard golf ball in kg
	public final double AREA =  0.001432; // cross-sec area of ball in metres
	public final double COEFF = 0.47; // Drag Coefficient is 0.47 for spherical objects (https://en.wikipedia.org/wiki/Drag_coefficient)
	
	
	private double height;
	private double[] path = new double[2];
	private double[] location = new double[2];
	private double angle2; // Angle from horizontal to straight line from ball to hole
	
	public void setAngle2(int[] hole)
	{
		this.angle2 = Math.toDegrees(Math.atan((hole[1] - this.location[1]) / (hole[0] - location[0])));
	}
	
	
	// The ball knows its own height
	public void setHeight(double h)
	{
		this.height = h;
	}
	
	//Let the ball know it's current path
	public void setPath(double r, double angle)
	{
		path[0] = r;
		path[1] = angle;
		
	}
	
	public double getAngle()
	{
		return this.path[1];
	}
	
	public double getRange()
	{
		return this.path[0];
	}
	
	public void setLocation(double x, double y)
	{
		location[0] = x;
		location[1] = y;
	}
	
	public double getLocation(String i)
	{
		return (i.equals("x")) ? location[0] : location[1];
	}
	
	
}

