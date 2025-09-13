/**
 * 
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Unathi Vayeke
 */
public class Main extends Application {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		GraphPane root = new GraphPane();
		root.startPane(primaryStage);
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Shortest Path Finder");
        primaryStage.show();
	}
}
