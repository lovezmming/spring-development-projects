package com.shev.itembank.exercise.service;

import com.shev.itembank.common.base.result.RecordSet;
import com.shev.itembank.exercise.request.*;

public interface ExerciseBusinessService
{

    public RecordSet getExerciseDetail(ExerciseGetDetailRequest request) throws Exception;

    public RecordSet searchByKeyword(ExerciseSearchByKeywordRequest request) throws Exception;

}
