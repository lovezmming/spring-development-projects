package com.shev.compilation.user.dao.custom;

import com.shev.compilation.user.entity.UserDutyRelation;

import java.util.List;

public interface UserDutyRelationCustomDao
{
    List<UserDutyRelation> getUserDutyRelationsByUserId(String userId);

    List<UserDutyRelation> getUserDutyRelationsByDutyId(String dutyId);

}
