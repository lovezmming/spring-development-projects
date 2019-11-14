package com.shev.itembank.common.Enum;

public enum TenantAttributeEnum
{

    IS_MULTIPLE_LOGIN("1", "MULTIPLELOGIN"),

    ACCESS_PUBLIC_DB("2", "ACCESS_PUBLIC_DB"),

    ADAPT_TEACHER_DEPT("3", "ADAPT_TEACHER_DEPT"),

    TEACHER_DUTY_MAXNUM("4", "TEACHER_DUTY_MAXNUM"),

    METHOD_INVOKE_LIMIT("5", "METHOD_INVOKE_LIMIT"),

    EXERCISE_FILTER("6", "EXERCISE_FILTER"),

    EXERCISE_SEARCH("7", "EXERCISE_SEARCH"),

    EXERCISE_SEARCH_TYPE_PRIVATE("8", "PRIVATE"),

    EXERCISE_SEARCH_TYPE_PUBLIC("9", "PUBLIC"),

    EXERCISE_SEARCH_TYPE_PUBLIC_PRIVATE("10", "PUBLIC_PRIVATE");

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

    TenantAttributeEnum(String key, String value)
    {
        this.key = key;
        this.value = value;
    }

}
