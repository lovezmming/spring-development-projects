package com.shev.itembank.common.datasource.Enum;

public enum DataSourceEnum
{

    MASTER("master"),

    SLAVE("slave");

    private String name;

    public String getName()
    {
        return name;
    }

    DataSourceEnum(String name)
    {
        this.name = name;
    }

}
