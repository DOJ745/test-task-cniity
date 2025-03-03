package com.example.springbootdemo.controllers;

import com.example.springbootdemo.messages.ResponseMsg;
import com.example.springbootdemo.models.User;
import com.example.springbootdemo.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Tag(name = "User Controller"
        , description = "Operations about users, which is represented by 'User' model")
@RestController
@RequestMapping("api/users")
public class UserController
{
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    //@Autowired(required = true)
    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @Operation(
            summary = "Get all users"
            , description = "Returns a list of all users from MongoDB database"
            , responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Users have been read",
                    content = @Content(schema = @Schema(implementation = User.class))
            ),
    }
    )
    @GetMapping
    public Flux<User> getAllUsers()
    {
        logger.info("Received GET request (all users)");

        return userService.getAllUsers();
    }

    @Operation(
            summary = "Get user by ID"
            , description = "Retrieves a user by their ID from MongoDB database"
            , responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User has been found",
                            content = @Content(schema = @Schema(
                                    implementation = ResponseMsg.class,
                                    example = "{\"msgType\":\"MSG_SUCCESS\",\"msg\":\"User has been found\",\"data\":{\"id\":1001,\"personalInfo\":{...}}}"
                            ))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "User not found",
                            content = @Content(schema = @Schema(
                                    implementation = ResponseMsg.class,
                                    example = "{\"msgType\":\"MSG_BAD_REQUEST\",\"msg\":\"User not found\",\"data\": null}"
                            ))
                    ),
                    @ApiResponse(
                    responseCode = "400",
                    description = "User ID must be greater that zero",
                            content = @Content(schema = @Schema(
                                    implementation = ResponseMsg.class,
                                    example = "{\"msgType\":\"MSG_BAD_REQUEST\",\"msg\":\"User ID must be greater that zero\",\"data\": null}"
                            ))
            )
            }
    )
    @GetMapping("/{id}")
    public Mono<ResponseEntity<String>> getUserById(
            @Parameter(
                    description = "User ID"
                    , example = "1"
                    , required = true)

            @PathVariable long id)
    {
        logger.info("Received GET request");

        return userService.getUserById(id);
    }

    @Operation(
            summary = "Get all users from .xml file, which is stored inside of a container."
            , description = "Returns a list of all users from .xml file (example data)"
    )
    @GetMapping("/xml")
    public ArrayList<User> getUsersFromXml()
    {
        return userService.getUsersFromXml();
    }

    @Operation(
            summary = "Add all users from .xml file"
            , description = "Add a list of all users from .xml file into MongoDB database"
    )
    @PostMapping("/xml")
    public Mono<ResponseEntity<String>> addUsersFromXml()
    {
       return userService.addUsersFromXml();
    }

    @Operation(
            summary = "Add new user"
            , description = "Adds a user by the data from JSON in request's body"
            , responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User has been added",
                    content = @Content(schema = @Schema(
                            implementation = ResponseMsg.class,
                            example = "{\"msgType\":\"MSG_SUCCESS\",\"msg\":\"User has been added\",\"data\":{\"id\":1001,\"personalInfo\":{...}}}"
                    ))

            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "User ID must be greater that zero",
                    content = @Content(schema = @Schema(
                            implementation = ResponseMsg.class,
                            example = "{\"msgType\":\"MSG_BAD_REQUEST\",\"msg\":\"User ID must be greater that zero\",\"data\": null}"
                    ))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation errors",
                    content = @Content(schema = @Schema(
                            example = "First name must be between 2 and 50 characters!\nEmail cannot be null!\nDegree must be between 3 and 100 characters!"
                    ))
            )
    }
    )
    @PostMapping
    public Mono<ResponseEntity<String>> createUser(
            @RequestBody
            @Valid
            @Parameter(
                    description = "User schema"
                    , example = ("{\n" +
                    "  \"id\": 1001,\n" +
                    "  \"personalInfo\": {\n" +
                    "    \"firstName\": \"John123\",\n" +
                    "    \"lastName\": \"Doe123\",\n" +
                    "    \"email\": \"john.doe123@example.com\",\n" +
                    "    \"dateOfBirth\": \"1990-03-22\",\n" +
                    "    \"gender\": \"Male\"\n" +
                    "  },\n" +
                    "  \"education\": {\n" +
                    "    \"universityName\": \"MIT\",\n" +
                    "    \"degree\": \"Computer Science\",\n" +
                    "    \"graduationYear\": 2015\n" +
                    "  },\n" +
                    "  \"employment\": {\n" +
                    "    \"companyName\": \"Google\",\n" +
                    "    \"position\": \"Software Engineer\",\n" +
                    "    \"startDate\": \"2016-01-15\",\n" +
                    "    \"endDate\": \"Present\"\n" +
                    "  },\n" +
                    "  \"contactInfo\": {\n" +
                    "    \"phoneNumber\": \"+16502530000\",\n" +
                    "    \"address\": {\n" +
                    "      \"street\": \"1600 Amphitheatre Pkwy\",\n" +
                    "      \"city\": \"Mountain View\",\n" +
                    "      \"state\": \"CA\",\n" +
                    "      \"postalCode\": 94043,\n" +
                    "      \"country\": \"USA\"\n" +
                    "    }\n" +
                    "  },\n" +
                    "  \"skills\": [\"Java\", \"Spring\", \"AWS\"]\n" +
                    "}")
                    , required = true)
            User user)
    {
        logger.info("Received POST request to create user: {}", user.toString());

        return userService.createUser(user);
    }

    @Operation(
            summary = "Update existing user"
            , description = "Updates a user by the data from JSON in request's body and its ID in MongoDB database"
            , responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User has been updated",
                    content = @Content(schema = @Schema(
                            implementation = ResponseMsg.class,
                            example = "{\"msgType\":\"MSG_SUCCESS\",\"msg\":\"User has been updated\",\"data\":{\"id\":1001,\"personalInfo\":{...}}}"
                    ))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "User ID must be greater that zero",
                    content = @Content(schema = @Schema(
                            implementation = ResponseMsg.class,
                            example = "{\"msgType\":\"MSG_BAD_REQUEST\",\"msg\":\"User ID must be greater that zero\",\"data\": null}"
                    ))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation errors",
                    content = @Content(schema = @Schema(
                            example = "First name must be between 2 and 50 characters!\nEmail cannot be null!\nDegree must be between 3 and 100 characters!"
                    ))
            )
    })
    @PutMapping("/{id}")
    public Mono<ResponseEntity<String>> updateUser(
            @RequestBody
            @Valid
            @Parameter(
                    description = "User schema"
                    , example = ("{\n" +
                    "  \"personalInfo\": {\n" +
                    "    \"firstName\": \"John123\",\n" +
                    "    \"lastName\": \"Doe123\",\n" +
                    "    \"email\": \"john.doe123@example.com\",\n" +
                    "    \"dateOfBirth\": \"1990-03-22\",\n" +
                    "    \"gender\": \"Male\"\n" +
                    "  },\n" +
                    "  \"education\": {\n" +
                    "    \"universityName\": \"MIT\",\n" +
                    "    \"degree\": \"Computer Science\",\n" +
                    "    \"graduationYear\": 2015\n" +
                    "  },\n" +
                    "  \"employment\": {\n" +
                    "    \"companyName\": \"Google\",\n" +
                    "    \"position\": \"Software Engineer\",\n" +
                    "    \"startDate\": \"2016-01-15\",\n" +
                    "    \"endDate\": \"Present\"\n" +
                    "  },\n" +
                    "  \"contactInfo\": {\n" +
                    "    \"phoneNumber\": \"+16502530000\",\n" +
                    "    \"address\": {\n" +
                    "      \"street\": \"1600 Amphitheatre Pkwy\",\n" +
                    "      \"city\": \"Mountain View\",\n" +
                    "      \"state\": \"CA\",\n" +
                    "      \"postalCode\": 94043,\n" +
                    "      \"country\": \"USA\"\n" +
                    "    }\n" +
                    "  },\n" +
                    "  \"skills\": [\"Java\", \"Spring\", \"AWS\"]\n" +
                    "}")
                    , required = true)
            User updateUser
            , @Parameter(
                    description = "User ID"
                    , example = "1"
                    , required = true)
            @PathVariable long id)
    {
        logger.info("Received PUT request to update user with id: {}\nNew data {}", id, updateUser.toString());

        return userService.updateUser(id, updateUser);
    }

    @Operation(
            summary = "Delete existing user"
            , description = "Deletes a user by its ID from MongoDB database"
            , responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User has been deleted",
                    content = @Content(schema = @Schema(
                            implementation = ResponseMsg.class,
                            example = "{\"msgType\":\"MSG_SUCCESS\",\"msg\":\"User has been deleted\",\"data\":null}"
                    ))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "User ID must be greater that zero",
                    content = @Content(schema = @Schema(
                            implementation = ResponseMsg.class,
                            example = "{\"msgType\":\"MSG_BAD_REQUEST\",\"msg\":\"User ID must be greater that zero\",\"data\": null}"
                    ))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(
                            implementation = ResponseMsg.class,
                            example = "{\"msgType\":\"MSG_INTERNAL_SERVER_ERROR\",\"msg\":\"Internal server error\",\"data\": null}"
                    ))
            )
    })
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> deleteUser(
            @PathVariable
            @Parameter(
            description = "User ID"
            , example = "1"
            , required = true) long id)
    {
        logger.info("Received DELETE request to update user with id: {}", id);

        return userService.deleteUser(id);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex)
    {

        StringBuilder errors = new StringBuilder();

        ex.getBindingResult().getAllErrors().forEach(error ->
                errors.append(error.getDefaultMessage()).append("\n"));

        return (ResponseEntity.badRequest().body(errors.toString()));
    }
}
