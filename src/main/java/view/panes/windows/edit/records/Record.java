package view.panes.windows.edit.records;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import view.Settings;
import view.panes.windows.Window;

import java.util.ArrayList;
import java.util.List;

public abstract class Record extends Window {

    private GridPane gridPane = new GridPane();
    protected String tableName;
    protected double buttonWidth;
    protected Region button;
    protected double height;

    protected List<String> headers;
    protected List<String> data;
    protected ArrayList<GridElement> elements = new ArrayList<>();

    public Record(String title, List<String> headers, List<String> data, String tableName, double width, double height) {
        this.headers = headers;
        this.data = data;
        this.height = height;
        this.tableName = tableName;

        setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                buttonClick();
            }
        });


        setTitle(title);
        setPrefSize(width, height);

        buttonWidth = width;
        initButton();
        if (button == null) throw new IllegalStateException("Button must be initialized");

        button.setMaxSize(0, 0);
        gridPane.setMaxSize(width, 0);

        width /= headers.size();

        for (int i = 0; i < headers.size(); i++) {
            gridPane.add(new GridElement(headers.get(i), width, false), i, 0);
            GridElement element = new GridElement(data == null ? "" : data.get(i), width, true);
            elements.add(element);
            gridPane.add(element, i, 1);
        }

        setLayoutX(100);
        setLayoutY(300);

        VBox.setMargin(gridPane, Settings.fullInsets);
        VBox.setMargin(button, Settings.fullInsets);

        VBox vBox = new VBox(gridPane, button);

        getContentPane().getChildren().add(vBox);
    }

    protected abstract void initButton();
    protected abstract void buttonClick();

    protected String getInsertPart() {
        return getInsertQueryPart() + ' ' + getQuestionMarks();
    }

    protected String getUpdatePart(String idHeader) {
        return getUpdateQueryPart(idHeader);
    }

    private String getUpdateQueryPart(String idHeader) {
        StringBuilder builder = new StringBuilder();
        builder.append("SET ");

        for (String header : headers) {
            builder.append(header);
            builder.append(" = ?,");
        }

        builder.setCharAt(builder.length() - 1, ' ');

        builder.append("WHERE ");
        builder.append(idHeader);
        builder.append(" = ?");

        return builder.toString();
    }

    private String getInsertQueryPart() {
        int number = headers.size();

        StringBuilder builder = new StringBuilder();
        builder.append('(');

        headers.forEach(s -> {
            builder.append(s);
            builder.append(',');
        });

        builder.setCharAt(builder.length() - 1, ')');
        return builder.toString();
    }

    private String getQuestionMarks() {
        int number = headers.size();
        int lastIndex = number * 2 + 7;


        StringBuilder builder = new StringBuilder(lastIndex + 1);
        builder.append("VALUES (");

        for (int i = 0; i < number; i++) {
            builder.append("?,");
        }

        builder.setCharAt(lastIndex, ')');

        return builder.toString();
    }

}
