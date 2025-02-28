package com.example.springbootdemo.structs;

import com.example.springbootdemo.structs.validators.ValidEndDate;
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
@Schema(name = "StructEmployment"
        , description = "Part of user entity, which is containing information about its employment (cannot be used separately!)"
        , example = "{\n" +
        "  \"companyName\": \"Microsoft\",\n" +
        "  \"position\": \"AI Specialist\",\n" +
        "  \"startDate\": \"2021-05-15\",\n" +
        "  \"endDate\": \"Present\"\n" +
        "}")
public class StructEmployment
{
    public StructEmployment(StructEmployment employment)
    {
        this.companyName = employment.companyName;
        this.endDate = employment.endDate;
        this.startDate = employment.startDate;
        this.position = employment.position;
    }

    @NotNull(message = "Company name cannot be null!")
    @Size(min = 2, max = 50, message = "Company name must be between 2 and 50 characters!")
    @Schema(description = "User's company name", example = "Super IT corp LTD")
    private String companyName;

    @NotNull(message = "Position cannot be null!")
    @Size(min = 2, max = 50, message = "Position must be between 2 and 50 characters!")
    @Schema(description = "User's position", example = "Super Senior Software Engineer")
    private String position;

    @NotNull(message = "Start date cannot be null!")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Start date must be in the format YYYY-MM-DD!")
    @Schema(description = "User's start date", example = "2018-05-29")
    private String startDate;

    @ValidEndDate
    @Schema(description = "User's end date", example = "Present")
    private String endDate;
}
