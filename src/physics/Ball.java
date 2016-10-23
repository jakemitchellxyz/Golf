package physics;
// Data from: https://www.wolframalpha.com/
	

public class Ball {

	public final double MASS = 0.04593; // mass of a standard golf ball in kg
	public final double AREA =  0.001432; // cross-sec area of ball in metres
	public final double COEFF = 0.47; // Drag Coefficient is 0.47 for spherical objects (https://en.wikipedia.org/wiki/Drag_coefficient)
	
	
	private double height; 
	
	
	
	
	
	// The ball knows its own height
	public void setHeight(double h)
	{
		this.height = h;
	}
}
