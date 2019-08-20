package com.shev.compilation.common.datasource.Enum;

import java.util.ArrayList;
import java.util.List;

public enum DataSourceName
{
    ACCOUNT_UPDATE("account", "master"),
    ACCOUNT_QUERY("account", "slave"),
    EDUEMTA_UPDATE("edumeta", "master"),
    EDUMETA_QUERY("edumeta", "slave");

    private String name;

    private String value;

    public String getName()
    {
        return name;
    }

    public String getValue()
    {
        return value;
    }

    DataSourceName(String name, String value)
    {
        this.name = name;
        this.value = value;
    }

    public static List<DataSourceName> getEnums(String dataType)
    {
        List<DataSourceName> dataTypeEnums = new ArrayList<>();
        for (DataSourceName enums : DataSourceName.values())
        {
            if (dataType.equals(enums.getValue()))
            {
                dataTypeEnums.add(enums);
            }
        }
        return dataTypeEnums;
    }
}
