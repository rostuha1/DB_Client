package view.panes.windows.edit;

import controller.DBConnector;
import controller.Messenger;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import main.Main;
import view.Settings;
import view.components.ComponentBuilder;
import view.panes.windows.edit.records.AddRecord;
import view.panes.windows.edit.records.EditRecord;
import view.panes.windows.edit.records.SearchRecord;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class Buttons extends HBox {

    private Pane root = Main.getWrapper();
    private EditWindow parentWindow;

    public Buttons(EditWindow parentWindow, Right right) {
        this.parentWindow = parentWindow;
        double btnWidth = parentWindow.getPrefWidth() / 4 - (Settings.horizontalInsets.getLeft() + Settings.horizontalInsets.getRight()) * 2;

        Region addBtn = ComponentBuilder.getButton("Add", 20, btnWidth, 40, 0.8, Color.web("0x70FF00"), Color.web("0x1A4600"));
        Region deleteBtn = ComponentBuilder.getButton("Delete", 20, btnWidth, 40, 0.8, Color.web("0x70FF00"), Color.web("0x1A4600"));
        Region searchBtn = ComponentBuilder.getButton("Search", 20, btnWidth, 40, 0.8, Color.web("0x70FF00"), Color.web("0x1A4600"));
        Region editBtn = ComponentBuilder.getButton("Edit", 20, btnWidth, 40, 0.8, Color.web("0x70FF00"), Color.web("0x1A4600"));

        addBtn.setOnMouseClicked(event -> addBtnClick());
        deleteBtn.setOnMouseClicked(event -> deleteBtnClick());
        searchBtn.setOnMouseClicked(event -> searchBtnClick());
        editBtn.setOnMouseClicked(event -> editBtnClick());

        addButton(addBtn);
        addButton(deleteBtn);
        addButton(searchBtn);
        addButton(editBtn);

        setAlignment(Pos.CENTER);
    }

    private void addButton(Region button) {
        HBox.setMargin(button, Settings.horizontalInsets);
        getChildren().add(button);
    }

    private void addBtnClick() {
        ArrayList<String> headers = new ArrayList<>(parentWindow.getTableHeaders());
        if (headers.get(0).contains("id")) headers.remove(0);
        root.getChildren().add(new AddRecord(parentWindow, headers));
    }

    private void deleteBtnClick() {
        int selectedRowIndex = parentWindow.getTableView().getSelectionModel().getSelectedIndex();
        if (selectedRowIndex == -1) return;

        String table = parentWindow.getTableName();

        String headerId = parentWindow.getTableHeaders().get(0);
        String id = parentWindow.getTableData().get(selectedRowIndex)[0];

        String sqlQuery = "DELETE FROM " + table + " WHERE " + headerId + " = " + id;
        try {
            DBConnector.instance.getStatement().execute(sqlQuery);
            parentWindow.reloadTable();
            Messenger.recordDeleted();
        } catch (SQLException e) {
            e.printStackTrace();
            Messenger.error();
        }
    }

    private void searchBtnClick() {
        root.getChildren().add(new SearchRecord(parentWindow));
    }

    private void editBtnClick() {
        int selectedRowIndex = parentWindow.getTableView().getSelectionModel().getSelectedIndex();
        if (selectedRowIndex == -1) return;

        ArrayList<String> data = new ArrayList<>(Arrays.asList(parentWindow.getTableData().get(selectedRowIndex)));
        ArrayList<String> headers = parentWindow.getTableHeaders();

        String idHeader = headers.get(0);
        String id = data.get(0);
        if (headers.get(0).contains("id")) {
            headers.remove(0);
            data.remove(0);
        }

        root.getChildren().add(new EditRecord(idHeader, id, parentWindow, headers, data));
    }

}