package com.shev.compilation.edumeta.dao.custom;

import com.shev.compilation.edumeta.entity.SourcePackage;

import java.util.List;
import java.util.Map;

public interface SourcePackageCustomDao
{
    List<SourcePackage> getSourcePackagesByParams(Map<String, Object> params);
}
