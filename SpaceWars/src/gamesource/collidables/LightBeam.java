package gamesource.collidables;

import gamesource.main.GameManager;
import gamesource.main.GameObject;
import gamesource.main.Helper;
import javafx.scene.image.Image;

public class LightBeam extends GameObject{

	public LightBeam(double xPosition, double yPosition) {
		super(xPosition, yPosition);
		
		setSprite(new Image("images/Green_laser.png"));
	
		scaleX = 0.16;
		scaleY = 0.16;
	}
	
	private int speed = 26;
	public int lastDirection;
	public int explosionTimer = 1;
	double explosionX, explosionY;
	
	public void Update() {
		switch(lastDirection) {
		case 1 : setRotation(0); x -= speed; break;
		case 2 : setRotation(180); x += speed; break;
		case 3 : setRotation(90); y -= speed; break;
		case 4 : setRotation(-90); y += speed; break;
		}
		
		Wall asteroid = (Wall)Helper.CheckCollisionRectangle(this, Wall.class);
		LightBeam bullet = (LightBeam)Helper.CheckCollisionRectangle(this, LightBeam.class);
		Enemy evilShip = (Enemy)Helper.CheckCollisionRectangle(this, Enemy.class);
		SpaceCity SC = (SpaceCity)Helper.CheckCollisionRectangle(this, SpaceCity.class);

		// CHECK COLLISION WITH WALLS:
		if (asteroid != null)
		{
			asteroid.health -= 50;
			Helper.RemoveGameObject(bullet);
		}
		// CHECK COLLISION WITH ENEMIES:
		if (evilShip != null)
		{
			explosionX = evilShip.x; explosionY = evilShip.y;
			Explosion explosion = new Explosion(explosionX, explosionY);
			Helper.AddGameObject(explosion);
			Helper.RemoveGameObject(evilShip);
			Helper.RemoveGameObject(bullet);
			Enemy.enemyCounter -= 1;
		}
		//BULLET CLEANUP WHEN OUT OF GAME-VIEW
		if(((x <= 0)||(x >= 1165))||((y <= 0)||(y >= 800))) {
			Helper.RemoveGameObject(bullet);
		}
	}

}
