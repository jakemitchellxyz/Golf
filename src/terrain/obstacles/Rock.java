package terrain.obstacles;

public class Rock extends Obstacle {
    public Rock () {
        // maxHeight, minHeight, maxRadius, minRadius, deviation
        super(2, 0.5, 4, 1, 1);
    }

    @Override
    public String hit() {
        return "You hit a rock!";
    }
}

