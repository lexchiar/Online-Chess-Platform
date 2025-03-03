package com.tco.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDatabase {

    public boolean userExists(String username) throws Exception {
        String query = "SELECT id FROM user WHERE nickname = ?;";
        try (
            Connection conn = DriverManager.getConnection(Credential.url(), Credential.USER, Credential.PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(query);
        ) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public void createUser(String username, String password) throws Exception {
        String query = "INSERT INTO user (nickname, email, password, account_creation) VALUES (?, ?, ?, NOW());";
        try (
            Connection conn = DriverManager.getConnection(Credential.url(), Credential.USER, Credential.PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(query);
        ) {
            stmt.setString(1, username);
            stmt.setString(2, username + "@example.com"); // Placeholder email
            stmt.setString(3, password);
            stmt.executeUpdate();
        }
    }

    public boolean validateUser(String username, String password) throws Exception {
        String query = "SELECT id FROM user WHERE nickname = ? AND password = ?;";
        try (
            Connection conn = DriverManager.getConnection(Credential.url(), Credential.USER, Credential.PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(query);
        ) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }
}

