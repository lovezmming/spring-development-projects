package com.shev.itembank.system.custom;

import com.shev.itembank.system.entity.Partner;

import java.util.List;
import java.util.Map;

public interface PartnerCustomMapper
{
    List<Partner> selectByParameter(Map<String, Object> params);
}