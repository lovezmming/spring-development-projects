package com.shev.compilation.common.Enum;

import lombok.Getter;

@Getter
public enum UserTypeEnum
{
    /** 学生 */
    STUDENT("S", "student"),

    /** 教师 */
    TEACHER("T", "teacher"),

    /** 管理员 */
    ADMIN("A", "admin");

    private String name;

    private String desc;

    UserTypeEnum(String name, String desc)
    {
        this.name = name;
        this.desc = desc;
    }
}
