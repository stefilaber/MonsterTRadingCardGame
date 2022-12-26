package org.example.application.user;

import org.example.application.user.controller.PackageController;
import org.example.application.user.controller.SessionController;
import org.example.application.user.controller.UserController;
import org.example.application.user.repository.*;
import org.example.server.Application;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.ContentType;
import org.example.server.http.StatusCode;

public class MTCGApp implements Application {

    private UserController userController;
    private SessionController sessionController;
    private PackageController packageController;
    private CardRepository cardRepository;

    public MTCGApp() {
        UserRepository userRepository = new UserDataBaseRepository();
        SessionRepository sessionRepository = new SessionDataBaseRepository();
        PackageRepository packageRepository = new PackageDataBaseRepository();
        CardRepository cardRepository = new CardDataBaseRepository();

        this.userController = new UserController(userRepository);
        this.sessionController = new SessionController(userRepository, sessionRepository);
        this.packageController = new PackageController(sessionRepository, packageRepository, cardRepository);
    }

    @Override
    public Response handle(Request request) {
        if (request.getPath().startsWith("/users")) {
            return userController.handle(request);
        }

        if (request.getPath().startsWith("/sessions")) {
            return sessionController.handle(request);
        }

        if (request.getPath().startsWith("/packages")) {
            return packageController.handle(request);
        }

        Response response = new Response();
        response.setStatusCode(StatusCode.NOT_FOUND);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.NOT_FOUND.message);

        return response;
    }
}
