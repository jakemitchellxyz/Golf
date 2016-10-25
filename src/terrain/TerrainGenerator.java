package terrain;

import java.util.Random;

public class TerrainGenerator {
    private static final int AVERAGE_AREA = 11000;
    private static final int DEVIATION = 15;
    private static final double MISC_OFFSET = 0.1; // what percentage of the x and y should the hole be offset from the tee, the path be offset from the tee, etc.
    private static final double T_OFFSET = 0.5;

    Random rand = new Random();

    private int[] tee;
    private int[] hole;
    private int height;
    private int width;
    private double theta;

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

        // Get the min distance from the tee to the hole (maximum value of the offset t)
        double maxT = Math.sqrt(Math.pow(hole[0] - tee[0], 2) + Math.pow(hole[1] - tee[1], 2));

        // t represents the offset from the tee along the straight line from the tee to the hole
        double t = 0;
        int[] pointL;
        int[] pointR;

        System.out.println(width + " " + height);

        // Handle the first quadrant
        while (t < maxT / 4) {
            pointL = getSinCurve(t, leftCurve(t));
            pointR = getSinCurve(t, rightCurve(t));

            System.out.println(pointL[0] + " " + pointL[1]);
            System.out.println(pointR[0] + " " + pointR[1]);

            // Set all of the points between the lines to true
            for(int i = pointL[1]; i <= pointR[1]; i++) {
                blockedPoints[pointL[0]][i] = true;
            }

            t += T_OFFSET;
        }

        printPoints(blockedPoints);

        return null;
    }

    // Returns the sin coordinates at an offset on a specific line
    private int[] getSinCurve(double t, double lineY) {
        double tX = Math.cos(theta) * t + tee[0];
        double tY = Math.sin(theta) * t + lineY;
        double s = sin(t);
        double sX = tX - Math.sin(theta) * s;
        double sY = tY + Math.cos(theta) * s;

        return new int[] { (int) Math.round(sX), (int) Math.round(sY) };
    }

    private double sin(double t) {
        return Math.sin(t);
    }

    // Left curve; left bound of the path to the hole
    private double leftCurve(double t) {
        return straightLineWithOffset(t, height * MISC_OFFSET);
    }

    // Right curve; right bound of the path to the hole
    private double rightCurve(double t) {
        return straightLineWithOffset(t, width * -MISC_OFFSET);
    }

    // Create a straight line between the tee and the hole with an offset on the y intercept (to generate two curved lines)
    private double straightLineWithOffset(double t, double offset) {
        double m = (hole[1] - tee[1]) / (hole[0] - tee[0]); // slope
        double b = (hole[1] - m * hole[0]); // get the y offset based on the hole
        theta = Math.atan(m); // Get the angle of the slope

        double x = Math.cos(theta) * t + tee[0]; // get the x coordinate from the t offset

        return m * x + b + offset;
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
