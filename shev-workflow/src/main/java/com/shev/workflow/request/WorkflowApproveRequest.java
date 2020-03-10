package com.shev.workflow.request;

import java.io.Serializable;

public class WorkflowApproveRequest implements Serializable
{
    private String taskId;

    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }

    public String getTaskId()
    {
        return taskId;
    }
}
