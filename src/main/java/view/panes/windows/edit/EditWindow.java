package view.panes.windows.edit;

import controller.DBConnector;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import main.WindowSettings;
import view.Settings;
import view.panes.windows.Window;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class EditWindow extends Window {

    private final String table;
    private final TableView tableView = new TableView();
    private ArrayList<String> tableHeaders = new ArrayList<>();
    private ArrayList<String[]> tableData = new ArrayList<>();

    public static final double cellWidth = 160;
    private static final double height = 350;

    private EditWindow(String table) {
        this.table = table;
        setTitle("Edit " + table + " data");

        reloadTable();

        if (tableView.getItems().isEmpty()) return;
        double width = tableView.getColumns().size() * cellWidth + 6;

        if (width < 500) width = 500;

        setPrefSize(width, height);

        setLayoutX((WindowSettings.width - width) / 2);
        setLayoutY(30);

        HBox buttons = new Buttons(this, Right.DEVELOPER);
        VBox.setMargin(buttons, Settings.verticalInsets);

        VBox vBox = new VBox(tableView, buttons);

        getContentPane().getChildren().add(vBox);
    }

    public static void create(String tableName) {
        parent.getChildren().add(new EditWindow(tableName));
    }

    public static void loadTableData(TableView tableView, String sqlQuery, ArrayList<String> tableHeaders, ArrayList<String[]> tableData) {
        try {
            tableView.getColumns().clear();
            tableView.getItems().clear();
            if (tableHeaders != null) tableHeaders.clear();
            if (tableData != null) tableData.clear();

            ObservableList<ObservableList> data = FXCollections.observableArrayList();

            ResultSet rs = DBConnector.instance.getStatement().executeQuery(sqlQuery);

            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;

                String columnData = rs.getMetaData().getColumnName(i + 1);
                TableColumn col = new TableColumn(columnData);
                if (tableHeaders != null) tableHeaders.add(columnData);

                col.setPrefWidth(cellWidth);
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                tableView.getColumns().addAll(col);
            }

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                int count = rs.getMetaData().getColumnCount();
                String[] strings = new String[count];
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                    strings[i-1] = rs.getString(i);
                }
                if (tableData != null) tableData.add(strings);
                data.add(row);
            }
            tableView.setItems(data);

        } catch (Exception ignored) {}
    }

    public Map<String, String> getTableColumns(int selectedRowIndex) {
        Map<String, String> map = new LinkedHashMap<>();

        for (int i = 0; i < tableHeaders.size(); i++) {
            map.put(tableHeaders.get(i), tableData.get(selectedRowIndex)[i]);
        }

        return map;
    }

    public ArrayList<String> getTableHeaders() {
        return tableHeaders;
    }

    public ArrayList<String[]> getTableData() {
        return tableData;
    }

    public TableView getTableView() {
        return tableView;
    }

    public String getTableName() {
        return table;
    }

    public void reloadTable() {
        EditWindow.loadTableData(tableView, "SELECT * FROM " + table, tableHeaders, tableData);
    }

}
