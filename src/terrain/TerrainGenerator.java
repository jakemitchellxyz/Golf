package terrain;

import java.util.Random;

public class TerrainGenerator {
    Random rand = new Random();

    private boolean[][] emptyPoints; // true means something is occupying that space
    private int[] hole;
    private int height;
    private int width;
    private int avgArea = 11000;
    private int deviation = 15;

    public TerrainGenerator (int difficulty) {
        width = (int) (rand.nextGaussian() * deviation + Math.floor(Math.sqrt(avgArea) + difficulty * 2));
        height = (int) (rand.nextGaussian() * deviation + Math.floor(Math.sqrt(avgArea) + difficulty * 2));
    }

    public Point[][] generate() {
        Point[][] grid = new Point[width][height];
        emptyPoints = new boolean[width][height];

        // Creates a clear path from tee to hole (generates where the hole is)
        createPath();

        // Prints out the empty points so we can debug it
        printPoints();

        // Adds more padding to the path
        thickenPath(1);

        // Prints out the empty points so we can debug it
        printPoints();

        return grid;
    }

    private void createPath() {
        int y = 1;

        // Draw a curvy line from tee to hole
        for (int x = 1; x <= width - 2; x++) {
            int dest = (int) Math.min(height - 2, Math.max(1, Math.round(sin(x))));

            if (dest > y) {
                for (int y1 = y; y1 < dest; y1++) {
                    emptyPoints[x][y1] = true;
                }
            } else if (dest < y) {
                for (int y1 = y; y1 > dest; y1--) {
                    emptyPoints[x][y1] = true;
                }
            }

            y = dest;

            emptyPoints[x][y] = true;
        }

        hole = new int[]{ width-2, y };
    }

    // Add padding around the path
    private void thickenPath(int size) {
        boolean[][] nEmptyPoints = emptyPoints;

        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < height - 1; y++) {
                if (emptyPoints[x][y]) {
                    nEmptyPoints[x][y] = true;

                    for (int i = 1; i <= size; i++) {
                        nEmptyPoints[x + i][y] = true; // right
                        nEmptyPoints[x - i][y] = true; // left
                        nEmptyPoints[x][y + i] = true; // top
                        nEmptyPoints[x][y - i] = true; // bottom
                        nEmptyPoints[x + i][y + i] = true; // top right
                        nEmptyPoints[x - i][y + i] = true; // top left
                        nEmptyPoints[x + i][y - i] = true; // bottom right
                        nEmptyPoints[x - i][y - i] = true; // bottom left
                    }
                }
            }
        }

        emptyPoints = nEmptyPoints;
    }

    // Generates the modified sin curve
    private double sin (int x) {
        return (Math.sin(5 * x) / x) * (height - 2) + x * 3/4;
    }

    // Print the empty points
    private void printPoints() {
        for (boolean[] column : emptyPoints) {
            for (boolean row : column) {
                System.out.print(row ? 1 : 0);
            }
            System.out.println();
        }
        System.out.println();
    }

    // Returns the hole we landed at
    public int[] getHole() {
        return hole;
    }

}
