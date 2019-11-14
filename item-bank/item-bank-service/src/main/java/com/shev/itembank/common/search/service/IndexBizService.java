package com.shev.itembank.common.search.service;

import com.shev.itembank.common.search.request.SubmitIndexingTaskRequest;

import java.util.Map;

public interface IndexBizService
{
    public Map<String, Object> submitIndexingTask(SubmitIndexingTaskRequest request) throws Exception;

    public Map<String, Object> submitPaperIndexingTask(SubmitIndexingTaskRequest request) throws Exception;

    void deletePaperIndex(SubmitIndexingTaskRequest request) throws Exception;
}
