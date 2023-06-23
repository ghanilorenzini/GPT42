package com.example.demo;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Pair;

public abstract class QuestionAnsweringModel {
    public void processTextFieldInput(TextField textField, VBox vbox) {
        String inputText = textField.getText();
        TextArea messageTextField = createMessageTextField("Akram: " + inputText);
        vbox.getChildren().add(messageTextField);

        TextArea AITextField = createAITextField();
        vbox.getChildren().add(AITextField);

        answerQuestion(inputText, AITextField);

        textField.setText("");
    }

    protected abstract TextArea createMessageTextField(String text);

    protected TextArea createAITextField() {
        TextArea AITextField = new TextArea("Chat42: ");
        AITextField.setWrapText(true);
        AITextField.setStyle("-fx-background-color: black;");
        AITextField.setVisible(true);
        AITextField.setWrapText(false);
        AITextField.setPrefHeight(30);
        AITextField.setEditable(false);
        return AITextField;
    }

    protected abstract void answerQuestion(String question, TextArea AITextField);
}
