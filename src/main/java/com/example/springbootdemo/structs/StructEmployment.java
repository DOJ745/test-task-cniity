package com.example.springbootdemo.structs;

import com.example.springbootdemo.structs.validators.ValidEndDate;
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
    private String companyName;

    @NotNull(message = "Position cannot be null!")
    @Size(min = 2, max = 50, message = "Position must be between 2 and 50 characters!")
    private String position;

    @NotNull(message = "Start date cannot be null!")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Start date must be in the format YYYY-MM-DD!")
    private String startDate;

    @ValidEndDate
    private String endDate;
}
