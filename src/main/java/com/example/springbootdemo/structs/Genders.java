package com.example.springbootdemo.structs;

import com.example.springbootdemo.file_operations.DefaultXmlAttributes;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum Genders
{
    Male ("Male"),
    Female ("Female"),
    Other ("Other");

    private final String genderName;

    Genders(String gender)
    {
        this.genderName = gender;
    }

    public static Genders fromString(String genderName)
    {
        for (Genders gender : Genders.values())
        {
            if (gender.genderName.equalsIgnoreCase(genderName))
            {
                return gender;
            }
        }

        return null;
    }

    @Override
    public String toString()
    {
        return genderName;
    }
}