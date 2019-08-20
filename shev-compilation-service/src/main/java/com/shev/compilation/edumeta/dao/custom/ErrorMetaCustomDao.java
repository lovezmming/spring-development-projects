package com.shev.compilation.edumeta.dao.custom;

import com.shev.compilation.edumeta.entity.ErrorMeta;

import java.util.List;
import java.util.Map;

public interface ErrorMetaCustomDao
{
    List<ErrorMeta> getErrorMetasByParams(Map<String, Object> params);
}
