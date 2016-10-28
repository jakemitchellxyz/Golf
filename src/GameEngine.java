import exceptions.*;
import physics.PhysicsEngine;
import terrain.Course;
import terrain.Hole;
import terrain.Visualizer;

public class GameEngine {
    private UserInteraction user;
    private Course course;
    private PhysicsEngine physics;
    private ScoreKeeper scoreboard;
    private Visualizer visualizer;

    public GameEngine() {
        this.user = new UserInteraction();
        this.physics = new PhysicsEngine();
        this.scoreboard = new ScoreKeeper();
        this.visualizer = new Visualizer();
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
            physics.resetBall(1, 1);

            visualizer.setHole(hole);
            visualizer.setBall(physics.getBall());

            // introduce them to the course
            if (course.isFirstHole()) {
                user.welcomeToCourse(course.numHoles());
            }

            user.welcomeToHole(course.holeNumber());

            // show the terrain if they want to see it
            if (user.wantsToSeeTerrain()) {
                hole.printTerrain();
            }

            // While the ball is not in the hole
            do {
                // Tell the physics engine to hit the ball based on the user's choices
                try {
                    physics.hitBall(user.hitBall(physics.getClubsForPrint(), visualizer), hole);
                } catch (BallOutOfBoundsException e) {
                    user.ballWentOutOfBounds();
                } catch (LandedInWaterException e) {
                    user.landedInWater();
                } catch (HitAnObstacle e) {
                    user.hitObstacle(e.getMessage());
                } finally {
                    scoreboard.swing();
                    visualizer.setBall(physics.getBall());
                }
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
