package view.panes.windows.edit;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import view.panes.windows.Window;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DatePickerWindow extends Window {

    private TextField textField;
    private DatePicker datePicker = new DatePicker();

    public DatePickerWindow(TextField textField) {
        this.textField = textField;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(textField.getText(), formatter);

        datePicker.setValue(localDate);

        datePicker.setOnAction(event -> textField.setText(datePicker.getValue().toString()));

        setTitle("Date");

        setPrefSize(200, 70);

        getContentPane().getChildren().add(datePicker);

    }

}
