package com.shev.compilation.user.dao.service.impl;

import com.shev.compilation.common.datasource.BaseDao;
import com.shev.compilation.common.datasource.dynamic.DataSourceQuery;
import com.shev.compilation.common.datasource.dynamic.DataSourceUpdate;
import com.shev.compilation.user.dao.custom.*;
import com.shev.compilation.user.dao.mapper.*;
import com.shev.compilation.user.dao.service.UserDaoService;
import com.shev.compilation.user.entity.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserDaoServiceImpl extends BaseDao implements UserDaoService
{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserDeleteMapper userDeleteMapper;

    @Autowired
    private UserLoginMapper userLoginMapper;

    @Autowired
    private UserLoginDeleteMapper userLoginDeleteMapper;

    @Autowired
    private UserDutyRelationMapper userDutyRelationMapper;

    @Autowired
    private UserDutyRelationDeleteMapper userDutyRelationDeleteMapper;

    @Autowired
    private UserPermissionRelationMapper userPermissionRelationMapper;

    @Autowired
    private UserPermissionRelationDeleteMapper userPermissionRelationDeleteMapper;

    @Autowired
    private UserPreferenceMapper userPreferenceMapper;

    @Autowired
    private UserCustomDao userCustomDao;

    @Autowired
    private UserLoginCustomDao userLoginCustomDao;

    @Autowired
    private UserDutyRelationCustomDao userDutyRelationCustomDao;

    @Autowired
    private UserPermissionRelationsCustomDao userPermissionRelationsCustomDao;

    @Autowired
    private UserRecordCustomDao userRecordCustomDao;

    @Autowired
    private UserPreferenceCustomDao userPreferenceCustomDao;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private DutyMapper dutyMapper;

    @Autowired
    private DutyDeleteMapper dutyDeleteMapper;

    @Autowired
    private DutyCustomDao dutyCustomDao;

    @Autowired
    private DutyPermissionRelationCustomDao dutyPermissionRelationCustomDao;

    @Autowired
    private DutyPermissionRelationMapper dutyPermissionRelationMapper;

    @Autowired
    private DutyPermissionRelationDeleteMapper dutyPermissionRelationDeleteMapper;

    @Override
    @DataSourceQuery
    public User getUserById(String id)
    {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    @DataSourceQuery
    public List<User> getUsersByParams(Map<String, Object> params)
    {
        return userCustomDao.getUsersByParams(params);
    }

    @Override
    @DataSourceUpdate
    public void updateUser(User user)
    {
        userMapper.updateByPrimaryKey(user);
    }

    @Override
    @DataSourceUpdate
    public void createUser(User user)
    {
        userMapper.insert(user);
    }

    @Override
    @DataSourceUpdate
    public void deleteUser(User user, String currUserId)
    {
        user.setUpdateTime(new Date());
        user.setUpdateUserId(currUserId);
        UserDelete userDelete = new UserDelete();
        BeanUtils.copyProperties(user, userDelete);
        userDeleteMapper.insert(userDelete);
        userMapper.deleteByPrimaryKey(user.getId());
    }

    @Override
    @DataSourceUpdate
    public void createUserLogin(UserLogin userLogin)
    {
        userLoginMapper.insert(userLogin);
    }

    @Override
    @DataSourceUpdate
    public void deleteUserLogin(UserLogin userLogin)
    {
        userLogin.setUpdateTime(new Date());
        UserLoginDelete userLoginDelete = new UserLoginDelete();
        BeanUtils.copyProperties(userLogin, userLoginDelete);
        userLoginDeleteMapper.insert(userLoginDelete);
        userLoginMapper.deleteByPrimaryKey(userLogin.getId());
    }

    @Override
    @DataSourceQuery
    public UserLogin getUserLoginById(String id)
    {
        return userLoginMapper.selectByPrimaryKey(id);
    }

    @Override
    @DataSourceUpdate
    public void updateUserLogin(UserLogin userLogin)
    {
        userLogin.setLastLoginTime(new Date());
        userLoginMapper.updateByPrimaryKey(userLogin);
    }

    @Override
    @DataSourceQuery
    public List<UserLogin> getUserLoginsByParams(Map<String, Object> params)
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        return userLoginCustomDao.getUserLoginsByParams(params);
    }

    @Override
    @DataSourceQuery
    public List<UserRecord> getUserRecordsByParams(Map<String, Object> params)
    {
        return userRecordCustomDao.getUserRecordsByParams(params);
    }

    @Override
    @DataSourceQuery
    public UserPreference getUserPreferenceById(String id)
    {
        return userPreferenceMapper.selectByPrimaryKey(id);
    }

    @Override
    @DataSourceUpdate
    public void updateUserPreference(UserPreference userPreference)
    {
        userPreferenceMapper.updateByPrimaryKey(userPreference);
    }

    @Override
    @DataSourceUpdate
    public void createUserPreference(UserPreference userPreference)
    {
        userPreferenceMapper.insert(userPreference);
    }

    @Override
    @DataSourceQuery
    public List<UserPreference> getUserPreferenceByUserId(String userId)
    {
        return userPreferenceCustomDao.getUserPreferenceByUserId(userId);
    }

    @Override
    @DataSourceQuery
    public List<UserDutyRelation> getUserDutyRelationsByUserId(String userId)
    {
        return userDutyRelationCustomDao.getUserDutyRelationsByUserId(userId);
    }

    @Override
    @DataSourceQuery
    public List<UserDutyRelation> getUserDutyRelationsByDutyId(String dutyId)
    {
        return userDutyRelationCustomDao.getUserDutyRelationsByDutyId(dutyId);
    }

    @Override
    @DataSourceUpdate
    public void createUserDutyRelation(UserDutyRelation userDutyRelation)
    {
        userDutyRelationMapper.insert(userDutyRelation);
    }

    @Override
    @DataSourceUpdate
    public void deleteUserDutyRelation(UserDutyRelation userDutyRelation, String currUserId)
    {
        userDutyRelation.setUpdateUserId(currUserId);
        userDutyRelation.setUpdateTime(new Date());
        UserDutyRelationDelete userDutyRelationDelete = new UserDutyRelationDelete();
        BeanUtils.copyProperties(userDutyRelation, userDutyRelationDelete);
        userDutyRelationDeleteMapper.insert(userDutyRelationDelete);
        userDutyRelationMapper.deleteByPrimaryKey(userDutyRelation.getId());
    }

    @Override
    @DataSourceQuery
    public Duty getDutyById(String id)
    {
        return dutyMapper.selectByPrimaryKey(id);
    }

    @Override
    @DataSourceQuery
    public List<Duty> getDutiesByParams(Map<String, Object> params)
    {
        return dutyCustomDao.getDutiesByParams(params);
    }

    @Override
    @DataSourceQuery
    public List<DutyPermissionRelation> getDutyPermissionRelationsByDutyId(String dutyId)
    {
        return dutyPermissionRelationCustomDao.getDutyPermissionRelationsByDutyId(dutyId);
    }

    @Override
    @DataSourceUpdate
    public void updateDuty(Duty duty)
    {
        dutyMapper.updateByPrimaryKey(duty);
    }

    @Override
    @DataSourceUpdate
    public void deleteDutyPermissionRelation(DutyPermissionRelation dutyPermissionRelation, String currentUserId)
    {
        dutyPermissionRelation.setUpdateTime(new Date());
        dutyPermissionRelation.setUpdateUserId(currentUserId);
        DutyPermissionRelationDelete dutyPermissionRelationDelete = new DutyPermissionRelationDelete();
        BeanUtils.copyProperties(dutyPermissionRelation, dutyPermissionRelationDelete);
        dutyPermissionRelationDeleteMapper.insert(dutyPermissionRelationDelete);
        dutyPermissionRelationMapper.deleteByPrimaryKey(dutyPermissionRelation.getId());
    }

    @Override
    @DataSourceUpdate
    public void createDutyPermissionRelation(DutyPermissionRelation dutyPermissionRelation)
    {
        dutyPermissionRelationMapper.insert(dutyPermissionRelation);
    }

    @Override
    @DataSourceUpdate
    public void createDuty(Duty duty)
    {
        dutyMapper.insert(duty);
    }

    @Override
    @DataSourceUpdate
    public void deleteDuty(Duty duty, String currentUserId)
    {
        duty.setUpdateTime(new Date());
        duty.setUpdateUserId(currentUserId);
        DutyDelete dutyDelete = new DutyDelete();
        BeanUtils.copyProperties(duty, dutyDelete);
        dutyDeleteMapper.insert(dutyDelete);
        dutyMapper.deleteByPrimaryKey(duty.getId());
    }

    @Override
    @DataSourceQuery
    public List<UserPermissionRelation> getUserPermissionRelationsByUserId(String userId)
    {
        return userPermissionRelationsCustomDao.getUserPermissionRelationsByUserId(userId);
    }

    @Override
    @DataSourceUpdate
    public void createUserPermissionRelation(UserPermissionRelation userPermissionRelation)
    {
        userPermissionRelationMapper.insert(userPermissionRelation);
    }

    @Override
    @DataSourceUpdate
    public void deleteUserPermissionRelation(UserPermissionRelation userPermissionRelation, String currUserId)
    {
        userPermissionRelation.setUpdateUserId(currUserId);
        userPermissionRelation.setUpdateTime(new Date());
        UserPermissionRelationDelete userPermissionRelationDelete = new UserPermissionRelationDelete();
        BeanUtils.copyProperties(userPermissionRelation, userPermissionRelationDelete);
        userPermissionRelationDeleteMapper.insert(userPermissionRelationDelete);
        userPermissionRelationMapper.deleteByPrimaryKey(userPermissionRelation.getId());
    }

    @Override
    @DataSourceQuery
    public Permission getPermissionById(String id)
    {
        return permissionMapper.selectByPrimaryKey(id);
    }

    @Override
    @DataSourceQuery
    public List<Permission> getPermissions()
    {
        return permissionMapper.selectAll();
    }

}
