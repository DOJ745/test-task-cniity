package com.example.springbootdemo.structs;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@JsonSerialize
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "StructContactInfo"
        , description = "Part of user entity, which is containing information about its phone number and full address (cannot be used separately!)"
        , example = "{\n" +
        "  \"phoneNumber\": \"+12065550123\",\n" +
        "  \"address\": {\n" +
        "    \"street\": \"12345 130th Pl NE\",\n" +
        "    \"city\": \"Bellevue\",\n" +
        "    \"state\": \"WA\",\n" +
        "    \"postalCode\": 98005,\n" +
        "    \"country\": \"USA\"\n" +
        "  }\n" +
        "}")
public class StructContactInfo
{
    @NotNull(message = "Phone number cannot be null!")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number format!")
    @Schema(description = "User's phone number", example = "+1234567890")
    public String phoneNumber;

    @NotNull(message = "Address cannot be null!")
    @Schema(description = "User's address", example = "{"
            + "\"street\": \"123 Main St\","
            + "\"city\": \"New York\","
            + "\"state\": \"NY\","
            + "\"postalCode\": \"10001\","
            + "\"country\": \"USA\"" +
            "}")
    public StructAddress address;

    public StructContactInfo(StructContactInfo contactInfo)
    {
        this.phoneNumber = contactInfo.phoneNumber;
        this.address = contactInfo.address;
    }

    @JsonSerialize
    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StructAddress
    {
        @NotNull(message = "Street cannot be null!")
        @Size(min = 2, max = 100, message = "Street must be between 2 and 100 characters!")
        @Schema(description = "User's street", example = "123 Main St")
        public String street;

        @NotNull(message = "City cannot be null!")
        @Size(min = 2, max = 50, message = "City must be between 2 and 50 characters!")
        @Schema(description = "User's city", example = "New York")
        public String city;

        @NotNull(message = "State cannot be null!")
        @Size(min = 2, max = 50, message = "State must be between 2 and 50 characters!")
        @Schema(description = "User's state", example = "NY")
        public String state;

        @NotNull(message = "Postal code cannot be null!")
        @Schema(description = "User's postal code", example = "10001")
        public int postalCode;

        @NotNull(message = "Country cannot be null!")
        @Size(min = 2, max = 50, message = "Country must be between 2 and 50 characters!")
        @Schema(description = "User's country", example = "USA")
        public String country;
    }
}
