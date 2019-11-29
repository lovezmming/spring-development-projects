package com.shev.compilation.user.dao.custom;

import com.shev.compilation.user.entity.Partner;

import java.util.List;
import java.util.Map;

public interface PartnerCustomDao
{
    List<Partner> getPartnersByParams(Map<String, Object> params);
}

