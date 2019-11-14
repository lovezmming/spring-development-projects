package com.shev.itembank.common.Enum;

public enum DataServiceEnum
{

    EDUMETA("edumeta", "08"),

    PAPER("paper", "32"),

    EXERCISE("exercise", "31"),

    TASK("task", "63"),

    SYSTEM("system", "01"),

    RESOURCE("resource", "61");

    private String name;

    private String serviceId;

    public String getName()
    {
        return name;
    }

    public String getServiceId()
    {
        return serviceId;
    }

    DataServiceEnum(String name, String serviceId)
    {
        this.name = name;
        this.serviceId = serviceId;
    }

}
