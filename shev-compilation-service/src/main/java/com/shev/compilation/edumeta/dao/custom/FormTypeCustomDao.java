package com.shev.compilation.edumeta.dao.custom;

import com.shev.compilation.edumeta.entity.FormType;

import java.util.List;
import java.util.Map;

public interface FormTypeCustomDao
{
    List<FormType> getFormTypesByParams(Map<String, Object> params);
}
