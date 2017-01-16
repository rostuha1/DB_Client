package view.panes.windows;

import controller.Messenger;
import events.KeyboardEvents;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.WindowSettings;
import view.Settings;
import view.components.ComponentBuilder;
import view.panes.windows.edit.EditWindow;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class SqlWindow extends Window {
    private final TableView tableView = new TableView();

    {
        setTitle("Sql Query");
        double width = 500;

        KeyboardEvents.changeFlag(false);

        setOnClose(event -> KeyboardEvents.changeFlag(true));

        setPrefSize(width, 500);

        setLayoutX((WindowSettings.width - width) / 2);
        setLayoutY(30);

        TextArea sqlTextArea = new TextArea();
        sqlTextArea.setPrefSize(width, 200);

        width = width / 2 - 10;

        Region executeBtn = ComponentBuilder.getButton("EXECUTE", 20, width, 40, 0.5, Color.web("0x70FF00"), Color.web("0x1A4600"));
        Region saveBtn = ComponentBuilder.getButton("SAVE", 20, width, 40, 0.5, Color.web("0x70FF00"), Color.web("0x1A4600"));

        executeBtn.setOnMouseClicked(event -> executeBtnClick(tableView, sqlTextArea.getText()));
        saveBtn.setOnMouseClicked(event -> saveBtnClick(sqlTextArea.getText()));

        VBox vBox = new VBox();

        HBox buttons = new HBox(executeBtn, saveBtn);

        HBox.setMargin(executeBtn, Settings.horizontalInsets);
        HBox.setMargin(saveBtn, Settings.horizontalInsets);

        VBox.setMargin(sqlTextArea, Settings.verticalInsets);
        VBox.setMargin(buttons, Settings.verticalInsets);
        VBox.setMargin(tableView, Settings.verticalInsets);

        vBox.getChildren().add(sqlTextArea);
        vBox.getChildren().add(buttons);
        vBox.getChildren().add(tableView);

        getContentPane().getChildren().add(vBox);

    }

    private void executeBtnClick(TableView tableView, String sqlQuery) {
        EditWindow.loadTableData(tableView, sqlQuery, null, null);
    }

    private void saveBtnClick(String sqlQuery) {
        sqlQuery += '\n';
        Path path = Paths.get("sql.txt");
        try {
            Files.write(path, sqlQuery.getBytes(), Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
            Messenger.querySaved();
        } catch (IOException ignored) {}
    }

}
