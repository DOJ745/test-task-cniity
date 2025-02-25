package com.example.springbootdemo.structs;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StructContactInfo
{
    public String phoneNumber;
    public StructAddress address;

    public StructContactInfo(StructContactInfo contactInfo)
    {
        this.phoneNumber = contactInfo.phoneNumber;
        this.address = contactInfo.address;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StructAddress
    {
        public String street;
        public String city;
        public String state;
        public int postalCode;
        public String country;
    }
}
