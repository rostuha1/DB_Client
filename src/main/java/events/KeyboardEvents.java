package events;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.Main;
import view.animation.TransitionAnimation;
import view.panes.TablesListPane;

public class KeyboardEvents {

    private static boolean isHidden;
    private static boolean flag = true;

    public static void setAction(Scene scene) {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.SPACE && flag) {
                if (isHidden) TransitionAnimation.start(Main.getRoot(), null, TablesListPane.instance);
                else TransitionAnimation.start(Main.getRoot(), TablesListPane.instance, null);
                isHidden = !isHidden;
            }
        });
    }

    public static void changeFlag(boolean flag) {
        KeyboardEvents.flag = flag;
    }

    public static boolean getFlag() {
        return flag;
    }

}
