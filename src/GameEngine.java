import physics.PhysicsEngine;
import terrain.Course;
import terrain.Hole;

public class GameEngine {
    private UserInteraction user;
    private Course course;
    private PhysicsEngine physics;

    public GameEngine() {
        this.user = new UserInteraction();
        this.physics = new PhysicsEngine();
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

    private void play() {
        Hole hole;

        // Iterate through the holes of the game
        while(course.hasNextHole()) {
            hole = course.nextHole();

            if (course.isFirstHole()) {
                user.welcomeToCourse();
            }

            user.giveHoleDetails();
            hole.printTerrain();
           

            // While the ball is not in the hole
            do {
                user.giveBallDetails();

                // Tell the physics engine to hit the ball based on the user's choices
                physics.hitBall(user.hitBall(), hole);
            } while(!physics.ballInHole());
        }
    }

    // Terminate the program
    private void quitGame() {
        System.exit(0);
    }
}
