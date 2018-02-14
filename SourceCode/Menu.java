import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Menu {
	
	int backPosition =- 120;
	
	//Elements
	Text title = new Text("Snake");
	Button startButton = new Button("Play");
	Button options = new Button("Options");
	Button fullscreen = new Button("Enable/Disable fullscreen");
	Button windowedScreen = new Button("Enable/Disable Windowed screen\n");
	Button back = new Button("<-");
	
	//Layouts
	VBox beginLayout = new VBox(title, startButton, options);
	VBox optionsLayout = new VBox(fullscreen, windowedScreen, back);
	
	public void menuConfig() {
		title.setScaleY(4);
		title.setScaleX(4);
		title.setTranslateY(-30);
		options.setTranslateY(+5);

		back.setTranslateY(-150);
		back.setTranslateX(backPosition);
	
		beginLayout.setAlignment(Pos.CENTER);
		beginLayout.setStyle("-fx-background-color: darkgrey");
		beginLayout.setScaleY(3);
		beginLayout.setScaleX(3);
		optionsLayout.setAlignment(Pos.CENTER);
		optionsLayout.setStyle("-fx-background-color: darkgrey");
		optionsLayout.setScaleY(3);
		optionsLayout.setScaleX(3);
	}
}
