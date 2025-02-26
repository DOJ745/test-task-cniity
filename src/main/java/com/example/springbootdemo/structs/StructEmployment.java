package com.example.springbootdemo.structs;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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

    private String companyName;
    private String position;
    private String startDate;
    private String endDate;
}
