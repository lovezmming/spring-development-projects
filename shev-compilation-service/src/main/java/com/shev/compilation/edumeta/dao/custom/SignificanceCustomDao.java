package com.shev.compilation.edumeta.dao.custom;

import com.shev.compilation.edumeta.entity.Significance;

import java.util.List;
import java.util.Map;

public interface SignificanceCustomDao
{
    List<Significance> getSignificancesByParams(Map<String, Object> params);
}
