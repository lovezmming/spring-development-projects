package com.shev.compilation.user.dao.custom;

import com.shev.compilation.user.entity.UserPermissionRelation;

import java.util.List;

public interface UserPermissionRelationsCustomDao
{
    List<UserPermissionRelation> getUserPermissionRelationsByUserId(String userId);
}
