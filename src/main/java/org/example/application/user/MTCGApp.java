package org.example.application.user;

import org.example.application.user.controller.SessionController;
import org.example.application.user.controller.UserController;
import org.example.application.user.repository.UserDataBaseRepository;
import org.example.application.user.repository.UserRepository;
import org.example.server.Application;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.ContentType;
import org.example.server.http.StatusCode;

public class MTCGApp implements Application {

    private UserController userController;
    private SessionController sessionController;

    public MTCGApp() {
        UserRepository userRepository = new UserDataBaseRepository();
        this.userController = new UserController(userRepository);
        this.sessionController = new SessionController(userRepository);
    }

    @Override
    public Response handle(Request request) {
        if (request.getPath().startsWith("/users")) {
            return userController.handle(request);
        }

        if (request.getPath().startsWith("/sessions")) {
            return sessionController.handle(request);
        }

        Response response = new Response();
        response.setStatusCode(StatusCode.NOT_FOUND);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.NOT_FOUND.message);

        return response;
    }
}
