public class ScoreKeeper {
    private int strokes;
    private int par;
    private int gameScore;

    // sets the par of the current hole
    public void setPar(int par) {
        this.par = par;
    }

    // swing the club; lose a point
    public void swing() {
        strokes++;
    }

    // reset strokes, add to global score, and return the hole score
    public int getHoleScore() {
        int score = strokes - par;

        gameScore += score;
        strokes = 0;

        return score;
    }

    // returns the total score of all the holes
    public int getFinalScore() {
        return gameScore;
    }
}
