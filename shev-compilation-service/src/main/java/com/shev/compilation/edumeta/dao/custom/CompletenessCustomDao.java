package com.shev.compilation.edumeta.dao.custom;

import com.shev.compilation.edumeta.entity.Completeness;

import java.util.List;
import java.util.Map;

public interface CompletenessCustomDao
{
    List<Completeness> getCompletenessesByParams(Map<String, Object> params);
}
