package org.example.application.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.application.user.model.Session;
import org.example.application.user.model.User;
import org.example.application.user.repository.SessionRepository;
import org.example.application.user.repository.UserRepository;
import org.example.application.user.controller.UserController;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.StatusCode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.engine.support.discovery.SelectorResolver;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    UserRepository userRepository;
    @Mock
    SessionRepository sessionRepository;
    UserController userController;

    @Test
    public void testReadUserPass(){
        //Arrange
        userController = new UserController(userRepository, sessionRepository);

        String username = "test";
        String token = "Basic test-mtcgToken";
        User user = new User(username, "test", 0);
        Session session = new Session(username, token);

        when(sessionRepository.findByToken(token)).thenReturn(session);
        when(userRepository.findByUsername(username)).thenReturn(user);

        Request request = new Request();
        request.setMethod("GET");
        request.setPath("/users/" +username);
        request.setAuthorization(token);
        request.setContentLength(0);
        request.setContent("");

        ObjectMapper objectMapper = new ObjectMapper();

        //Act
        Response response = userController.handle(request);

        //Assert
        assertEquals(StatusCode.OK.code, response.getStatus());
        try {
            assertEquals(response.getContent(), objectMapper.writeValueAsString(user));
        } catch (JsonProcessingException e) {
            fail(e);
        }
    }

    @Test
    public void testReadUserFail(){
        //Arrange
        userController = new UserController(userRepository, sessionRepository);

        String username = "test";
        String token = "Basic test-mtcgToken";
        User user = new User(username, "test", 0);
        Session session = new Session(username, token);

        when(sessionRepository.findByToken(token)).thenReturn(session);

        Request request = new Request();
        request.setMethod("GET");
        request.setPath("/users/notTest");
        request.setAuthorization(token);
        request.setContentLength(0);
        request.setContent("");

        //Act
        Response response = userController.handle(request);

        //Assert
        assertEquals(StatusCode.UNAUTHORIZED.code, response.getStatus());
        assertEquals(response.getContent(), "Access token is missing or invalid");
    }

    @Test
    public void testCreate(){
        //Arrange
        userController = new UserController(userRepository, sessionRepository);

        String username = "test";
        String password = "testpw";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        when(userRepository.save(user)).thenReturn(user);

        Request request = new Request();
        request.setMethod("POST");
        request.setPath("/users");
        request.setContent("{\"Username\":\"kienboec\", \"Password\":\"daniel\"}");
        request.setContentLength(request.getContent().length());

        ObjectMapper objectMapper = new ObjectMapper();

        //Act
        Response response = userController.handle(request);

        //Assert
        assertEquals(StatusCode.CREATED.code, response.getStatus());
        try {
            assertEquals(response.getContent(), objectMapper.writeValueAsString(user));
        } catch (JsonProcessingException e) {
            fail(e);
        }
    }
}