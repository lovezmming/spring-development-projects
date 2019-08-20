package com.shev.compilation.user.dao.custom;

import com.shev.compilation.user.entity.DutyPermissionRelation;

import java.util.List;

public interface DutyPermissionRelationCustomDao
{
    List<DutyPermissionRelation> getDutyPermissionRelationsByDutyId(String dutyId);
}

