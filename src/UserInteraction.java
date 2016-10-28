import java.util.Scanner;
import terrain.Visualizer;
import terrain.Hole;

public class UserInteraction {
    private Scanner sc;
    private Visualizer visualizer;

    public UserInteraction() {
        this.sc = new Scanner(System.in);
        this.visualizer = new Visualizer();
    }

    public void giveInstructions() {
        // TODO: give user instructions for the game
        sayln("Here are some instructions!");
    }

    // Ask the user if they want to play
    public boolean wantsToPlay() {
        return wantsToDoSomething("So what do you say? Do you want to play? (yes/no) ",
                                        "So do you want to play or not? (yes/no) ");
    }

    // Ask the user if they want to see the terrain
    public boolean wantsToSeeTerrain() {
        return wantsToDoSomething("Would you like to see an output of the terrain (yes/no)? ",
                                        "So do you want to see it or not? (yes/no) ");
    }

    // Ask the user if they want to do something
    private boolean wantsToDoSomething(String question, String reAsk) {
        say(question);
        String answer = sc.nextLine();

        boolean wantsTo = false; // Defaults to false, only true if they explicitly say yes

        // Keep asking until they say yes or no
        while (!wantsTo) {

            // If they say yes
            if (answer.toLowerCase().equals("yes")
                    || answer.toLowerCase().equals("y")) {

                wantsTo = true;

                // If they explicitly said "no" or "n"
            } else if (answer.toLowerCase().equals("no")
                    || answer.toLowerCase().equals("n")) {

                break;

                // Else, re-ask them if they want to play
            } else {
                say(reAsk);
                answer = sc.nextLine();
            }
        }

        return wantsTo;
    }

    // Gets the difficulty and number of holes for the game
    public int[] getSettings() {
        String area;

        // Keep asking until they decide one of these three
        do {
            say("Where do you want to play (desert, forest, swamp)? ");
            area = sc.nextLine();
        } while(!(area.toLowerCase().equals("desert")
                || area.toLowerCase().equals("forest")
                || area.toLowerCase().equals("swamp")));

        // desert = 0; forest = 1; swamp = 2
        int biome = area.toLowerCase().equals("desert") ? 0 :
                        area.toLowerCase().equals("forest") ? 1 : 2;

        say("How difficult do you want the game (1-10)? ");
        int difficulty = Math.min(10, Math.max(1, sc.nextInt())); // constrains input to 1-10

        say("And how many holes do you want to play (recommended 9 or 18)? ");
        int numHoles = Math.max(1, sc.nextInt()); // Forces at least 1

        return new int[]{ difficulty, numHoles, biome };
    }

    // Give user details about the course
    public void welcomeToCourse() {
        // TODO: welcome user nicely
        sayln("Welcome to the course!");
        
    }

    // Tell the user the details of the hole
    public void giveHoleDetails() {
        // TODO: give details about the hole
        sayln("Here are the hole details: ");
    }
    
    public void giveBallDetails(Visualizer visualizer) {
    	// TODO: give visualizer details and ball details
    	visualizer.getObstacles(golfBall, terrain, hole);
        sayln("Here are the details from the visualizer: ");
        sayln("Distance to hole: " + visualizer.getDistanceToHole() + " yards.");
        
    }

    // Ask user for details about how to hit the ball: [ club, power, userAngle ]
    public int[] hitBall(String[][] clubs) {
        String input;
        do { // while they haven't decided on an angle,
            giveBallDetails(this.visualizer);

            // Ask for an angle
            sayln("The hole is directly in front of you. Which direction do you want to aim in degrees (negative is left, positive is right)?");
            say("(remember, type 'look around' to get more information about your surroundings) ");
            input = sc.nextLine();

            // Give them more details and ask again if they say so
        } while(input.equals("look around"));

        int angle = Integer.parseInt(input); // Convert input to an integer

        // TODO: give club table and ask which
        sayln("Which club would you like? ");
        printClubs(clubs);
        int club = Math.min(10, Math.max(1, sc.nextInt())); // constrains input to 1-10

        say("How much power do you want to use?");
        int power = Math.min(10, Math.max(1, sc.nextInt())); // constrains input to 1-10

        return new int[] { club, power, angle };
    }

    // TODO: make fancy
    public void congratulateHole() {
        sayln("you made it in the hole!");
    }

    // TODO: pretty-ify
    public void updateScore(int score) {
        sayln("Your score is: " + score);
    }

    // TODO: be happier
    public void congratulateGame() {
        sayln("Congratulations, you beat the game!");
    }

    // TODO: make good
    public void updateFinalScore(int score) {
        sayln("Your final score is: " + score);
    }

    // Prints the clubs beautifully
    private void printClubs(String[][] clubs) {
        int[] maximums = new int[] {
                Integer.parseInt(clubs[11][0]), // maxNumber
                Integer.parseInt(clubs[11][1]), // maxName
                Integer.parseInt(clubs[11][2]), // maxDistance
                Integer.parseInt(clubs[11][3]) // maxAccuracy
            };

        // top border
        sayln(repeat("-", maximums[0] + maximums[1] + maximums[2] + maximums[3] + 13));

        String spacingL;
        String spacingR;
        int padding;
        // print the item
        String[] row;
        for (int i = 0; i < clubs.length - 1; i++) { // for each club and title
            row = clubs[i];
            for (int j = 0; j < row.length; j++) {

                padding = maximums[j] - row[j].length();
                spacingL = repeat(" ", padding / 2);
                spacingR = repeat(" ", padding - padding / 2);

                say("| " + spacingL + row[j] + spacingR + " ");
            }

            sayln("|");
        }

        // bottom border
        sayln(repeat("-", maximums[0] + maximums[1] + maximums[2] + maximums[3] + 13));
    }

    // repeat string s, n times
    private String repeat (String s, int n) {
        return new String(new char[n]).replace("\0", s); // from http://stackoverflow.com/a/22460319
    }

    // Helper method to print out a message (without typing "System.out.print" a million times
    public void say (String msg) {
        System.out.print(msg);
    }

    // Println helper
    public void sayln (String msg) {
        System.out.println(msg);
    }
}
