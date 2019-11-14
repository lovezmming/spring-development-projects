package com.shev.itembank.common.Enum;

public enum GradeEnum
{

    YES("1", "默认是"),

    NO("0", "默认否"),

    DEFAULT_TENANT_ID("000001", "默认商户id"),

    DEFAULT_PATH("$$path$$", "默认路径配置"),

    FIRST_TERM("01", "第一学期"),

    SECOND_TERM("02", "第二学期"),

    THIRD_TERM("03", "第三学期"),

    FIRST_HOLIDAY("10", "寒假"),

    SECOND_HOLIDAY("20", "暑假"),

    FINAL_HOLIDAY("30", "春假");


    private String value;

    private String name;

    public String getName()
    {
        return name;
    }

    public String getValue()
    {
        return value;
    }

    GradeEnum(String name, String value)
    {
        this.name = name;
        this.value = value;
    }

    public static String getNameByValue(String value)
    {
        for (GradeEnum enums : GradeEnum.values())
        {
            if (value.equals(enums.getValue()))
            {
                return enums.getName();
            }
        }
        return null;
    }
}
