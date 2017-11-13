package geometry;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class UserInterface {
    private static final int WIDTH = 1000;
    private static final int MENU_WIDTH = 200;
    private static final int HEIGHT = 700;
    private static final int BUTTON_WIDTH = 120;

    public static Scene getInterface(){
        VBox menu = Components.createVbox(MENU_WIDTH);
        menu.getChildren().addAll(Components.createButton("Multy Threads", BUTTON_WIDTH, () -> {}),
                Components.createButton("Single Thread", BUTTON_WIDTH, () -> {}),
                Components.createButton("Optimal Threads", BUTTON_WIDTH, () -> {}));

        CheckBox checkBox = new CheckBox("Collide with each other");
        checkBox.setTextFill(Color.WHITE);

        TextField minNumRec = new TextField();
        TextField maxNumRec = new TextField();
        menu.getChildren().addAll(checkBox, Components.createLabel("Min. number of rectangles"), minNumRec,
                Components.createLabel("Max. number of rectangles"), maxNumRec,
                Components.createButton("Generate", BUTTON_WIDTH, () -> {}),
                Components.createButton("Generate all shapes", BUTTON_WIDTH, () -> {
                    menu.getChildren().clear();
                    menu.setSpacing(3);
                    ArrayList<Label> labels = new ArrayList<>();
                    try {
                        labels = Components.labels();
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                    ArrayList<TextField> textFields = Components.textFields(labels.size());
                    for (int i = 0; i < labels.size(); i++){
                        menu.getChildren().add(labels.get(i));
                        menu.getChildren().add(textFields.get(i));
                    }
                    menu.getChildren().add(Components.createButton("Generate", BUTTON_WIDTH, () -> {}));
                }));

        Canvas canvas = new Canvas(WIDTH - menu.getMinWidth(), HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        HBox root = new HBox();
        root.getChildren().addAll(menu, canvas);
        return new Scene(root, WIDTH, HEIGHT);
    }

}
