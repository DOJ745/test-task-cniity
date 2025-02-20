package models;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import structs.*;

import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
@Document(collection = "users")
public class User {

    @Id
    private long m_id;

    private StructPersonalInfo m_personalInfo;
    private StructEducation m_education;
    private StructEmployment m_employment;
    private StructContactInfo m_contactInfo;
    private ArrayList<String> m_skills;

    @Getter
    @Setter
    public static class StructEducation {
        private String s_universityName;
        private String s_degree;
        private int s_graduationYear;
    }

    @Getter
    @Setter
    public static class StructEmployment {
        private String s_companyName;
        private String s_position;
        private Date s_startDate;
        private Date s_endDate;
    }

    @Getter
    @Setter
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
    public static class StructContactInfo {
        public String s_phoneNumber;
        public StructAddress s_address;

        @Getter
        @Setter
        public static class StructAddress {
            public String s_street;
            public String s_city;
            public String s_state;
            public int s_postalCode;
            public String s_country;
        }
    }
}
