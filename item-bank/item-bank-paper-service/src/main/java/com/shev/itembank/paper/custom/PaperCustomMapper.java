package com.shev.itembank.paper.custom;

import com.shev.itembank.paper.entity.Paper;

import java.util.List;
import java.util.Map;

public interface PaperCustomMapper
{
    List<Paper> selectByParameter(Map<String, Object> params);
}