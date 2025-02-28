package com.example.springbootdemo.structs;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@JsonSerialize
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "StructPersonalInfo"
        , description = "Part of user entity, which is containing information about its personal information (cannot be used separately!)"
        , example = "{\n" +
        "  \"firstName\": \"John\",\n" +
        "  \"lastName\": \"Doe\",\n" +
        "  \"email\": \"john.doe@example.com\",\n" +
        "  \"dateOfBirth\": \"1990-03-22\",\n" +
        "  \"gender\": \"Male\"\n" +
        "}")
public class StructPersonalInfo
{
    public StructPersonalInfo(StructPersonalInfo structPersonalInfo)
    {
        this.firstName = structPersonalInfo.firstName;
        this.lastName = structPersonalInfo.lastName;
        this.email = structPersonalInfo.email;
        this.dateOfBirth = structPersonalInfo.dateOfBirth;
        this.gender = structPersonalInfo.gender;
    }

    @NotNull(message = "First name cannot be null!")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters!")
    @Schema(description = "User's first name", example = "John")
    private String firstName;

    @NotNull(message = "Last name cannot be null!")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters!")
    @Schema(description = "User's last name", example = "Doe")
    private String lastName;

    @NotNull(message = "Email cannot be null!")
    @Email(message = "Invalid email format!")
    @Schema(description = "User's email", example = "johndoesuperemail@gmail.com")
    private String email;

    @NotNull(message = "Date of birth cannot be null!")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date of birth must be in the format YYYY-MM-DD!")
    @Schema(description = "User's date of birth", example = "1990-03-22")
    private String dateOfBirth;

    @NotNull(message = "Gender cannot be null!")
    @Schema(description = "User's gender", example = "Male")
    private Genders gender;
};
