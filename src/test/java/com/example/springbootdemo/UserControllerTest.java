package com.example.springbootdemo;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import com.example.springbootdemo.controllers.UserController;
import com.example.springbootdemo.messages.ResponseMsg;
import com.example.springbootdemo.models.User;
import com.example.springbootdemo.services.UserService;
import com.example.springbootdemo.structs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest
{

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private WebTestClient webTestClient;


    @BeforeEach
    void setup()
    {
        webTestClient = WebTestClient.bindToController(userController).build();
    }


    // ------------------------- Тесты для GET /api/users -------------------------

    @Test
    void getAllUsers_WhenUsersExist_ReturnFluxOfUsers()
    {
        // Arrange
        final int size = 11;
        List<User> users = IntStream.rangeClosed(1, size)
                .mapToObj(UserControllerTest::createValidUser)
                .toList();

        when(userService.getAllUsers()).thenReturn(Flux.fromIterable(users));

        // Act & Assert
        webTestClient.get()
                .uri("/api/users")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .hasSize(size)
                .value(list -> list.forEach(user ->
                {
                    assertNotNull(user.getContactInfo());
                    assertNotNull(user.getEducation());
                    assertNotNull(user.getEmployment());
                    assertNotNull(user.getSkills());
                }));
    }

    // ------------------------- Тесты для GET /api/users/{id} -------------------------

    @Test
    void getUserById_WhenIdIsValid_ReturnUser()
    {
        long validId = 1L;
        User mockUser = createValidUser(validId);

        when(userService.getUserById(validId))
                .thenReturn(Mono.just(ResponseEntity.ok(ResponseMsg.createMsg(MsgTypes.MSG_SUCCESS
                        , "User has been found"
                        , mockUser))));

        webTestClient.get()
                .uri("/api/users/{id}", validId)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void getUserById_WhenIdIsInvalid_ReturnBadRequest()
    {
        long invalidId = -1L;

        when(userService.getUserById(invalidId))
                .thenReturn(Mono.just(ResponseEntity.badRequest().body("Invalid ID")));

        webTestClient.get()
                .uri("/api/users/{id}", invalidId)
                .exchange()
                .expectStatus().isBadRequest();
    }

    // ------------------------- Тесты для POST /api/users -------------------------
    @Test
    void createUser_WhenUserIsValid_ReturnCreatedUser()
    {
        User validUser = createValidUser(101L);

        when(userService.createUser(validUser))
                .thenReturn(Mono.just(ResponseEntity.ok(ResponseMsg.createMsg(MsgTypes.MSG_SUCCESS
                        , "User has been created"
                        , validUser))));

        webTestClient.post()
                .uri("/api/users")
                .bodyValue(ResponseMsg.createMsg(MsgTypes.MSG_SUCCESS
                        , "User has been created"
                        , validUser))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void createUser_WhenUserIsInvalid_ReturnBadRequest()
    {
        User invalidUser = createInvalidUser(0L);

        when(userService.createUser(invalidUser))
                .thenReturn(Mono.just(ResponseEntity.badRequest().body(ResponseMsg.createMsg(MsgTypes.MSG_BAD_REQUEST
                        , "User id must be greater than 0"
                        , null))));

        webTestClient.post()
                .uri("/api/users")
                .bodyValue(ResponseMsg.createMsg(MsgTypes.MSG_BAD_REQUEST
                        , "User id must be greater than 0"
                        , null))
                .exchange()
                .expectStatus().isBadRequest();
    }

    // ------------------------- Тесты для PUT /api/users/{id} -------------------------
    @Test
    void updateUser_WhenDataIsValid_ReturnUpdatedUser()
    {
        long id = 1L;
        User updatedUser = createValidUser(id);

        when(userService.updateUser(eq(id), any(User.class)))
                .thenReturn(Mono.just(ResponseEntity.ok("Updated")));

        webTestClient.put()
                .uri("/api/users/{id}", id)
                .bodyValue(updatedUser)
                .exchange()
                .expectStatus().isOk();
    }

    // ------------------------- Тесты для DELETE /api/users/{id} -------------------------
    @Test
    void deleteUser_WhenIdIsValid_ReturnSuccess()
    {
        long validId = 1L;
        when(userService.deleteUser(validId))
                .thenReturn(Mono.just(ResponseEntity.ok("Deleted")));

        webTestClient.delete()
                .uri("/api/users/{id}", validId)
                .exchange()
                .expectStatus().isOk();
    }

    // ------------------------- Тесты для обработки исключений -------------------------
    @Test
    void handleValidationExceptions_WhenValidationFails_ReturnBadRequest()
    {
        // MethodArgumentNotValidException
        User invalidUser = createInvalidUser(1L);

        webTestClient.post()
                .uri("/api/users")
                .bodyValue(invalidUser)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .value(msg -> assertThat(msg).contains("Validation error"));
    }

    private static User createValidUser(long id)
    {
        StructPersonalInfo personalInfo = new StructPersonalInfo(
                "John",
                "Doe",
                "john.doe@example.com",
                "1990-03-22",
                Genders.Male
        );

        StructEducation education = new StructEducation(
                "Massachusetts Institute of Technology",
                "Master's in Computer Science",
                2020
        );

        // Employment
        StructEmployment employment = new StructEmployment(
                "Google",
                "Software Engineer",
                "2016-01-15",
                "Present"
        );

        // Address
        StructContactInfo.StructAddress address = new StructContactInfo.StructAddress(
                "1600 Amphitheatre Parkway",
                "Mountain View",
                "CA",
                94043,
                "USA"
        );

        // ContactInfo
        StructContactInfo contactInfo = new StructContactInfo();
        contactInfo.setPhoneNumber("+16502530000");
        contactInfo.setAddress(address);

        // Skills
        ArrayList<String> skills = new ArrayList<>();
        skills.add("Java");
        skills.add("Spring");
        skills.add("AWS");

        return new User(
                id,
                personalInfo,
                education,
                employment,
                contactInfo,
                skills
        );
    }

    private static User createInvalidUser(long id)
    {

        StructPersonalInfo personalInfo = new StructPersonalInfo(
                "A",                    // size(min=2)
                "Doe",
                "invalid-email",
                "1990-03-22",
                Genders.Male
        );

        StructEducation education = new StructEducation(
                "MIT",
                "CS",                    // size(min=3)
                0                               // graduationYear <= 0
        );

        StructEmployment employment = new StructEmployment(
                "G",                // size(min=2)
                "Engineer",
                "2016-01-15",
                "Present"
        );

        StructContactInfo.StructAddress address = new StructContactInfo.StructAddress(
                "S", // size(min=2)
                "Mountain View",
                "CA",
                94043,
                "USA"
        );

        StructContactInfo contactInfo = new StructContactInfo();
        contactInfo.setPhoneNumber("invalid_phone");            // @Pattern
        contactInfo.setAddress(address);

        ArrayList<String> skills = new ArrayList<>();           // @NotNull

        return new User(
                id,                                         // id <= 0
                personalInfo,
                education,
                employment,
                contactInfo,
                skills
        );
    }
}
