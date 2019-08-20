package com.shev.compilation.edumeta.dao.custom;

import com.shev.compilation.edumeta.entity.SemanticType;

import java.util.List;
import java.util.Map;

public interface SemanticTypeCustomDao
{
    List<SemanticType> getSemanticTypesByParams(Map<String, Object> params);
}
