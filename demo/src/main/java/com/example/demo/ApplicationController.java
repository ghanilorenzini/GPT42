package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ApplicationController implements Initializable {

    @FXML
    private AnchorPane anchor_links;

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

    public void initialize() {
    }

    boolean darkmode = false;
    @FXML
    void lightmode(ActionEvent event){
        if (!darkmode) {
            anchor_links.setStyle("-fx-background-color: darkgrey;");
            anchor_rechts.setStyle("-fx-background-color: grey;");
            darkmode = true;
        }
        else {
            anchor_links.setStyle("-fx-background-color:  lightblue;");
            anchor_rechts.setStyle("-fx-background-color: white;");
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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    @FXML
    void login(ActionEvent event) throws IOException {

        String uname = username.getText();
        String pass = password.getText();

        if (username.getText().isBlank() == false && password.getText().isBlank() == false) {
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
                    loginMessageLabel.setText("Vul alstublieft een geldige email of wachtword in");
                    username.setText("");
                    password.setText("");
                    username.requestFocus();
                }

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ApplicationController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ApplicationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            loginMessageLabel.setText("Vul alstublieft een geldige email of wachtwoord in");
        }
    }

    public void register(ActionEvent actionEvent) {
    }
}

