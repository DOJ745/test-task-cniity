package com.example.springbootdemo.structs;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
public class StructContactInfo
{
    @NotNull(message = "Phone number cannot be null!")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number format!")
    public String phoneNumber;

    @NotNull(message = "Address cannot be null!")
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
        public String street;

        @NotNull(message = "City cannot be null!")
        @Size(min = 2, max = 50, message = "City must be between 2 and 50 characters!")
        public String city;

        @NotNull(message = "State cannot be null!")
        @Size(min = 2, max = 50, message = "State must be between 2 and 50 characters!")
        public String state;

        @NotNull(message = "Postal code cannot be null!")
        public int postalCode;

        @NotNull(message = "Country cannot be null!")
        @Size(min = 2, max = 50, message = "Country must be between 2 and 50 characters!")
        public String country;
    }
}
