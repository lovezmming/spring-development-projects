package com.shev.compilation.common.Enum;

import lombok.Getter;

/**
 * 缓存名称配置：与ehcache.xml配置中name保持一致
 */
@Getter
public enum CacheNameEnum
{
    SYSTEM("system", "系统管理信息"),

    ACCOUNT("account", "用户管理信息"),

    EDUMETA("edumeta", "教育管理信息");

    private String name;

    private String desc;

    CacheNameEnum(String name, String desc)
    {
        this.name = name;
        this.desc = desc;
    }
}
