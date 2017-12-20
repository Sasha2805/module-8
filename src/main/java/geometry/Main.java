package geometry;

import geometry.shapes.MovingRectangle;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import random.RandomGenerator;
import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 700;
    private static final int MENU_WIDTH = 200;
    private static final int BUTTON_WIDTH = 120;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox menu = new VBox();
        menu.setPadding(new Insets(10));
        menu.setStyle("-fx-background-color: #27333F;");
        menu.setMinWidth(MENU_WIDTH);
        menu.setSpacing(20);

        Pane movementArea = new Pane();
        movementArea.setMinWidth(WIDTH - MENU_WIDTH);
        movementArea.setMinHeight(HEIGHT);

        // Кнопка multyThreads создает от 3 до 7 потоков, каждый с которых рассчитывает движение своего прямоугольника
        Button multyThreads = new Button("Multy Threads");
        multyThreads.setMaxWidth(BUTTON_WIDTH);
        multyThreads.setOnAction(event -> {
            movementArea.getChildren().clear();
            startMove(movementArea, RandomGenerator.random(3, 7));
        });

        // Один поток рассчитывает движение каждого прямоугольника
        Button singleThread = new Button("Single Thread");
        singleThread.setMaxWidth(BUTTON_WIDTH);
        singleThread.setOnAction((event) -> {
            movementArea.getChildren().clear();
            int countRectangles = RandomGenerator.random(3, 7);
            ArrayList<Rectangle> rectangles = new ArrayList<>();
            for (int i = 0; i < countRectangles; i++){
                rectangles.add(MovingRectangle.generateRectangle(movementArea,100, 120, 80, 100));
            }
            new Thread(() -> MovingRectangle.move(rectangles, movementArea, 10000)).start();
        });

        TextField minNumRec = new TextField();
        TextField maxNumRec = new TextField();
        Text min = new Text("Min. number of rectangles");
        min.setFill(Color.WHITE);
        Text max = new Text("Max. number of rectangles");
        max.setFill(Color.WHITE);

        // Кнопка generate создает от min до max потоков, каждый с которых рассчитывает движение своего прямоугольника
        Button generate = new Button("Generate");
        generate.setMaxWidth(BUTTON_WIDTH);
        generate.setOnAction(event -> {
            try {
                movementArea.getChildren().clear();
                startMove(movementArea, RandomGenerator.random(Integer.parseInt(minNumRec.getText()),
                        Integer.parseInt(maxNumRec.getText())));
            } catch (NumberFormatException e) {
                System.out.println("Вы ввели некорректные данные...");
            }
        });

        menu.getChildren().addAll(multyThreads, singleThread, min, minNumRec, max, maxNumRec, generate);
        primaryStage.setScene(new Scene(new HBox(menu, movementArea), WIDTH, HEIGHT));
        primaryStage.show();
    }

    public void startMove(Pane movementArea, int countRectangles){
        for (int i = 0; i < countRectangles; i++){
            Rectangle rectangle = MovingRectangle.generateRectangle(movementArea,100, 120, 80, 100);
            int stepX = MovingRectangle.randomStep(new Random().nextBoolean());
            int stepY = MovingRectangle.randomStep(new Random().nextBoolean());
            new Thread(() -> MovingRectangle.move(rectangle, movementArea, 10000, stepX, stepY)).start();
        }
    }
}
