package terrain;

public class Course {
    private Hole[] holes;
    private int currentHole;
    private int holesInGame;

    // settings = [ difficulty(1-10), numHoles, biome ]
    public Course(int[] settings) {
        this.currentHole = 0;
        this.holesInGame = settings[1]; // numHoles
        this.holes = generateHoles(settings[0], settings[2]); // difficulty, biome
    }

    private Hole[] generateHoles(int difficulty, int biome) {
        Hole[] holes = new Hole[holesInGame];

        for (int i = 0; i < holesInGame; i++) {
            holes[i] = new Hole(difficulty + i, biome);
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

    public int numHoles() {
        return holesInGame;
    }

}
