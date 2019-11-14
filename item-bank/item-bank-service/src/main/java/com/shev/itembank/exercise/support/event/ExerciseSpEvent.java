package com.shev.itembank.exercise.support.event;

import com.shev.itembank.common.Enum.EventEnum;
import com.shev.itembank.common.base.util.TextUtil;
import com.shev.itembank.common.search.service.index.IndexManageService;
import com.shev.itembank.exercise.entity.Exercise;
import com.shev.itembank.exercise.mapper.ExerciseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ExerciseSpEvent
{
    private static final Logger logger = LoggerFactory.getLogger(ExerciseSpEvent.class);

    @Autowired
    private IndexManageService indexManageService;

    @Autowired
    private ExerciseMapper exerciseMapper;

    @Async
    public void dealExercise(Map<String, Object> paramMap, EventEnum type) throws Exception
    {
        String exerciseId = (String) paramMap.get("exerciseId");
        if(TextUtil.isEmpty(exerciseId))
            logger.error("exerciseId.Empty");

        Exercise exercise = exerciseMapper.selectByPrimaryKey(exerciseId);
        if(exercise == null)
            logger.error("exerciseId.Invalid:"+exerciseId);

        if (EventEnum.CREATE.getValue().equals(type.getValue())
                || EventEnum.UPDATE.getValue().equals(type.getValue()))
        {
            String tenantId = (String) paramMap.get("tenantId");
            Boolean isPublic = (Boolean) paramMap.get("isPublic");
            indexManageService.updateExerciseES(exercise, isPublic, tenantId);
        } else if (EventEnum.DELETE.getValue().equals(type.getValue()))
        {
            indexManageService.deleteSpExercise(exerciseId);
        }
    }

}
