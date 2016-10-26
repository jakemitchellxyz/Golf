package terrain.obstacles;

public class Pond extends Obstacle {
    public Pond () {
        // maxHeight, minHeight, maxRadius, minRadius, deviation
        super(0, 0, 80, 10, 10);
    }

    @Override
    public String hit() {
        return "You landed in a pond!";
    }

    @Override
    public String getType() {
        return "P";
    }
}

