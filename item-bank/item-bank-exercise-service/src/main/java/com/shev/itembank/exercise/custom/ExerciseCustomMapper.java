package com.shev.itembank.exercise.custom;

import com.shev.itembank.exercise.entity.Exercise;

import java.util.List;
import java.util.Map;

public interface ExerciseCustomMapper
{
    List<Exercise> selectByParameter(Map<String, Object> params);
}