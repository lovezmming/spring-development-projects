package com.shev.compilation.user.dao.custom;

import com.shev.compilation.user.entity.Duty;

import java.util.List;
import java.util.Map;

public interface DutyCustomDao
{
    List<Duty> getDutiesByParams(Map<String, Object> params);
}

