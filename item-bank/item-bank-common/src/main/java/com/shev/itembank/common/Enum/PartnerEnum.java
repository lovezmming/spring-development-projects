package com.shev.itembank.common.Enum;

public enum PartnerEnum
{

    QINIU("QINIU", "STORAGE");

    private String name;

    private String type;

    public String getName()
    {
        return name;
    }

    public String getType()
    {
        return type;
    }

    PartnerEnum(String name, String type)
    {
        this.name = name;
        this.type = type;
    }

}
