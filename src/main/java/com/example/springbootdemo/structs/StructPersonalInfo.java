package com.example.springbootdemo.structs;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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

    private String firstName;
    private String lastName;
    private String email;
    private String dateOfBirth;
    private Genders gender;
};
