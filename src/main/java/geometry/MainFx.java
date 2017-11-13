package geometry;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class UserInterface extends Application {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        HBox root = new HBox();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setHeight(HEIGHT);
        primaryStage.setWidth(WIDTH);
        primaryStage.show();
    }

}
