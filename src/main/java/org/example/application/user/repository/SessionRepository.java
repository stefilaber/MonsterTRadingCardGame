package org.example.application.user.repository;

import org.example.application.user.model.Session;
import org.example.application.user.model.User;

public interface SessionRepository {

        Session save(User user);
}
