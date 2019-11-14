package com.shev.itembank.resource.custom;

import com.shev.itembank.resource.entity.ResourcePublish;

import java.util.List;
import java.util.Map;

public interface ResourcePublishCustomMapper
{
   List<ResourcePublish> selectByParameter(Map<String, Object> params);
}