package com.example.springbootdemo.structs;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum Genders
{
    MALE ("Male"),
    FEMALE ("Female"),
    OTHER ("Other");

    private String gender;

    Genders(String gender)
    {
        this.gender = gender;
    }

    @Override
    public String toString()
    {
        return "Gender{"
                + "gender='"
                + gender
                + '\''
                + '}';
    }
}