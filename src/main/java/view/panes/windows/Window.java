package view.panes.windows;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import jfxtras.labs.scene.control.window.CloseIcon;
import main.Main;

public abstract class Window extends jfxtras.labs.scene.control.window.Window {

    protected static final Pane parent = Main.getWrapper();
    private CloseIcon closeIcon = new CloseIcon(this);

    {
        getRightIcons().add(closeIcon);

        setResizableWindow(false);

        setTitleBarStyleClass("window-title");
        setStyle("-fx-background-color: transparent");
    }

    protected void setOnClose(EventHandler<? super MouseEvent> onClose) {
        closeIcon.setOnMousePressed(onClose);
    }

}
