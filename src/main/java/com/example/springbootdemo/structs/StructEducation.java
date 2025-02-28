package com.example.springbootdemo.structs;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "StructEducation"
        , description = "Part of user entity, which is containing information about its education (cannot be used separately!)"
        , example = "{\n" +
        "  \"universityName\": \"Harvard University\",\n" +
        "  \"degree\": \"Master's in AI\",\n" +
        "  \"graduationYear\": 2020\n" +
        "}")
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
    @Schema(description = "User's university name", example = "University of Cool Programmers")
    private String universityName;

    @NotNull(message = "Degree cannot be null!")
    @Size(min = 3, max = 100, message = "Degree  be between 3 and 100 characters!")
    @Schema(description = "User's degree", example = "Bachelor's in Computer Science")
    private String degree;

    @NotNull(message = "Graduation year cannot be null!")
    @Schema(description = "User's graduationYear", example = "2001")
    private int graduationYear;
}
