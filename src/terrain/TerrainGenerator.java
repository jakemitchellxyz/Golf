package terrain;

import java.util.Random;
import terrain.obstacles.*;

public class TerrainGenerator {
    private static final int AVERAGE_AREA = 16000; // average area of a map
    private static final int DEVIATION = 15; // Std deviation for height and width
    private static final double MISC_OFFSET = 0.1; // what percentage of the x and y should the hole be offset from the tee, the path be offset from the tee, etc.
    private static final double T_OFFSET = 0.5; // Amount to move the offset t

    private Random rand = new Random();

    private int difficulty;
    private double[] tee;
    private double[] hole;

    private int height;
    private int width;

    public TerrainGenerator (int difficulty) {
        this.difficulty = difficulty;

        width = (int) (rand.nextGaussian() * DEVIATION + Math.floor(Math.sqrt(AVERAGE_AREA) + difficulty * 2));
        height = (int) (rand.nextGaussian() * DEVIATION + Math.floor(Math.sqrt(AVERAGE_AREA) + difficulty * 2));

        // Position the tee and the hole
        tee = new double[] { 1.0, 1.0 };
        hole = new double[] {
                    width * (1 - MISC_OFFSET),
                    height * (1 - MISC_OFFSET)
                };
    }

    // Generate a new terrain
    public Point[][] generate(int biome) {

        /*
         * Create a path from the tee to the hole and some green around the hole, so that obstacles cannot be generated there
         */

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
        double theta = Math.atan((hole[1] - tee[1]) / (hole[0] - tee[0])); // Get the angle of the slope

        double t = 0; // t represents the offset from the tee along the straight line from the tee to the hole
        int tri = 1; // first of 3 segments of the line (to add some deviation along the sine curve
        int[] pointL;
        int[] pointR;

        // split the line into four segments and handle each slightly differently with the same code:
        while (tri <= 3) {
            double amplitude = Math.abs(rand.nextGaussian()) * (((tri % 2 == 0) ? 5 : 2) * difficulty / 5.0);
            double freq = maxT / 3.0;

            // first iteration is 1/3, second 2/3, and last 3/3 of the line
            while (t < maxT * (tri / 3.0)) {
                pointL = getSinCurve(t, height * MISC_OFFSET, theta, amplitude, freq);
                pointR = getSinCurve(t, width * -MISC_OFFSET, theta, amplitude, freq);

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

        /*
         * Start adding obstacles to the terrain
         */
        Point[][] terrain = new Point[width][height];
        Obstacle obstacle;
        double odds;

        // Insert obstacles as necessary at each point
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // If this point is empty
                if (!blockedPoints[x][y]) {
                    odds = Math.min(0.1, difficulty / 100.0);

                    if (rand.nextDouble() <= odds) {
                        obstacle = newObstacle(biome);

                        // if any of the surrounding spots are taken
                        if (surroundingPointsTaken(blockedPoints, x, y, obstacle)) {
                            // Skip to the next space
                            continue;
                        }

                        insertObstacleAtPoints(terrain, blockedPoints, x, y, obstacle);
                    }
                }
            }
        }

        return terrain;
    }

    // Returns the sin coordinates at an offset on a specific line
    private int[] getSinCurve(double t, double lineY, double theta, double amplitude, double freq) {
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
    private Obstacle newObstacle(int biome) {
        Obstacle obstacle;
        double odds = rand.nextDouble();

        if (biome == 0) { // Desert
            if (odds <= 0.75) {
                obstacle = new Cactus();
            } else {
                obstacle = new Rock();
            }
        } else if (biome == 1) { // Forest
            if (odds <= 0.75) {
                obstacle = new Tree();
            } else {
                if (odds <= 0.85) {
                    obstacle = new Rock();
                } else {
                    obstacle = new Pond();
                }
            }
        } else { // Swamp
            if (odds <= 0.75) {
                obstacle = new Pond();
            } else {
                obstacle = new Tree();
            }
        }

        return obstacle;
    }

    // Check nearby spaces while preventing null reference errors; true if any nearby are taken
    private boolean surroundingPointsTaken(boolean[][] blockedPoints, int x, int y, Obstacle obstacle) {
        int radius = obstacle.getRadius();

        for (int i = x - radius - 1; i <= x + radius + 1; i++) {
            for (int j = y + radius + 1; j >= y - radius - 1; j--) {
                // If this point is in the grid and
                if (i >= 0 && i < width
                    && j >= 0 && j < height
                    && blockedPoints[i][j]) {

                    return true;
                }
            }
        }

        return false;
    }

    private void insertObstacleAtPoints(Point[][] terrain, boolean[][] blockedPoints, int x, int y, Obstacle obstacle) {
        Point point = new Point(obstacle);
        int radius = obstacle.getRadius();

        for (int i = x - radius; i <= x + radius; i++) {
            for (int j = y + radius; j >= y - radius; j--) {
                // Make sure this is in the grid first
                if (i >= 0 && i < width
                    && j >= 0 && j < height) {

                    terrain[i][j] = point;
                    blockedPoints[i][j] = true; // block off that point
                }
            }
        }
    }

    // Returns the hole we landed at
    public int[] getHole() {
        // Convert it to integers and return it
        return new int[] { (int) Math.round(this.hole[0]), (int) Math.round(this.hole[1]) };
    }

}
