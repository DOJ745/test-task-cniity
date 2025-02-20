package com.example.springbootdemo.models;

import com.example.springbootdemo.structs.Genders;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User
{

    @Id
    private long id;

    private StructPersonalInfo personalInfo;
    private StructEducation education;
    private StructEmployment employment;
    private StructContactInfo contactInfo;
    private ArrayList<String> skills;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StructEducation
    {
        private String universityName;
        private String degree;
        private int graduationYear;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StructEmployment
    {
        private String companyName;
        private String position;
        private Date startDate;
        private String endDate;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StructPersonalInfo
    {
        private String firstName;
        private String lastName;
        private String email;
        private Date dateOfBirth;
        private Genders gender;
    };

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StructContactInfo
    {
        public String phoneNumber;
        public StructAddress address;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class StructAddress
        {
            public String street;
            public String city;
            public String state;
            public int postalCode;
            public String country;
        }
    }
}
