import terrain.Course;
import terrain.Hole;
import terrain.Point;

public class GameEngine {
    private UserInteraction user;
    private Course course;

    public GameEngine() {
        this.user = new UserInteraction();
    }

    // Give user instructions and create a new game
    public void run() {
        user.giveInstructions();

        if(user.wantsToPlay()) {
            this.course = new Course(user.getSettings());
            play();
        } else {
            quitGame();
        }
    }

    public void play() {
        user.sayln("We're playing now!");
//        Hole hole;
//        Point[][] terrain;
//
//        // Iterate through the holes of the game
//        while(course.hasNextHole()) {
//            hole = course.nextHole();
//
//            terrain = hole.getTerrain();
//            System.out.println("Hole " + course.holeNumber() + ": height " + terrain.length + ", width " + terrain[0].length + ", area " + terrain.length * terrain[0].length);
//        }
    }

    // Terminate the program
    private void quitGame() {
        System.exit(0);
    }
}
