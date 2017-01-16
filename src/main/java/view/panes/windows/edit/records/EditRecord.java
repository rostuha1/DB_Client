package view.panes.windows.edit.records;

import controller.DBConnector;
import controller.Messenger;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import main.Main;
import view.components.ComponentBuilder;
import view.panes.windows.edit.DatePickerWindow;
import view.panes.windows.edit.EditWindow;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EditRecord extends Record {

    private String idHeader;
    private String id;
    private EditWindow parentWindow;

    public EditRecord(String idHeader, String id, EditWindow parentWindow, List<String> headers, List<String> data) {
        super("Edit", headers, data, parentWindow.getTableName(), parentWindow.getPrefWidth(), 160);
        this.idHeader = idHeader;
        this.id = id;
        this.parentWindow = parentWindow;

        for (GridElement element: elements) {
            setTextFieldDoubleClick(element.getTextField());
        }

    }

    @Override
    protected void initButton() {
        button = ComponentBuilder.getButton("EDIT", 20, buttonWidth, 40, 0.8, Color.web("0x70FF00"), Color.web("0x1A4600"));
        button.setOnMouseClicked(event -> buttonClick());
    }

    @Override
    protected void buttonClick() {
        {
            String sqlQuery = "UPDATE " + tableName + ' ' + getUpdatePart(idHeader);

            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = DBConnector.instance.getConnection().prepareStatement(sqlQuery);

                int i;
                for (i = 0; i < elements.size(); i++) {
                    preparedStatement.setString(i + 1, elements.get(i).getTextField().getText());
                }

                preparedStatement.setString(i + 1, id);

                preparedStatement.executeUpdate();
                parentWindow.reloadTable();
                Messenger.recordUpdated();
            } catch (SQLException e) {
                e.printStackTrace();
                Messenger.error();
            } finally {
                try {
                    if (preparedStatement != null) preparedStatement.close();
                } catch (Exception ignored) {
                }
            }
        }

    }

    private void setTextFieldDoubleClick(TextField field) {
        field.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                if(mouseEvent.getClickCount() == 2){
                    Date date = parseDate(field.getText());
                    if (date != null) {
                        Main.getWrapper().getChildren().add(new DatePickerWindow(field));
                    }
                }
            }
        });
    }

    private Date parseDate(String date){
        SimpleDateFormat[] possibleFormats = new SimpleDateFormat[] { new SimpleDateFormat("yyyy-MM-dd") };

        Date retVal = null;
        for (SimpleDateFormat f: possibleFormats) {
            f.setLenient(false);
            try {
                retVal = f.parse(date);
            } catch (ParseException e) {}
        }
        return retVal;
    }

}
