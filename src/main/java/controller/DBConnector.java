package controller;

import main.Main;

import java.nio.file.Paths;
import java.sql.*;

public class DBConnector {

    public static final DBConnector instance = new DBConnector();

    private Connection connection = null;
    private Statement statement = null;

    private DBConnector() {
        try {
            String dbPath = Paths.get("src/main/resources/" + Main.dbName).toAbsolutePath().toString();
            connection = DriverManager.getConnection("jdbc:SQLite:" + dbPath);

            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }

    public void close() {
        try {
            if (connection != null) connection.close();
            if (statement != null) statement.close();
        } catch (SQLException ignored) {
        }
    }

}