package random;

import javafx.scene.paint.Color;
import java.util.Random;

public class RandomGenerator {
    private static final Random random = new Random();

    public static Color randomColor(){
        return Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble());
    }

    public static int randomNumber(int min, int max){
        return (int) (min + Math.random() * (max - min));
    }

    public static double randomNumber(double min, double max){
        return min + Math.random() * (max - min);
    }

}
