package com.shev.itembank.common.search.task;

import com.shev.itembank.common.Enum.ConstantEnum;
import com.shev.itembank.common.Enum.DataServiceEnum;
import com.shev.itembank.common.base.result.RecordSet;
import com.shev.itembank.common.base.util.TextUtil;
import com.shev.itembank.common.search.service.index.IndexManageService;
import com.shev.itembank.common.search.service.search.SearchManageService;
import com.shev.itembank.exercise.entity.Exercise;
import com.shev.itembank.exercise.mapper.ExerciseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

public class ExerciseESTask implements Callable<String>
{
    private static final Logger logger = LoggerFactory.getLogger(ExerciseESTask.class);

    private String tenantId;

    private Boolean isPublic;

    private SearchManageService searchManageService;

    private IndexManageService indexManageService;

    private ExerciseMapper exerciseMapper;

    private Integer start;

    private Integer end;

    private Integer pageSize = 500;

    public static String PUBLIC_DB = "1";

    public static String PRIVATE_DB = "2";

    public ExerciseESTask(ExerciseMapper exerciseMapper, SearchManageService searchManageService, IndexManageService indexManageService, String tenantId, Boolean isPublic, Integer start, Integer end)
    {
        this.exerciseMapper = exerciseMapper;
        this.searchManageService = searchManageService;
        this.indexManageService = indexManageService;
        this.tenantId = tenantId;
        this.isPublic = isPublic;
        this.start = start;
        this.end = end;
    }

    /**
     * @see Callable#call()
     */
    @Override
    public String call() throws Exception
    {
        String exerciseServiceId = TextUtil.expandNum(Integer.valueOf(DataServiceEnum.EXERCISE.getServiceId()), "0", 6);
        String publicExercisePrefix = PUBLIC_DB + ConstantEnum.DEFAULT_TENANT_ID.getValue() + exerciseServiceId;
        String privateExercisePrefix = PRIVATE_DB + tenantId + exerciseServiceId;

        int pos = Integer.valueOf(start).intValue();
        int countPos = Integer.valueOf(start).intValue();
        RecordSet rSet = searchManageService.searchExerciseByKnowledgePoint(tenantId, null, null, null, null, null, null, null, null, pos, pageSize);
        while (rSet.getValues().length > 0)
        {
            for (Object object : rSet.getValues())
            {
                String exerciseId = (String) object;
                if (!TextUtil.isEmpty(exerciseId))
                {
                    if (!isPublic)
                        exerciseId = exerciseId.replace(publicExercisePrefix, privateExercisePrefix);
                    Exercise exercise = exerciseMapper.selectByPrimaryKey(exerciseId);
                    if (exercise != null)
                    {
                        indexManageService.updateExerciseES(exercise, isPublic, tenantId);
                    } else
                        logger.error("invalid exerciseId: " + exerciseId);                    
                }
                countPos ++;
                if(countPos > end.intValue())
                    break;
            }
            if(countPos > end.intValue())
                break;
            pos += pageSize;
            rSet = searchManageService.searchExerciseByKnowledgePoint(tenantId, null, null, null, null, null, null, null, null, pos, pageSize);
        }
        return null;
    }

}
