package com.shev.compilation.edumeta.dao.custom;

import com.shev.compilation.edumeta.entity.KnowledgePoint;

import java.util.List;
import java.util.Map;

public interface KnowledgePointCustomDao
{
    List<KnowledgePoint> getKnowledgePointsByParams(Map<String, Object> params);
}
