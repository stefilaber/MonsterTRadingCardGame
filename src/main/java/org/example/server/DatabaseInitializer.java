package org.example.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/swe1db";

    private static final String DB_USER = "swe1user";

    private static final String DB_PW = "swe1pw";

    public static Connection conn;
    public static void initialize() throws SQLException{

        conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);

        Statement stmt1 = conn.createStatement();
        stmt1.execute(
                """
                    CREATE TABLE IF NOT EXISTS users (
                        id int PRIMARY KEY,
                        username VARCHAR(255),
                        password VARCHAR(255) NOT NULL
                    );
                    """
        );
        stmt1.close();

        Statement stmt3 = conn.createStatement();
        stmt3.execute(
                """
                    CREATE TABLE IF NOT EXISTS sessions (
                        id int PRIMARY KEY,
                        username VARCHAR(255),
                        token VARCHAR(255) NOT NULL
                    );
                    """
        );
        stmt3.close();

        Statement stmt4 = conn.createStatement();
        stmt4.execute(
                """
                    CREATE TABLE IF NOT EXISTS cards (
                        id uuid PRIMARY KEY,
                        cardName VARCHAR(255) NOT NULL,
                        damage int,
                        cardType VARCHAR(255) NOT NULL
                    );
                    """
        );
        stmt4.close();

        Statement stmt5 = conn.createStatement();
        stmt5.execute(
                """
                    CREATE TABLE IF NOT EXISTS packages (
                        id int PRIMARY KEY,
                        username VARCHAR(255),
                        card1 uuid,
                        card2 uuid,
                        card3 uuid,
                        card4 uuid,
                        card5 uuid
                    );
                    """
        );
        stmt5.close();

    }

}
