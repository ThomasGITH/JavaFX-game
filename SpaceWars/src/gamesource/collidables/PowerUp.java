package gamesource.collidables;

import gamesource.main.*;
import javafx.scene.image.*;
import javafx.scene.media.AudioClip;


public class PowerUp extends GameObject
{

	public PowerUp(double xPosition, double yPosition)
	{
		super(xPosition, yPosition);
		
		setSprite(new Image("images/green_power_up.png"));
		
		scaleX = 0.12;
		scaleY = 0.12;
		
	}
	
	public void Update() {
		setRotation(getRotation() - 1);		
	}
	
}