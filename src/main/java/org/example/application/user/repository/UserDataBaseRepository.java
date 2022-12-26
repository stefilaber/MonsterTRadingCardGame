package org.example.application.user.repository;

import org.example.DatabaseInit;
import org.example.application.user.model.User;
import org.example.server.DatabaseInitializer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.example.server.DatabaseInitializer.conn;

public class UserDataBaseRepository implements UserRepository {

    //
    private final List<User> users;

    public UserDataBaseRepository() {
        this.users = new ArrayList<>();
    }

    @Override
    public List<User> findAll() {

        String userFindAllSql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        try(
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(userFindAllSql)
        ) {
            while (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    @Override
    public User findByUsername(String username) {

        String LookForUser = "SELECT * FROM users WHERE username = ?";

        try(PreparedStatement ps = conn.prepareStatement(LookForUser)) {
            ps.setString(1, username);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next())
                {
                    return new User(rs.getString("username"), rs.getString("password"));
                }
                else
                {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public User save(User user)
    {

        // check if the user already exists:
        User LookForUser = findByUsername(user.getUsername());

        if (LookForUser == null){
            // insert new user
            String InsertUser = "INSERT INTO users (username, password) VALUES (?, ?)";

            try(PreparedStatement ps2 = conn.prepareStatement(InsertUser)) {
                ps2.setString(1, user.getUsername());
                ps2.setString(2, user.getPassword());
                ps2.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            return user;
        }
        else{
            return null;
        }


    }

    @Override
    public User delete(User user) {

        // check if the user really exists:
        User LookForUser = findByUsername(user.getUsername());

        if (LookForUser != null){
            // delete user
            String DeleteUser = "DELETE FROM users WHERE username = ? AND password = ?";

            try(PreparedStatement ps2 = conn.prepareStatement(DeleteUser)) {
                ps2.setString(1, user.getUsername());
                ps2.setString(2, user.getPassword());
                ps2.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            return user;
        }
        else{
            return null;
        }
    }

    public User login(User user){

        String LookForUserWithPassword = "SELECT * FROM users WHERE username = ? AND password = ?";

        try(PreparedStatement ps = conn.prepareStatement(LookForUserWithPassword)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next())
                {
                    return new User(rs.getString("username"), rs.getString("password"));
                }
                else
                {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
