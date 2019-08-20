package com.shev.compilation.common.Enum;

import lombok.Getter;

@Getter
public enum PartnerTypeEnum
{
    /** BASE */
    BASE("BASE", "BASE"),

    /** QINIU RECORD */
    RECORD("QINIU", "RECORD");

    private String name;

    private String value;

    PartnerTypeEnum(String name, String value)
    {
        this.name = name;
        this.value = value;
    }
}
