package com.shev.itembank.common.batch;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shev.itembank.common.Enum.DataServiceEnum;
import com.shev.itembank.common.base.util.PrimaryKeyUtil;
import com.shev.itembank.common.base.util.TextUtil;
import com.shev.itembank.common.redis.service.CacheService;
import com.shev.itembank.common.search.service.index.IndexManageService;
import com.shev.itembank.exercise.custom.ExerciseCustomMapper;
import com.shev.itembank.exercise.entity.Exercise;
import com.shev.itembank.system.entity.Tenant;
import com.shev.itembank.system.mapper.TenantMapper;
import com.shev.itembank.system.service.PublicService;
import com.shev.itembank.task.custom.EsBatchIndexTaskCustomMapper;
import com.shev.itembank.task.entity.EsBatchIndexTask;
import com.shev.itembank.task.mapper.EsBatchIndexTaskMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ExerciseProcessor
{
    private static final Logger logger = LoggerFactory.getLogger(ExerciseProcessor.class);

    @Autowired
    private CacheService cacheService;

    @Autowired
    private PublicService publicService;

    @Autowired
    private IndexManageService indexManageService;

    @Autowired
    private TenantMapper tenantMapper;

    @Autowired
    private EsBatchIndexTaskCustomMapper esBatchIndexTaskCustomMapper;

    @Autowired
    private EsBatchIndexTaskMapper esBatchIndexTaskMapper;

    @Autowired
    private ExerciseCustomMapper exerciseCustomMapper;

    @Scheduled(cron = "0 0 1 * * ?")
    public void batchExerciseIndex()
    {
        String indexKey = "EXERCISE_ES_INDEXING_SCHEDULE";
        logger.info(indexKey);
        Object scheduleExerciseIndex = cacheService.get(indexKey);
        if (scheduleExerciseIndex != null)
        {
            logger.warn("exercise schedule index locked !");
            return;
        }
        cacheService.set(indexKey, indexKey);
        try
        {
            logger.info(indexKey);
            updateLatestExerciseIndex();
        } catch (Exception e)
        {
            logger.error("{}exception:{}", indexKey, e.getMessage());
        } finally
        {
            cacheService.del(indexKey);
        }
    }

    private void updateLatestExerciseIndex() throws Exception
    {
        List<Tenant> tenants = tenantMapper.selectAll();
        for (Tenant tenant : tenants)
        {
            String tenantId = tenant.getCoreId();
            if (TextUtil.isEmpty(tenantId))
                continue;
            logger.info("tenantId:" + tenantId );
            Boolean isPublic = publicService.isPublic(tenantId);
            Map<String, Object> params = new HashMap<>();
            params.put("tenantId", tenantId);
            params.put("type", "EXERCISE");
            params.put("status", Boolean.FALSE);
            PageHelper.startPage(0, 1, " CREATE_TIME DESC");
            List<EsBatchIndexTask> esBatchIndexTasks = esBatchIndexTaskCustomMapper.selectByParameter(params);
            PageInfo<EsBatchIndexTask> pageInfo = new PageInfo<>(esBatchIndexTasks);
            if (!TextUtil.isEmpty(esBatchIndexTasks))
            {
                EsBatchIndexTask task = pageInfo.getList().get(0);
                Date dateFrom = task.getCreateTime();
                if (dateFrom == null)
                    continue;

                params.clear();
                params.put("dateFrom", dateFrom);
                List<Exercise> exercises = exerciseCustomMapper.selectByParameter(params);
                logger.info("exercise totalCount:" + exercises.size());
                for (Exercise exercise : exercises)
                {
                    indexManageService.updateExerciseES(exercise, isPublic, tenantId);
                }
                EsBatchIndexTask esBatchIndexTask = new EsBatchIndexTask();
                BeanUtils.copyProperties(task, esBatchIndexTask);
                Date now = new Date();
                esBatchIndexTask.setCreateTime(now);
                esBatchIndexTask.setUpdateTime(now);
                esBatchIndexTask.setId(PrimaryKeyUtil.nextId(isPublic, tenantId, DataServiceEnum.TASK));
                esBatchIndexTaskMapper.insert(esBatchIndexTask);
                task.setStatus(Boolean.TRUE);
                esBatchIndexTaskMapper.updateByPrimaryKey(task);
                logger.info("exercise es indexing task finished.");
            }
        }
    }

}
