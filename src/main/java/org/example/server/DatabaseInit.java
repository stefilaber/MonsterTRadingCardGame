package org.example.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseInit {

    private static String DB_URL = "jdbc:postgresql://localhost:5432/swe1db";

    private static String DB_USER = "swe1user";

    private static String DB_PW = "swe1pw";

    public static void main(String[] args) throws SQLException{
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
    }



}
