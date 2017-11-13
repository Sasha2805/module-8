package geometry;

import javafx.scene.canvas.GraphicsContext;
import random.RandomGenerator;

public class Rectangle {

    public static void draw(GraphicsContext gc, int x, int y, int width, int height) {
        gc.setFill(RandomGenerator.randomColor());
        gc.fillRect(x, y, width, height);
    }

}
