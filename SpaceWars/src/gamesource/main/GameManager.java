package gamesource.main;


import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import gamesource.collidables.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


public class GameManager
{
	private Pane _mainPane;
	private Canvas _mainCanvas;
	private GraphicsContext _context;
	
	String _imageLocation = "images/playerSpaceShip.png";
	
	
	// PROPERTY GAME OBJECT LIST:
	public ArrayList<GameObject> _gameObjList = new ArrayList<GameObject>();
	public ArrayList<GameObject> getGameObjectsList()
	{
		return _gameObjList;
	}
	
	
	public GameManager(Pane paneReference)
	{
		_mainPane = paneReference;
		_mainCanvas = (Canvas)_mainPane.getChildren().get(0);
		_context = _mainCanvas.getGraphicsContext2D();
		
		
		Helper.SetGameManagerReference(this);
		
		
		InitializeGame();
	}
	
	int EnemySpawnTime = 2000;
	
	Enemy evilShip;
	public void Update()
	{
		// UPDATE ALL THE GAME OBJECTS:
		if (_gameObjList != null)
		{
			for (int i = 0; i < _gameObjList.size(); i++)
			{
				_gameObjList.get(i).Update();
			}
		}
		
		System.out.println(_gameObjList.size());
		
			//RESPAWNS ENEMIES
			if(Enemy.enemyCounter < 3) {
			EnemySpawnTime -= 10;
				if(EnemySpawnTime <= 0) {
					int EnemyX = ThreadLocalRandom.current().nextInt(10, 1145);
					evilShip = new Enemy(EnemyX, -125);
					_gameObjList.add(evilShip);
					Enemy.enemyCounter += 1;
					EnemySpawnTime = 2000;
				}
			}
	}
	
	
	public void Draw()
	{
		// DRAW ALL THE GAME OBJECTS:
		if (_gameObjList != null)
		{
			for (int i = 0; i < _gameObjList.size(); i++)
			{
				_gameObjList.get(i).Draw(_context);
			}
		}
	}
	
	Player playerRef;
	SpaceCity SCRef;
	public void DrawGUI()
	{
		_context.setFill(Color.YELLOW);
		// DRAW INTERFACE HERE:
		_context.fillText("PLAYER X: 0" + String.valueOf(playerRef.x), 0, 15);
		_context.fillText("PLAYER Y: 0" + String.valueOf(playerRef.y), 0, 30);
		ArrayList<GameObject> playerList = Helper.GetGameObjectsOfType(Player.class);
		if(playerList.size() > 0) {
		_context.fillText("HEALTH: " + playerRef.health, playerRef.x + 10, playerRef.y);
		}
		
		ArrayList<GameObject> spaceCityList = Helper.GetGameObjectsOfType(SpaceCity.class);
		if(spaceCityList.size() > 0) {
		_context.fillText("HEALTH: " + SCRef.health, SCRef.x + 30, SCRef.y);
		}
		//_context.fillText("PLAYER X: 0",playerRef.x, playerRef.y);

	}
	
	Player myPlayer;
	// PRIVATE FUNCTIONS, INTERNAL GAME STRUCTURE:
	private void InitializeGame()
	{	
		BackGround bg = new BackGround(0, 0);
		_gameObjList.add(bg);
		
		myPlayer = new Player(570, 500);
		myPlayer.setSprite(new Image(_imageLocation));
		playerRef = myPlayer;
		
		
		// ADD PLAYER TO LIST OF OBJECTS TO UPDATE AND DRAW:
		_gameObjList.add(myPlayer);
		
		//PLACE SPACE-CITY
		SpaceCity sc = new SpaceCity(550, 700);
		_gameObjList.add(sc);
		SCRef = sc;
		
		//PLACE POWER-UP'S
		int PUX = ThreadLocalRandom.current().nextInt(0, 1145);
		int PUY = ThreadLocalRandom.current().nextInt(80, 730);
		PowerUp pu = new PowerUp(PUX, PUY);
		_gameObjList.add(pu);
		
		//PLACE OBSTACLES
		for(int x = 0; x < 37; x++) {
		int WallX = ThreadLocalRandom.current().nextInt(0, 1145);
		int WallY = ThreadLocalRandom.current().nextInt(80, 730);
		
		Wall wallTest = new Wall(WallX, WallY);
		_gameObjList.add(wallTest);
		}
		
		//SPAWN FIRST ENEMIES
		for(int i = 0; i < 3; i++) {
		int EnemyX = ThreadLocalRandom.current().nextInt(10, 1145);
		evilShip = new Enemy(EnemyX, -125); //VORIGE Y WAS 15
		_gameObjList.add(evilShip);
		Enemy.enemyCounter += 1;
		}
		
		
	}
	
	
	
	// PUBLIC FUNCTIONS:
	public GameObject CheckCollisionRectangle(GameObject objA, Class type)
	{
		for (GameObject objB : _gameObjList)
		{
			// FIRST CHECK IF IT'S THE TYPE OF OBJECT WE'RE LOOKING FOR:
			if (type.isInstance(objB))
			{
				// CHECK FOR COLLISION:
				if (
						objA.getBBoxLeft() <= objB.getBBoxRight() &&
						objA.getBBoxRight() >= objB.getBBoxLeft() &&
						objA.getBBoxTop() <= objB.getBBoxBottom() &&
						objA.getBBoxBottom() >= objB.getBBoxTop()
					)
				{
					// THERE IS COLLISION! RETURN THE OBJECT THAT HAS COLLISION WITH THE GIVEN OBJECT:
					// (The method stops executing after that.)
					return objB;
				}
			}
		}
		
		return null;
	}
	
	
}