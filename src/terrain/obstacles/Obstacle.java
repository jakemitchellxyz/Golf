package terrain.obstacles;

import java.util.Random;

public abstract class Obstacle {

    private double height;
    private int radius;

    public Obstacle (double maxHeight, double minHeight, double maxRadius, double minRadius, double deviation) {
        this.height = deviate(maxHeight, minHeight, deviation);
        this.radius = (int) Math.abs(deviate(maxRadius, minRadius, ((maxHeight == minHeight) ? deviation : 1)));
    }

    public double getHeight() {
        return height;
    }

    public int getRadius() {
        return radius;
    }

    private double deviate (double max, double min, double deviation) {
        Random rand = new Random();
        double val = rand.nextGaussian() * deviation + (max - min / 2.0);
        return Math.min(max, Math.max(min, val));
    }

    public abstract String hit();
    public abstract String getType();
}
