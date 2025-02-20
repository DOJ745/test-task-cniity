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
    private long m_id;

    private StructPersonalInfo m_personalInfo;
    private StructEducation m_education;
    private StructEmployment m_employment;
    private StructContactInfo m_contactInfo;
    private ArrayList<String> m_skills;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StructEducation
    {
        private String s_universityName;
        private String s_degree;
        private int s_graduationYear;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StructEmployment
    {
        private String s_companyName;
        private String s_position;
        private Date s_startDate;
        private String s_endDate;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StructPersonalInfo
    {
        private String s_firstName;
        private String s_lastname;
        private String s_email;
        private Date s_dateOfBirth;
        private Genders s_gender;
    };

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StructContactInfo
    {
        public String s_phoneNumber;
        public StructAddress s_address;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class StructAddress
        {
            public String s_street;
            public String s_city;
            public String s_state;
            public int s_postalCode;
            public String s_country;
        }
    }
}
