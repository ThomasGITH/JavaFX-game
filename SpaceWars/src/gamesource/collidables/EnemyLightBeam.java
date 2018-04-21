package gamesource.collidables;

import java.util.ArrayList;

import gamesource.main.GameManager;
import gamesource.main.GameObject;
import gamesource.main.Helper;
import javafx.scene.image.Image;

public class EnemyLightBeam extends GameObject{

	public EnemyLightBeam(double xPosition, double yPosition) {
		super(xPosition, yPosition);
		
		setSprite(new Image("images/Red_laser.png"));
	
		scaleX = 0.08;
		scaleY = 0.08;
	}
	
	private int speed = 26;
	public String lastDirection;
	
	public void Update() {
		switch(lastDirection) {
		case "LEFT" : setRotation(0); x -= speed; break;
		case "RIGHT" : setRotation(180); x += speed; break;
		case "UP" : setRotation(90); y -= speed; break;
		case "DOWN" : setRotation(-90); y += speed; break;
		}
		
		Wall rots = (Wall)Helper.CheckCollisionRectangle(this, Wall.class);
		EnemyLightBeam bullet = (EnemyLightBeam)Helper.CheckCollisionRectangle(this, EnemyLightBeam.class);
		Player player = (Player)Helper.CheckCollisionRectangle(this, Player.class);
		SpaceCity SC = (SpaceCity)Helper.CheckCollisionRectangle(this, SpaceCity.class);
		
		//GEEF PLAYER-INFORMATIE OP
		ArrayList<GameObject> playerList = Helper.GetGameObjectsOfType(Player.class);
		ArrayList<GameObject> SCList = Helper.GetGameObjectsOfType(SpaceCity.class);

		if(playerList.size() > 0) {
		GameObject myPlayer = playerList.get(0);
		
		// CHECK COLLISION WITH ENEMIES:
		
		if (player != null)
		{
			myPlayer.health -= 20;
			Helper.RemoveGameObject(bullet);
			if(myPlayer.health <= 0) {
				Explosion explosion = new Explosion(myPlayer.x, myPlayer.y);
				Helper.AddGameObject(explosion);
				Helper.RemoveGameObject(player);
			}
		}
		
		}
		
		// CHECK COLLISION WITH WALLS:
		if (rots != null)
		{
			rots.health -= 50;
			Helper.RemoveGameObject(bullet);
		}
		
		//BULLET CLEANUP WHEN OUT OF GAME-VIEW
		if(((x <= 0)||(x >= 1165))||((y <= 0)||(y >= 800))) {
			Helper.RemoveGameObject(bullet);
		}
	}

}
