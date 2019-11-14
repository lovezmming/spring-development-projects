package com.shev.itembank.common.Enum;

import lombok.Getter;

@Getter
public enum CacheNameEnum
{

    PAPER("paper", "试卷信息"),

    EXERCISE("exercise", "题目信息"),

    EDUMETA("edumeta", "教育信息");

    private String name;

    private String desc;

    CacheNameEnum(String name, String desc)
    {
        this.name = name;
        this.desc = desc;
    }
}
