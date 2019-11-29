package com.shev.itembank.exercise.support.detail;

import com.shev.itembank.exercise.custom.*;
import com.shev.itembank.exercise.entity.*;
import com.shev.itembank.exercise.mapper.*;
import com.shev.itembank.exercise.support.ExerciseDetail;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("ExerciseDetailALL")
public class ExerciseDetailALLQuery extends ExerciseDetail
{

    @Override
    public Map<String, Object> getExerciseDetail(Boolean isPublic, String tenantId, Exercise exercise, String imagePrefix) throws Exception
    {

    }
}
