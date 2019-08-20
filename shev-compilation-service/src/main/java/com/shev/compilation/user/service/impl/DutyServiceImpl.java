package com.shev.compilation.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shev.compilation.common.Enum.ServiceIdEnum;
import com.shev.compilation.common.support.web.RecordSet;
import com.shev.compilation.common.util.PrimaryKeyUtil;
import com.shev.compilation.common.util.TextUtil;
import com.shev.compilation.user.dao.service.UserDaoService;
import com.shev.compilation.user.entity.Duty;
import com.shev.compilation.user.entity.DutyPermissionRelation;
import com.shev.compilation.user.entity.Permission;
import com.shev.compilation.user.entity.User;
import com.shev.compilation.user.request.*;
import com.shev.compilation.user.service.DutyService;
import com.shev.compilation.user.task.UserAsyncTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class DutyServiceImpl implements DutyService
{

    private static final Logger logger = LoggerFactory.getLogger(DutyServiceImpl.class);

    @Autowired
    private UserDaoService userDaoService;

    @Autowired
    private UserAsyncTask asyncTask;

    @Override
    public RecordSet getDuties(DutyGetRequest dutyGetRequest)
    {
        String name = dutyGetRequest.getName();
        Boolean status = dutyGetRequest.getStatus();
        Date updateTimeEnd = dutyGetRequest.getUpdateTimeEnd();
        Date updateTimeStart = dutyGetRequest.getUpdateTimeStart();
        String updateUserId = dutyGetRequest.getUpdateUserId();

        String currentUserTenantId = dutyGetRequest.getCurrentUserTenantId();
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("updateTimeStart", updateTimeStart);
        params.put("updateTimeEnd", updateTimeEnd);
        params.put("updateUserId", updateUserId);
        params.put("tenantId", currentUserTenantId);
        params.put("status", status);

        Integer start = dutyGetRequest.getStart();
        Integer pageSize = dutyGetRequest.getPageSize();
        PageHelper.startPage(start, pageSize);
        List<Duty> duties = userDaoService.getDutiesByParams(params);
        PageInfo<Duty> pageInfo = new PageInfo<>(duties);
        List<Object> resultList = getDutyResults(pageInfo.getList());
        return new RecordSet(start, pageSize, duties.size(), resultList.toArray());
    }

    @Override
    public RecordSet getDutyDetail(DutyDetailGetRequest dutyDetailGetRequest)
    {
        String id = dutyDetailGetRequest.getId();
        Duty duty = userDaoService.getDutyById(id);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", duty.getId());
        resultMap.put("name", duty.getName());
        resultMap.put("status", duty.getStatus());
        List<DutyPermissionRelation> dutyPermissionRelations = userDaoService.getDutyPermissionRelationsByDutyId(id);
        Map<String, Object> permissionMap = new HashMap<>();
        if (!TextUtil.isEmpty(dutyPermissionRelations))
        {
            for (DutyPermissionRelation dutyPermissionRelation : dutyPermissionRelations)
            {
                Permission permission = userDaoService.getPermissionById(dutyPermissionRelation.getPermissionId());
                permissionMap.put("id", permission.getId());
                permissionMap.put("name", permission.getDescription());
                permissionMap.put("url", permission.getUrl());
            }
        }
        resultMap.put("permissionInfo", permissionMap);
        return new RecordSet(0, 1, 1, new Object[]{resultMap});
    }

    private List<Object> getDutyResults(List<Duty> duties)
    {
        List<Object> resultList = new ArrayList<>();
        User updateUser;
        for (Duty duty : duties)
        {
            Map<String, Object> result = new HashMap<>();
            result.put("id", duty.getId());
            result.put("name", duty.getName());
            result.put("status", duty.getStatus());
            updateUser = userDaoService.getUserById(duty.getUpdateUserId());
            result.put("updateUserName", updateUser.getName());
            result.put("updateTime", duty.getUpdateTime());
            resultList.add(result);
        }
        return resultList;
    }

    @Override
    public Map<String, Object> updateDuty(DutyUpdateRequest dutyUpdateRequest)
    {
        String id = dutyUpdateRequest.getId();
        String name = dutyUpdateRequest.getName();
        Boolean status = dutyUpdateRequest.getStatus();
        List<String> permissionIds = dutyUpdateRequest.getPermissionIds();
        String currentUserId = dutyUpdateRequest.getCurrentUserId();
        Duty duty = userDaoService.getDutyById(id);
        if (!TextUtil.isEmpty(name))
        {
            duty.setName(name);
        }
        if (!TextUtil.isEmpty(status))
        {
            duty.setStatus(status);
        }
        Date now = new Date();
        duty.setUpdateTime(now);
        duty.setUpdateUserId(currentUserId);
        userDaoService.updateDuty(duty);

        deleteDutyPermissionRelation(id, currentUserId);
        createDutyPermissionRelation(id, permissionIds, currentUserId, dutyUpdateRequest.getCurrentUserTenantId(), now);

        asyncTask.refreshDutyPermissionCache(id);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", id);
        return resultMap;
    }

    private void deleteDutyPermissionRelation(String id, String currentUserId)
    {
        List<DutyPermissionRelation> dutyPermissionRelations = userDaoService.getDutyPermissionRelationsByDutyId(id);
        if (!TextUtil.isEmpty(dutyPermissionRelations))
        {
            for (DutyPermissionRelation dutyPermissionRelation : dutyPermissionRelations)
            {
                userDaoService.deleteDutyPermissionRelation(dutyPermissionRelation, currentUserId);
            }
        }
    }

    private void createDutyPermissionRelation(String id, List<String> permissionIds, String currentUserId, String tenantId, Date now)
    {
        if (!TextUtil.isEmpty(permissionIds))
        {
            String newId;
            for (String permissionId : permissionIds)
            {
                DutyPermissionRelation dutyPermissionRelation = new DutyPermissionRelation();
                newId = PrimaryKeyUtil.nextId(
                        ServiceIdEnum.USER.getIsPublic(),
                        tenantId,
                        ServiceIdEnum.USER.getServiceId());
                dutyPermissionRelation.setId(newId);
                dutyPermissionRelation.setDutyId(id);
                dutyPermissionRelation.setPermissionId(permissionId);
                dutyPermissionRelation.setCreateTime(now);
                dutyPermissionRelation.setUpdateTime(now);
                dutyPermissionRelation.setUpdateUserId(currentUserId);
                dutyPermissionRelation.setCreateUserId(currentUserId);
                userDaoService.createDutyPermissionRelation(dutyPermissionRelation);
            }
        }
    }

    @Override
    public Map<String, Object> createDuty(DutyCreateRequest dutyCreateRequest)
    {
        String name = dutyCreateRequest.getName();
        Boolean status = dutyCreateRequest.getStatus();
        List<String> permissionIds = dutyCreateRequest.getPermissionIds();

        String currentUserId = dutyCreateRequest.getCurrentUserId();
        String tenantId = dutyCreateRequest.getCurrentUserTenantId();
        Date now = new Date();
        Duty duty = new Duty();
        String newId = PrimaryKeyUtil.nextId(
                ServiceIdEnum.USER.getIsPublic(),
                tenantId,
                ServiceIdEnum.USER.getServiceId());
        duty.setId(newId);
        duty.setStatus(status);
        duty.setName(name);
        duty.setTenantId(tenantId);
        duty.setUpdateTime(now);
        duty.setCreateTime(now);
        duty.setUpdateUserId(currentUserId);
        duty.setCreateUserId(currentUserId);
        userDaoService.createDuty(duty);

        createDutyPermissionRelation(newId, permissionIds, currentUserId, tenantId, now);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", newId);
        return resultMap;
    }

    @Override
    public void deleteDuty(DutyDeleteRequest dutyDeleteRequest)
    {
        String id = dutyDeleteRequest.getId();
        String currentUserId = dutyDeleteRequest.getCurrentUserId();
        Duty duty = userDaoService.getDutyById(id);
        userDaoService.deleteDuty(duty, currentUserId);
        deleteDutyPermissionRelation(id, currentUserId);

        asyncTask.refreshDutyPermissionCache(id);
    }
}
