package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ChatHandler extends QuestionAnsweringModel {
    private int buttonCount = 0;
    private Map<Button, Pair<VBox, TextField>> buttonAnchorMap = new HashMap<>();

    @FXML
    private VBox chatVBox;

    @FXML
    private ScrollPane messagesScroll;

    @FXML
    private ScrollPane mainScroll;

    @FXML
    private AnchorPane anchorRechts;

    public ChatHandler(VBox chatVBox, ScrollPane messagesScroll, AnchorPane anchorRechts) {

        this.chatVBox = chatVBox;
        this.messagesScroll = messagesScroll;
        this.anchorRechts = anchorRechts;
    }


    public void nieuweChat() {
        if (buttonCount < 30) {
            Button newButton = createButton();
            VBox newVBox = createVBox();
            ScrollPane newScrollPane = createScrollPane(newVBox);
            TextField newTextField = createTextField(newButton, newVBox, newScrollPane);

            chatVBox.getChildren().add(newButton);
            buttonAnchorMap.put(newButton, new Pair<>(newVBox, newTextField));

            newButton.setOnAction(event -> {
                highlightButton(newButton);
                showAssociatedAnchorPane(newButton);
                newTextField.setVisible(true);
                anchorRechts.getChildren().clear();
                anchorRechts.getChildren().addAll(newScrollPane, newTextField);
            });

            newButton.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    changeButtonSubject(newButton);
                }
            });

            newTextField.setOnMouseClicked(event -> {
                clearTextField(newTextField);
            });

            newTextField.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    processTextFieldInput(newTextField, newVBox);
                }
            });
            anchorRechts.getChildren().add(newVBox);
        }
    }

    private Button createButton() {
        Button newButton = new Button("Chat " + (++buttonCount));
        newButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-pref-width: 230px; -fx-pref-height: 26px; -fx-border-color: black; -fx-border-width: 1px;");
        return newButton;
    }


    private VBox createVBox() {
        EventHandler eventHandler = new EventHandler();
        VBox newVBox = new VBox();
        newVBox.setPrefWidth(450);
        newVBox.setPrefHeight(520);
        newVBox.setLayoutX(119);
        newVBox.setLayoutY(40);
        if (!eventHandler.isLightMode) {
            newVBox.setStyle("-fx-background-color: grey");
        } else {
            newVBox.setStyle("-fx-background-color: grey");
        }
        newVBox.setVisible(false);
        return newVBox;
    }

    private ScrollPane createScrollPane(VBox content) {
        EventHandler eventHandler = new EventHandler();
        ScrollPane newScrollPane = new ScrollPane(content);
        newScrollPane.setPrefWidth(450);
        newScrollPane.setPrefHeight(520);
        newScrollPane.setLayoutX(119);
        newScrollPane.setLayoutY(40);
        newScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        newScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        if (eventHandler.isLightMode) {
            newScrollPane.setStyle("-fx-background-color: white");
        } else {
            newScrollPane.setStyle("-fx-background-color: grey");
        }
        return newScrollPane;
    }

    private TextField createTextField(Button button, VBox vbox, ScrollPane scrollPane) {
        TextField newTextField = new TextField();
        newTextField.setPrefHeight(26.0);
        newTextField.setPrefWidth(450);
        newTextField.setLayoutX(119);
        newTextField.setLayoutY(605);
        newTextField.setVisible(false);
        newTextField.setText("Stel een vraag...");
        newTextField.setStyle("-fx-text-fill: white; -fx-background-color: grey;");
        return newTextField;
    }


    private void changeButtonSubject(Button button) {
        TextInputDialog dialog = new TextInputDialog(button.getText());
        dialog.setHeaderText("Verander het onderwerp");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newText -> button.setText(newText));
    }

    private void clearTextField(TextField textField) {
        if (textField.getText().equals("Stel een vraag...")) {
            textField.setText("");
        }
    }

    @Override
    protected TextArea createMessageTextField(String text) {
        TextArea messageTextField = new TextArea(text);
        messageTextField.setVisible(true);
        messageTextField.setWrapText(false);
        messageTextField.setPrefHeight(30);
        messageTextField.setEditable(false);
        return messageTextField;
    }

    @Override
    protected void answerQuestion(String question, TextArea AITextField) {
        if (question.equals("Wat is de hoofdstad van Frankrijk?")) {
            AITextField.setText("Chat42: " + "De hoofdstad van Frankrijk is Parijs.");
        } else {
            AITextField.setText("Chat42: " + "Sorry, ik weet het antwoord daar niet op.");
        }
        if (question.equals("Via welke wegen kan ik het snelst naar Rome gaan?")) {
            AITextField.setText("Chat42: " + "Ga gewoon met de vliegtuig...");
        }
    }

    private void showAssociatedAnchorPane(Button button) {
        // Hide all VBox and TextField
        for (Pair<VBox, TextField> pair : buttonAnchorMap.values()) {
            pair.getKey().setVisible(false);
            pair.getValue().setVisible(false);
        }

        // Show the associated VBox and TextField for the clicked button
        Pair<VBox, TextField> associatedPair = buttonAnchorMap.get(button);
        associatedPair.getKey().setVisible(true);
        associatedPair.getValue().setVisible(true);
    }

    private void highlightButton(Button button) {
        // Reset styles for all buttons
        for (Button b : buttonAnchorMap.keySet()) {
            b.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-pref-width: 230px; -fx-pref-height: 26px; -fx-border-color: black; -fx-border-width: 1px;");
        }

        // Apply special style to the selected button
        button.setStyle("-fx-background-color:  #2d4474; -fx-text-fill: white; -fx-pref-width: 230px; -fx-pref-height: 26px; -fx-border-color: black; -fx-border-width: 1px;");
    }


    @FXML
    private Button instellingen;
    }