package com.shev.compilation.user.dao.custom;

import com.shev.compilation.user.entity.UserRecord;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface UserRecordCustomDao
{
    List<UserRecord> getUserRecordsByParams(Map<String, Object> params);
}
