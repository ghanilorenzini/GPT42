package com.example.demo;

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
    private ChoiceBox<String> myChoiceBox = new ChoiceBox<>();

    public String[] account = {"User", "Administrator"};

    private String account_status;




    /*===*\
    |*===*|   Dashboard
    \*===*/

    @FXML
    private ScrollPane chat_scroll;



    /*===*\
    |*===*|   Dashboard
    \*===*/
    public void initialize(URL url, ResourceBundle resourceBundle) {
        myChoiceBox.getItems().addAll(account);

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
        }
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

    public void register(ActionEvent actionEvent) {
    }

    @FXML
    private VBox chat_vbox;

    @FXML
    private TextField chat_field;

    private int buttonCount = 0;
    private Map<Button, Pair<VBox, TextField>> buttonAnchorMap = new HashMap<>();

    @FXML
    public void nieuwe_chat() {
        if (buttonCount < 30) {
            Button newButton = new Button("Chat " + (++buttonCount));
            newButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-pref-width: 230px; -fx-pref-height: 26px; -fx-border-color: black; -fx-border-width: 1px;");

            VBox newVBox = new VBox();
            TextField newTextField = new TextField();
            newVBox.setPrefWidth(432);
            newVBox.setPrefHeight(520);
            String[] colors = {"red", "green", "blue"}; // Add more colors as desired
            String color = colors[(buttonCount - 1) % colors.length];
            newVBox.setStyle("-fx-background-color: " + color + ";");
            newVBox.setVisible(false); // Initially hide the VBox

            newTextField.setLayoutX(119.0);
            newTextField.setLayoutY(605.0);
            newTextField.setPrefHeight(26.0);
            newTextField.setPrefWidth(434.0);
            newTextField.setText("Stel een vraag..");
            newTextField.setStyle("-fx-text-fill: red; -fx-background-color: yellow;");

            chat_vbox.getChildren().add(newButton);
            newVBox.getChildren().add(newTextField); // Add the TextField to the VBox

            buttonAnchorMap.put(newButton, new Pair<>(newVBox, newTextField));

            newButton.setOnAction(event -> {
                highlightButton(newButton);
                showAssociatedAnchorPane(newButton);
            });

            newButton.setOnMouseClicked(event -> {
                        if (event.getClickCount() == 2) {
                            // Change the button text when double-clicked
                            TextInputDialog dialog = new TextInputDialog(newButton.getText());
                            dialog.setHeaderText("Verander het onderwerp");
                            Optional<String> result = dialog.showAndWait();
                            result.ifPresent(newText -> newButton.setText(newText));
                        }
                    });

        anchor_rechts.getChildren().add(newVBox); // Add the VBox to anchor_rechts after button setup
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


}





