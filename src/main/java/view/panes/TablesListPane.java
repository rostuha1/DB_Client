package view.panes;

import controller.DBConnector;
import events.KeyboardEvents;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.Main;
import view.Settings;
import view.components.ComponentBuilder;
import view.panes.windows.SqlWindow;
import view.panes.windows.edit.EditWindow;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TablesListPane extends VBox {

    private static final int width = 300;

    public static final TablesListPane instance = new TablesListPane();
    private ArrayList<String> tableNames;

    private TablesListPane() {
        tableNames = getTableNames();
        ListView<String> tableList = new ListView<>(FXCollections.observableArrayList(tableNames));

        setAlignment(Pos.CENTER);
        tableList.setMaxSize(width, 300);

        int btnWidth = width / 2 - 10;
        Region editBtn = ComponentBuilder.getButton("Edit", 15, btnWidth, 30, 0.5, Color.web("0x70FF00"), Color.web("0x1A4600"));
        Region sqlBtn = ComponentBuilder.getButton("SQL", 15, btnWidth, 30, 0.5, Color.web("0x70FF00"), Color.web("0x1A4600"));

        editBtn.setOnMouseClicked(event -> editBtnClick(tableList));
        sqlBtn.setOnMouseClicked(event -> sqlBtnClick());

        tableList.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                if(mouseEvent.getClickCount() == 2){
                    editBtnClick(tableList);
                }
            }
        });

        HBox buttons = new HBox();

        HBox.setMargin(editBtn, Settings.horizontalInsets);
        HBox.setMargin(sqlBtn, Settings.horizontalInsets);

        buttons.getChildren().add(editBtn);
        buttons.getChildren().add(sqlBtn);

        buttons.setMaxWidth(width);

        VBox.setMargin(buttons, Settings.verticalInsets);

        getChildren().add(tableList);
        getChildren().add(buttons);

    }

    private ArrayList<String> getTableNames() {
        try {
            DatabaseMetaData metaData = DBConnector.instance.getConnection().getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = metaData.getTables(null, null, "%", types);

            ArrayList<String> res = new ArrayList<>();

            while (rs.next()) {
                res.add(rs.getString("TABLE_NAME"));
            }
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void editBtnClick(ListView<String> tableList) {
        String selectedTable = tableList.getSelectionModel().getSelectedItem();
        if (selectedTable != null) EditWindow.create(selectedTable);
    }

    private void sqlBtnClick() {
        if (KeyboardEvents.getFlag()) Main.getWrapper().getChildren().add(new SqlWindow());
    }

    public ArrayList<String> getTableNmaes() {
        return tableNames;
    }

}
