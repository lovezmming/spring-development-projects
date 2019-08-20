package com.shev.compilation.user.service;

import com.shev.compilation.common.support.web.RecordSet;
import com.shev.compilation.user.request.*;

import java.util.Map;

public interface UserService
{
    Map<String, Object> register(UserRegisterRequest userRegisterRequest) throws Exception;

    RecordSet getUserDetail(UserDetailGetRequest userDetailGetRequest);

    RecordSet getUserList(UserGetRequest userListRequest);

    Map<String, Object> updateUser(UserUpdateRequest userUpdateRequest) throws Exception;

    Map<String, Object> createUser(UserCreateRequest userCreateRequest) throws Exception;

    void deleteUser(UserDeleteRequest userDeleteRequest);

    RecordSet getUserRecords(UserRecordGetRequest userRecordGetRequest);

    RecordSet getUserRecordDetail(UserRecordDetailGetRequest userRecordDetailGetRequest);

    RecordSet exportUserRecordDetail(UserRecordDetailGetRequest userRecordDetailGetRequest) throws Exception;

    RecordSet getUserPreferenceDetail(UserPreferenceDetailGetRequest userPreferenceDetailGetRequest);

    Map<String, Object> updateUserPreference(UserPreferenceUpdateRequest userPreferenceUpdateRequest);

    Map<String, Object> createUserPreference(UserPreferenceCreateRequest userPreferenceCreateRequest);

}
