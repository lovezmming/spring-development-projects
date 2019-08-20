package com.shev.compilation.common.datasource.dynamic.Enum;

import java.util.ArrayList;
import java.util.List;

public enum DataSourceEnum
{

    ACCOUNT_MASTER("account", "master", "accountMasterDatasource"),
    ACCOUNT_SLAVE1("account", "slave1", "accountSlave1Datasource"),
    ACCOUNT_SLAVE2("account", "slave2", "accountSlave2Datasource"),
    EDUEMTA_MASTER1("edumeta", "master1", "edumetaMaster1Datasource"),
    EDUMETA_SLAVE1("edumeta", "slave1", "edumetaSlave1Datasource"),
    EDUEMTA_MASTER2("edumeta", "master2", "edumetaMaster2Datasource"),
    EDUMETA_SLAVE2("edumeta", "slave2", "edumetaSlave2Datasource"),
    EDUEMTA_MASTER3("edumeta", "master3", "edumetaMaster3Datasource"),
    EDUMETA_SLAVE3("edumeta", "slave3", "edumetaSlave3Datasource"),
    EDUEMTA_MASTER4("edumeta", "master4", "edumetaMaster4Datasource"),
    EDUMETA_SLAVE4("edumeta", "slave4", "edumetaSlave4Datasource");

    private String name;

    private String value;

    private String keyName;

    public String getName()
    {
        return name;
    }

    public String getValue()
    {
        return value;
    }

    public String getKeyName()
    {
        return keyName;
    }

    DataSourceEnum(String name, String value, String keyName)
    {
        this.name = name;
        this.value = value;
        this.keyName = keyName;
    }

    public static DataSourceEnum getEnumByKeyName(String keyName)
    {
        for (DataSourceEnum enums : DataSourceEnum.values())
        {
            if (keyName.equals(enums.keyName))
            {
                return enums;
            }
        }
        return null;
    }

    public static List<DataSourceEnum> getEnumsByName(String name, String type)
    {
        List<DataSourceEnum> currEnums = new ArrayList<>();
        for (DataSourceEnum enums : DataSourceEnum.values())
        {
            if (name.equals(enums.name))
            {
                if (type != null)
                {
                    if (enums.getValue().contains(type))
                    {
                        currEnums.add(enums);
                    }
                } else
                {
                    currEnums.add(enums);
                }
            }
        }
        return currEnums;
    }
}
