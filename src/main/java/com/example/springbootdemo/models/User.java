package com.example.springbootdemo.models;

import com.example.springbootdemo.structs.*;
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

    private StructPersonalInfo personalInfo;
    private StructEducation education;
    private StructEmployment employment;
    private StructContactInfo contactInfo;
    private ArrayList<String> skills;

}
