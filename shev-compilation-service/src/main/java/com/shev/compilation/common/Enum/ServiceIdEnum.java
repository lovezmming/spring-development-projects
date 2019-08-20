package com.shev.compilation.common.Enum;

import lombok.Getter;

@Getter
public enum ServiceIdEnum
{
    /** 用户 */
    USER("1", "000001", "01"),

    /** 教育元件 */
    EDUMETA("1", "000001", "01");

    private String isPublic;

    private String tenantId;

    private String serviceId;

    ServiceIdEnum(String isPublic, String tenantId, String serviceId)
    {
        this.isPublic = isPublic;
        this.tenantId = tenantId;
        this.serviceId = serviceId;
    }
}
