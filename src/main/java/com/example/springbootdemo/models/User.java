package com.example.springbootdemo.models;

import com.example.springbootdemo.structs.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "users")
public class User
{
    @Id
    @Indexed(unique = true)
    private long id;

    @Valid
    @NotNull(message = "Personal Info cannot be null!")
    private StructPersonalInfo personalInfo;

    @Valid
    @NotNull(message = "Education cannot be null!")
    private StructEducation education;

    @Valid
    @NotNull(message = "Employment cannot be null!")
    private StructEmployment employment;

    @Valid
    @NotNull(message = "Contact info cannot be null!")
    private StructContactInfo contactInfo;

    @Valid
    @NotNull(message = "Skills cannot be null!")
    private ArrayList<String> skills;

    public String toJson()
    {
        ObjectMapper objectMapper = new ObjectMapper();

        try
        {
            return objectMapper.writeValueAsString(this);
        }
        catch (JsonProcessingException e)
        {
            return "{\"error\": \"Failed to create response message\"}";
        }
    }
}
