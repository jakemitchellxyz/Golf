package physics;

import java.util.Random;

// Loft angles form: http://murdofrazer.com/golf-equipment/golf-clubs/golf-club-angles-and-distances

public class Club {

	private String name;
	private int range;
	private int deviation;
	private int loft;
	
	private Random random = new Random();
	
	public Club(String name, int range, int deviation, int loft) {
		
		this.name = name;
		this.range = range;
		this.deviation = deviation;
		this.loft = loft;
	}
	
	
	public String getName() {
		return name;
	}
	
	
	
	public double nextRange(int power)
	{

		this.range = this.range * (power/10);
		this.deviation = this.deviation * (power/10);
		
		return Math.abs(random.nextGaussian() * deviation + range);
	}
	
	public int getLoft()
	{
		return this.loft;
	}
	
	
	
}
