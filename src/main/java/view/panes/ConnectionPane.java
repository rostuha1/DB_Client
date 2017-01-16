package view.panes;

import controller.MainController;
import controller.Messenger;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.Main;
import view.Settings;
import view.animation.TransitionAnimation;
import view.components.Component;
import view.components.ComponentBuilder;

public class ConnectionPane extends VBox {

    private static final int width = 300;

    public static ConnectionPane instance = new ConnectionPane();

    private ConnectionPane() {
        Label loginLbl = getLabel("Login");
        Label passwordLbl = getLabel("Password");

        TextField loginField = (TextField) ComponentBuilder.create(Component.FIELD, "Login");
        PasswordField passwordField = (PasswordField) ComponentBuilder.create(Component.PASSWORD_FIELD, "Password");

        Region connectBtn = ComponentBuilder.getButton("Connect", 20, width, 40, 0.5, Color.web("0x70FF00"), Color.web("0x1A4600"));

        connectBtn.setOnMouseClicked(event -> connectBtnClick(loginField.getText(), passwordField.getText()));

        setMaxSize(width, 0);

        VBox.setMargin(loginField, Settings.verticalInsets);
        VBox.setMargin(passwordField, Settings.verticalInsets);

//        loginField.setText("root");
//        passwordField.setText("root");

        getChildren().add(loginLbl);
        getChildren().add(loginField);
        getChildren().add(passwordLbl);
        getChildren().add(passwordField);
        getChildren().add(connectBtn);

    }

    private Label getLabel(String text) {
        Label label = new Label(text);

        label.setFont(Settings.textFont);
        label.setTextFill(Settings.textColor);
        label.setMaxWidth(width);
        label.setAlignment(Pos.CENTER);

        return label;
    }

    private void connectBtnClick(String login, String password) {
        if (!MainController.checkConnectionData(login, password)) {
            Messenger.incorrectInputData();
            return;
        }

        TransitionAnimation.start(Main.getRoot(), this, TablesListPane.instance);

    }

}
