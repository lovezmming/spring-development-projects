package com.shev.itembank.exercise.service;

import com.shev.itembank.common.base.result.RecordSet;
import com.shev.itembank.common.base.web.HttpServletBasicRequest;
import com.shev.itembank.exercise.request.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ExerciseBusinessService
{

    public RecordSet getExerciseDetail(ExerciseGetDetailRequest request) throws Exception;

    public RecordSet searchByKeyword(ExerciseSearchByKeywordRequest request) throws Exception;

}
