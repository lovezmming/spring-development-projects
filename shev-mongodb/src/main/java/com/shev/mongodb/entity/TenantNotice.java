package com.shev.mongodb.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document
public class TenantNotice
{
    private String id;

    private String tenantId;

    private String title;

    private String content;

    private Date createTime;

    private Date updateTime;
}
