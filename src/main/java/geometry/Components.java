package geometry;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import library.filesJSON.LoadJSON;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Components {
    private static final String LABELS_JSON = "src/main/java/library/filesJSON/labels.json";

    public static Button createButton(String name, int minWidth, Runnable runnable){
        Button button = new Button(name);
        button.setMaxWidth(minWidth);
        button.setOnAction(event -> runnable.run());
        return button;
    }

    public static Label createLabel(String title){
       Label label = new Label(title);
       label.setStyle("-fx-font-size: 10pt;");
       label.setTextFill(Color.WHITE);
       return label;
    }

    public static VBox createVbox(int minWidth){
        VBox vBox = new VBox();
        vBox.setMinWidth(minWidth);
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(20);
        vBox.setStyle("-fx-background-color: #27333F;");
        return vBox;
    }

    public static ArrayList<Label> labels() throws FileNotFoundException {
        ArrayList<String> titles = LoadJSON.load(LABELS_JSON);
        ArrayList<Label> labels = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++){
            labels.add(Components.createLabel(titles.get(i)));
        }
        return labels;
    }

    public static ArrayList<TextField> textFields(int count) {
        ArrayList<TextField> textFields = new ArrayList<>();
        for (int i = 0; i < count; i++){
            textFields.add(new TextField());
        }
        return textFields;
    }

}
