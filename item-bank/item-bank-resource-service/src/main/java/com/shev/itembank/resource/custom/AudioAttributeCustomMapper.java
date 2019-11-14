package com.shev.itembank.resource.custom;

import com.shev.itembank.resource.entity.AudioAttribute;

import java.util.List;
import java.util.Map;

public interface AudioAttributeCustomMapper
{
   List<AudioAttribute> selectByParameter(Map<String, Object> params);
}