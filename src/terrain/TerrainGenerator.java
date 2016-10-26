package terrain;

import java.util.Random;
import terrain.obstacles.*;

public class TerrainGenerator {
    private static final int AVERAGE_AREA = 11000; // average area of a map
    private static final int DEVIATION = 15; // Std deviation for height and width
    private static final double MISC_OFFSET = 0.1; // what percentage of the x and y should the hole be offset from the tee, the path be offset from the tee, etc.
    private static final double T_OFFSET = 0.5; // Amount to move the offset t

    Random rand = new Random();

    private int difficulty;
    private int biome;
    private double[] tee;
    private double[] hole;

    private int height;
    private int width;
    private double theta;

    public TerrainGenerator (int difficulty, int biome) {
        this.difficulty = difficulty;
        this.biome = biome;
        width = (int) (rand.nextGaussian() * DEVIATION + Math.floor(Math.sqrt(AVERAGE_AREA) + difficulty * 2));
        height = (int) (rand.nextGaussian() * DEVIATION + Math.floor(Math.sqrt(AVERAGE_AREA) + difficulty * 2));

        // Position the tee and the hole
        tee = new double[] { 1.0, 1.0 };
        hole = new double[] {
                    width * (1 - MISC_OFFSET),
                    height * (1 - MISC_OFFSET)
                };
    }

    public Point[][] generate() {
        boolean[][] blockedPoints = new boolean[width][height]; // True means we shouldn't put anything at this point

        // Bounds for the green
        int greenLeftBound = (int) Math.round(hole[0] - width * MISC_OFFSET);
        int greenBottomBound = (int) Math.round(hole[1] - height * MISC_OFFSET);

        // Clear out the area around the hole (the green)
        for (int x = greenLeftBound; x < width; x++) {
            for (int y = greenBottomBound; y < height; y++) {
                blockedPoints[x][y] = true;
            }
        }

        // Get the min distance from the tee to the hole (maximum value of the offset t)
        double maxT = Math.sqrt(Math.pow(hole[0] - tee[0], 2) + Math.pow(hole[1] - tee[1], 2));

        // Calculate variables for a straight line from the tee to the hole
        theta = Math.atan((hole[1] - tee[1]) / (hole[0] - tee[0])); // Get the angle of the slope

        // t represents the offset from the tee along the straight line from the tee to the hole
        double t = 0;
        int tri = 1;
        int[] pointL;
        int[] pointR;

        // split the line into four segments and handle each slightly differently with the same code:
        while (tri <= 3) {
            double amplitude = Math.abs(rand.nextGaussian()) * (((tri % 2 == 0) ? 5 : 2) * difficulty / 5.0);
            double freq = maxT / 3.0;

            // first iteration is 1/3, second 2/3, and last 3/3 of the line
            while (t < maxT * (tri / 3.0)) {
                pointL = getSinCurve(t, height * MISC_OFFSET, amplitude, freq);
                pointR = getSinCurve(t, width * -MISC_OFFSET, amplitude, freq);

                // Set all of the points between the lines to true
                for (int i = pointR[1]; i <= pointL[1]; i++) {
                    // If statement prevents null reference errors for y values lower or higher than the grid
                    if (i >= 0 && i < height
                            && pointR[0] >= 0 && pointR[0] < width) {
                        blockedPoints[pointR[0]][i] = true;
                    }
                }
                t += T_OFFSET;
            }
            tri++;
        }

        // TODO: remove
        printPoints(blockedPoints);

        Point[][] terrain = new Point[width][height];
        Obstacle obstacle;
        int radius;
        double odds;

        for (int x = 0; x < width; x++) {
            column:
            for (int y = 0; y < height; y++) {
                // If this point is empty
                if (!blockedPoints[x][y]) {
                    // Chances are 10% for easiest level and 46% for hardest
                    odds = (10 + 4 * (difficulty - 1)) / 100.0;

                    if (rand.nextDouble() <= odds) {
                        obstacle = newObstacle();
                        radius = obstacle.getRadius();

                        for (int i = 0; i <= radius; i++) {
                            // if any of the surrounding spots are taken
                            if (surroundingPointsTaken(blockedPoints, x, y, i)) {
                                // Skip to the next space
                                continue column;
                            }
                        }

                        terrain[x][y] = new Point(obstacle);
                    }
                }
            }
        }

        // TODO: remove
        printPoints(terrain);

        return terrain;
    }

    // Returns the sin coordinates at an offset on a specific line
    private int[] getSinCurve(double t, double lineY, double amplitude, double freq) {
        double tX = Math.cos(theta) * t;
        double tY = Math.sin(theta) * t + lineY;
        double s = sin(t, amplitude, freq);
        double sX = tX - Math.sin(theta) * s;
        double sY = tY + Math.cos(theta) * s;

        return new int[] { (int) Math.round(sX), (int) Math.round(sY) };
    }

    // Generate the sine curve with specific amplitude and frequency
    private double sin(double t, double amplitude, double freq) {
        return amplitude * Math.sin((2 * Math.PI / freq) * t);
    }

    // Generate a random obstacle based on the biome
    private Obstacle newObstacle() {
        Obstacle obstacle;
        double odds = Math.abs(rand.nextGaussian() * 5);

        if (biome == 0) { // Desert
            if (odds < 5) {
                obstacle = new Rock();
            } else {
                if (rand.nextBoolean()) {
                    obstacle = new Tree();
                } else {
                    obstacle = new Pond();
                }
            }
        } else if (biome == 1) { // Forest
            if (odds < 5) {
                obstacle = new Tree();
            } else {
                if (rand.nextBoolean()) {
                    obstacle = new Rock();
                } else {
                    obstacle = new Pond();
                }
            }
        } else { // Swamp
            if (odds < 5) {
                obstacle = new Pond();
            } else {
                if (rand.nextBoolean()) {
                    obstacle = new Tree();
                } else {
                    obstacle = new Rock();
                }
            }
        }

        return obstacle;
    }

    // Check nearby spaces while preventing null reference errors; true if any nearby are taken
    private boolean surroundingPointsTaken(boolean[][] blockedPoints, int x, int y, int i) {
        int plusX = Math.min(width - 1, x + i);
        int minusX = Math.max(0, x - i);

        int plusY = Math.min(height - 1, y + i);
        int minusY = Math.max(0, y - i);

        return blockedPoints[plusX][y]
                || blockedPoints[minusX][y]
                || blockedPoints[x][plusY]
                || blockedPoints[x][minusY]
                || blockedPoints[plusX][plusY]
                || blockedPoints[plusX][minusY]
                || blockedPoints[minusX][plusY]
                || blockedPoints[minusX][minusY];
    }

    // Print the empty points
    private void printPoints(boolean[][] blockedPoints) {
        for (boolean[] column : blockedPoints) {
            for (boolean row : column) {
                System.out.print(row ? ("\u001B[33m" + 1 + "\u001B[0m") : 0);
            }
            System.out.println();
        }
        System.out.println();
    }

    // Print the terrain
    private void printPoints(Point[][] terrain) {
        for (Point[] column : terrain) {
            for (Point row : column) {
                System.out.print((row != null) ? row.getObstacle().getType() : 0);
            }
            System.out.println();
        }
        System.out.println();
    }

    // Returns the hole we landed at
    public int[] getHole() {
        // Convert it to integers
        int[] hole = new int[] { (int) Math.round(this.hole[0]), (int) Math.round(this.hole[1]) };

        return hole;
    }

}
