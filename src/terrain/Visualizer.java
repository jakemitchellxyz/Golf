package terrain;

import physics.Ball;
import terrain.obstacles.Obstacle;

// https://upload.wikimedia.org/wikipedia/commons/thumb/4/4c/Peripheral_vision.svg/2000px-Peripheral_vision.svg.png

public class Visualizer { // Allow user to look around
	
	public static final int LINE_OF_SIGHT = 10; // User can look a maximum of 10 m around him
	public static final double STEPS = 0.5; // Steps to check for an obstacle along a straight line within LINE_OF_SIGHT
	
	private double[] ballStartingPosition;
	private Obstacle[] obstacles; 
	private String info[];
	private int viewAngle;	
	private String locationOfObstacle;
	
		
	public void getObstacles(Ball golfBall, Point[][] terrain, Hole hole) {
		int[] holeCoord = hole.getHole();
		double angle = Math.atan((holeCoord[1] - golfBall.getY()) / (holeCoord[0] - golfBall.getX()));
		
		int x;
		int y;
		double distanceTraveled = 0;
		double viewAngle = angle + 30;
		
		ballStartingPosition = new double[] { golfBall.getX(), golfBall.getY() };
		
		if (viewAngle >= 0 ) {
			
			while (Math.abs(viewAngle) >= 0) {
				
				int count = 0;
				
			
				for (int i = 0; i < LINE_OF_SIGHT; i += STEPS) {
					x = (int) Math.round(distanceTraveled * Math.cos(Math.toRadians(golfBall.getAngle())) + ballStartingPosition[0]);
					y = (int) Math.round(distanceTraveled * Math.sin(Math.toRadians(golfBall.getAngle())) + ballStartingPosition[1]);

					 
					if (hole.hasObstacle(x, y)) {
						obstacles[i] = terrain[x][y].getObstacle();
						continue;				
					}
				
					distanceTraveled += STEPS;
				}
				
				if ( viewAngle > angle) {
					locationOfObstacle = "is " + (viewAngle - angle) + " on your left.";
				}
				else if ( viewAngle < angle){
					locationOfObstacle = "is " + (viewAngle) + " on your right.";
				}
				
				info[count] = "There is a " + obstacles[count].getType() + "at " + locationOfObstacle;
				count++;
				
				
				viewAngle -= 5;
			}
		
	
		
	}
		
	else if ( viewAngle < 0 )
	{
		while (Math.abs(viewAngle) >= 0) {
			
			int count = 0;
			
		
			for (int i = 0; i < LINE_OF_SIGHT; i += STEPS) {
				x = (int) Math.round(distanceTraveled * Math.cos(Math.toRadians(golfBall.getAngle())) + ballStartingPosition[0]);
				y = (int) Math.round(distanceTraveled * Math.sin(Math.toRadians(golfBall.getAngle())) + ballStartingPosition[1]);

				 
				if (hole.hasObstacle(x, y)) {
					obstacles[i] = terrain[x][y].getObstacle();
					continue;				
				}
			
				distanceTraveled += STEPS;
			}
			
			if ( viewAngle > angle) {
				locationOfObstacle = "is " + (viewAngle - angle) + " on your left.";
			}
			else if ( viewAngle < angle){
				locationOfObstacle = "is " + (viewAngle) + " on your right.";
			}
			
			info[count] = "There is a " + obstacles[count].getType() + "at " + locationOfObstacle;
			count++;
			
			
			viewAngle -= 5;
		}

	}
	
	}
	
}
