package com.shev.itembank.common.search.service.impl;

import com.shev.itembank.common.base.util.TextUtil;
import com.shev.itembank.common.search.request.SubmitIndexingTaskRequest;
import com.shev.itembank.common.search.service.IndexBizService;
import com.shev.itembank.common.search.service.index.IndexManageService;
import com.shev.itembank.common.search.service.search.SearchManageService;
import com.shev.itembank.common.search.task.IndexingDeletePaperRelatedTask;
import com.shev.itembank.common.search.task.IndexingExerciseRelatedTask;
import com.shev.itembank.common.search.task.IndexingPaperRelatedTask;
import com.shev.itembank.common.search.task.IndexingTextbookStructureCategoryRelatedTask;
import com.shev.itembank.edumeta.custom.TextbookStructureCategoryCustomMapper;
import com.shev.itembank.exercise.custom.ExerciseCustomMapper;
import com.shev.itembank.exercise.mapper.ExerciseMapper;
import com.shev.itembank.paper.custom.PaperCustomMapper;
import com.shev.itembank.system.service.PublicService;
import com.shev.itembank.task.mapper.EsBatchIndexTaskMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class IndexBizServiceImpl implements IndexBizService
{
    private static final Logger logger = LoggerFactory.getLogger(IndexingExerciseRelatedTask.class);

    @Autowired
    private IndexManageService indexManageService;

    @Autowired
    private TextbookStructureCategoryCustomMapper textbookStructureCategoryCustomMapper;

    @Autowired
    private PublicService publicService;

    @Autowired
    private SearchManageService searchManageService;

    @Autowired
    private ExerciseCustomMapper exerciseCustomMapper;

    @Autowired
    private ExerciseMapper exerciseMapper;

    @Autowired
    private EsBatchIndexTaskMapper esBatchIndexTaskMapper;

    @Autowired
    private PaperCustomMapper paperCustomMapper;

    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);

    @Override
    public Map<String, Object> submitIndexingTask(SubmitIndexingTaskRequest request) throws Exception
    {
        String tenantId = request.getCurrentUserTenantId();
        Date date = request.getTime() == null ? null : new Date(request.getTime());
        if(request.getType().equalsIgnoreCase("JC"))
        {
            logger.info("JC ES INDEX");
            fixedThreadPool.submit(new IndexingTextbookStructureCategoryRelatedTask(tenantId, indexManageService, textbookStructureCategoryCustomMapper, date));
        } else if(request.getType().equalsIgnoreCase("EXERCISE"))
        {
            logger.info("EXERCISE ES INDEX");
            Boolean isPublic  = publicService.isPublic(tenantId);
            fixedThreadPool.submit(new IndexingExerciseRelatedTask(esBatchIndexTaskMapper, exerciseMapper, exerciseCustomMapper, indexManageService, searchManageService, isPublic, tenantId, date, null));
        }        
        return null;
    }

    @Override
    public Map<String, Object> submitPaperIndexingTask(SubmitIndexingTaskRequest request) throws Exception
    {
        String tenantId = request.getCurrentUserTenantId();
        Date date = request.getTime() == null ? null : new Date(request.getTime());

        if(TextUtil.isEmpty(request.getType())
                || request.getType().equalsIgnoreCase("PAPER"))
        {
            logger.info("PAPER ES INDEX");
            Boolean isPublic  = publicService.isPublic(tenantId);
            fixedThreadPool.submit(new IndexingPaperRelatedTask(paperCustomMapper, indexManageService, isPublic, tenantId, date, null));
        } else
        {
            logger.info("Illegal type!");
        }
        return null;
    }

    @Override
    public void deletePaperIndex(SubmitIndexingTaskRequest request) throws Exception
    {
        Date date = request.getTime() == null ? null : new Date(request.getTime());
        if(TextUtil.isEmpty(request.getType())
                || request.getType().equalsIgnoreCase("PAPER"))
        {
            logger.info("DELETE PAPER ES INDEX");
            fixedThreadPool.submit(new IndexingDeletePaperRelatedTask(paperCustomMapper, indexManageService, date, null));
        } else
        {
            logger.info("Illegal type!");
        }
    }

}
