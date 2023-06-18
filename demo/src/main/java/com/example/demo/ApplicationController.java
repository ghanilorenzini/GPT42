package com.example.demo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Pair;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class ApplicationController implements Initializable {

    @FXML
    private AnchorPane anchor_main;

    @FXML
    private AnchorPane anchor_rechts;

    @FXML
    private Button lightmode;

    @FXML
    private Button inloggen;

    @FXML
    private Button register;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label loginMessageLabel;

    @FXML
    private Button login_terug;

    /* ----- login_screen ----- */

    @FXML
    private Label account_label;

    @FXML
    private ScrollPane chat_scroll;

    public void initialize(URL url, ResourceBundle resourceBundle) {


    }


    @FXML
    void inloggen_scherm(ActionEvent event) throws IOException {
        HelloApplication.changeScreen(event, "42_login_screen.fxml");
    }

    @FXML
    void registreren_scherm(ActionEvent event) throws IOException {
        HelloApplication.changeScreen(event, "42_register_screen.fxml");
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
        if (!darkmode) {
            anchor_rechts.setStyle("-fx-background-color: darkgrey;");
            anchor_main.setStyle("-fx-background-color: grey;");
            darkmode = true;
        } else {
            anchor_rechts.setStyle("-fx-background-color: white;");
            anchor_main.setStyle("-fx-background-color: lightblue;");
            darkmode = false;
    }}


    public void register(ActionEvent event) throws IOException {
        if (username.getText().isBlank() == false && password.getText().isBlank() == false) {
            if ((username.getText().equals("test")) && (password.getText().equals("test"))) {
                HelloApplication.changeScreen(event, "42_dashboard.fxml");
            } else {
                loginMessageLabel.setText("Vul alstublieft een geldige email of wachtwoord in");
                username.setText("");
                password.setText("");
                username.requestFocus();
            }
        }
    }

    Connection con;
    PreparedStatement pst;
    ResultSet rs;


    @FXML
    void login(ActionEvent event) throws IOException {

        String uname = username.getText();
        String pass = password.getText();


        if (username.getText().isBlank() == false && password.getText().isBlank() == false) {

            if ((username.getText().equals("test")) && (password.getText().equals("test"))) {
                HelloApplication.changeScreen(event, "42_dashboard.fxml");
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/easyplantDB", "root", "password");

                    pst = con.prepareStatement("select * from account where gebruikersnaam=? and wachtwoord=?");
                    pst.setString(1, uname);
                    pst.setString(2, pass);


                    rs = pst.executeQuery();

                    if (rs.next()) {

                        HelloApplication.changeScreen(event, "42_dashboard.fxml");
                    } else {
                        loginMessageLabel.setText("Vul alstublieft een geldige email of wachtwoord in");
                        username.setText("");
                        password.setText("");
                        username.requestFocus();
                    }

                } catch(ClassNotFoundException ex){
                    Logger.getLogger(ApplicationController.class.getName()).log(Level.SEVERE, null, ex);
                } catch(SQLException ex){
                    Logger.getLogger(ApplicationController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else{
                loginMessageLabel.setText("Vul alstublieft een geldige email of wachtwoord in");
            }


        }
    }

    @FXML
    private VBox chat_vbox;

    @FXML
    private ScrollPane messages_scroll;

    @FXML
    private ScrollPane main_scroll;

    private int buttonCount = 0;
    private Map<Button, Pair<VBox, TextField>> buttonAnchorMap = new HashMap<>();

    @FXML
    public void nieuwe_chat() {
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

            chat_vbox.getChildren().add(newButton);
            buttonAnchorMap.put(newButton, new Pair<>(newVBox, newTextField));

            newButton.setOnAction(event -> {
                highlightButton(newButton);
                showAssociatedAnchorPane(newButton);
                newTextField.setVisible(true);
                anchor_rechts.getChildren().clear();
                anchor_rechts.getChildren().addAll(newScrollPane, newTextField);
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
                    if (newTextField.getText().equals("Wat vind jij van Arzu?")) {
                        AITextField.setText("Chat42: " + "Arzu ziet er echt lekker uit");
                    }

                    newVBox.getChildren().add(AITextField);
                    newTextField.setText("");

                }
            });
            anchor_rechts.getChildren().add(newVBox);

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

    @FXML
    void instellingen(ActionEvent event) throws IOException {
        HelloApplication.changeScreen(event, "42_settings.fxml");
    }


    public void uitloggen(ActionEvent event) throws IOException{
        HelloApplication.changeScreen(event, "42_start_screen.fxml");

    }
}





