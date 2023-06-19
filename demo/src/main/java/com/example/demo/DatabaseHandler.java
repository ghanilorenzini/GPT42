package com.example.demo;

import java.sql.*;

public class DatabaseHandler {
    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;

    public boolean registerUser(String username, String password) {
        boolean success = false;

        if (!username.isBlank() && !password.isBlank()) {
            if (username.equals("test") && password.equals("test")) {
                success = true;
            }
        }

        return success;
    }

    public boolean loginUser(String username, String password) {
        boolean success = false;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/easyplantDB", "root", "password");

            pst = con.prepareStatement("SELECT * FROM account WHERE gebruikersnaam=? AND wachtwoord=?");
            pst.setString(1, username);
            pst.setString(2, password);

            rs = pst.executeQuery();

            if (rs.next()) {
                success = true;
            }

            rs.close();
            pst.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }

        return success;
    }
}
