package terrain;

public class Point {
    private Obstacle obstacle;

    public Point(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    public double getHeight() {
        if (this.obstacle != null) {
            return this.obstacle.getHeight();
        }

        return 0;
    }

    public Obstacle getObstacle() {
        return obstacle;
    }
}
