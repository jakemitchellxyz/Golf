package terrain;

public class Hole {
    private Point[][] terrain;
    private int[] hole;

    public Hole (int difficulty, int biome) {
        TerrainGenerator tg = new TerrainGenerator(difficulty, biome);
        this.terrain = tg.generate();
        this.hole = tg.getHole();
    }

    public Point[][] getTerrain() {
        return terrain;
    }

    public int[] getHole() {
        return hole;
    }
}
