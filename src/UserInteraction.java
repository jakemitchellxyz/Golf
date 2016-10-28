import java.util.Scanner;
import terrain.Visualizer;

public class UserInteraction {
    private Scanner sc;
    private String area;

    public UserInteraction() {
        this.sc = new Scanner(System.in);
    }

    public void giveInstructions() {
        // TODO: give user instructions for the game
        sayln("Here are some instructions! >>> ");
        sayln("You start by picking a Club from your golf bag, and decide on how hard to want to swing the ball.");
        sayln("Be careful while choosing an angle to shoot the ball at.");
        sayln("REMEMBER: you are always directly looking at the hole.");
        say("");
    }

    public void welcomeToHole(int num) {
        sayln("You are on hole " + num + "!");
        System.out.println();
    }

    // Ask the user if they want to play
    public boolean wantsToPlay() {
        return wantsToDoSomething("So what do you say? Do you want to play? (yes/no) ",
                                        "So do you want to play or not? (yes/no) ");
    }

    // Ask the user if they want to see the terrain
    public boolean wantsToSeeTerrain() {
        return wantsToDoSomething("Would you like to see an output of the terrain (yes/no)? ", "");
    }

    // Ask the user if they want to do something
    private boolean wantsToDoSomething(String question, String reAsk) {
        say(question);

        // Reset the scanner
        sc.reset();
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
        // Reset the scanner
        sc.reset();

        say("How difficult do you want the game (1-10)? ");
        int difficulty = Math.min(10, Math.max(1, sc.nextInt())); // constrains input to 1-10

        // Reset the scanner
        sc.reset();

        say("And how many holes do you want to play (recommended 9 or 18)? ");
        int numHoles = Math.max(1, sc.nextInt()); // Forces at least 1

        return new int[]{ difficulty, numHoles, biome };
    }

    // Give user details about the course
    public void welcomeToCourse(int numHoles) {
        System.out.println();
        sayln("You are playing " + numHoles + " holes in a " + area + "!");
    }
    
    public void lookAround(Visualizer visualizer) {
        sayln("Here are the details from the visualizer: ");
        visualizer.lookAround();

        sayln("Distance to hole: " + visualizer.getDistanceToHole() + ".");
        System.out.println();
    }

    // Ask user for details about how to hit the ball: [ club, power, userAngle ]
    public int[] hitBall(String[][] clubs, Visualizer visualizer) {
        lookAround(visualizer);

        // Ask for an angle
        say("The hole is directly in front of you. Which direction do you want to aim in degrees (negative is left, positive is right)? ");

        // Reset the scanner
        sc.reset();

        int angle = sc.nextInt();

        printClubs(clubs); // Show the user all the clubs

        // Reset the scanner
        sc.reset();

        say("Which club would you like (type the number)? ");
        int club = Math.min(10, Math.max(1, sc.nextInt())); // constrains input to 1-10

        // Reset the scanner
        sc.reset();

        say("How much power do you want to use? ");
        int power = Math.min(10, Math.max(1, sc.nextInt())); // constrains input to 1-10

        return new int[] { club - 1, power, angle };
    }

    public void ballWentOutOfBounds() {
        sayln("Oh no! The ball went out of bounds! It's back where you hit it from.");
    }

    public void landedInWater() {
        sayln("Oh no! The ball landed in water! It's back where you hit it from.");
    }

    public void hitObstacle(String obstacle) {
        sayln("Oh no! You hit a " + obstacle + "!");
    }

    public void congratulateHole() {
        sayln("You made it in the hole!");
    }

    public void updateScore(int score) {
        sayln("Your score is: " + score);
    }

    public void congratulateGame() {
        sayln("Congratulations, you beat the game!");
    }

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
