package com.example.demo;

import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class EventHandler {
        public static void handleLogin(ActionEvent event) throws IOException {
            HelloApplication.changeScreen(event, "42_login_screen.fxml");
        }

        public static void handleRegistration(ActionEvent event) throws IOException {
            HelloApplication.changeScreen(event, "42_register_screen.fxml");
        }

        public static void handleLightMode(boolean darkmode, AnchorPane anchorRechts, AnchorPane anchor_main) {
            if (!darkmode) {
                anchorRechts.setStyle("-fx-background-color: darkgrey;");
                anchor_main.setStyle("-fx-background-color: grey;");
            }
            else {
                anchorRechts.setStyle("-fx-background-color: white;");
                anchor_main.setStyle("-fx-background-color: lightblue;");
            }
        }

        public boolean isLightMode;

        public EventHandler() {
            isLightMode = true;
        }

        public void setDarkMode(boolean darkMode) {
            isLightMode = !darkMode;
        }

        public boolean isDarkMode() {
            return !isLightMode;
        }

    }

