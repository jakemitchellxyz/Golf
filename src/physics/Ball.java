package physics;
// Data from: https://www.wolframalpha.com/
	

public class Ball {

	private double mass = 0.04593; // mass of a standard golf ball in kg
	private double area =  0.001432; // cross-sec area of ball in metres
	private double coeff = 0.47; // Drag Coefficient is 0.47 for spherical objects (https://en.wikipedia.org/wiki/Drag_coefficient)
	
	
	private double height; 
	
	
	
	public double getMass()
	{
		return this.mass;
	}
	
	public double getArea()
	{
		return this.area;
	}
	
	public double getCoeff()
	{
		return this.coeff;
	}
	
	// The ball knows it's own height
	public void setHeight(double h)
	{
		this.height = h;
	}
}
