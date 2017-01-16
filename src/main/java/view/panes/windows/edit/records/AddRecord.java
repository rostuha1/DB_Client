package view.panes.windows.edit.records;

import controller.DBConnector;
import controller.Messenger;
import javafx.scene.paint.Color;
import view.components.ComponentBuilder;
import view.panes.windows.edit.EditWindow;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AddRecord extends Record {

    private EditWindow parentWindow;

    public AddRecord(EditWindow parentWindow, List<String> headers) {
        super("ADD", headers, null, parentWindow.getTableName(), parentWindow.getPrefWidth(), 160);
        this.parentWindow = parentWindow;
    }

    @Override
    protected void initButton() {
        button = ComponentBuilder.getButton("ADD", 20, buttonWidth, 40, 0.8, Color.web("0x70FF00"), Color.web("0x1A4600"));
        button.setOnMouseClicked(event -> buttonClick());
    }

    @Override
    protected void buttonClick() {

        String sqlQuery = "INSERT INTO " + tableName + ' ' + getInsertPart();

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DBConnector.instance.getConnection().prepareStatement(sqlQuery);

            for (int i = 0; i < elements.size(); i++) {
                preparedStatement.setString(i + 1, elements.get(i).getTextField().getText());
            }

            preparedStatement.executeUpdate();
            parentWindow.reloadTable();
            Messenger.recordAdded();
        } catch (SQLException e) {
            e.printStackTrace();
            Messenger.error();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            }catch (Exception ignored) {}
        }
    }

}
