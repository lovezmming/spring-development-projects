package com.shev.compilation.edumeta.dao.custom;

import com.shev.compilation.edumeta.entity.Subject;

import java.util.List;
import java.util.Map;

public interface SubjectCustomDao
{
    List<Subject> getSubjectsByParams(Map<String, Object> params);
}
