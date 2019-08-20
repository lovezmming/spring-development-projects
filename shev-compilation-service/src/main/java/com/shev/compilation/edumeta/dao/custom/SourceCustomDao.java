package com.shev.compilation.edumeta.dao.custom;

import com.shev.compilation.edumeta.entity.Source;

import java.util.List;
import java.util.Map;

public interface SourceCustomDao
{
    List<Source> getSourcesByParams(Map<String, Object> params);
}
