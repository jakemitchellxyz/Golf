package terrain;

public class Hole {
	
	private Point[][] terrain;
    private int[] hole;
    private int par;

    public Hole (int difficulty, int biome) {
        TerrainGenerator tg = new TerrainGenerator(difficulty);
        this.terrain = tg.generate(biome);
        this.hole = tg.getHole();
        this.par = (int) (Math.random() * 3) + 3; // between 3-5
    }

    // Returns whether coordinates exist within the terrain
    public boolean exists (int x, int y) {
        return x >= 0 && y >= 0 && terrain.length > x && terrain[x].length > y;
    }

    // returns true if there is an obstacle at that point
    public boolean hasObstacle (int x, int y) {
        return terrain[x][y] != null;
    }

    // Gets the height of the obstacle at the coordinates
    public double getHeight(int x, int y) {
        return terrain[x][y].getHeight();
    }

    // Get the type of the obstacle at a point
    public String getObstacle(int x, int y) {
        return terrain[x][y].getObstacle().getFullType();
    }

    // Print the terrain
    public void printTerrain() {
        for (Point[] column : terrain) {
            for (Point row : column) {
            	System.out.print((row != null) ? row.getObstacle().getType() : 0);
            }
            System.out.println();
        }
        System.out.println();
    }

    public int[] getHole() {
        return hole;
    }
    public int getPar() {
        return par;
    }
}
