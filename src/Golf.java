import terrain.Course;
import terrain.Hole;
import terrain.Point;

/**
 * Jake Mitchell (jmitch32), Koushul Ramjattun (kramjatt)
 * Golf_Mitchell_Ramjattun
 * 13 October 2016
 * 
 * TAs: Jiageng Zhang, Dallis Polashenski
 */

public class Golf {

	public static void main(String[] args) {
		Course course = new Course(5, 18);
		Hole hole;

		while(course.hasNextHole()) {
			hole = course.nextHole();

			Point[][] terrain = hole.getTerrain();
			System.out.println("Hole " + course.holeNumber() + ": height " + terrain.length + ", width " + terrain[0].length + ", area " + terrain.length * terrain[0].length);
		}
	}

}
