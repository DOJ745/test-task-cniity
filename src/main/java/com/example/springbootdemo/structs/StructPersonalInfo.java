package com.example.springbootdemo.structs;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.*;
import lombok.*;

@JsonSerialize
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
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
    private String firstName;

    @NotNull(message = "Last name cannot be null!")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters!")
    private String lastName;

    @NotNull(message = "Email cannot be null!")
    @Email(message = "Invalid email format!")
    private String email;

    @NotNull(message = "Date of birth cannot be null!")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date of birth must be in the format YYYY-MM-DD!")
    private String dateOfBirth;

    @NotNull(message = "Gender cannot be null!")
    private Genders gender;
};
