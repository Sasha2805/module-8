package geometry.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import random.RandomGenerator;

public class MovingRectangle implements Runnable {
    private final int WIDTH;
    private final int HEIGHT;
    private GraphicsContext gc;
    private Rectangle rectangle = new Rectangle();
    private int distance = 30;
    private int x;
    private int y;

    public MovingRectangle(int WIDTH, int HEIGHT, GraphicsContext gc, int minW, int maxW, int minH, int maxH) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.gc = gc;
        this.rectangle.setFill(RandomGenerator.randomColor());
        this.rectangle.setWidth(RandomGenerator.random(minW, maxW));
        this.rectangle.setHeight(RandomGenerator.random(minH, maxH));
    }

    @Override
    public void run() {
        x = RandomGenerator.random(0, WIDTH);
        y = RandomGenerator.random(0, HEIGHT);
       for(int i = 0; i < 100; i++){
            MovingShape moving = MovingShape.randomMoving();
            if (moving == MovingShape.up){
                y -= distance;
            } else if (moving == MovingShape.right){
                x += distance;
            } else if (moving == MovingShape.down){
                y += distance;
            } else if (moving == MovingShape.left){
                x -= distance;
            }
            draw();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gc.clearRect(x, y, rectangle.getWidth(), rectangle.getHeight());
        }

    }

    private void draw(){
        gc.setFill(rectangle.getFill());
        gc.fillRect(x, y, rectangle.getWidth(), rectangle.getHeight());
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

}
