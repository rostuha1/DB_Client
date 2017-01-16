package controller;

import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class Messenger {

    public static void incorrectInputData() {
        Notifications.create()
                .title("Incorrect input data")
                .text("Check your data")
                .hideAfter(Duration.seconds(1))
                .showError();
    }

    public static void error() {
        Notifications.create()
                .text("Error")
                .hideAfter(Duration.seconds(1))
                .showError();
    }

    public static void recordAdded() {
        Notifications.create()
                .text("Record added")
                .hideAfter(Duration.seconds(1))
                .showInformation();
    }

    public static void recordUpdated() {
        Notifications.create()
                .text("Record updated")
                .hideAfter(Duration.seconds(1))
                .showInformation();
    }

    public static void recordDeleted() {
        Notifications.create()
                .text("Record deleted")
                .hideAfter(Duration.seconds(1))
                .showInformation();
    }

    public static void querySaved() {
        Notifications.create()
                .text("Query saved")
                .hideAfter(Duration.seconds(1))
                .showInformation();
    }

}
