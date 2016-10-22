package terrain;

public class Hole {
    private Point[][] terrain;
    private int[] hole;

    public Hole (int difficulty) {
        TerrainGenerator tg = new TerrainGenerator(difficulty);
        this.terrain = tg.generate();
        this.hole = tg.getHole();
    }

    public Point[][] getTerrain() {
        return terrain;
    }
}
