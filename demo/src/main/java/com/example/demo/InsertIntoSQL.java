package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertIntoSQL {

    /**
     * Connect to the vb1.db database
     *
     * @return the Connection object
     */
    private static Connection connect() {
        Connection conn = null;
        String driver = "com.mysql.cj.jdbc.Driver";
        // MySQL connection string, pas zonodig het pad aan:
        System.out.println("reach");
        String connection = "jdbc:mysql://localhost:3306/EasyplantDB?serverTimezone=UTC";
        String user = "root";
        String password = "password";
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(connection, user, password);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Insert a new row into the tbl1 table
     *
     * @param accountnummer
     * @param temperatuur
     */
    public static void insert(String accountnummer) {
        String sql = "INSERT INTO account(accountnummer) VALUES(?)";
        try {
            PreparedStatement preparedStatement = connect().prepareStatement(sql);
            preparedStatement.setString(1, accountnummer);
            preparedStatement.executeUpdate();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
