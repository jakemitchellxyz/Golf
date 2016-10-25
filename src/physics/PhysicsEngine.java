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
import java.util.Random;

public class PhysicsEngine{
	private static final double LENGTH = 0.5;
	private Ball golfBall;
	
	
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
	
	public PhysicsEngine()
	{
		golfBall = new Ball();
	}

	public double getAngle()
	{
		return this.angle;
	}

	public double getVelocity()
	{
		return this.velocity;
	}
		
	public void hitBall(int userAngle, Club club, int power, int[] ball, int[] hole)
	{
		Random random = new Random();
		
		
		userAngle -= random.nextInt(club.getRange() / 10);
		
		int pick = random.nextInt(2);
		int [] multiplier = {-1,1};
		
		double angle = Math.atan(( hole[1] - ball[1] ) / (hole[0] - ball[0]));
		angle = Math.toDegrees(angle) + (userAngle*multiplier[pick]);
		
		double range = club.nextRange(power);	
		golfBall.setPath(range, angle);
		
	}

	// Function to launch ball
	// Takes distance along x and the club being used; returns height at that point
	public void nextHeight(Club club)
	{
		/*
		 * 
		 * 
		Credits to http://farside.ph.utexas.edu/teaching/336k/Newtonhtml/node29.html for equations.
		Credits to http://hyperphysics.phy-astr.gsu.edu/hbase/airfri2.html for Terminal Velocity function at high speeds.
		
			vT is the terminal velocity for fast moving object
			Density of air is assumed to be 1.225 kg/m^3

		*/
		
		double g = 9.81;
		double vT = Math.sqrt( (2*golfBall.MASS*g) / (golfBall.COEFF*1.225*golfBall.AREA) ); // Terminal Velocity
		double a = 1 - ((LENGTH*g) / (Math.cos(club.getLoft())*vT*this.velocity));
		double time = (Math.log(a) * vT) / -g; // time at which projectile is at that point
		double b = ((this.velocity*Math.sin(club.getLoft())) + vT);
		double x = Math.pow(Math.E,(-1*g*time)/vT);
		double c = 1 - (x);
		double height = ((vT/g)*(b)*(c)) - (vT*time); // Height of projectile above ground		
		
		golfBall.setHeight(height);

	}
	
	public void move()
	{
		
		double x = golfBall.getLocation("x");
		double y = golfBall.getLocation("y");
		
		x += LENGTH * Math.toDegrees(Math.cos(Math.toRadians(golfBall.getAngle())));
		y += LENGTH * Math.toDegrees(Math.sin(Math.toRadians(golfBall.getAngle())));
		
		
		
		golfBall.setLocation(x, y);
	}

} 
