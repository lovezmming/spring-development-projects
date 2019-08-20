package com.shev.compilation.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shev.compilation.common.Enum.PartnerTypeEnum;
import com.shev.compilation.common.Enum.ServiceIdEnum;
import com.shev.compilation.common.Enum.UserTypeEnum;
import com.shev.compilation.common.exception.BusinessException;
import com.shev.compilation.common.qiniu.UploadService;
import com.shev.compilation.common.support.web.RecordSet;
import com.shev.compilation.common.util.*;
import com.shev.compilation.common.util.entity.ExcelExportFormatEntity;
import com.shev.compilation.user.dao.service.UserDaoService;
import com.shev.compilation.user.entity.*;
import com.shev.compilation.user.request.*;
import com.shev.compilation.user.service.UserService;
import com.shev.compilation.user.task.UserAsyncTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService
{

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("request.dateThru.period")
    private String dateThruPeriod;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private UserAsyncTask asyncTask;

    @Autowired
    private UserDaoService userDaoService;

    @Override
    public Map<String, Object> register(UserRegisterRequest userRegisterRequest) throws Exception
    {
        UserCreateRequest userCreateRequest = new UserCreateRequest();
        userCreateRequest.setName(userRegisterRequest.getName());
        userCreateRequest.setBirthDay(userRegisterRequest.getBirthDay());
        userCreateRequest.setGender(userRegisterRequest.getGender());
        userCreateRequest.setIdNumber(userRegisterRequest.getIdNumber());
        userCreateRequest.setPhoneNumber(userRegisterRequest.getPhoneNumber());
        userCreateRequest.setUserName(userRegisterRequest.getUserName());
        userCreateRequest.setPassWord(userRegisterRequest.getPassWord());
        userCreateRequest.setCurrentUserTenantId(userRegisterRequest.getTenantId());
        userCreateRequest.setType(userRegisterRequest.getType());
        return createUser(userCreateRequest);
    }

    @Override
    public RecordSet getUserDetail(UserDetailGetRequest userDetailGetRequest)
    {
        User user = userDaoService.getUserById(userDetailGetRequest.getId());
        Map<String, Object> params = new HashMap<>();
        params.put("tenantId", user.getTenantId());
        params.put("userId", user.getId());
        List<UserLogin> userLogins = userDaoService.getUserLoginsByParams(params);
        Map<String, Object> resultMap = new HashMap<>();
        if (!TextUtil.isEmpty(userLogins))
        {
            UserLogin userLogin = userLogins.get(0);
            resultMap.put("username", userLogin.getUserName());
            resultMap.put("password", userLogin.getPassword());
        }
        List<UserDutyRelation> userDutyRelations = userDaoService.getUserDutyRelationsByUserId(user.getId());
        Map<String, Object> dutyMap = new HashMap<>();
        if (!TextUtil.isEmpty(userDutyRelations))
        {
            for (UserDutyRelation dutyRelation : userDutyRelations)
            {
                Duty duty = userDaoService.getDutyById(dutyRelation.getDutyId());
                dutyMap.put("id", duty.getId());
                dutyMap.put("name", duty.getName());
            }
        }
        List<UserPermissionRelation> userPermissionRelations = userDaoService.getUserPermissionRelationsByUserId(user.getId());
        Map<String, Object> permissionMap = new HashMap<>();
        if (!TextUtil.isEmpty(userPermissionRelations))
        {
            for (UserPermissionRelation userPermissionRelation : userPermissionRelations)
            {
                Permission permission = userDaoService.getPermissionById(userPermissionRelation.getPermissionId());
                permissionMap.put("id", permission.getId());
                permissionMap.put("name", permission.getDescription());
                permissionMap.put("url", permission.getUrl());
                permissionMap.put("categoryId", userPermissionRelation.getCategoryId());
                permissionMap.put("educationalStageId", userPermissionRelation.getEducationalStageId());
                permissionMap.put("subjectId", userPermissionRelation.getSubjectId());
            }
        }
        resultMap.put("dutyInfo", dutyMap);
        resultMap.put("permissionInfo", permissionMap);
        resultMap.put("id", user.getId());
        resultMap.put("name", user.getName());
        resultMap.put("gender", user.getGender());
        resultMap.put("status", user.getStatus());
        resultMap.put("idNumber", user.getIdNumber());
        resultMap.put("phoneNumber", user.getPhoneNumber());
        return new RecordSet(0, 1, 1, new Object[]{resultMap});
    }

    @Override
    public RecordSet getUserList(UserGetRequest userListRequest)
    {
        String name = userListRequest.getName();
        String phoneNumber = userListRequest.getPhoneNumber();
        String idNumber = userListRequest.getIdNumber();
        String updateUserId = userListRequest.getUpdateUserId();
        Integer status = userListRequest.getStatus();
        Date updateTimeStart = userListRequest.getUpdateTimeStart();
        Date updateTimeEnd = userListRequest.getUpdateTimeEnd();

        Integer start = userListRequest.getStart();
        Integer pageSize = userListRequest.getPageSize();

        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("phoneNumber", phoneNumber);
        params.put("idNumber", idNumber);
        params.put("updateUserId", updateUserId);
        params.put("status", status);
        params.put("updateTimeStart", updateTimeStart);
        params.put("updateTimeEnd", updateTimeEnd);
        params.put("tenantId", userListRequest.getCurrentUserTenantId());

        PageHelper.startPage(start, pageSize);
        List<User> users = userDaoService.getUsersByParams(params);
        PageInfo<User> pageInfo = new PageInfo<>(users);
        List<Object> resultList = getResultUsers(pageInfo.getList());
        return new RecordSet(start, pageSize, users.size(), resultList.toArray());
    }

    private List<Object> getResultUsers(List<User> users)
    {
        List<Object> resultList = new ArrayList<>();
        User updateUser;
        for (User user : users)
        {
            Map<String, Object> result = new HashMap<>();
            result.put("id", user.getId());
            result.put("name", user.getName());
            result.put("idNumber", user.getId());
            result.put("phoneNumber", user.getPhoneNumber());
            result.put("status", user.getStatus());
            updateUser = userDaoService.getUserById(user.getUpdateUserId());
            result.put("updateUserName", updateUser.getName());
            result.put("updateTime", user.getUpdateTime());
            resultList.add(result);
        }
        return resultList;
    }

    @Override
    public Map<String, Object> updateUser(UserUpdateRequest userUpdateRequest) throws Exception
    {
        String id = userUpdateRequest.getId();
        String type = userUpdateRequest.getType();
        String name = userUpdateRequest.getName();
        Boolean gender = userUpdateRequest.getGender();
        Boolean status = userUpdateRequest.getStatus();
        String idNumber = userUpdateRequest.getIdNumber();
        String phoneNumber = userUpdateRequest.getPhoneNumber();
        Date birthDay = userUpdateRequest.getBirthDay();

        Date now = new Date();
        String currentUserId = userUpdateRequest.getCurrentUserId();
        User user = userDaoService.getUserById(id);
        if (!TextUtil.isEmpty(user.getUserLoginId()))
        {
            throw new BusinessException("exist username or password!", "exist username or password!");
        }
        if (!TextUtil.isEmpty(name))
        {
            user.setName(name);
        }
        if (!TextUtil.isEmpty(type))
        {
            user.setType(type);
        }
        if (!TextUtil.isEmpty(status))
        {
            user.setStatus(status);
        }
        if (!TextUtil.isEmpty(gender))
        {
            user.setGender(gender);
        }
        if (!TextUtil.isEmpty(idNumber))
        {
            user.setIdNumber(idNumber);
        }
        if (!TextUtil.isEmpty(phoneNumber))
        {
            user.setPhoneNumber(phoneNumber);
        }
        if (!TextUtil.isEmpty(birthDay))
        {
            user.setBirthday(birthDay);
        }
        user.setUpdateTime(now);
        user.setUpdateUserId(currentUserId);
        userDaoService.updateUser(user);

        String tenantId = userUpdateRequest.getCurrentUserTenantId();
        String userName = userUpdateRequest.getUserName();
        String passWord = userUpdateRequest.getPassWord();
        createUserLogin(id, userName, passWord, tenantId, now);

        List<String> dutyIds = userUpdateRequest.getDutyIds();
        createUserDutyRelation(id, dutyIds, tenantId, currentUserId, now);

        String categoryId = userUpdateRequest.getCategoryId();
        String educationStageId = userUpdateRequest.getEducationStageId();
        String subjectId = userUpdateRequest.getSubjectId();
        List<String> permissionIds = userUpdateRequest.getPermissionIds();
        createUserPermissionRelation(id, educationStageId, subjectId, categoryId, permissionIds, tenantId, currentUserId, now);

        asyncTask.refreshUserCache(user);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", id);
        return resultMap;
    }

    private String createUserLogin(String userId, String userName, String passWord, String tenantId, Date date) throws Exception
    {
        String newId = PrimaryKeyUtil.nextId(
                ServiceIdEnum.USER.getIsPublic(),
                tenantId,
                ServiceIdEnum.USER.getServiceId());
        UserLogin userLogin = new UserLogin();
        userLogin.setId(newId);
        userLogin.setUserId(userId);
        userLogin.setTenantId(tenantId);
        userLogin.setUserName(userName);
        userLogin.setPassword(PrimaryKeyUtil.password(passWord));
        userLogin.setLastLoginTime(date);
        userLogin.setCreateTime(date);
        userLogin.setUpdateTime(date);
        userDaoService.createUserLogin(userLogin);
        return newId;
    }

    private void createUserDutyRelation(String userId, List<String> dutyIds, String tenantId, String currentUserId, Date date)
    {
        if (!TextUtil.isEmpty(dutyIds))
        {
            String newId;
            for (String dutyId : dutyIds)
            {
                newId = PrimaryKeyUtil.nextId(
                        ServiceIdEnum.USER.getIsPublic(),
                        tenantId,
                        ServiceIdEnum.USER.getServiceId());
                UserDutyRelation userDutyRelation = new UserDutyRelation();
                userDutyRelation.setId(newId);
                userDutyRelation.setUserId(userId);
                userDutyRelation.setDutyId(dutyId);
                userDutyRelation.setCreateTime(date);
                userDutyRelation.setUpdateTime(date);
                userDutyRelation.setCreateUserId(currentUserId);
                userDutyRelation.setUpdateUserId(currentUserId);
                userDaoService.createUserDutyRelation(userDutyRelation);
            }
        }
    }

    private void createUserPermissionRelation(String userId, String educationStageId, String subjectId, String categoryId, List<String> permissionIds, String tenantId, String currentUserId, Date date)
    {
        if (!TextUtil.isEmpty(permissionIds))
        {
            String newId;
            for (String permissionId : permissionIds)
            {
                newId = PrimaryKeyUtil.nextId(
                        ServiceIdEnum.USER.getIsPublic(),
                        tenantId,
                        ServiceIdEnum.USER.getServiceId());
                UserPermissionRelation userPermissionRelation = new UserPermissionRelation();
                userPermissionRelation.setId(newId);
                userPermissionRelation.setUserId(userId);
                userPermissionRelation.setPermissionId(permissionId);
                userPermissionRelation.setEducationalStageId(educationStageId);
                userPermissionRelation.setSubjectId(subjectId);
                userPermissionRelation.setCategoryId(categoryId);
                userPermissionRelation.setCreateTime(date);
                userPermissionRelation.setUpdateTime(date);
                userPermissionRelation.setCreateUserId(currentUserId);
                userPermissionRelation.setUpdateUserId(currentUserId);
                userDaoService.createUserPermissionRelation(userPermissionRelation);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> createUser(UserCreateRequest userCreateRequest) throws Exception
    {
        logger.info("create user !");
        String type = userCreateRequest.getType();
        String name = userCreateRequest.getName();
        Boolean gender = userCreateRequest.getGender();
        Boolean status = userCreateRequest.getStatus();
        String idNumber = userCreateRequest.getIdNumber();
        String phoneNumber = userCreateRequest.getPhoneNumber();
        Date birthDay = userCreateRequest.getBirthDay();

        String tenantId = userCreateRequest.getCurrentUserTenantId();
        Map<String, Object> params = new HashMap<>();
        params.put("idNumber", idNumber);
        params.put("tenantId", tenantId);
        List<User> users = userDaoService.getUsersByParams(params);
        if (!TextUtil.isEmpty(users))
        {
            throw new BusinessException("exist user!", "exist user!");
        }

        String currentUserId = userCreateRequest.getCurrentUserId();
        Date now = new Date();
        User user = new User();
        String newId = PrimaryKeyUtil.nextId(
                ServiceIdEnum.USER.getIsPublic(),
                tenantId,
                ServiceIdEnum.USER.getServiceId());
        user.setId(newId);
        user.setType(TextUtil.isEmpty(type)? UserTypeEnum.TEACHER.getName():type);
        user.setName(name);
        user.setGender(gender);
        user.setBirthday(birthDay);
        user.setPhoneNumber(phoneNumber);
        user.setIdNumber(idNumber);
        user.setStatus(status);
        user.setTenantId(tenantId);
        user.setCreateTime(now);
        user.setUpdateTime(now);
        if (TextUtil.isEmpty(currentUserId))
        {
            currentUserId = newId;
        }
        user.setCreateUserId(currentUserId);
        user.setUpdateUserId(currentUserId);

        logger.info("create user login !");
        String userName = userCreateRequest.getUserName();
        String passWord = userCreateRequest.getPassWord();
        String userLoginId = createUserLogin(newId, userName, passWord, tenantId, now);
        user.setUserLoginId(userLoginId);
        userDaoService.createUser(user);

        logger.info("create user duty !");
        List<String> dutyIds = userCreateRequest.getDutyIds();
        createUserDutyRelation(newId, dutyIds, tenantId, currentUserId, now);

        logger.info("create user permission !");
        String categoryId = userCreateRequest.getCategoryId();
        String educationStageId = userCreateRequest.getEducationStageId();
        String subjectId = userCreateRequest.getSubjectId();
        List<String> permissionIds = userCreateRequest.getPermissionIds();
        createUserPermissionRelation(newId, educationStageId, subjectId, categoryId, permissionIds, tenantId, currentUserId, now);

        asyncTask.createUserCache(user);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", user.getId());
        return resultMap;
    }

    @Override
    public void deleteUser(UserDeleteRequest userDeleteRequest)
    {
        String id = userDeleteRequest.getId();
        String currUserId = userDeleteRequest.getCurrentUserId();
        User user = userDaoService.getUserById(id);
        userDaoService.deleteUser(user, currUserId);

        Map<String, Object> params = new HashMap<>();
        params.put("userId", id);
        params.put("tenantId", userDeleteRequest.getCurrentUserTenantId());
        List<UserLogin> userLogins = userDaoService.getUserLoginsByParams(params);
        for (UserLogin userLogin : userLogins)
        {
            userDaoService.deleteUserLogin(userLogin);
        }

        List<UserDutyRelation> userDutyRelations = userDaoService.getUserDutyRelationsByUserId(id);
        for (UserDutyRelation userDutyRelation : userDutyRelations)
        {
            userDaoService.deleteUserDutyRelation(userDutyRelation, currUserId);
        }

        List<UserPermissionRelation> userPermissionRelations = userDaoService.getUserPermissionRelationsByUserId(id);
        for (UserPermissionRelation userPermissionRelation : userPermissionRelations)
        {
            userDaoService.deleteUserPermissionRelation(userPermissionRelation, currUserId);
        }
        asyncTask.removeUserCache(user);
    }

    @Override
    public RecordSet getUserRecords(UserRecordGetRequest userRecordGetRequest)
    {
        String categoryId = userRecordGetRequest.getCategoryId();
        String educationStageId = userRecordGetRequest.getEducationStageId();
        String subjectId = userRecordGetRequest.getSubjectId();
        Integer exerciseErrorMeta = userRecordGetRequest.getExerciseErrorMeta();
        Integer pageErrorMeta = userRecordGetRequest.getPageErrorMeta();
        String sourceId = userRecordGetRequest.getSourceId();
        String sourceTypeId = userRecordGetRequest.getSourceTypeId();
        String operateType = userRecordGetRequest.getOperateType();
        String type = userRecordGetRequest.getType();

        String currentUserTenantId = userRecordGetRequest.getCurrentUserTenantId();
        String currentUserId = userRecordGetRequest.getCurrentUserId();

        Integer start = userRecordGetRequest.getStart();
        Integer pageSize = userRecordGetRequest.getPageSize();

        Map<String, Object> params = new HashMap<>();
        params.put("userId", currentUserId);
        params.put("tenantId", currentUserTenantId);
        params.put("categoryId", categoryId);
        params.put("educationStageId", educationStageId);
        params.put("subjectId", subjectId);
        params.put("sourceId", sourceId);
        params.put("sourceTypeId", sourceTypeId);
        params.put("operateType", operateType);
        params.put("type", type);
        params.put("exerciseErrorMeta", exerciseErrorMeta);
        params.put("pageErrorMeta", pageErrorMeta);

        PageHelper.startPage(start, pageSize);
        List<UserRecord> exerciseRecords = userDaoService.getUserRecordsByParams(params);
        PageInfo<UserRecord> pageInfo = new PageInfo<>(exerciseRecords);
        List<Object> recordResults = getResultUserRecords(pageInfo.getList(), type);
        return new RecordSet(start, pageSize, exerciseRecords.size(), recordResults.toArray());
    }

    private List<Object> getResultUserRecords(List<UserRecord> allRecords, String type)
    {
        Map<String, String> operateTypeMap = getOperateTypeMap(type);
        List<Object> resultList = new ArrayList<>();
        for (UserRecord userRecord : allRecords)
        {
            Map<String, Object> result = new HashMap<>();
            result.put("refenceId", userRecord.getRefenceId());
            result.put("operateType", operateTypeMap.get(userRecord.getOperateType()));
            result.put("operateTime", userRecord.getCreateTime());
            resultList.add(result);
        }
        return resultList;
    }

    @Override
    public RecordSet getUserRecordDetail(UserRecordDetailGetRequest userRecordDetailGetRequest)
    {
        return getUserRecordDetails(userRecordDetailGetRequest);
    }

    private RecordSet getUserRecordDetails(UserRecordDetailGetRequest userRecordDetailGetRequest)
    {
        String categoryId = userRecordDetailGetRequest.getCategoryId();
        String educationStageId = userRecordDetailGetRequest.getEducationStageId();
        Date operateTimeEnd = userRecordDetailGetRequest.getOperateTimeEnd();
        Date operateTimeStart = userRecordDetailGetRequest.getOperateTimeStart();
        String operateType = userRecordDetailGetRequest.getOperateType();
        String subjectId = userRecordDetailGetRequest.getSubjectId();
        String type = userRecordDetailGetRequest.getType();
        String userName = userRecordDetailGetRequest.getUserName();

        String tenantId = userRecordDetailGetRequest.getCurrentUserTenantId();

        Integer start = userRecordDetailGetRequest.getStart();
        Integer pageSize = userRecordDetailGetRequest.getPageSize();
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("tenantId", tenantId);
        params.put("categoryId", categoryId);
        params.put("educationStageId", educationStageId);
        params.put("subjectId", subjectId);
        params.put("operateType", operateType);
        params.put("operateTimeStart", operateTimeStart);
        params.put("operateTimeEnd", operateTimeEnd);

        PageHelper.startPage(start, pageSize);
        List<UserRecord> exerciseRecords = userDaoService.getUserRecordsByParams(params);
        PageInfo<UserRecord> pageInfo = new PageInfo<>(exerciseRecords);
        List<Object> recordResults = getUserRecordDetailResult(pageInfo.getList(), userName, type);
        return new RecordSet(start, pageSize, exerciseRecords.size(), recordResults.toArray());
    }

    private List<Object> getUserRecordDetailResult(List<UserRecord> allRecords, String userName, String type)
    {
        Map<String, String> operateTypeMap = getOperateTypeMap(type);
        List<Object> resultList = new ArrayList<>();
        for (UserRecord userRecord : allRecords)
        {
            User user = userDaoService.getUserById(userRecord.getUserId());
            if (TextUtil.isEmpty(userName) || userName.equals(user.getName()))
            {
                Map<String, Object> result = new HashMap<>();
                result.put("refenceId", userRecord.getRefenceId());
                result.put("userName", user.getName());
                result.put("operateType", operateTypeMap.get(userRecord.getOperateType()));
                result.put("operateTime", userRecord.getCreateTime());
                resultList.add(result);
            }
        }
        return resultList;
    }

    private Map<String, String> getOperateTypeMap(String type)
    {
        Map<String, String> operateTypeMap = new HashMap<>();
        String name;
        if ("EXERCISE".equals(type))
        {
            name = "题目";
        } else if ("PAPER".equals(type))
        {
            name = "试卷";
        } else
        {
            name = "";
        }
        operateTypeMap.put("SELECT", "读取" + name);
        operateTypeMap.put("UPDATE", "编辑" + name);
        operateTypeMap.put("INSERT", "新建" + name);
        operateTypeMap.put("DELETE", "删除" + name);
        return operateTypeMap;
    }

    @Override
    public RecordSet exportUserRecordDetail(UserRecordDetailGetRequest userRecordDetailGetRequest) throws Exception
    {
        String tenantId = userRecordDetailGetRequest.getCurrentUserTenantId();
        String currentUserId = userRecordDetailGetRequest.getCurrentUserId();
        List<ExcelExportFormatEntity> excelExportFormatEntityList = new ArrayList<>();
        RecordSet recordSet = getUserRecordDetails(userRecordDetailGetRequest);
        if (recordSet.getTotalCount() > 0)
        {
            ExcelExportFormatEntity excelExportFormatEntity = new ExcelExportFormatEntity();
            excelExportFormatEntity.setSheetName("工作记录");
            List<List<String>> dataList = new ArrayList<>();
            List<String> line = new ArrayList<>();
            //            line.add("操作");
            line.add("操作时间");
            line.add("人员");
            line.add("操作类型");
            line.add("相关id");
            dataList.add(line);
            Map<String, Object> resMap;
            for (Object object : recordSet.getValues())
            {
                resMap = (Map<String, Object>) object;
                dataList = new ArrayList<>();
                line = new ArrayList<>();
                line.add(DateTimeUtil.formatDate((Date) resMap.get("operateTime")));
                line.add((String) resMap.get("userName"));
                line.add((String) resMap.get("operateType"));
                line.add((String) resMap.get("refenceId"));
                dataList.add(line);
            }
            excelExportFormatEntity.setDataList(dataList);
            excelExportFormatEntityList.add(excelExportFormatEntity);
        }
        File file = ExcelUtil.exportExcelByStringData("工作记录", excelExportFormatEntityList);
        String filename = file.getName();
        String now = System.currentTimeMillis() + "";
        String url = uploadService.uploadResource(tenantId, PartnerTypeEnum.RECORD.getName(), PartnerTypeEnum.RECORD.getValue(), FileUtil.file2byte(file), tenantId + "/" + currentUserId + "/temp/" + now, filename);
        if (!TextUtil.isEmpty(url))
        {
            url = url + "?" + now;
        }
        if (file.exists())
        {
            file.delete();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("url", url);
        return new RecordSet(0, 1, 1, new Object[]{map});
    }

    @Override
    public RecordSet getUserPreferenceDetail(UserPreferenceDetailGetRequest userPreferenceDetailGetRequest)
    {
        String userId = userPreferenceDetailGetRequest.getCurrentUserId();
        List<UserPreference> userPreferences = userDaoService.getUserPreferenceByUserId(userId);
        return new RecordSet(0, 1, 1, userPreferences.toArray());
    }

    @Override
    public Map<String, Object> updateUserPreference(UserPreferenceUpdateRequest userPreferenceUpdateRequest)
    {
        String id = userPreferenceUpdateRequest.getId();
        Boolean autoBackgroundPicture = userPreferenceUpdateRequest.getAutoBackgroundPicture();
        Boolean retainCheckInfo = userPreferenceUpdateRequest.getRetainCheckInfo();
        Boolean retainExerciseType = userPreferenceUpdateRequest.getRetainExerciseType();
        Boolean retainSubjectStage = userPreferenceUpdateRequest.getRetainSubjectStage();
        UserPreference userPreference = userDaoService.getUserPreferenceById(id);
        if (!TextUtil.isEmpty(autoBackgroundPicture))
        {
            userPreference.setAutoBackgroundPicture(autoBackgroundPicture);
        }
        if (!TextUtil.isEmpty(retainCheckInfo))
        {
            userPreference.setRetainCheckInfo(retainCheckInfo);
        }
        if (!TextUtil.isEmpty(retainExerciseType))
        {
            userPreference.setRetainExerciseType(retainExerciseType);
        }
        if (!TextUtil.isEmpty(retainSubjectStage))
        {
            userPreference.setRetainSubjectStage(retainSubjectStage);
        }
        userPreference.setUpdateTime(new Date());
        userDaoService.updateUserPreference(userPreference);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", id);
        return resultMap;
    }

    @Override
    public Map<String, Object> createUserPreference(UserPreferenceCreateRequest userPreferenceCreateRequest)
    {
        Boolean autoBackgroundPicture = userPreferenceCreateRequest.getAutoBackgroundPicture();
        Boolean retainCheckInfo = userPreferenceCreateRequest.getRetainCheckInfo();
        Boolean retainExerciseType = userPreferenceCreateRequest.getRetainExerciseType();
        Boolean retainSubjectStage = userPreferenceCreateRequest.getRetainSubjectStage();
        String tenantId = userPreferenceCreateRequest.getCurrentUserTenantId();
        String userId = userPreferenceCreateRequest.getCurrentUserId();
        String newId = PrimaryKeyUtil.nextId(
                ServiceIdEnum.USER.getIsPublic(),
                tenantId,
                ServiceIdEnum.USER.getServiceId());
        Date now = new Date();
        UserPreference userPreference = new UserPreference();
        userPreference.setId(newId);
        userPreference.setAutoBackgroundPicture(autoBackgroundPicture);
        userPreference.setRetainCheckInfo(retainCheckInfo);
        userPreference.setRetainExerciseType(retainExerciseType);
        userPreference.setRetainSubjectStage(retainSubjectStage);
        userPreference.setUserId(userId);
        userPreference.setUpdateTime(now);
        userPreference.setCreateTime(now);
        userDaoService.createUserPreference(userPreference);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", newId);
        return resultMap;
    }

}
