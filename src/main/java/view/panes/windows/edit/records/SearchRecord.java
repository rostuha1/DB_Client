package view.panes.windows.edit.records;

import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import view.Settings;
import view.components.ComponentBuilder;
import view.panes.windows.Window;
import view.panes.windows.edit.EditWindow;

import java.util.ArrayList;

public class SearchRecord extends Window {

    private GridPane gridPane = new GridPane();
    private EditWindow parentWindow;
    private ArrayList<GridElement> elements = new ArrayList<>();
    private TableView tableView = new TableView();
    private VBox vBox;

    public SearchRecord(EditWindow parentWindow) {
        this.parentWindow = parentWindow;

        setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                searchBtnClick();
            }
        });


        setTitle("Search");

        double width = parentWindow.getPrefWidth();

        setPrefSize(width, 160);
        tableView.setPrefSize(width, 300);

        Region searchBtn = ComponentBuilder.getButton("SEARCH", 20, width - 20, 40, 0.8, Color.web("0x70FF00"), Color.web("0x1A4600"));
        searchBtn.setOnMouseClicked(event -> searchBtnClick());

        ArrayList<String> headers = parentWindow.getTableHeaders();

        width /= headers.size();

        for (int i = 0; i < headers.size(); i++) {
            gridPane.add(new GridElement(headers.get(i), width, false), i, 0);
            GridElement element = new GridElement("", width, true);
            elements.add(element);
            gridPane.add(element, i, 1);
        }

        setLayoutX(100);
        setLayoutY(300);

        VBox.setMargin(gridPane, Settings.fullInsets);
        VBox.setMargin(searchBtn, Settings.fullInsets);
        vBox = new VBox(gridPane, searchBtn);

        getContentPane().getChildren().add(vBox);

    }

    private void searchBtnClick() {

        int count = 0;
        for (GridElement element : elements) {
            if (element.getTextField().getText().isEmpty()) count++;
        }

        if (count == elements.size()) {
            vBox.getChildren().remove(tableView);
            setPrefHeight(160);
            return;
        }

        String sqlQuery = "SELECT * FROM " + parentWindow.getTableName() + ' ' + getWherePart(parentWindow.getTableHeaders(), elements);
        EditWindow.loadTableData(tableView, sqlQuery, null, null);

        if (tableView.getItems().isEmpty()) {
            vBox.getChildren().remove(tableView);
            setPrefHeight(160);
        } else {
            if (!vBox.getChildren().contains(tableView)) vBox.getChildren().add(tableView);
            setPrefHeight(250 + 35 * tableView.getItems().size());
        }

    }

    private String getWherePart(ArrayList<String> headers, ArrayList<GridElement> elements) {
        StringBuilder builder = new StringBuilder();
        builder.append("WHERE ");

        for (int i = 0; i < headers.size(); i++) {
            String queryWord = elements.get(i).getTextField().getText();
            if (queryWord.isEmpty()) continue;

            builder.append(headers.get(i));
            builder.append(" LIKE '%");
            builder.append(queryWord);
            builder.append("%' AND ");
        }

        builder.delete(builder.length() - 5, builder.length() - 1);

        return builder.toString();
    }

}
