package com.example.springbootdemo.structs;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@JsonSerialize
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

    @NotNull(message = "University name cannot be null!")
    @Size(min = 2, max = 120, message = "University name  be between 2 and 120 characters!")
    private String universityName;

    @NotNull(message = "Degree cannot be null!")
    @Size(min = 3, max = 100, message = "Degree  be between 3 and 100 characters!")
    private String degree;

    @NotNull(message = "Graduation year cannot be null!")
    private int graduationYear;
}
