import java.util.Scanner;

public class UserInteraction {
    private Scanner sc;

    public UserInteraction() {
        this.sc = new Scanner(System.in);
    }

    public void giveInstructions() {
        // TODO: give user instructions for the game
        sayln("Here are some instructions!");
    }

    // Ask the user if they want to play
    public boolean wantsToPlay() {
        say("So what do you say? Do you want to play? (yes/no) ");
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
                say("So do you want to play or not? (yes/no) ");
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
        say("Welcome to the course!");
    }

    // Tell the user the details of the hole
    public void giveHoleDetails() {
        // TODO: give details about the hole
        sayln("Here are the hole details: ");
        
        
        
        
    }

    // Helper method to print out a message (without typing "System.out.print" a million times
    public void say (String msg) {
        System.out.print(msg);
    }

    // Println helper
    public void sayln (String msg) {
        System.out.println(msg);
    }

    // Line break for formatting
    public void newLine () {
        System.out.println();
    }
}
