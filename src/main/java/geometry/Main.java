package geometry;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainFx extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(UserInterface.getScene());
        primaryStage.show();
    }
}
