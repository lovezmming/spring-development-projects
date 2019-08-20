package com.shev.compilation.edumeta.dao.custom;

import com.shev.compilation.edumeta.entity.EducationalStage;

import java.util.List;
import java.util.Map;

public interface EducationalStageCustomDao
{
    List<EducationalStage> getEducationalStagesByParams(Map<String, Object> params);
}
