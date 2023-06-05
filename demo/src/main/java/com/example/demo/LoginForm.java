package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class LoginForm extends Application {
    // SQL Database credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/easyplantDB";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    // GUI elements
    private TextField usernameField;
    private PasswordField passwordField;
    private Label messageLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Inloggen");

        // Create GUI elements
        Label usernameLabel = new Label("Gebruikersnaam:");
        usernameField = new TextField();
        Label passwordLabel = new Label("Wachtwoord:");
        passwordField = new PasswordField();
        Button loginButton = new Button("inloggen");
        messageLabel = new Label();

        // FXML Loader
        FXMLLoader loader = new FXMLLoader(getClass().getResource("42_login_screen.fxml"));
        loader.setController(new ApplicationController());
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();


        // Add event handler to login button
        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            try {
                if (checkCredentials(username, password)) {
                    messageLabel.setText("Inloggen gelukt!");
                } else {
                    messageLabel.setText("Inloggen mislukt. Controleer je gebruikersnaam en wachtwoord.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private boolean checkCredentials(String username, String password) throws SQLException {
        // Connect to database
        Connection connection = DriverManager.getConnection("easyplantDB", "Gebruikersnaam", "Wachtwoord");
        Statement statement = connection.createStatement();

        // Define SQL query to check credentials
        String sql = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";

        // Execute query and store result
        ResultSet resultSet = statement.executeQuery(sql);

        // Check if a match was found
        boolean matchFound = resultSet.next();

        // Close connection
        connection.close();

        // Return true if a match was found, otherwise false
        return matchFound;
    }
}
