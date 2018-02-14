import javafx.application.*;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.*;

public class HeadClass extends Application{
	
	int WIDTH = 995;
	int HEIGHT = 995;
	int snakeLength;
	int score;
	
	boolean FS;
	
	//VERANDER HIER BEIDE FILE-PATHS OP DEZELFDE MANIER ALS HIERONDER
	Media buttonSound = new Media("FILE:///D:/Users/tlins/eclipse-workspace/newProjectGame/src/sounds/327725__distillerystudio__button-02.wav");
	Media buttonConfirmation = new Media("FILE:///D:/Users/tlins/eclipse-workspace/newProjectGame/src/sounds/403015__inspectorj__ui-confirmation-alert-c2.wav");
	MediaPlayer BC = new MediaPlayer(buttonConfirmation);
	MediaPlayer MP = new MediaPlayer(buttonSound);
	
	public void start(Stage primaryStage) throws Exception{
		
		Menu menu = new Menu();
		menu.menuConfig();
		//Scenes
		GridControll gc = new GridControll();
		Apple apple = new Apple(gc.Grid.length);
		apple.randomize();
		Scene startMenu = new Scene(menu.beginLayout, WIDTH, HEIGHT);
		Scene optionsMenu = new Scene(menu.optionsLayout, WIDTH, HEIGHT);
		Scene gameScreen = new Scene(gc.gameLayout, WIDTH, HEIGHT);
		//Stage settings
		primaryStage.setScene(startMenu);
		primaryStage.setTitle("Play Snake");
		primaryStage.setFullScreen(false);
		primaryStage.setResizable(false);
		primaryStage.centerOnScreen();
		primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		primaryStage.show();
		//Button events
		menu.startButton.setOnAction(event -> {
			MP.stop();
			BC.play();
			primaryStage.setScene(gameScreen);
			if(FS == true) {
				primaryStage.setFullScreen(true);
			}else {
				primaryStage.setFullScreen(false);
			}
			gc.sceneConfig();
			gc.showGrid();
		});
		
		menu.options.setOnAction(event -> {
			MP.stop();
			MP.play();
			primaryStage.setScene(optionsMenu);
			if(FS == true) {
				primaryStage.setFullScreen(true);
			}else {
				primaryStage.setFullScreen(false);
			}
		});
		
		menu.fullscreen.setOnAction(event -> {
			MP.stop();
			MP.play();
			if(!primaryStage.isFullScreen()) {
			primaryStage.setFullScreen(true);
			menu.backPosition = -250;
			menu.back.setTranslateX(menu.backPosition);
			FS = true;
			}else{
				primaryStage.setFullScreen(false);
				menu.backPosition = -120;
				menu.back.setTranslateX(menu.backPosition);
				FS = false;
				primaryStage.centerOnScreen();
			}
		});
		
		menu.windowedScreen.setOnAction(event -> {
			MP.stop();
			MP.play();
			if((!primaryStage.isMaximized())||(primaryStage.isFullScreen())){
			primaryStage.setFullScreen(false);
			menu.backPosition = -250;
			menu.back.setTranslateX(menu.backPosition);
			primaryStage.setMaximized(true);
			}else {
				primaryStage.setMaximized(false);
				menu.backPosition = -120;
				menu.back.setTranslateX(menu.backPosition);
			}
		});

		menu.back.setOnAction(event -> {
			MP.stop();
			MP.play();
			primaryStage.setScene(startMenu);
			if(FS == true) {
				primaryStage.setFullScreen(true);
			}else {
				primaryStage.setFullScreen(false);
			}
		});
		
		apple.randomize();
		gc.Grid[apple.appleY][apple.appleX] = gc.A;
        gameScreen.addEventHandler(KeyEvent.KEY_PRESSED,
        		
            	new EventHandler<KeyEvent>(){
                public void handle(KeyEvent event) 
                {
                	if((event.getCode() == KeyCode.W)||(event.getCode() == KeyCode.UP)){
                		if(gc.playerY > 0) {
                		gc.tailY = gc.playerY + snakeLength;
                		gc.tailX = gc.playerX;
                		gc.playerY -= 1;
                		}else {System.out.println("Game over");}
                	}
                	else if((event.getCode() == KeyCode.A)||(event.getCode() == KeyCode.LEFT)){
                		if(gc.playerX > 0) {
                		gc.tailY = gc.playerY;
                		gc.tailX = gc.playerX + snakeLength;
                		gc.playerX -= 1;
                		}else {System.out.println("Game over");}
                	}
                	else if((event.getCode() == KeyCode.S)||(event.getCode() == KeyCode.DOWN)){
                		if(gc.playerY < gc.Grid.length - 1) {
                		gc.tailY = gc.playerY - snakeLength;
                		gc.tailX = gc.playerX;
                		gc.playerY += 1;
                		}else {System.out.println("Game over");}
                	}
                	else if((event.getCode() == KeyCode.D)||(event.getCode() == KeyCode.RIGHT)){
                		if(gc.playerX < gc.Grid.length - 1) {
                		gc.tailY = gc.playerY;
                		gc.tailX = gc.playerX - snakeLength;
                		gc.playerX += 1;
                		}else {System.out.println("Game over");}
                	}
            		if(gc.Grid[gc.playerY][gc.playerX] == gc.Grid[apple.appleY][apple.appleX])  {
            			snakeLength += 1;
            			score += 1;
            			apple.randomize();
            			System.out.println("Score: " + score);
            		}
            		gc.Grid[gc.tailY][gc.tailX] = gc.B;
            		gc.Grid[gc.playerY][gc.playerX] = gc.W;
            		gc.Grid[apple.appleY][apple.appleX] = gc.A;
                	gc.showGrid();
               }
        		
    });
	
	}
	
}
