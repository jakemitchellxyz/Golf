import physics.PhysicsEngine;
import terrain.Course;
import terrain.Hole;

public class GameEngine {
    private UserInteraction user;
    private Course course;
    private PhysicsEngine physics;
    private ScoreKeeper scoreboard;

    public GameEngine() {
        this.user = new UserInteraction();
        this.physics = new PhysicsEngine();
        this.scoreboard = new ScoreKeeper();
    }
    
    public PhysicsEngine getPhysicsEngine() {
    	return this.physics;
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

    // Start playing the game
    private void play() {
        Hole hole;
        
        // Iterate through the holes of the game
        while(course.hasNextHole()) {
            hole = course.nextHole();
            scoreboard.setPar(hole.getPar());

            // introduce them to the course
            if (course.isFirstHole()) {
                user.welcomeToCourse();
            }

            // show the terrain if they want to see it
            if (user.wantsToSeeTerrain()) {
                hole.printTerrain();
            }

            // While the ball is not in the hole
            do {
                // Tell the physics engine to hit the ball based on the user's choices
                physics.hitBall(user.hitBall(physics.getClubsForPrint()), hole);
                scoreboard.swing();
            } while(!physics.ballInHole());

            // If they made a ball in the hole
            user.congratulateHole();
            user.updateScore(scoreboard.getHoleScore());
        }

        // If they finished the game
        user.congratulateGame();
        user.updateFinalScore(scoreboard.getFinalScore());
    }

    // Terminate the program
    private void quitGame() {
        System.exit(0);
    }
}
