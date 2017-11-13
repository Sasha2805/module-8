package geometry;

import javafx.scene.control.Button;

public class MyButton {
    public static Button createButton(String name, int minWidth, Runnable runnable){
        Button button = new Button(name);
        button.setMaxWidth(minWidth);
        button.setOnAction(event -> runnable.run());
        return button;
    }
}
