package org.example.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    private static String DB_URL = "jdbc:postgresql://localhost:5432/swe1db";

    private static String DB_USER = "swe1user";

    private static String DB_PW = "swe1pw";

    public static Connection conn;
    public static void initialize() throws SQLException{

        conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);

        Statement stmt1 = conn.createStatement();
        stmt1.execute(
                """
                    CREATE TABLE IF NOT EXISTS users (
                        username VARCHAR(255) PRIMARY KEY,
                        password VARCHAR(255) NOT NULL
                    );
                    """
        );
        stmt1.close();

        Statement stmt2 = conn.createStatement();
        stmt2.execute(
                """
                    CREATE TABLE IF NOT EXISTS cards (
                        username VARCHAR(255) PRIMARY KEY,
                        password VARCHAR(255) NOT NULL
                    );
                    """
        );
        stmt2.close();

        Statement stmt3 = conn.createStatement();
        stmt3.execute(
                """
                    CREATE TABLE IF NOT EXISTS sessions (
                        username VARCHAR(255) PRIMARY KEY,
                        token VARCHAR(255) NOT NULL
                    );
                    """
        );
        stmt3.close();

    }

}
