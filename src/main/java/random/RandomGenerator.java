package random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class MyRandom {

    public static Color randomColor(){
        Random random = new Random();
        return Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble());
    }

    public static int randomNumber(int min, int max){
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public static ArrayList<Rectangle> randomRectangle(int minCount, int maxCount){
        ArrayList<Rectangle> rectangles = new ArrayList<>(randomNumber(minCount, maxCount));
        for (int i = 0; i < rectangles.size(); i++){
            Rectangle rectangle = new Rectangle();
            rectangle.setFill(randomColor());
            rectangles.add(rectangle);
        }
        return rectangles;
    }

}
