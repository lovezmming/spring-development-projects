package com.shev.workflow.request;

import java.io.Serializable;

public class WorkflowApplyRequest implements Serializable
{
    private String userId;

    private Integer money;

    private String description;

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setMoney(Integer money)
    {
        this.money = money;
    }

    public Integer getMoney()
    {
        return money;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
}
