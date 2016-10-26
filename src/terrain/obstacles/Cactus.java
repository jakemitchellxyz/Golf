package terrain.obstacles;

public class Cactus extends Obstacle {

    public Cactus () {
        // maxHeight, minHeight, maxRadius, minRadius, deviation
        super(20, 5, 1, 1, 4);
    }

    @Override
    public String hit() {
        return "You hit a Cactus!";
    }

    @Override
    public String getType() {
        return "C";
    }
}
