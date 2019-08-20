package com.shev.compilation.edumeta.dao.custom;

import com.shev.compilation.edumeta.entity.Difficulty;

import java.util.List;
import java.util.Map;

public interface DifficultyCustomDao
{
    List<Difficulty> getDifficultiesByParams(Map<String, Object> params);
}
