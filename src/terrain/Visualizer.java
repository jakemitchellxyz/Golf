package terrain;

// https://upload.wikimedia.org/wikipedia/commons/thumb/4/4c/Peripheral_vision.svg/2000px-Peripheral_vision.svg.png

public class Visualizer { // Allow user to look around
	
	public static final int LINE_OF_SIGHT = 10; // User can look a maximum of 10 m around him
	public static final double STEPS = 0.5; // Steps to check for an obstacle along a straight line within LINE_OF_SIGHT
    public static final int DEGREES_OF_CHANGE = 5;

	private int[][] obstacles;
    private double viewerAngle;

    private Hole hole;
    private double[] ball;

    public void setHole(Hole hole) {
        this.hole = hole;
    }

    public void setBall(double[] ball) {
        this.ball = ball;
    }

	public void lookAround() {
		int[] holeCoord = hole.getHole();

        this.viewerAngle = Math.toDegrees(Math.atan((holeCoord[1] - ball[1]) / (holeCoord[0] - ball[0])));
		double startingAngle = viewerAngle + 30;
        double offsetDegrees;

        // from 1-12
        for (int d = 0; d < 13; d++) {
            offsetDegrees = d * DEGREES_OF_CHANGE;
            look(startingAngle - offsetDegrees);
        }

        saySurroundings();
	}

	// Look at obstacles
	private void look(double angle) {
        int x;
        int y;

		for (double i = 0; i < LINE_OF_SIGHT; i += STEPS) {
			x = (int) Math.round(i * Math.cos(Math.toRadians(angle)) + ball[0]);
			y = (int) Math.round(i * Math.sin(Math.toRadians(angle)) + ball[1]);

            // If coordinate exists
            if (hole.exists(x, y)) {
                // if there is an obstacle there
                if (hole.hasObstacle(x, y)) {
                    // Add point to it
                    int[][] nObstacles = new int[obstacles.length + 1][];
                    for (int j = 0; j < obstacles.length; j++) {
                        nObstacles[j] = obstacles[j];
                    }
                    nObstacles[nObstacles.length - 1] = new int[] { x, y, (int) Math.round(i * 1.09361), (int) Math.round(angle) };

                    obstacles = nObstacles;
                }
            } else {
                break; // we're looking off the map
            }
		}
	}

	// Print out all of the obstacles near you
	private void saySurroundings () {
        String locationOfObstacle;
        int angle;

        // If there are no obstacles
        if (obstacles.length == 0) {
            System.out.println("There are no obstacles near you!");
        } else {
            for (int[] obstacle : obstacles) {
                angle = obstacle[3];

                locationOfObstacle = "There is a " + hole.getObstacle(obstacle[0], obstacle[1]);

                if (angle < viewerAngle) {
                    locationOfObstacle += " " + obstacle[3] + " degrees on your left";
                } else if (angle > viewerAngle) {
                    locationOfObstacle += " " + obstacle[3] + " degrees on your right";
                } else {
                    locationOfObstacle += " is directly in front of you";
                }

                locationOfObstacle += " " + obstacle[2] + " yards away.";

                System.out.println(locationOfObstacle);
            }
        }
	}

    // return the current distance between the ball to the hole in yards
	public String getDistanceToHole() {
        // Compute and save distance to hole
        double distanceToHole = Math.sqrt((Math.pow((hole.getHole()[1] - ball[1]), 2) +  Math.pow((hole.getHole()[0] - ball[0]), 2)));
        distanceToHole *= 1.09361; // Convert to yards

        return (distanceToHole < 20) ? Math.round(distanceToHole * 3) + " feet" : Math.round(distanceToHole) + " yards";
	}
}
