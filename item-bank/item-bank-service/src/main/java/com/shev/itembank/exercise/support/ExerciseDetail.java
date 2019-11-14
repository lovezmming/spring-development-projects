package com.shev.itembank.exercise.support;

import com.shev.itembank.exercise.entity.Exercise;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public abstract class ExerciseDetail
{
    public abstract Map<String, Object> getExerciseDetail(Boolean isPublic, String tenantId, Exercise exercise, String imagePrefix) throws Exception;
}
