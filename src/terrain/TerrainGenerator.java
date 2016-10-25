package terrain;

import java.util.Random;

public class TerrainGenerator {
    private static final int AVERAGE_AREA = 11000;
    private static final int DEVIATION = 15;
    private static final double MISC_OFFSET = 1/10; // what percentage of the x and y should the hole be offset from the tee, the path be offset from the tee, etc.

    Random rand = new Random();

    private int[] tee;
    private int[] hole;
    private int height;
    private int width;

    public TerrainGenerator (int difficulty) {
        width = (int) (rand.nextGaussian() * DEVIATION + Math.floor(Math.sqrt(AVERAGE_AREA) + difficulty * 2));
        height = (int) (rand.nextGaussian() * DEVIATION + Math.floor(Math.sqrt(AVERAGE_AREA) + difficulty * 2));

        tee = new int[] { 1, 1 };
        hole = new int[]{
                    (int) Math.round(width * (1 - MISC_OFFSET)),
                    (int) Math.round(height * (1 - MISC_OFFSET))
                };
    }

    public Point[][] generate() {
        boolean[][] blockedPoints = new boolean[width][height]; // True means we shouldn't put anything at this point

        // Bounds for the green
        int greenLeftBound = (int) Math.round(hole[0] - width * (MISC_OFFSET / 2));
        int greenRightBound = (int) Math.round(hole[0] + width * (MISC_OFFSET / 2));
        int greenBottomBound = (int) Math.round(hole[1] - height * (MISC_OFFSET / 2));
        int greenTopBound = (int) Math.round(hole[1] + height * (MISC_OFFSET / 2));

        // Clear out the area around the hole (the green)
        for (int x = greenLeftBound; x <= greenRightBound; x++) {
            for (int y = greenBottomBound; y <= greenTopBound; y++) {
                blockedPoints[x][y] = true;
            }
        }

        int x = tee[0];
        int y = tee[1];
        double t = 0;

        while (x < hole[0]) {
            y = straightLineWithOffset(t, 0);
            t += 0.5;
            x = xFromT(t);
        }
        leftCurve(t);

        rightCurve(t);


    }

    private int xFromT(double t) {
        double m = (hole[1] - tee[1]) / (hole[0] - tee[0]); // slope
        double theta = Math.atan(m); // Get the angle of the slope
        double x = Math.cos(theta) * t + tee[0]; // get the x coordinate from the t offset
        return (int) Math.round(x);
    }

    // Left curve; left bound of the path to the hole
    private int leftCurve(double t) {
        return straightLineWithOffset(t, height * MISC_OFFSET);
    }

    // Right curve; right bound of the path to the hole
    private int rightCurve(double t) {
        return straightLineWithOffset(t, width * -MISC_OFFSET);
    }

    // Create a straight line between the tee and the hole with an offset on the y intercept (to generate two curved lines)
    private int straightLineWithOffset(double t, double offset) {
        double m = (hole[1] - tee[1]) / (hole[0] - tee[0]); // slope
        double b = (hole[1] - m * hole[0]); // get the y offset based on the hole
        double theta = Math.atan(m); // Get the angle of the slope
        double x = Math.cos(theta) * t + tee[0]; // get the x coordinate from the t offset

        return (int) Math.round(m * x + b + offset);
    }

    // Print the empty points
    private void printPoints(boolean[][] blockedPoints) {
        for (boolean[] column : blockedPoints) {
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
