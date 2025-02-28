package com.example.springbootdemo.models;

import com.example.springbootdemo.structs.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "User", description = "User entity", example = "{\n" +
        "  \"id\": 1001,\n" +
        "  \"personalInfo\": {\n" +
        "    \"firstName\": \"John\",\n" +
        "    \"lastName\": \"Doe\",\n" +
        "    \"email\": \"john.doe@example.com\",\n" +
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
public class User
{
    @Id
    @Indexed(unique = true)
    private long id;

    @Valid
    @NotNull(message = "Personal Info cannot be null!")
    @Schema(description = "User's personal info struct")
    private StructPersonalInfo personalInfo;

    @Valid
    @NotNull(message = "Education cannot be null!")
    @Schema(description = "User's education struct")
    private StructEducation education;

    @Valid
    @NotNull(message = "Employment cannot be null!")
    @Schema(description = "User's employment struct")
    private StructEmployment employment;

    @Valid
    @NotNull(message = "Contact info cannot be null!")
    @Schema(description = "User's contact info struct")
    private StructContactInfo contactInfo;

    @Valid
    @NotNull(message = "Skills cannot be null!")
    @Schema(description = "User's skills struct")
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
