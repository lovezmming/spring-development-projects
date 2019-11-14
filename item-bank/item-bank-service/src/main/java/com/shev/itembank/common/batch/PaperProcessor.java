package com.shev.itembank.common.batch;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shev.itembank.common.Enum.DataServiceEnum;
import com.shev.itembank.common.base.util.PrimaryKeyUtil;
import com.shev.itembank.common.base.util.TextUtil;
import com.shev.itembank.common.redis.service.CacheService;
import com.shev.itembank.common.search.service.index.IndexManageService;
import com.shev.itembank.paper.custom.PaperCustomMapper;
import com.shev.itembank.paper.entity.Paper;
import com.shev.itembank.system.entity.Tenant;
import com.shev.itembank.system.mapper.TenantMapper;
import com.shev.itembank.system.service.PublicService;
import com.shev.itembank.task.custom.EsBatchIndexTaskCustomMapper;
import com.shev.itembank.task.entity.EsBatchIndexTask;
import com.shev.itembank.task.mapper.EsBatchIndexTaskMapper;

@Component
public class PaperProcessor
{
    private static final Logger logger = LoggerFactory.getLogger(PaperProcessor.class);

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
    private PaperCustomMapper paperCustomMapper;

    @Scheduled(cron = "0 0 1 * * ?")
    public void batchExerciseIndex()
    {
        String indexKey = "PAPER_ES_INDEXING_SCHEDULE";
        logger.info(indexKey);
        Object scheduleExerciseIndex = cacheService.get(indexKey);
        if (scheduleExerciseIndex != null)
        {
            logger.warn("paper schedule index locked !");
            return;
        }
        cacheService.set(indexKey, indexKey);
        try
        {
            logger.info(indexKey);
            updateLatestPaperIndex();
        } catch (Exception e)
        {
            logger.error("{}exception:{}", indexKey, e.getMessage());
        } finally
        {
            cacheService.del(indexKey);
        }
    }

    private void updateLatestPaperIndex() throws Exception
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
            params.put("type", "PAPER");
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
                List<Paper> papers = paperCustomMapper.selectByParameter(params);
                logger.info("paper totalCount:" + papers.size());
                for (Paper paper : papers)
                {
                    indexManageService.updatePaperES(paper, isPublic, tenantId);
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
                logger.info("paper es indexing task finished.");
            }
        }
    }

}
