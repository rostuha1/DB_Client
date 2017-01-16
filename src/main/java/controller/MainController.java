package controller;

public class MainController {

    private static final String correctLogin = "root";
    private static final String correctPassword = "root";

    public static boolean checkConnectionData(String login, String password) {
        return correctLogin.equals(login) && correctPassword.equals(password);
    }

}
