package terrain;

public class Course {
    private Hole[] holes;
    private int currentHole;
    private int holesInGame;

    public Course(int difficulty, int numHoles) {
        this.currentHole = 0;
        this.holesInGame = numHoles;
        this.holes = generateHoles(difficulty);
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

}
