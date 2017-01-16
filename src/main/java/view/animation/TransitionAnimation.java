package view.animation;

import javafx.animation.FadeTransition;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class TransitionAnimation {

    private static boolean pressLock;

    static int EFFECT_DURATION = 500;

    private static FadeTransition showAnimation = new FadeTransition(Duration.millis(EFFECT_DURATION));
    private static FadeTransition hideAnimation = new FadeTransition(Duration.millis(EFFECT_DURATION));

    static {
        showAnimation.setFromValue(0);
        showAnimation.setToValue(1);
        hideAnimation.setFromValue(1);
        hideAnimation.setToValue(0);

        showAnimation.setOnFinished(event -> pressLock = false);
    }

    public static void start(Pane parent, Pane currentMenu, Pane newMenu) {
        if (pressLock) return;
        pressLock = true;

        hideAnimation.setNode(currentMenu);
        showAnimation.setNode(newMenu);

        hideAnimation.setOnFinished(event -> {
            if (newMenu != null) newMenu.setOpacity(0);
            if (parent instanceof BorderPane) {
                ((BorderPane) parent).setCenter(newMenu);
            } else {
                parent.getChildren().remove(currentMenu);
                parent.getChildren().add(newMenu);
            }
            showAnimation.play();
        });

        hideAnimation.play();
    }

}