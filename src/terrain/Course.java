package terrain;

public class Course {
    private Hole[] holes;
    private int currentHole;
    private int holesInGame;

    // settings = [ difficulty(1-10), numHoles ]
    public Course(int[] settings) {
        this.currentHole = 0;
        this.holesInGame = settings[1]; // numHoles
        this.holes = generateHoles(settings[0]); // difficulty
    }

    private Hole[] generateHoles(int difficulty) {
        Hole[] holes = new Hole[holesInGame];

        for (int i = 0; i < holesInGame; i++) {
            holes[i] = new Hole(difficulty + i);
        }

        return holes;
    }

    public boolean hasNextHole() {
        return currentHole < holesInGame;
    }

    public Hole nextHole() {
        return this.holes[currentHole++];
    }

    public int holeNumber() {
        return currentHole;
    }

    public boolean isFirstHole() {
        return currentHole == 1;
    }

}
