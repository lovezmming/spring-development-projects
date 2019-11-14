package com.shev.itembank.resource.custom;

import com.shev.itembank.resource.entity.Resource;

import java.util.List;
import java.util.Map;

public interface ResourceCustomMapper
{
   List<Resource> selectByParameter(Map<String, Object> params);
}