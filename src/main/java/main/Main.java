package main;

import controller.DBConnector;
import events.KeyboardEvents;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.animation.AnimatedCircles;
import view.panes.ConnectionPane;

import java.nio.file.Paths;

public class Main extends Application {

    public static final String dbName = "Travel_Agency.db";
//    public static final String dbName = "Insurance_Company.db";

    private static Pane wrapper = new Pane();
    private static BorderPane root = new BorderPane();
    private static Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {

        wrapper.setPrefSize(WindowSettings.width, WindowSettings.height);
        root.setPrefSize(WindowSettings.width, WindowSettings.height);
        wrapper.getChildren().add(root);
        scene = new Scene(wrapper);

        primaryStage.setFullScreen(WindowSettings.fullscreen);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setScene(scene);

        appInit(primaryStage);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static void appInit(Stage primaryStage) {

        root.setStyle("-fx-background-color: rgb(20, 25, 15)");
        AnimatedCircles.createSpawnNodes(root);

        String css = Paths.get("src/main/resources/style.css").toAbsolutePath().toUri().normalize().toString();
        wrapper.getStylesheets().add(css);

        root.setCenter(ConnectionPane.instance);

        KeyboardEvents.setAction(scene);

        primaryStage.setOnCloseRequest(event -> DBConnector.instance.close());

    }

    public static Scene getScene() {
        return scene;
    }

    public static Pane getRoot() {
        return root;
    }

    public static Pane getWrapper() {
        return wrapper;
    }
}
