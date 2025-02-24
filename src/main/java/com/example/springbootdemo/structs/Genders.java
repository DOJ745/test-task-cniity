package com.example.springbootdemo.structs;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum Genders
{
    Male ("Male"),
    Female ("Female"),
    Other ("Other");

    private String gender;

    Genders(String gender)
    {
        this.gender = gender;
    }

    @Override
    public String toString()
    {
        return gender;
    }
}