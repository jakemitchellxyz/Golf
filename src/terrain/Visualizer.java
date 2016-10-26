package terrain;

import physics.Ball;

// https://upload.wikimedia.org/wikipedia/commons/thumb/4/4c/Peripheral_vision.svg/2000px-Peripheral_vision.svg.png

public class Visualizer { // Allow user to look around
	
	private double[] obstacles; 
		
	public void getObstacles(Ball golfBall, Point[][] terrain, int[] hole) {
		golfBall.setAngle2(hole);
		double angle = golfBall.getAngle();
		
	}
	
	

}
