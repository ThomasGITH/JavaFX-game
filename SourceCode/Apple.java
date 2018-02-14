import java.util.concurrent.ThreadLocalRandom;

public class Apple {
	
	int gridLength;
	
	int appleY;
	int appleX;
	
	public void randomize() {
	int appleY = ThreadLocalRandom.current().nextInt(0, gridLength);
	int appleX = ThreadLocalRandom.current().nextInt(0, gridLength);
	
	this.appleY = appleY;
	this.appleX = appleX;
	}
	
	public Apple(int gridLength){
		this.gridLength = gridLength;
	}
}
