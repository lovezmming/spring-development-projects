package com.shev.compilation.user.dao.custom;

import com.shev.compilation.common.datasource.dynamic.DataSourceQuery;
import com.shev.compilation.user.entity.UserLogin;

import java.util.List;
import java.util.Map;

public interface UserLoginCustomDao
{
    @DataSourceQuery
    List<UserLogin> getUserLoginsByParams(Map<String, Object> params);
}
