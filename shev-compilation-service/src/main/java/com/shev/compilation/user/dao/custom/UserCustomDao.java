package com.shev.compilation.user.dao.custom;

import com.shev.compilation.user.entity.User;

import java.util.List;
import java.util.Map;

public interface UserCustomDao
{
    List<User> getUsersByParams(Map<String, Object> params);
}

