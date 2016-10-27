package physics;

import java.util.Random;

// Loft angles form: http://murdofrazer.com/golf-equipment/golf-clubs/golf-club-angles-and-distances

public class Club {

	private String name;
	private int range;
	private int deviation;
	private int loft;
	private int accuracy;
	
	private Random random = new Random();
	
	public Club(String name, int range, int deviation, int loft, int accuracy) {
		this.name = name;
		this.range = range;
		this.deviation = deviation;
		this.loft = loft;
        this.accuracy = accuracy;
	}
	
	public String getName() {
		return name;
	}

	public double nextRange(int power)
	{
		double range = this.range * (power / 10.0); // power 10 yields full range, power 1 yields small portion of range
		double deviation = this.deviation * (power / 10.0);
		
		return random.nextGaussian() * deviation + range;
	}
	
	public int getLoft()
	{
		return loft;
	}
	
	public int getAccuracy() {
        return accuracy;
    }

    public int getRange() {
        return range;
    }
}
