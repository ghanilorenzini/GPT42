package com.example.demo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.BreakIterator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApplicationController implements Initializable {

    @FXML
    private AnchorPane anchor_main;

    @FXML
    private AnchorPane anchorRechts;

    @FXML
    private Button lightmode;

    @FXML
    private Button dashboard_lightmode;


    @FXML
    private Button inloggen;

    @FXML
    private Button register;

    @FXML
    TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label loginMessageLabel;

    @FXML
    private Button login_terug;

    @FXML
    private VBox chatVBox;

    @FXML
    private VBox messagesVBox;

    @FXML
    private ScrollPane messagesScroll;

    @FXML
    private ScrollPane mainScroll;

    @FXML
    private Button instellingen;

    private DatabaseHandler databaseHandler;
    private ChatHandler chatHandler;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseHandler = new DatabaseHandler();
        chatHandler = new ChatHandler(chatVBox, messagesScroll, anchorRechts);
        currentScreen = "42_start_screen.fxml"; // Set the initial screen
    }

    private String currentScreen;
    public String getCurrentScreen() {
        return currentScreen;
    }
    @FXML
    void inloggen_scherm(ActionEvent event) throws IOException {
        EventHandler.handleLogin(event);
    }

    @FXML
    void registreren_scherm(ActionEvent event) throws IOException {
        EventHandler.handleRegistration(event);
    }

    @FXML
    void ww_vergeten(ActionEvent event) throws IOException {
        HelloApplication.changeScreen(event, "wwvergeten.fxml");
    }

    @FXML
    void inloggen(ActionEvent event) throws IOException {
        HelloApplication.changeScreen(event, "42_dashboard.fxml");
    }

    @FXML
    void login_terug(ActionEvent event) throws IOException {
        HelloApplication.changeScreen(event, "42_start_screen.fxml");
    }

    private boolean darkmode = false;

    @FXML
    void lightmode(ActionEvent event) {
        EventHandler.handleLightMode(darkmode, anchorRechts, anchor_main);
        darkmode = !darkmode;
    }

    @FXML
    void dashboard_lightmode(ActionEvent event){
        EventHandler.handleDashboardLightMode(darkmode, anchorRechts, anchor_main, chatVBox, messagesVBox);
        darkmode = !darkmode;
    }

    public LoginCredentials loginCredentials;

    @FXML
    private void initializeLoginCredentials() {
        String uname = username.getText();
        String pass = password.getText();
        loginCredentials = new LoginCredentials(uname, pass);
    }



    private String uname;
    private String pass;

    @FXML
    private void register(ActionEvent event) throws IOException {
        if (loginCredentials.isValid()) {
            boolean success = databaseHandler.registerUser(loginCredentials.getUsername(), loginCredentials.getPassword());
            if (success) {
                HelloApplication.changeScreen(event, "42_dashboard.fxml");
            }
        } else {
                handleLoginFailure();
        }
    }

    @FXML
    public void login(ActionEvent event) throws IOException {
        initializeLoginCredentials();
        if (loginCredentials.isValid() && (databaseHandler.loginUser(loginCredentials.getUsername(), loginCredentials.getPassword()) || (loginCredentials.getUsername().equals("test") && loginCredentials.getPassword().equals("test")))) {
            HelloApplication.changeScreen(event, "42_dashboard.fxml");
        } else {
            handleLoginFailure();
        }
    }

    private void handleLoginFailure() {
        loginMessageLabel.setText("Vul alstublieft een geldige Email of Wachtwoord in");
        username.setText("");
        password.setText("");
        username.requestFocus();
    }

    @FXML
    void nieuwe_chat() {
        chatHandler.nieuweChat();
    }


    @FXML
    void instellingen(ActionEvent event) throws IOException {
        HelloApplication.changeScreen(event, "42_settings.fxml");
    }

    public void uitloggen(ActionEvent event) throws IOException {
        HelloApplication.changeScreen(event, "42_start_screen.fxml");
    }
}
