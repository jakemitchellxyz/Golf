package physics;

// Data from: https://www.wolframalpha.com/
public class Ball {

	public final double MASS = 0.04593; // mass of a standard golf ball in kg
	public final double AREA =  0.001432; // cross-sec area of ball in metres
	public final double COEFF = 0.47; // Drag Coefficient is 0.47 for spherical objects (https://en.wikipedia.org/wiki/Drag_coefficient)

	private double angle;
    private double distance;
    private double x;
    private double y;

    // Default the location to the tee
    public Ball() {
        x = y = 1;
    }

	// The ball knows its terminating distance
    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

	public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getAngle() {
        return angle;
    }

    public void setLocation (double x, double y) {
        this.x = x;
        this.y = y;
    }

	public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
	
	
}

