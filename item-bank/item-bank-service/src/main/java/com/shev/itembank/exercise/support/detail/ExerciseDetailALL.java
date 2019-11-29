package com.shev.itembank.exercise.support.detail;

import com.shev.itembank.exercise.entity.*;
import com.shev.itembank.exercise.mapper.*;
import com.shev.itembank.exercise.support.ExerciseDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Deprecated
@Service("ExerciseDetailALLQuery")
public class ExerciseDetailALL extends ExerciseDetail
{

    @Autowired
    private ExerciseMapper exerciseMapper;

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getExerciseDetail(Boolean isPublic, String tenantId, Exercise exercise, String imagePrefix) throws Exception
    {
        System.out.println("begin "+System.currentTimeMillis());
    }
}
