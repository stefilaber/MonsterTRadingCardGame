package org.example.application.user;

import org.example.application.user.controller.UserController;
import org.example.application.user.repository.UserMemoryRepository;
import org.example.application.user.repository.UserRepository;
import org.example.server.Application;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.ContentType;
import org.example.server.http.StatusCode;

public class UserApp implements Application {

    private UserController userController;

    public UserApp() {
        UserRepository userRepository = new UserMemoryRepository();
        this.userController = new UserController(userRepository);
    }

    @Override
    public Response handle(Request request) {
        if (request.getPath().startsWith("/users")) {
            return userController.handle(request);
        }

        Response response = new Response();
        response.setStatusCode(StatusCode.NOT_FOUND);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.NOT_FOUND.message);

        return response;
    }
}
