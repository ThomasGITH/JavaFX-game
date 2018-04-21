package gamesource.collidables;

import java.util.ArrayList;

import gamesource.main.*;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.*;


public class Player extends GameObject
{
	
	public double xPrevious = 0;
	public double yPrevious = 0;
	public static int Direction;
	
	AudioClip shoot = new AudioClip("FILE:///D:/Users/tlins/eclipse-workspace/SpaceWars/src/sounds/shoot8.wav");
	AudioClip power_up = new AudioClip("FILE:///D:/Users/tlins/eclipse-workspace/SpaceWars/src/sounds/power_up4.wav");
			
	public Player(double xPosition, double yPosition)
	{
		super(xPosition, yPosition);
		
		scaleX = 0.28; //0.35
		scaleY = 0.28; //0.35
		
		health = 100;		
	}
	
	private int speed = 7;
	int PowerUpDuration = 600;
	
	@Override
	public void Update()
	{
		// REMEMBER PREVIOUS POSITION FIRST:
		xPrevious = x;
		yPrevious = y;
		
		// THEN APPLY NEW POSITION:
		if ((Input.KEYS_PRESSED.contains("A"))||(Input.KEYS_PRESSED.contains("LEFT")))
		{
			x -= speed;
			setRotation(-90);
			Direction = 1;
		}
		else if ((Input.KEYS_PRESSED.contains("D"))||(Input.KEYS_PRESSED.contains("RIGHT")))
		{
			x += speed;
			setRotation(90);
			Direction = 2;
		}
		
		else if ((Input.KEYS_PRESSED.contains("W"))||(Input.KEYS_PRESSED.contains("UP")))
		{
			y -= speed;
			setRotation(0);
			Direction = 3;
		}
		else if ((Input.KEYS_PRESSED.contains("S"))||(Input.KEYS_PRESSED.contains("DOWN")))
		{
			y += speed;
			setRotation(180);
			Direction = 4;
		}

		//SHOOT MECHANIC
		if ((Input.KEYS_RELEASED.contains("SPACE"))||(Input.MOUSE_PRESSED_LB))
		{
			shoot.play();
			if(Direction == 2) {
			LightBeam lightbeam = new LightBeam(x + 80, y + 35);
			Helper.AddGameObject(lightbeam);
			lightbeam.lastDirection = Direction;
			}
			else if(Direction == 1) {
			LightBeam lightbeam = new LightBeam(x - 80, y + 35);
			Helper.AddGameObject(lightbeam);
			lightbeam.lastDirection = Direction;
			}
			else if(Direction == 3) {
			LightBeam lightbeam = new LightBeam(x, y - 35);
			Helper.AddGameObject(lightbeam);
			lightbeam.lastDirection = Direction;
			}
			else if(Direction == 4) {
			LightBeam lightbeam = new LightBeam(x, y + 120);
			Helper.AddGameObject(lightbeam);
			lightbeam.lastDirection = Direction;
			}
			Input.MOUSE_PRESSED_LB = false; //PREVENT BULLET SPAMM
		}
		
		else
		{
			// CONDITIONAL ASSIGNMENT, WORKS AS FOLLOWS.
			// VALUE = CONDITION (if-statement) ? VALUE WHEN TRUE : VALUE WHEN FALSE;
			scaleX -= scaleX > 0.35 ? 0.01 : 0;
			scaleY -= scaleY > 0.35 ? 0.01 : 0;
		}
		
		if ((Input.KEYS_RELEASED.contains("M"))){
			ArrayList<GameObject> backgroundList = Helper.GetGameObjectsOfType(BackGround.class);
			BackGround BG = (BackGround)Helper.CheckCollisionRectangle(this, BackGround.class);
			if(BG.music.isMute()) {
			BG.music.setMute(false);
			}else{BG.music.setMute(true);}
		}
		
		PowerUp pu = (PowerUp)Helper.CheckCollisionRectangle(this, PowerUp.class);

		// CHECK COLLISION WITH WALLS:
		if (Helper.CheckCollisionRectangle(this, Wall.class) != null)
		{
			// SET TO PREVIOUS POSITION WHEN A WALL IS HIT:
			x = xPrevious;
			y = yPrevious;
		}
		
		//BORDER COLLISION
		if((x <= 0)||(x >= 1165)||(y <= -30)||(y >= 740)) {
			x = xPrevious;
			y = yPrevious;
		}
		
		// CHECK COLLISION WITH POWER-UP's AND GIVE THE PLAYER AN EFFECT:
		if (pu != null)
		{
			power_up.play();
			Helper.RemoveGameObject(pu);
			speed *= 2;
			PowerUpDuration -= 1;
		}if(PowerUpDuration < 600) {PowerUpDuration -= 1;}
		 if(PowerUpDuration <= 0) {speed /= 2; PowerUpDuration = 600;}
		
	}
	
	
	@Override
	public void Draw(GraphicsContext gc)
	{
		
		// EXTRA SHADOW EFFECT:
		//gc.setFill(Color.rgb(0, 0, 0, 0.5));
		//gc.fillOval(x, y + (getHeight() * 0.9), getWidth(), 50 * scaleY);
		
		
		// ORIGINAL DRAWING METHOD FROM THE INHERITED GAMEOBJECT CLASS:
		super.Draw(gc);
		
	}
	
	
}