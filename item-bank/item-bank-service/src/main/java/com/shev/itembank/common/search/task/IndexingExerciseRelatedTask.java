package com.shev.itembank.common.search.task;

import com.shev.itembank.common.Enum.ConstantEnum;
import com.shev.itembank.common.Enum.DataServiceEnum;
import com.shev.itembank.common.base.result.RecordSet;
import com.shev.itembank.common.base.util.PrimaryKeyUtil;
import com.shev.itembank.common.search.service.index.IndexManageService;
import com.shev.itembank.common.search.service.search.SearchManageService;
import com.shev.itembank.exercise.custom.ExerciseCustomMapper;
import com.shev.itembank.exercise.entity.Exercise;
import com.shev.itembank.exercise.mapper.ExerciseMapper;
import com.shev.itembank.task.entity.EsBatchIndexTask;
import com.shev.itembank.task.mapper.EsBatchIndexTaskMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class IndexingExerciseRelatedTask implements Callable<String>
{
    private static final Logger logger = LoggerFactory.getLogger(IndexingExerciseRelatedTask.class);

    private IndexManageService indexManageService;

    private SearchManageService searchManageService;

    private ExerciseCustomMapper exerciseCustomMapper;

    private ExerciseMapper exerciseMapper;

    private EsBatchIndexTaskMapper esBatchIndexTaskMapper;

    private Boolean isPublic;

    private String tenantId;

    private Date dateFrom;

    private Date dateThru;

    public IndexingExerciseRelatedTask(EsBatchIndexTaskMapper esBatchIndexTaskMapper, ExerciseMapper exerciseMapper, ExerciseCustomMapper exerciseCustomMapper, IndexManageService indexManageService, SearchManageService searchManageService, Boolean isPublic, String tenantId, Date dateFrom, Date dateThru)
    {
        this.esBatchIndexTaskMapper = esBatchIndexTaskMapper;
        this.exerciseMapper = exerciseMapper;
        this.exerciseCustomMapper = exerciseCustomMapper;
        this.indexManageService = indexManageService;
        this.searchManageService = searchManageService;
        this.isPublic = isPublic;
        this.tenantId = tenantId;
        this.dateFrom = dateFrom;
        this.dateThru = dateThru;
    }

    @Override
    public String call() throws Exception
    {
        try
        {
            logger.info("tenantId:" + tenantId + " isPublic:" + isPublic.toString() + " dateFrom:" + dateFrom);
            if (dateFrom != null)
            {
                Map<String, Object> params = new HashMap<>();
                params.put("dateFrom", dateFrom);
                params.put("dateThru", dateThru);
                List<Exercise> exercises = exerciseCustomMapper.selectByParameter(params);
                logger.info("exercise totalCount:" + exercises.size());
                for (Exercise exercise : exercises)
                {
                    indexManageService.updateExerciseES(exercise, isPublic, tenantId);
                }
            } else
            {
                Integer pos = 0;
                Integer pageSize = 1;

                RecordSet rSet = searchManageService.searchExerciseByKnowledgePoint(tenantId, null, null, null, null, null, null, null, null,  pos, pageSize);
                int totalCount = rSet.getTotalCount();
                if (totalCount > 0)
                {
                    int threadNumber = 8;
                    List<Future<String>> futures = new ArrayList<Future<String>>();
                    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(threadNumber+1);
                    int singleCount = totalCount / threadNumber;
                    int start = 1;
                    int end = start + singleCount;
                    while (start < totalCount && end < totalCount +singleCount)
                    {
                        futures.add(fixedThreadPool.submit(new ExerciseESTask(exerciseMapper, searchManageService, indexManageService, tenantId, isPublic, start, end)));
                        start = end + 1;
                        end = start + singleCount;
                    }
                    for(Future<String> future: futures)
                        future.get();
                    fixedThreadPool.shutdown();
                }

                logger.info("es exercise totalCount:" + totalCount);
                Date now = new Date();
                EsBatchIndexTask esBatchIndexTask = new EsBatchIndexTask();
                esBatchIndexTask.setId(PrimaryKeyUtil.nextId(isPublic, tenantId, DataServiceEnum.TASK));
                esBatchIndexTask.setUser(ConstantEnum.DEFAULT_USER_ID.getValue());
                esBatchIndexTask.setType("EXERCISE");
                esBatchIndexTask.setTenantId(tenantId);
                esBatchIndexTask.setStatus(Boolean.FALSE);
                esBatchIndexTask.setCreateTime(now);
                esBatchIndexTask.setUpdateTime(now);
                esBatchIndexTaskMapper.insert(esBatchIndexTask);
            }
            logger.info("exercise realtime indexing task submited.");
        } catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

}
