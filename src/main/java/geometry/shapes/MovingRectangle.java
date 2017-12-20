package geometry.shapes;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import random.RandomGenerator;
import java.util.ArrayList;
import java.util.Random;

public class MovingRectangle {
    public static Rectangle generateRectangle(Pane movementArea, int minW, int maxW, int minH, int maxH){
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(RandomGenerator.random(minW, maxW));
        rectangle.setHeight(RandomGenerator.random(minH, maxH));
        rectangle.setFill(RandomGenerator.randomColor());
        rectangle.setTranslateX(RandomGenerator.random(0, (int) (movementArea.getMinWidth() - rectangle.getWidth())));
        rectangle.setTranslateY(RandomGenerator.random(0, (int) (movementArea.getMinHeight() - rectangle.getHeight())));
        movementArea.getChildren().add(rectangle);
        return rectangle;
    }

    // Рассчитываем движение прямоугольника
    public static void move(Rectangle rectangle, Pane movementArea, int numberMovements, int stepX, int stepY) {
        for (int i = 0; i < numberMovements; i++){
            if ((rectangle.getTranslateX() == 0) ||
                    (rectangle.getTranslateX() == movementArea.getMinWidth() - rectangle.getWidth())) {
                stepX *= -1;
            }
            if ((rectangle.getTranslateY() == 0) ||
                    (rectangle.getTranslateY() == movementArea.getMinHeight() - rectangle.getHeight())) {
                stepY *= -1;
            }
            final double x = rectangle.getTranslateX() + stepX;
            final double y = rectangle.getTranslateY() + stepY;
            Platform.runLater(() -> {
                rectangle.setTranslateX(x);
                rectangle.setTranslateY(y);
            });
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Рассчитываем движение прямоугольников. Метод для одного потока.
    public static void move(ArrayList<Rectangle> rectangles, Pane movementArea, int numberMovements) {
        Integer[] stepsX = new Integer[rectangles.size()];
        Integer[] stepsY = new Integer[rectangles.size()];
        for (int i = 0; i < rectangles.size(); i++){
            stepsX[i] = randomStep(new Random().nextBoolean());
            stepsY[i] = randomStep(new Random().nextBoolean());
        }
        for (int i = 0; i < numberMovements; i++){
            for (int j = 0; j < rectangles.size(); j++){
                if ((rectangles.get(j).getTranslateX() == 0) ||
                        (rectangles.get(j).getTranslateX() == movementArea.getMinWidth() - rectangles.get(j).getWidth())) {
                    stepsX[j] *= -1;
                }
                if ((rectangles.get(j).getTranslateY() == 0) ||
                        (rectangles.get(j).getTranslateY() == movementArea.getMinHeight() - rectangles.get(j).getHeight())) {
                    stepsY[j] *= -1;
                }
                final double x = rectangles.get(j).getTranslateX() + stepsX[j];
                final double y = rectangles.get(j).getTranslateY() + stepsY[j];
                int finalI = j;
                Platform.runLater(() -> {
                    rectangles.get(finalI).setTranslateX(x);
                    rectangles.get(finalI).setTranslateY(y);
                });
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int randomStep(Boolean value){
        if (value) return 1;
        return -1;
    }
}
