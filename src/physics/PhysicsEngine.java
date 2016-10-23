/**
 * Physics Engine
 * Variables:
 * 	Velocity
 * 	Angle - from user (angle ON THE GROUND)
 * 	Loft Angle - from club (launch angle)
 * 
 *Methods:
 *	nextHeight: returns height at x
 *	
 * 	
 * 
 * Generates one ball
 * Generates 10 clubs
 */


package physics;

public class PhysicsEngine{
	
	private Ball ball;

	
	// An array with all the 10 clubs
	// Name, mean, standard deviation, loft
	private static final Club[] CLUBS = new Club[] {
					
		new Club("Driver", 230, 30, 13),
		new Club("3-wood", 215, 20, 17),
		new Club("3-iron", 180, 20, 24),
		new Club("4-iron", 170, 17, 28),
		new Club("5-iron", 155, 15, 32),
		new Club("6-iron", 145, 15, 36),
		new Club("7-iron", 135, 15, 40),
		new Club("8-iron", 125, 15, 44),
		new Club("9-iron", 110, 10, 48),
		new Club("Wedge ",  50, 10, 60) 
					
			
	};
	
	
	private double angle;
	private double velocity;
	
	
	

	public PhysicsEngine(double angle, double velocity)
	{
		this.angle = angle;
		this.velocity = velocity;
		

		
		// Make us a new ball
		this.ball = new Ball();
		
		
		
	}

	public double getAngle()
	{
		return this.angle;
	}

	public double getVelocity()
	{
		return this.velocity;
	}

	public double getRange()
	{
		/* 

		Calculate maximum horizontal distance the projectile can travel
		This formula is for no air resistance,
			it works since range with air resistance would be less anyway.
		*/


		double range = (this.velocity* this.velocity)*Math.sin(2*Math.toRadians(this.angle));
		
		return range;
	}

	

	// Function to launch ball
	// Takes distance along x and the club being used; returns height at that point
	
	public void nextHeight(Club club)
	{

		
		double distanceX = 0.5;
		
		/*
		 * 
		 * 
		Credits to http://farside.ph.utexas.edu/teaching/336k/Newtonhtml/node29.html for equations.
		Credits to http://hyperphysics.phy-astr.gsu.edu/hbase/airfri2.html for Terminal Velocity function at high speeds.
		
			vT is the terminal velocity for fast moving object
			Density of air is assumed to be 1.225 kg/m^3
			
			
		

		*/
		
		double g = 9.81;
		double vT = Math.sqrt( (2*ball.MASS*g) / (ball.COEFF*1.225*ball.AREA) ); // Terminal Velocity
		
		double a = 1 - ((distanceX*g) / (Math.cos(club.getLoft())*vT*this.velocity));
		double time = (Math.log(a) * vT) / -g; // time at which projectile is at that point

		double b = ((this.velocity*Math.sin(club.getLoft())) + vT);
		double x = Math.pow(Math.E,(-1*g*time)/vT);
		double c = 1 - (x);
		
		double height = ((vT/g)*(b)*(c)) - (vT*time); // Height of projectile above ground
		
		
		ball.setHeight(height);
		

		

	}

	
	
	
} 
