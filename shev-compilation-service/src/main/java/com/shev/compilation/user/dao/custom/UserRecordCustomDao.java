package com.shev.compilation.user.dao.custom;

import com.shev.compilation.user.entity.UserRecord;

import java.util.List;
import java.util.Map;

public interface UserRecordCustomDao
{
    List<UserRecord> getUserRecordsByParams(Map<String, Object> params);
}
