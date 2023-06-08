package com.example.demo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("42_dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 936, 677);
        stage.setTitle("Chat42");
        stage.setScene(scene);
        stage.show();
    }
    public static void changeScreen(ActionEvent actionEvent, String location) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(location));
        Scene scene = new Scene(fxmlLoader.load(), 936, 677);
        stage.setScene(scene);
        stage.show();
        DatabaseConnection.getQuery("select * from account");
    }


    public static void main(String[] args) {
        launch();
    }

}