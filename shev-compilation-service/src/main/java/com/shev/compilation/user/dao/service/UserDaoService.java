package com.shev.compilation.user.dao.service;

import com.shev.compilation.user.entity.*;

import java.util.List;
import java.util.Map;

public interface UserDaoService
{
    // user
    User getUserById(String id);

    List<User> getUsersByParams(Map<String, Object> params);

    void updateUser(User user);

    void createUser(User user);

    void deleteUser(User user, String currUserId);

    // user login
    void createUserLogin(UserLogin userLogin);

    void deleteUserLogin(UserLogin userLogin);

    UserLogin getUserLoginById(String id);

    void updateUserLogin(UserLogin userLogin);

    List<UserLogin> getUserLoginsByParams(Map<String, Object> params);

    // user record
    List<UserRecord> getUserRecordsByParams(Map<String, Object> params);

    // user preference
    UserPreference getUserPreferenceById(String id);

    void updateUserPreference(UserPreference userPreference);

    void createUserPreference(UserPreference userPreference);

    List<UserPreference> getUserPreferenceByUserId(String userId);

    // duty
    List<UserDutyRelation> getUserDutyRelationsByUserId(String userId);

    List<UserDutyRelation> getUserDutyRelationsByDutyId(String dutyId);

    void createUserDutyRelation(UserDutyRelation userDutyRelation);

    void deleteUserDutyRelation(UserDutyRelation userDutyRelation, String currUserId);

    Duty getDutyById(String id);

    List<Duty> getDutiesByParams(Map<String, Object> params);

    void updateDuty(Duty duty);

    void createDuty(Duty duty);

    void deleteDuty(Duty duty, String currentUserId);

    // permission
    List<UserPermissionRelation> getUserPermissionRelationsByUserId(String userId);

    void createUserPermissionRelation(UserPermissionRelation userPermissionRelation);

    List<DutyPermissionRelation> getDutyPermissionRelationsByDutyId(String dutyId);

    void deleteUserPermissionRelation(UserPermissionRelation userPermissionRelation, String currUserId);

    void deleteDutyPermissionRelation(DutyPermissionRelation dutyPermissionRelation, String currentUserId);

    void createDutyPermissionRelation(DutyPermissionRelation dutyPermissionRelation);

    Permission getPermissionById(String id);

    List<Permission> getPermissions();

}
