package com.shev.compilation.common.Enum;

import lombok.Getter;

@Getter
public enum CacheKeyEnum
{
    // account
    TOKEN("account", "token:", "token"),

    USER("account", "user:", "用户信息"),

    PERMISSION("account", "permission:", "权限信息"),

    // edumeta
    SUBJECT("edumeta", "subject:", "权限信息"),

    EDUCATIONALSTAGE("edumeta", "educationalstage:", "学段信息"),

    DIFFICULTY("edumeta", "difficulty:", "难度信息"),

    FORMTYPE("edumeta", "formtype:", "结构题型信息"),

    SEMANTICTYPE("edumeta", "semantictype:", "语义题型信息"),

    SIGNIFICANCE("edumeta", "significance:", "正规程度信息"),

    COMPLETENESS("edumeta", "completeness:", "完整程度信息"),

    ERRORMETA("edumeta", "errormeta:", "错误程度信息"),

    CATEGORY("edumeta", "category:", "分类信息"),

    SOURCE("edumeta", "source:", "来源信息"),

    SOURCETYPE("edumeta", "sourcetype:", "来源类型信息"),

    KNOWLEDGEPOINT("edumeta", "knowledgepoint:", "知识点信息"),

    // exercise
    ABILITY("exercise", "ability:", "能力信息"),

    ABILITYTYPE("exercise", "abilitytype:", "能力类型信息");

    private String name;

    private String key;

    private String desc;

    CacheKeyEnum(String name, String key, String desc)
    {
        this.name = name;
        this.key = key;
        this.desc = desc;
    }

    public static CacheKeyEnum getEnumByKey(String key)
    {
        for (CacheKeyEnum enums : CacheKeyEnum.values())
        {
            if (key.equals(enums.getKey()))
            {
                return enums;
            }
        }
        return null;
    }
}
