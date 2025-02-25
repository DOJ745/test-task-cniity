package com.example.springbootdemo.file_operations;

public enum DefaultXmlTags
{
    TAG_USER("user"),
    TAG_PERSONAL_INFO("personalinfo"),
    TAG_CONTACT_INFO("contactinfo"),
    TAG_ADDRESS("address"),
    TAG_EMPLOYMENT("employment"),
    TAG_EDUCATION("education"),
    TAG_SKILLS("skills"),
    TAG_FIRST_NAME("firstname"),
    TAG_LAST_NAME("lastname"),
    TAG_EMAIL("email"),
    TAG_DATE_OF_BIRTH("dateofbirth"),
    TAG_GENDER("gender"),
    TAG_PHONE_NUMBER("phonenumber"),
    TAG_STREET("street"),
    TAG_CITY("city"),
    TAG_STATE("state"),
    TAG_POSTAL_CODE("postalcode"),
    TAG_COUNTRY("country"),
    TAG_COMPANY_NAME("companyname"),
    TAG_POSITION("position"),
    TAG_START_DATE("startdate"),
    TAG_END_DATE("enddate"),
    TAG_UNIVERSITY_NAME("universityname"),
    TAG_DEGREE("degree"),
    TAG_GRADUATION_YEAR("graduationyear"),
    TAG_SKILL("skill");

    private final String tagName;

    DefaultXmlTags(String tagName)
    {
        this.tagName = tagName;
    }

    public static DefaultXmlTags fromString(String tagName)
    {
        for (DefaultXmlTags tag : DefaultXmlTags.values())
        {
            if (tag.tagName.equalsIgnoreCase(tagName))
            {
                return tag;
            }
        }

        return null;
    }
}
