package exceptions;

public class HitAnObstacle extends Exception {
    private static final long serialVersionUID = 1L;

    public HitAnObstacle() { super(); }

    public HitAnObstacle(String msg) { super(msg); }
}
