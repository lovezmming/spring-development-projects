package com.shev.itembank.common.Enum;

public enum EventEnum
{

    DELETE("1", "DELETE"),

    UPDATE("2", "UPDATE"),

    CREATE("3", "CREATE");

    private String key;

    private String value;

    public String getKey()
    {
        return key;
    }

    public String getValue()
    {
        return value;
    }

    EventEnum(String key, String value)
    {
        this.key = key;
        this.value = value;
    }

}
