package org.example.application.user.repository;

import org.example.application.user.model.User;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    User findByUsername(String username);

    User save(User user);

    User delete(User user);
}
