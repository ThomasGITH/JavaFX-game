package gamesource.collidables;

import gamesource.main.GameManager;
import gamesource.main.GameObject;
import gamesource.main.Helper;
import javafx.scene.image.Image;

public class SpaceCity extends GameObject{

	public SpaceCity(double xPosition, double yPosition) {
		super(xPosition, yPosition);
		
		setSprite(new Image("images/spaceCity.png"));
	
		scaleX = 0.375; //0.375
		scaleY = 0.375;
		
		x = 550;

		health = 100;
	}
	
	public void Update() {
			
			// CHECK COLLISION WITH ENEMY LIGHTBEAMS
		EnemyLightBeam ELB = (EnemyLightBeam)Helper.CheckCollisionRectangle(this, EnemyLightBeam.class);
		SpaceCity SC = (SpaceCity)Helper.CheckCollisionRectangle(this, SpaceCity.class);

			if (ELB != null)
			{
				health -= 5;
				Helper.RemoveGameObject(ELB);
				if(health <= 0) {
					CityExplosion explosion = new CityExplosion(SC.x, SC.y);
					Helper.AddGameObject(explosion);
					Helper.RemoveGameObject(SC);
				}
			}
			

	}

}
