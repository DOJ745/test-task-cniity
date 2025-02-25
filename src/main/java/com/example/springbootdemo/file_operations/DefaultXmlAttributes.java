package com.example.springbootdemo.file_operations;

public enum DefaultXmlAttributes
{
    ATTR_ID("id");

    private final String attrName;

    DefaultXmlAttributes(String attrName)
    {
        this.attrName = attrName;
    }

    public static DefaultXmlAttributes fromString(String attrName)
    {
        for (DefaultXmlAttributes attr : DefaultXmlAttributes.values())
        {
            if (attr.attrName.equalsIgnoreCase(attrName))
            {
                return attr;
            }
        }

        return null;
    }
}
