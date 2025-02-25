package com.example.springbootdemo.structs;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StructEducation
{
    public StructEducation(StructEducation currentEducation)
    {
        this.universityName = currentEducation.universityName;
        this.degree = currentEducation.degree;
        this.graduationYear = currentEducation.graduationYear;
    }

    private String universityName;
    private String degree;
    private int graduationYear;
}
