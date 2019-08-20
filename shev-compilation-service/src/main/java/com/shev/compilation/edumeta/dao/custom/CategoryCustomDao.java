package com.shev.compilation.edumeta.dao.custom;

import com.shev.compilation.edumeta.entity.Category;

import java.util.List;
import java.util.Map;

public interface CategoryCustomDao
{
    List<Category> getCategoriesByParams(Map<String, Object> params);
}
