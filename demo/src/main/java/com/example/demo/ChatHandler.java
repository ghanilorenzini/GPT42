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

public class ChatHandler {
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
            Button newButton = new Button("Chat " + (++buttonCount));
            newButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-pref-width: 230px; -fx-pref-height: 26px; -fx-border-color: black; -fx-border-width: 1px;");

            VBox newVBox = new VBox();
            newVBox.setPrefWidth(450);
            newVBox.setPrefHeight(520);
            newVBox.setLayoutX(119);
            newVBox.setLayoutY(40);
            newVBox.setStyle("-fx-background-color: white");
            newVBox.setVisible(false);

            ScrollPane newScrollPane = new ScrollPane(newVBox);
            newScrollPane.setPrefWidth(450);
            newScrollPane.setPrefHeight(520);
            newScrollPane.setLayoutX(119);
            newScrollPane.setLayoutY(40);
            newScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            newScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

            TextField newTextField = new TextField();
            newTextField.setPrefHeight(26.0);
            newTextField.setPrefWidth(450);
            newTextField.setLayoutX(119);
            newTextField.setLayoutY(605);
            newTextField.setVisible(false);
            newTextField.setText("Stel een vraag...");
            newTextField.setStyle("-fx-text-fill: white; -fx-background-color: grey;");

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
                    TextInputDialog dialog = new TextInputDialog(newButton.getText());
                    dialog.setHeaderText("Verander het onderwerp");
                    Optional<String> result = dialog.showAndWait();
                    result.ifPresent(newText -> newButton.setText(newText));
                }
            });

            newTextField.setOnMouseClicked(event -> {
                if (newTextField.getText().equals("Stel een vraag...")) {
                    newTextField.setText("");
                }
            });

            newTextField.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    TextArea messageTextField = new TextArea("Akram: " + newTextField.getText());
                    messageTextField.setVisible(true);
                    messageTextField.setWrapText(false); // Enable text wrapping
                    messageTextField.setPrefHeight(30);
                    messageTextField.setEditable(false);


                    TextArea AITextField = new TextArea("Chat42: ");
                    AITextField.setWrapText(true); // Enable text wrapping
                    AITextField.setStyle("-fx-background-color: black;");
                    AITextField.setVisible(true);
                    AITextField.setWrapText(false); // Enable text wrapping
                    AITextField.setPrefHeight(30);
                    AITextField.setEditable(false);

                    newVBox.getChildren().add(messageTextField);

                    if (newTextField.getText().equals("Hoi ik ben Akram")) {
                        AITextField.setText("Chat42: " + "Hoi Akram, ik ben Chat42");
                    }

                    newVBox.getChildren().add(AITextField);
                    newTextField.setText("");

                }
            });
            anchorRechts.getChildren().add(newVBox);

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