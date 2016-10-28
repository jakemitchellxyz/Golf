package physics;

import terrain.Hole;
import java.util.Random;

public class PhysicsEngine {

	private static final double G = 9.81; // Gravity
	private static final double STEP = 0.5; // distance to move the ball each step
	private static final Club[] CLUBS = new Club[] { // An array with all the 10 clubs
			// Name, mean, standard deviation, loft, accuracy (10 is bad, 1 is good)
			new Club("Driver", 230, 30, 13, 10),
			new Club("3-wood", 215, 20, 17, 9),
			new Club("3-iron", 180, 20, 24, 7),
			new Club("4-iron", 170, 17, 28, 6),
			new Club("5-iron", 155, 15, 32, 5),
			new Club("6-iron", 145, 15, 36, 4),
			new Club("7-iron", 135, 15, 40, 3),
			new Club("8-iron", 125, 15, 44, 2),
			new Club("9-iron", 110, 10, 48, 1),
			new Club("Wedge ",  50, 10, 60, 10)
	};

	private Ball golfBall;
	private Club thisClub;
	private Hole hole;
	private double[] ballStartingPosition;

	private double velocity;
	
	public PhysicsEngine() {
		golfBall = new Ball();
	}
	
	public Ball getBall() {
		return this.golfBall;
	}

	// settings = [ club, power, userAngle ]
	public void hitBall(int[] settings, Hole hole) {
		Random random = new Random();
		thisClub = CLUBS[settings[0]]; // Get the club the user chose
		this.hole = hole;
		int[] holeCoord = hole.getHole();
		ballStartingPosition = new double[] { golfBall.getX(), golfBall.getY() };

		// user's angle + inaccuracy from club + inaccuracy from more power) / 4.4 to give a max of 5 * either + or - 1 to decide left or right
		double userAngle = settings[2] + (((random.nextGaussian() + thisClub.getAccuracy()) + (random.nextGaussian() + settings[1])) / 4.4) * (random.nextBoolean() ? 1 : -1);

		// set the ball's angle to launch
		double angle = Math.atan((holeCoord[1] - golfBall.getY()) / (holeCoord[0] - golfBall.getX()));
		angle = Math.toDegrees(angle) + userAngle;
		golfBall.setAngle(angle);

		// Set the terminating distance of the ball
		double distance = thisClub.nextRange(settings[1]);
		golfBall.setDistance(distance);
		
		/*
		 * Let's convert the user's [1-10] power input to actual power values, then to velocity
		 * Assuming all the energy from the user is converted to kinetic energy of the golf ball
		 * DATA: http://members.swingmangolf.com/wp-content/uploads/2014/04/golf-swing-speed-chart.gif
		 * 
		 * 
		 */
		
		this.velocity = Math.sqrt( (2 * settings[1] * 0.1) / ( golfBall.MASS ) ) * 30;
		this.velocity -= thisClub.getLoft(); // Take the club being used into consideration

		// Move the ball towards the target
		move();
	}

	// Move the ball step by step towards the target
	private void move() {
		double distanceTraveled = 0;
		int x;
		int y;

		// As long as we haven't reached the goal
		while (distanceTraveled < golfBall.getDistance()) {
			x = (int) Math.round(distanceTraveled * Math.cos(Math.toRadians(golfBall.getAngle())) + ballStartingPosition[0]);
			y = (int) Math.round(distanceTraveled * Math.sin(Math.toRadians(golfBall.getAngle())) + ballStartingPosition[1]);

			// If there is no obstacle at this coordinate
			if (!hole.hasObstacle(x, y)) {
				golfBall.setLocation(x, y);
			} else {
				// If we are shorter than an obstacle, stop there
				if (nextHeight(distanceTraveled) <= hole.getHeight(x, y)) {
					break;
				}
			}

			distanceTraveled += STEP;
		}
	}

	// Function to launch ball
	// Takes distance along x and the club being used; returns height at that point
	private double nextHeight (double distanceTraveled) {
		/*
		Credits to http://farside.ph.utexas.edu/teaching/336k/Newtonhtml/node29.html for equations.
		Credits to http://hyperphysics.phy-astr.gsu.edu/hbase/airfri2.html for Terminal Velocity function at high speeds.
		
			vT is the terminal velocity for fast moving object
			Density of air is assumed to be 1.225 kg/m^3
		*/

		double vT = Math.sqrt((2 * golfBall.MASS * G) / (golfBall.COEFF * 1.225 * golfBall.AREA)); // Terminal Velocity
		double a = 1 - (distanceTraveled * G) / (Math.cos(thisClub.getLoft()) * vT * this.velocity);
		double time = (Math.log(a) * vT) / -G; // time at which projectile is at that point
		double b = this.velocity * Math.sin(thisClub.getLoft()) + vT;
		double x = Math.pow(Math.E, (-1 * G * time) / vT);
		double c = 1 - x;
		double height = (vT / G) * (b * c) - (vT * time); // Height of projectile above ground
		
		return height;
	}

	// True if ball is at the location of the hole (within the radius of the hole)
	public boolean ballInHole() {
		double holeRadius = 0.053975; // in meters

		return Math.abs(golfBall.getX() - hole.getHole()[0]) <= holeRadius && Math.abs(golfBall.getY() - hole.getHole()[1]) <= holeRadius;
	}

	// Structure the clubs in a way that can be presented to the user in a pretty way
	public String[][] getClubsForPrint() {
		// [ number, name, max distance, accuracy ]
		String[][] output = new String[12][];
		int maxName = 4;
		int maxDistance = 8;

		// Titles
		output[0] = new String[] { "Number", "Name", "Distance", "Accuracy" };

		Club club;
		String name;
		String distance;

		// Store each club
		for(int i = 0; i < CLUBS.length; i++) {
			club = CLUBS[i];
			name = club.getName();
			distance = club.getRange() + "";

			// store the club in the array
			output[i + 1] = new String[] { (i + 1) + "", name, distance, club.getAccuracy() + "" };

			// update the maximums
			maxName = Math.max(maxName, name.length());
			maxDistance = Math.max(maxDistance, distance.length());
		}

		// save the maximums for use later
		output[11] = new String[] { "6", maxName + "", maxDistance + "", "8" };

		// send back the formatted output
		return output;
	}

} 
