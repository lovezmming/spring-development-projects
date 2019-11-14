package com.shev.itembank.task.custom;

import com.shev.itembank.task.entity.EsBatchIndexTask;

import java.util.List;
import java.util.Map;

public interface EsBatchIndexTaskCustomMapper
{
    List<EsBatchIndexTask> selectByParameter(Map<String, Object> params);
}