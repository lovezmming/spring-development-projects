package com.shev.itembank.common.Enum;

public enum ConstantEnum
{

    YES("1", "默认是"),

    NO("0", "默认否"),

    DEFAULT_TENANT_ID("000001", "默认商户id"),

    DEFAULT_USER_ID("10000010000030000000000000000000", "默认用户id"),

    IMAGE_PLACE_HOLDER("$$path$$", "默认路径配置"),

    CHAPTER("CHAPTER", "章"),

    SECTION("SECTION", "节"),

    SUBSECTION("SUBSECTION", "下一节"),

    CITY_TYPE("CITY", "城市"),

    FIRST_TERM("01", "第一学期"),

    SECOND_TERM("02", "第二学期"),

    THIRD_TERM("03", "第三学期"),

    FIRST_HOLIDAY("10", "寒假"),

    SECOND_HOLIDAY("20", "暑假"),

    FINAL_HOLIDAY("30", "春假");

    private String value;

    private String name;

    public String getValue()
    {
        return value;
    }

    public String getName()
    {
        return name;
    }

    ConstantEnum(String value, String name)
    {
        this.value = value;
        this.name = name;
    }

    public static String getNameByValue(String value)
    {
        for (ConstantEnum enums : ConstantEnum.values())
        {
            if (value.equals(enums.getValue()))
            {
                return enums.getName();
            }
        }
        return null;
    }
}
