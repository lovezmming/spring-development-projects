package com.shev.compilation.user.dao.custom;

import com.shev.compilation.user.entity.UserPreference;

import java.util.List;

public interface UserPreferenceCustomDao
{
    List<UserPreference> getUserPreferenceByUserId(String userId);
}

