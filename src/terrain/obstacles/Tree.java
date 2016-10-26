package terrain.obstacles;

public class Tree extends Obstacle {

    public Tree () {
        // maxHeight, minHeight, maxRadius, minRadius, deviation
        super(30, 15, 1, 1, 4);
    }

    @Override
    public String hit() {
        return "You hit a tree!";
    }

    @Override
    public String getType() {
        return "T";
    }
}
