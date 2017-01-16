package view.panes.windows.edit.records;

import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import view.Settings;

import java.util.ArrayList;

public class GridElement extends StackPane {

    private TextField textField;

    public GridElement(String data, double elementWidth, boolean editableElement) {
        setPrefSize(elementWidth, 40);
        GridPane.setMargin(this, new Insets(1));

        if (editableElement) {
            TextField textField = new TextField();
            textField.setOpacity(0.8);
            textField.setText(data);
            textField.setPrefSize(elementWidth, 40);

            this.textField = textField;
            getChildren().add(textField);
        } else {
            setStyle("-fx-background-color: rgba(200, 140, 0, 0.4)");
            Text text = new Text(data);
            text.setFont(Settings.textFont);
            text.setStyle("-fx-font-weight: bold");
            getChildren().add(text);
        }
    }

    public TextField getTextField() {
        return textField;
    }

}