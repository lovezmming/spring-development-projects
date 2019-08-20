package com.shev.compilation.edumeta.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shev.compilation.common.Enum.CacheKeyEnum;
import com.shev.compilation.common.Enum.CacheNameEnum;
import com.shev.compilation.common.Enum.ServiceIdEnum;
import com.shev.compilation.common.support.compare.SeqAscComparator;
import com.shev.compilation.common.support.compare.SeqDescComparator;
import com.shev.compilation.common.support.compare.UpdateTimeAscComparator;
import com.shev.compilation.common.support.compare.UpdateTimeDescComparator;
import com.shev.compilation.common.support.web.RecordSet;
import com.shev.compilation.common.util.PrimaryKeyUtil;
import com.shev.compilation.common.util.TextUtil;
import com.shev.compilation.edumeta.dao.service.EdumetaDaoService;
import com.shev.compilation.edumeta.entity.*;
import com.shev.compilation.edumeta.request.*;
import com.shev.compilation.edumeta.service.EdumetaService;
import com.shev.compilation.edumeta.task.EdumetaAsyncTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@CacheConfig(cacheNames = "edumeta")
public class EdumetaServiceImpl implements EdumetaService
{

    private static final Logger logger = LoggerFactory.getLogger(EdumetaServiceImpl.class);

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private EdumetaAsyncTask edumetaAsyncTask;

    @Autowired
    private EdumetaDaoService edumetaDaoService;

    @Override
    public RecordSet getSubjectList(SubjectGetRequest request)
    {
        String subjectName = request.getName();
        Boolean status = request.getStatus();
        String updateUserId = request.getUpdateUserId();
        Date updateTimeStart = request.getUpdateTimeStart();
        Date updateTimeEnd = request.getUpdateTimeEnd();
        String tenantId = request.getCurrentUserTenantId();
        Integer start = request.getStart();
        Integer pageSize = request.getPageSize();
        String sortType = request.getSortType();
        String sortOrder = request.getSortOrder();

        Map<String, Object> params = new HashMap<>();
        params.put("tenantId", tenantId);
        params.put("name", subjectName);
        params.put("status", status);
        params.put("updateUserId", updateUserId);
        params.put("updateTimeStart", updateTimeStart);
        params.put("updateTimeEnd", updateTimeEnd);

        PageHelper.startPage(start, pageSize);
        RecordSet recordSet = edumetaDaoService.getSubjectsByParams(params, sortResult(sortType, sortOrder));
        PageInfo<Object> pageInfo = new PageInfo<>(new ArrayList<>(Arrays.asList(recordSet.getValues())));

        Cache cache = cacheManager.getCache(CacheNameEnum.ACCOUNT.getName());
        List<Object> resultList = new ArrayList<>();
        for (Object object : pageInfo.getList())
        {
            Subject subject = (Subject) object;
            setResult(cache, resultList, subject.getId(), subject.getName(), subject.getStatus(), subject.getSeq(), subject.getCoreId(), subject.getUpdateUserId(), subject.getUpdateTime());
        }
        return new RecordSet(start, pageSize, recordSet.getTotalCount(), resultList.toArray());
    }

    @Override
    @Cacheable(key = "'subject:' + #request.id")
    public RecordSet getSubjectDetail(SubjectDetailGetRequest request)
    {
        Subject subject = edumetaDaoService.getSubjectById(request.getId());
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", subject.getId());
        resultMap.put("name", subject.getName());
        resultMap.put("seq", subject.getSeq());
        resultMap.put("coreId", subject.getCoreId());
        resultMap.put("status", subject.getStatus());
        return new RecordSet(0, 1, 1, new Object[]{resultMap});
    }

    @Override
    public Map<String, Object> updateSubject(SubjectUpdateRequest subjectUpdateRequest)
    {
        String id = subjectUpdateRequest.getId();
        String name = subjectUpdateRequest.getName();
        Integer seq = subjectUpdateRequest.getSeq();
        Integer coreId = subjectUpdateRequest.getCoreId();
        Boolean status = subjectUpdateRequest.getStatus();

        String currUserId = subjectUpdateRequest.getCurrentUserId();

        Subject subject = edumetaDaoService.getSubjectById(id);
        if (!TextUtil.isEmpty(name))
        {
            subject.setName(name);
        }
        if (!TextUtil.isEmpty(seq))
        {
            subject.setSeq(seq);
        }
        if (!TextUtil.isEmpty(coreId))
        {
            subject.setCoreId(coreId);
        }
        if (!TextUtil.isEmpty(status))
        {
            subject.setStatus(status);
        }
        subject.setUpdateTime(new Date());
        subject.setUpdateUserId(currUserId);
        edumetaDaoService.updateSubject(subject);

        edumetaAsyncTask.refreshEdumetaCache(CacheKeyEnum.SUBJECT.getKey() + id, subject);

        Map<String, Object> result = new HashMap<>();
        result.put("id", id);
        return result;
    }

    @Override
    public Map<String, Object> createSubject(SubjectCreateRequest subjectCreateRequest)
    {
        String name = subjectCreateRequest.getName();
        Integer seq = subjectCreateRequest.getSeq();
        Integer coreId = subjectCreateRequest.getCoreId();
        Boolean status = subjectCreateRequest.getStatus();

        String currTenantId = subjectCreateRequest.getCurrentUserTenantId();
        String currUserId = subjectCreateRequest.getCurrentUserId();

        Subject subject = new Subject();
        String newId = PrimaryKeyUtil.nextId(
                ServiceIdEnum.EDUMETA.getIsPublic(),
                currTenantId,
                ServiceIdEnum.EDUMETA.getServiceId());
        subject.setId(newId);
        subject.setName(name);
        subject.setSeq(seq);
        subject.setCoreId(coreId);
        subject.setStatus(status);
        subject.setTenantId(currTenantId);
//        subject.setCoreId(0);
        Date now = new Date();
        subject.setCreateTime(now);
        subject.setUpdateTime(now);
        subject.setUpdateUserId(currUserId);
        subject.setUpdateUserId(currUserId);
        edumetaDaoService.createSubject(subject);

        edumetaAsyncTask.refreshEdumetaCache(CacheKeyEnum.SUBJECT.getKey() + newId, subject);

        Map<String, Object> result = new HashMap<>();
        result.put("id", newId);
        return result;
    }

    @Override
    public RecordSet getEducationalStages(EducationalStageGetRequest educationalStageGetRequest)
    {
        String name = educationalStageGetRequest.getName();
        Boolean status = educationalStageGetRequest.getStatus();
        Date updateTimeStart = educationalStageGetRequest.getUpdateTimeStart();
        Date updateTimeEnd = educationalStageGetRequest.getUpdateTimeEnd();
        String updateUserId = educationalStageGetRequest.getUpdateUserId();
        String tenantId = educationalStageGetRequest.getCurrentUserTenantId();
        String sortType = educationalStageGetRequest.getSortType();
        String sortOrder = educationalStageGetRequest.getSortOrder();
        Map<String, Object> params = new HashMap<>();
        params.put("tenantId", tenantId);
        params.put("name", name);
        params.put("status", status);
        params.put("updateTimeStart", updateTimeStart);
        params.put("updateTimeEnd", updateTimeEnd);
        params.put("updateUserId", updateUserId);
        Integer start = educationalStageGetRequest.getStart();
        Integer pageSize = educationalStageGetRequest.getPageSize();

        PageHelper.startPage(start, pageSize);
        RecordSet recordSet = edumetaDaoService.getEducationalStagesByParams(params, sortResult(sortType, sortOrder));
        PageInfo<Object> pageInfo = new PageInfo<>(new ArrayList<>(Arrays.asList(recordSet.getValues())));

        Cache cache = cacheManager.getCache(CacheNameEnum.ACCOUNT.getName());
        List<Object> resultList = new ArrayList<>();
        for (Object object : pageInfo.getList())
        {
            EducationalStage educationalStage = (EducationalStage) object;
            setResult(cache, resultList, educationalStage.getId(), educationalStage.getName(), educationalStage.getStatus(), educationalStage.getSeq(), educationalStage.getCoreId(), educationalStage.getUpdateUserId(), educationalStage.getUpdateTime());
        }
        return new RecordSet(start, pageSize, recordSet.getTotalCount(), resultList.toArray());
    }

    @Override
    @Cacheable(key = "'educationalstage:' + #request.id")
    public RecordSet getEducationalStageDetail(EducationalStageDetailGetRequest request)
    {
        String id = request.getId();
        EducationalStage educationalStage = edumetaDaoService.getEducationalStageById(id);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", educationalStage.getId());
        resultMap.put("name", educationalStage.getName());
        resultMap.put("seq", educationalStage.getSeq());
        resultMap.put("coreId", educationalStage.getCoreId());
        resultMap.put("status", educationalStage.getStatus());
        return new RecordSet(0, 1, 1, new Object[]{resultMap});
    }

    @Override
    public Map<String, Object> updateEducationalStage(EducationalStageUpdateRequest educationalStageUpdateRequest)
    {
        String id = educationalStageUpdateRequest.getId();
        String name = educationalStageUpdateRequest.getName();
        Integer seq = educationalStageUpdateRequest.getSeq();
        Integer coreId = educationalStageUpdateRequest.getCoreId();
        Boolean status = educationalStageUpdateRequest.getStatus();
        String currUserId = educationalStageUpdateRequest.getCurrentUserId();

        EducationalStage educationalStage = edumetaDaoService.getEducationalStageById(id);
        if (!TextUtil.isEmpty(coreId))
        {
            educationalStage.setCoreId(coreId);
        }
        if (!TextUtil.isEmpty(name))
        {
            educationalStage.setName(name);
        }
        if (!TextUtil.isEmpty(seq))
        {
            educationalStage.setSeq(seq);
        }
        if (!TextUtil.isEmpty(status))
        {
            educationalStage.setStatus(status);
        }
        educationalStage.setUpdateUserId(currUserId);
        educationalStage.setUpdateTime(new Date());
        edumetaDaoService.updateEducationalStage(educationalStage);

        edumetaAsyncTask.refreshEdumetaCache(CacheKeyEnum.EDUCATIONALSTAGE.getKey() + id, educationalStage);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", id);
        return resultMap;
    }

    @Override
    public Map<String, Object> createEducationalStage(EducationalStageCreateRequest educationalStageCreateRequest)
    {
        String name = educationalStageCreateRequest.getName();
        Integer seq = educationalStageCreateRequest.getSeq();
        Integer coreId = educationalStageCreateRequest.getCoreId();
        Boolean status = educationalStageCreateRequest.getStatus();
        String currUserId = educationalStageCreateRequest.getCurrentUserId();
        String currTenantId = educationalStageCreateRequest.getCurrentUserTenantId();

        EducationalStage educationalStage = new EducationalStage();
        String newId = PrimaryKeyUtil.nextId(
                ServiceIdEnum.EDUMETA.getIsPublic(),
                currTenantId,
                ServiceIdEnum.EDUMETA.getServiceId());
        educationalStage.setId(newId);
        educationalStage.setStatus(status);
        educationalStage.setSeq(seq);
        educationalStage.setCoreId(coreId);
        educationalStage.setTenantId(currTenantId);
        educationalStage.setName(name);
        Date now = new Date();
        educationalStage.setCreateTime(now);
        educationalStage.setUpdateTime(now);
        educationalStage.setCreateUserId(currUserId);
        educationalStage.setUpdateUserId(currUserId);
        edumetaDaoService.createEducationalStage(educationalStage);

        edumetaAsyncTask.refreshEdumetaCache(CacheKeyEnum.EDUCATIONALSTAGE.getKey() + newId, educationalStage);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", newId);
        return resultMap;
    }

    @Override
    public RecordSet getDifficultys(DifficultyGetRequest difficultyGetRequest)
    {
        String name = difficultyGetRequest.getName();
        Boolean status = difficultyGetRequest.getStatus();
        Date updateTimeStart = difficultyGetRequest.getUpdateTimeStart();
        Date updateTimeEnd = difficultyGetRequest.getUpdateTimeEnd();
        String updateUserId = difficultyGetRequest.getUpdateUserId();
        String tenantId = difficultyGetRequest.getCurrentUserTenantId();
        Map<String, Object> params = new HashMap<>();
        params.put("tenantId", tenantId);
        params.put("name", name);
        params.put("status", status);
        params.put("updateTimeStart", updateTimeStart);
        params.put("updateTimeEnd", updateTimeEnd);
        params.put("updateUserId", updateUserId);
        Integer start = difficultyGetRequest.getStart();
        Integer pageSize = difficultyGetRequest.getPageSize();
        String sortType = difficultyGetRequest.getSortType();
        String sortOrder = difficultyGetRequest.getSortOrder();

        PageHelper.startPage(start, pageSize);
        RecordSet recordSet = edumetaDaoService.getDifficultiesByParams(params, sortResult(sortType, sortOrder));
        PageInfo<Object> pageInfo = new PageInfo<>(new ArrayList<>(Arrays.asList(recordSet.getValues())));

        Cache cache = cacheManager.getCache(CacheNameEnum.ACCOUNT.getName());
        List<Object> resultList = new ArrayList<>();
        for (Object object : pageInfo.getList())
        {
            Difficulty difficulty = (Difficulty) object;
            setResult(cache, resultList, difficulty.getId(), difficulty.getName(), difficulty.getStatus(), difficulty.getSeq(), difficulty.getCoreId(), difficulty.getUpdateUserId(), difficulty.getUpdateTime());
        }

        return new RecordSet(start, pageSize, recordSet.getTotalCount(), resultList.toArray());
    }

    @Override
    @Cacheable(key = "'difficulty:' + #request.id")
    public RecordSet getDifficultyDetail(DifficultyDetailGetRequest request)
    {
        String id = request.getId();
        Difficulty difficulty = edumetaDaoService.getDifficultyById(id);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", difficulty.getId());
        resultMap.put("name", difficulty.getName());
        resultMap.put("seq", difficulty.getSeq());
        resultMap.put("coreId", difficulty.getCoreId());
        resultMap.put("status", difficulty.getStatus());
        return new RecordSet(0, 1, 1, new Object[]{resultMap});
    }

    @Override
    public Map<String, Object> updateDifficulty(DifficultyUpdateRequest difficultyUpdateRequest)
    {
        String id = difficultyUpdateRequest.getId();
        String name = difficultyUpdateRequest.getName();
        Integer seq = difficultyUpdateRequest.getSeq();
        Integer coreId = difficultyUpdateRequest.getCoreId();
        Boolean status = difficultyUpdateRequest.getStatus();

        String currUserId = difficultyUpdateRequest.getCurrentUserId();

        Difficulty difficulty = edumetaDaoService.getDifficultyById(id);
        if (!TextUtil.isEmpty(name))
        {
            difficulty.setName(name);
        }
        if (!TextUtil.isEmpty(seq))
        {
            difficulty.setSeq(seq);
        }
        if (!TextUtil.isEmpty(status))
        {
            difficulty.setStatus(status);
        }
        if (!TextUtil.isEmpty(coreId))
        {
            difficulty.setCoreId(coreId);
        }
        difficulty.setUpdateTime(new Date());
        difficulty.setUpdateUserId(currUserId);
        edumetaDaoService.updateDifficulty(difficulty);

        edumetaAsyncTask.refreshEdumetaCache(CacheKeyEnum.DIFFICULTY.getKey() + id, difficulty);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", id);
        return resultMap;
    }

    @Override
    public Map<String, Object> createDifficulty(DifficultyCreateRequest difficultyCreateRequest)
    {
        String name = difficultyCreateRequest.getName();
        Integer seq = difficultyCreateRequest.getSeq();
        Integer coreId = difficultyCreateRequest.getCoreId();
        Boolean status = difficultyCreateRequest.getStatus();
        String currUserId = difficultyCreateRequest.getCurrentUserId();
        String currTenantId = difficultyCreateRequest.getCurrentUserTenantId();

        Difficulty difficulty = new Difficulty();
        String id = PrimaryKeyUtil.nextId(
                ServiceIdEnum.EDUMETA.getIsPublic(),
                currTenantId,
                ServiceIdEnum.EDUMETA.getServiceId());
        difficulty.setId(id);
        difficulty.setStatus(status);
        difficulty.setSeq(seq);
        difficulty.setCoreId(coreId);
        difficulty.setTenantId(currTenantId);
        difficulty.setName(name);
        Date now = new Date();
        difficulty.setCreateTime(now);
        difficulty.setUpdateTime(now);
        difficulty.setCreateUserId(currUserId);
        difficulty.setUpdateUserId(currUserId);
        edumetaDaoService.createDifficulty(difficulty);

        edumetaAsyncTask.refreshEdumetaCache(CacheKeyEnum.DIFFICULTY.getKey() + id, difficulty);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", id);
        return resultMap;
    }

    @Override
    public RecordSet getFormTypes(FormTypeGetRequest formTypeGetRequest)
    {
        String name = formTypeGetRequest.getName();
        Boolean status = formTypeGetRequest.getStatus();
        Date updateTimeStart = formTypeGetRequest.getUpdateTimeStart();
        Date updateTimeEnd = formTypeGetRequest.getUpdateTimeEnd();
        String updateUserId = formTypeGetRequest.getUpdateUserId();
        String tenantId = formTypeGetRequest.getCurrentUserTenantId();
        Map<String, Object> params = new HashMap<>();
        params.put("tenantId", tenantId);
        params.put("name", name);
        params.put("status", status);
        params.put("updateTimeStart", updateTimeStart);
        params.put("updateTimeEnd", updateTimeEnd);
        params.put("updateUserId", updateUserId);
        Integer start = formTypeGetRequest.getStart();
        Integer pageSize = formTypeGetRequest.getPageSize();
        String sortType = formTypeGetRequest.getSortType();
        String sortOrder = formTypeGetRequest.getSortOrder();

        PageHelper.startPage(start, pageSize);
        RecordSet recordSet = edumetaDaoService.getFormTypesByParams(params, sortResult(sortType, sortOrder));
        PageInfo<Object> pageInfo = new PageInfo<>(new ArrayList<>(Arrays.asList(recordSet.getValues())));

        Cache cache = cacheManager.getCache(CacheNameEnum.ACCOUNT.getName());
        List<Object> resultList = new ArrayList<>();
        for (Object object : pageInfo.getList())
        {
            FormType formType = (FormType) object;
            setResult(cache, resultList, formType.getId(), formType.getName(), formType.getStatus(), formType.getSeq(), formType.getCoreId(), formType.getUpdateUserId(), formType.getUpdateTime());
        }

        return new RecordSet(start, pageSize, recordSet.getTotalCount(), resultList.toArray());
    }

    @Override
    @Cacheable(key = "'formtype:' + #request.id")
    public RecordSet getFormTypeDetail(FormTypeDetailGetRequest request)
    {
        String id = request.getId();
        FormType formType = edumetaDaoService.getFormTypeById(id);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", formType.getId());
        resultMap.put("name", formType.getName());
        resultMap.put("seq", formType.getSeq());
        resultMap.put("coreId", formType.getCoreId());
        resultMap.put("status", formType.getStatus());
        return new RecordSet(0, 1, 1, new Object[]{resultMap});
    }

    @Override
    public Map<String, Object> updateFormType(FormTypeUpdateRequest formTypeUpdateRequest)
    {
        String id = formTypeUpdateRequest.getId();
        String name = formTypeUpdateRequest.getName();
        Integer seq = formTypeUpdateRequest.getSeq();
        Integer coreId = formTypeUpdateRequest.getCoreId();
        Boolean status = formTypeUpdateRequest.getStatus();

        String currUserId = formTypeUpdateRequest.getCurrentUserId();

        FormType formType = edumetaDaoService.getFormTypeById(id);
        if (!TextUtil.isEmpty(name))
        {
            formType.setName(name);
        }
        if (!TextUtil.isEmpty(seq))
        {
            formType.setSeq(seq);
        }
        if (!TextUtil.isEmpty(status))
        {
            formType.setStatus(status);
        }
        if (!TextUtil.isEmpty(coreId))
        {
            formType.setCoreId(coreId);
        }
        formType.setUpdateTime(new Date());
        formType.setUpdateUserId(currUserId);
        edumetaDaoService.updateFormType(formType);

        edumetaAsyncTask.refreshEdumetaCache(CacheKeyEnum.FORMTYPE.getKey() + id, formType);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", id);
        return resultMap;
    }

    @Override
    public Map<String, Object> createFormType(FormTypeCreateRequest formTypeCreateRequest)
    {
        String name = formTypeCreateRequest.getName();
        Integer seq = formTypeCreateRequest.getSeq();
        Integer coreId = formTypeCreateRequest.getCoreId();
        Boolean status = formTypeCreateRequest.getStatus();
        String currUserId = formTypeCreateRequest.getCurrentUserId();
        String currTenantId = formTypeCreateRequest.getCurrentUserTenantId();

        FormType formType = new FormType();
        String id = PrimaryKeyUtil.nextId(
                ServiceIdEnum.EDUMETA.getIsPublic(),
                currTenantId,
                ServiceIdEnum.EDUMETA.getServiceId());
        formType.setId(id);
        formType.setStatus(status);
        formType.setSeq(seq);
        formType.setCoreId(coreId);
        formType.setTenantId(currTenantId);
        formType.setName(name);
        Date now = new Date();
        formType.setCreateTime(now);
        formType.setUpdateTime(now);
        formType.setCreateUserId(currUserId);
        formType.setUpdateUserId(currUserId);
        edumetaDaoService.createFormType(formType);

        edumetaAsyncTask.refreshEdumetaCache(CacheKeyEnum.FORMTYPE.getKey() + id, formType);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", id);
        return resultMap;
    }

    @Override
    public RecordSet getSemanticTypes(SemanticTypeGetRequest semanticTypeGetRequest)
    {
        String name = semanticTypeGetRequest.getName();
        Boolean status = semanticTypeGetRequest.getStatus();
        Date updateTimeStart = semanticTypeGetRequest.getUpdateTimeStart();
        Date updateTimeEnd = semanticTypeGetRequest.getUpdateTimeEnd();
        String updateUserId = semanticTypeGetRequest.getUpdateUserId();
        String tenantId = semanticTypeGetRequest.getCurrentUserTenantId();
        Map<String, Object> params = new HashMap<>();
        params.put("tenantId", tenantId);
        params.put("name", name);
        params.put("status", status);
        params.put("updateTimeStart", updateTimeStart);
        params.put("updateTimeEnd", updateTimeEnd);
        params.put("updateUserId", updateUserId);
        Integer start = semanticTypeGetRequest.getStart();
        Integer pageSize = semanticTypeGetRequest.getPageSize();
        String sortType = semanticTypeGetRequest.getSortType();
        String sortOrder = semanticTypeGetRequest.getSortOrder();

        PageHelper.startPage(start, pageSize);
        RecordSet recordSet = edumetaDaoService.getSemanticTypesByParams(params, sortResult(sortType, sortOrder));
        PageInfo<Object> pageInfo = new PageInfo<>(new ArrayList<>(Arrays.asList(recordSet.getValues())));

        Cache cache = cacheManager.getCache(CacheNameEnum.ACCOUNT.getName());
        List<Object> resultList = new ArrayList<>();
        for (Object object : pageInfo.getList())
        {
            SemanticType semanticType = (SemanticType) object;
            setResult(cache, resultList, semanticType.getId(), semanticType.getName(), semanticType.getStatus(), semanticType.getSeq(), semanticType.getCoreId(), semanticType.getUpdateUserId(), semanticType.getUpdateTime());
        }

        return new RecordSet(start, pageSize, recordSet.getTotalCount(), resultList.toArray());
    }

    @Override
    @Cacheable(key = "'semantictype:' + #request.id")
    public RecordSet getSemanticTypeDetail(SemanticTypeDetailGetRequest request)
    {
        String id = request.getId();
        SemanticType semanticType = edumetaDaoService.getSemanticTypeById(id);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", semanticType.getId());
        resultMap.put("name", semanticType.getName());
        resultMap.put("seq", semanticType.getSeq());
        resultMap.put("coreId", semanticType.getCoreId());
        resultMap.put("status", semanticType.getStatus());
        return new RecordSet(0, 1, 1, new Object[]{resultMap});
    }

    @Override
    public Map<String, Object> updateSemanticType(SemanticTypeUpdateRequest semanticTypeUpdateRequest)
    {
        String id = semanticTypeUpdateRequest.getId();
        String name = semanticTypeUpdateRequest.getName();
        Integer seq = semanticTypeUpdateRequest.getSeq();
        Integer coreId = semanticTypeUpdateRequest.getCoreId();
        Boolean status = semanticTypeUpdateRequest.getStatus();

        String currUserId = semanticTypeUpdateRequest.getCurrentUserId();

        SemanticType semanticType = edumetaDaoService.getSemanticTypeById(id);
        if (!TextUtil.isEmpty(name))
        {
            semanticType.setName(name);
        }
        if (!TextUtil.isEmpty(seq))
        {
            semanticType.setSeq(seq);
        }
        if (!TextUtil.isEmpty(status))
        {
            semanticType.setStatus(status);
        }
        if (!TextUtil.isEmpty(coreId))
        {
            semanticType.setCoreId(coreId);
        }
        semanticType.setUpdateTime(new Date());
        semanticType.setUpdateUserId(currUserId);
        edumetaDaoService.updateSemanticType(semanticType);

        edumetaAsyncTask.refreshEdumetaCache(CacheKeyEnum.SEMANTICTYPE.getKey() + id, semanticType);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", id);
        return resultMap;
    }

    @Override
    public Map<String, Object> createSemanticType(SemanticTypeCreateRequest semanticTypeCreateRequest)
    {
        String name = semanticTypeCreateRequest.getName();
        Integer seq = semanticTypeCreateRequest.getSeq();
        Integer coreId = semanticTypeCreateRequest.getCoreId();
        Boolean status = semanticTypeCreateRequest.getStatus();
        String currUserId = semanticTypeCreateRequest.getCurrentUserId();
        String currTenantId = semanticTypeCreateRequest.getCurrentUserTenantId();

        SemanticType semanticType = new SemanticType();
        String id = PrimaryKeyUtil.nextId(
                ServiceIdEnum.EDUMETA.getIsPublic(),
                currTenantId,
                ServiceIdEnum.EDUMETA.getServiceId());
        semanticType.setId(id);
        semanticType.setStatus(status);
        semanticType.setSeq(seq);
        semanticType.setCoreId(coreId);
        semanticType.setTenantId(currTenantId);
        semanticType.setName(name);
        Date now = new Date();
        semanticType.setCreateTime(now);
        semanticType.setUpdateTime(now);
        semanticType.setCreateUserId(currUserId);
        semanticType.setUpdateUserId(currUserId);
        edumetaDaoService.createSemanticType(semanticType);

        edumetaAsyncTask.refreshEdumetaCache(CacheKeyEnum.SEMANTICTYPE.getKey() + id, semanticType);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", id);
        return resultMap;
    }

    @Override
    public RecordSet getSignificances(SignificanceGetRequest significanceGetRequest)
    {
        String name = significanceGetRequest.getName();
        Boolean status = significanceGetRequest.getStatus();
        Date updateTimeStart = significanceGetRequest.getUpdateTimeStart();
        Date updateTimeEnd = significanceGetRequest.getUpdateTimeEnd();
        String updateUserId = significanceGetRequest.getUpdateUserId();
        String tenantId = significanceGetRequest.getCurrentUserTenantId();
        String type = significanceGetRequest.getType();
        Map<String, Object> params = new HashMap<>();
        params.put("tenantId", tenantId);
        params.put("name", name);
        params.put("type", type);
        params.put("status", status);
        params.put("updateTimeStart", updateTimeStart);
        params.put("updateTimeEnd", updateTimeEnd);
        params.put("updateUserId", updateUserId);
        Integer start = significanceGetRequest.getStart();
        Integer pageSize = significanceGetRequest.getPageSize();
        String sortType = significanceGetRequest.getSortType();
        String sortOrder = significanceGetRequest.getSortOrder();

        PageHelper.startPage(start, pageSize);
        RecordSet recordSet = edumetaDaoService.getSignificancesByParams(params, sortResult(sortType, sortOrder));
        PageInfo<Object> pageInfo = new PageInfo<>(new ArrayList<>(Arrays.asList(recordSet.getValues())));

        Cache cache = cacheManager.getCache(CacheNameEnum.ACCOUNT.getName());
        List<Object> resultList = new ArrayList<>();
        for (Object object : pageInfo.getList())
        {
            Significance significance = (Significance) object;
            setResult(cache, resultList, significance.getId(), significance.getName(), significance.getStatus(), significance.getSeq(), significance.getCoreId(), significance.getUpdateUserId(), significance.getUpdateTime());
        }

        return new RecordSet(start, pageSize, recordSet.getTotalCount(), resultList.toArray());
    }

    @Override
    @Cacheable(key = "'significance:' + #request.id")
    public RecordSet getSignificanceDetail(SignificanceDetailGetRequest request)
    {
        String id = request.getId();
        Significance significance = edumetaDaoService.getSignificanceById(id);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", significance.getId());
        resultMap.put("name", significance.getName());
        resultMap.put("seq", significance.getSeq());
        resultMap.put("coreId", significance.getCoreId());
        resultMap.put("status", significance.getStatus());
        return new RecordSet(0, 1, 1, new Object[]{resultMap});
    }

    @Override
    public Map<String, Object> updateSignificance(SignificanceUpdateRequest significanceUpdateRequest)
    {
        String id = significanceUpdateRequest.getId();
        String name = significanceUpdateRequest.getName();
        Integer seq = significanceUpdateRequest.getSeq();
        Integer coreId = significanceUpdateRequest.getCoreId();
        Boolean status = significanceUpdateRequest.getStatus();
        String type = significanceUpdateRequest.getType();

        String currUserId = significanceUpdateRequest.getCurrentUserId();

        Significance significance = edumetaDaoService.getSignificanceById(id);
        if (!TextUtil.isEmpty(name))
        {
            significance.setName(name);
        }
        if (!TextUtil.isEmpty(seq))
        {
            significance.setSeq(seq);
        }
        if (!TextUtil.isEmpty(status))
        {
            significance.setStatus(status);
        }
        if (!TextUtil.isEmpty(coreId))
        {
            significance.setCoreId(coreId);
        }
        if (!TextUtil.isEmpty(type))
        {
            significance.setType(type);
        }
        significance.setUpdateTime(new Date());
        significance.setUpdateUserId(currUserId);
        edumetaDaoService.updateSignificance(significance);

        edumetaAsyncTask.refreshEdumetaCache(CacheKeyEnum.SIGNIFICANCE.getKey() + id, significance);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", id);
        return resultMap;
    }

    @Override
    public Map<String, Object> createSignificance(SignificanceCreateRequest request)
    {
        String name = request.getName();
        Integer seq = request.getSeq();
        Integer coreId = request.getCoreId();
        Boolean status = request.getStatus();
        String currUserId = request.getCurrentUserId();
        String currTenantId = request.getCurrentUserTenantId();
        String type = request.getType();

        Significance significance = new Significance();
        String id = PrimaryKeyUtil.nextId(
                ServiceIdEnum.EDUMETA.getIsPublic(),
                currTenantId,
                ServiceIdEnum.EDUMETA.getServiceId());
        significance.setId(id);
        significance.setStatus(status);
        significance.setSeq(seq);
        significance.setCoreId(coreId);
        significance.setTenantId(currTenantId);
        significance.setName(name);
        significance.setType(type);
        Date now = new Date();
        significance.setCreateTime(now);
        significance.setUpdateTime(now);
        significance.setCreateUserId(currUserId);
        significance.setUpdateUserId(currUserId);
        edumetaDaoService.createSignificance(significance);

        edumetaAsyncTask.refreshEdumetaCache(CacheKeyEnum.SIGNIFICANCE.getKey() + id, significance);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", id);
        return resultMap;
    }

    @Override
    public RecordSet getCompleteness(CompletenessGetRequest request)
    {
        String name = request.getName();
        Boolean status = request.getStatus();
        Date updateTimeStart = request.getUpdateTimeStart();
        Date updateTimeEnd = request.getUpdateTimeEnd();
        String updateUserId = request.getUpdateUserId();
        String tenantId = request.getCurrentUserTenantId();
        Map<String, Object> params = new HashMap<>();
        params.put("tenantId", tenantId);
        params.put("name", name);
        params.put("status", status);
        params.put("updateTimeStart", updateTimeStart);
        params.put("updateTimeEnd", updateTimeEnd);
        params.put("updateUserId", updateUserId);
        Integer start = request.getStart();
        Integer pageSize = request.getPageSize();
        String sortType = request.getSortType();
        String sortOrder = request.getSortOrder();

        PageHelper.startPage(start, pageSize);
        RecordSet recordSet = edumetaDaoService.getCompletenessesByParams(params, sortResult(sortType, sortOrder));
        PageInfo<Object> pageInfo = new PageInfo<>(new ArrayList<>(Arrays.asList(recordSet.getValues())));

        Cache cache = cacheManager.getCache(CacheNameEnum.ACCOUNT.getName());
        List<Object> resultList = new ArrayList<>();
        for (Object object : pageInfo.getList())
        {
            Completeness completeness = (Completeness) object;
            setResult(cache, resultList, completeness.getId(), completeness.getName(), completeness.getStatus(), completeness.getSeq(), completeness.getCoreId(), completeness.getUpdateUserId(), completeness.getUpdateTime());
        }

        return new RecordSet(start, pageSize, recordSet.getTotalCount(), resultList.toArray());
    }

    @Override
    @Cacheable(key = "'completeness:' + #request.id")
    public RecordSet getCompletenessDetail(CompletenessDetailGetRequest request)
    {
        String id = request.getId();
        Completeness completeness = edumetaDaoService.getCompletenessById(id);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", completeness.getId());
        resultMap.put("name", completeness.getName());
        resultMap.put("seq", completeness.getSeq());
        resultMap.put("coreId", completeness.getCoreId());
        resultMap.put("status", completeness.getStatus());
        return new RecordSet(0, 1, 1, new Object[]{resultMap});
    }

    @Override
    public Map<String, Object> updateCompleteness(CompletenessUpdateRequest request)
    {
        String id = request.getId();
        String name = request.getName();
        Integer seq = request.getSeq();
        Integer coreId = request.getCoreId();
        Boolean status = request.getStatus();

        String currUserId = request.getCurrentUserId();

        Completeness completeness = edumetaDaoService.getCompletenessById(id);
        if (!TextUtil.isEmpty(name))
        {
            completeness.setName(name);
        }
        if (!TextUtil.isEmpty(seq))
        {
            completeness.setSeq(seq);
        }
        if (!TextUtil.isEmpty(status))
        {
            completeness.setStatus(status);
        }
        if (!TextUtil.isEmpty(coreId))
        {
            completeness.setCoreId(coreId);
        }
        completeness.setUpdateTime(new Date());
        completeness.setUpdateUserId(currUserId);
        edumetaDaoService.updateCompleteness(completeness);

        edumetaAsyncTask.refreshEdumetaCache(CacheKeyEnum.COMPLETENESS.getKey() + id, completeness);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", id);
        return resultMap;
    }

    @Override
    public Map<String, Object> createCompleteness(CompletenessCreateRequest request)
    {
        String name = request.getName();
        Integer seq = request.getSeq();
        Integer coreId = request.getCoreId();
        Boolean status = request.getStatus();
        String currUserId = request.getCurrentUserId();
        String currTenantId = request.getCurrentUserTenantId();

        Completeness completeness = new Completeness();
        String id = PrimaryKeyUtil.nextId(
                ServiceIdEnum.EDUMETA.getIsPublic(),
                currTenantId,
                ServiceIdEnum.EDUMETA.getServiceId());
        completeness.setId(id);
        completeness.setStatus(status);
        completeness.setSeq(seq);
        completeness.setCoreId(coreId);
        completeness.setTenantId(currTenantId);
        completeness.setName(name);
        Date now = new Date();
        completeness.setCreateTime(now);
        completeness.setUpdateTime(now);
        completeness.setCreateUserId(currUserId);
        completeness.setUpdateUserId(currUserId);
        edumetaDaoService.createCompleteness(completeness);

        edumetaAsyncTask.refreshEdumetaCache(CacheKeyEnum.COMPLETENESS.getKey() + id, completeness);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", id);
        return resultMap;
    }

    @Override
    public RecordSet getErrorMetas(ErrorMetaGetRequest request)
    {
        String name = request.getName();
        Boolean status = request.getStatus();
        Date updateTimeStart = request.getUpdateTimeStart();
        Date updateTimeEnd = request.getUpdateTimeEnd();
        String updateUserId = request.getUpdateUserId();
        String tenantId = request.getCurrentUserTenantId();
        String type = request.getType();
        Map<String, Object> params = new HashMap<>();
        params.put("tenantId", tenantId);
        params.put("name", name);
        params.put("type", type);
        params.put("status", status);
        params.put("updateTimeStart", updateTimeStart);
        params.put("updateTimeEnd", updateTimeEnd);
        params.put("updateUserId", updateUserId);
        Integer start = request.getStart();
        Integer pageSize = request.getPageSize();
        String sortType = request.getSortType();
        String sortOrder = request.getSortOrder();

        PageHelper.startPage(start, pageSize);
        RecordSet recordSet = edumetaDaoService.getErrorMetasByParams(params, sortResult(sortType, sortOrder));
        PageInfo<Object> pageInfo = new PageInfo<>(new ArrayList<>(Arrays.asList(recordSet.getValues())));

        Cache cache = cacheManager.getCache(CacheNameEnum.ACCOUNT.getName());
        List<Object> resultList = new ArrayList<>();
        for (Object object : pageInfo.getList())
        {
            ErrorMeta errorMeta = (ErrorMeta) object;
            setResult(cache, resultList, errorMeta.getId(), errorMeta.getName(), errorMeta.getStatus(), errorMeta.getSeq(), errorMeta.getCoreId(), errorMeta.getUpdateUserId(), errorMeta.getUpdateTime());
        }

        return new RecordSet(start, pageSize, recordSet.getTotalCount(), resultList.toArray());
    }

    @Override
    @Cacheable(key = "'errormeta:' + #request.id")
    public RecordSet getErrorMetaDetail(ErrorMetaDetailGetRequest request)
    {
        String id = request.getId();
        ErrorMeta errorMeta = edumetaDaoService.getErrorMetaById(id);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", errorMeta.getId());
        resultMap.put("name", errorMeta.getName());
        resultMap.put("seq", errorMeta.getSeq());
        resultMap.put("coreId", errorMeta.getCoreId());
        resultMap.put("status", errorMeta.getStatus());
        return new RecordSet(0, 1, 1, new Object[]{resultMap});
    }

    @Override
    public Map<String, Object> updateErrorMeta(ErrorMetaUpdateRequest request)
    {
        String id = request.getId();
        String name = request.getName();
        String type = request.getType();
        Integer seq = request.getSeq();
        Integer coreId = request.getCoreId();
        Boolean status = request.getStatus();
        String currUserId = request.getCurrentUserId();

        ErrorMeta errorMeta = edumetaDaoService.getErrorMetaById(id);
        if (!TextUtil.isEmpty(name))
        {
            errorMeta.setName(name);
        }
        if (!TextUtil.isEmpty(seq))
        {
            errorMeta.setSeq(seq);
        }
        if (!TextUtil.isEmpty(status))
        {
            errorMeta.setStatus(status);
        }
        if (!TextUtil.isEmpty(coreId))
        {
            errorMeta.setCoreId(coreId);
        }
        if (!TextUtil.isEmpty(type))
        {
            errorMeta.setType(type);
        }
        errorMeta.setUpdateTime(new Date());
        errorMeta.setUpdateUserId(currUserId);
        edumetaDaoService.updateErrorMeta(errorMeta);

        edumetaAsyncTask.refreshEdumetaCache(CacheKeyEnum.ERRORMETA.getKey() + id, errorMeta);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", id);
        return resultMap;
    }

    @Override
    public Map<String, Object> createErrorMeta(ErrorMetaCreateRequest request)
    {
        String name = request.getName();
        Integer seq = request.getSeq();
        Integer coreId = request.getCoreId();
        Boolean status = request.getStatus();
        String currUserId = request.getCurrentUserId();
        String currTenantId = request.getCurrentUserTenantId();
        String type = request.getType();

        ErrorMeta errorMeta = new ErrorMeta();
        String id = PrimaryKeyUtil.nextId(
                ServiceIdEnum.EDUMETA.getIsPublic(),
                currTenantId,
                ServiceIdEnum.EDUMETA.getServiceId());
        errorMeta.setId(id);
        errorMeta.setStatus(status);
        errorMeta.setSeq(seq);
        errorMeta.setCoreId(coreId);
        errorMeta.setTenantId(currTenantId);
        errorMeta.setName(name);
        errorMeta.setType(type);
        Date now = new Date();
        errorMeta.setCreateTime(now);
        errorMeta.setUpdateTime(now);
        errorMeta.setCreateUserId(currUserId);
        errorMeta.setUpdateUserId(currUserId);
        edumetaDaoService.createErrorMeta(errorMeta);

        edumetaAsyncTask.refreshEdumetaCache(CacheKeyEnum.ERRORMETA.getKey() + id, errorMeta);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", id);
        return resultMap;
    }

    @Override
    public RecordSet getCategorys(CategoryGetRequest request)
    {
        String name = request.getName();
        Boolean status = request.getStatus();
        Date updateTimeStart = request.getUpdateTimeStart();
        Date updateTimeEnd = request.getUpdateTimeEnd();
        String updateUserId = request.getUpdateUserId();
        String tenantId = request.getCurrentUserTenantId();
        Map<String, Object> params = new HashMap<>();
        params.put("tenantId", tenantId);
        params.put("name", name);
        params.put("status", status);
        params.put("updateTimeStart", updateTimeStart);
        params.put("updateTimeEnd", updateTimeEnd);
        params.put("updateUserId", updateUserId);
        Integer start = request.getStart();
        Integer pageSize = request.getPageSize();
        String sortType = request.getSortType();
        String sortOrder = request.getSortOrder();

        PageHelper.startPage(start, pageSize);
        RecordSet recordSet = edumetaDaoService.getCategoriesByParams(params, sortResult(sortType, sortOrder));
        PageInfo<Object> pageInfo = new PageInfo<>(new ArrayList<>(Arrays.asList(recordSet.getValues())));

        Cache cache = cacheManager.getCache(CacheNameEnum.ACCOUNT.getName());
        List<Object> resultList = new ArrayList<>();
        for (Object object : pageInfo.getList())
        {
            Category category = (Category) object;
            setResult(cache, resultList, category.getId(), category.getName(), category.getStatus(), category.getSeq(), null, category.getUpdateUserId(), category.getUpdateTime());
        }

        return new RecordSet(start, pageSize, recordSet.getTotalCount(), resultList.toArray());
    }

    @Override
    @Cacheable(key = "'category:' + #request.id")
    public RecordSet getCategoryDetail(CategoryDetailGetRequest request)
    {
        String id = request.getId();
        Category category = edumetaDaoService.getCategoryById(id);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", category.getId());
        resultMap.put("name", category.getName());
        resultMap.put("seq", category.getSeq());
        resultMap.put("status", category.getStatus());
        return new RecordSet(0, 1, 1, new Object[]{resultMap});
    }

    @Override
    public Map<String, Object> updateCategory(CategoryUpdateRequest request)
    {
        String id = request.getId();
        String name = request.getName();
        Integer seq = request.getSeq();
        Boolean status = request.getStatus();
        String currUserId = request.getCurrentUserId();

        Category category = edumetaDaoService.getCategoryById(id);
        if (!TextUtil.isEmpty(name))
        {
            category.setName(name);
        }
        if (!TextUtil.isEmpty(seq))
        {
            category.setSeq(seq);
        }
        if (!TextUtil.isEmpty(status))
        {
            category.setStatus(status);
        }
        category.setUpdateTime(new Date());
        category.setUpdateUserId(currUserId);
        edumetaDaoService.updateCategory(category);

        edumetaAsyncTask.refreshEdumetaCache(CacheKeyEnum.CATEGORY.getKey() + id, category);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", id);
        return resultMap;
    }

    @Override
    public Map<String, Object> createCategory(CategoryCreateRequest request)
    {
        String name = request.getName();
        Integer seq = request.getSeq();
        Boolean status = request.getStatus();
        String currUserId = request.getCurrentUserId();
        String currTenantId = request.getCurrentUserTenantId();

        Category category = new Category();
        String id = PrimaryKeyUtil.nextId(
                ServiceIdEnum.EDUMETA.getIsPublic(),
                currTenantId,
                ServiceIdEnum.EDUMETA.getServiceId());
        category.setId(id);
        category.setStatus(status);
        category.setSeq(seq);
        category.setTenantId(currTenantId);
        category.setName(name);
        Date now = new Date();
        category.setCreateTime(now);
        category.setUpdateTime(now);
        category.setCreateUserId(currUserId);
        category.setUpdateUserId(currUserId);
        edumetaDaoService.createCategory(category);

        edumetaAsyncTask.refreshEdumetaCache(CacheKeyEnum.CATEGORY.getKey() + id, category);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", id);
        return resultMap;
    }

    @Override
    public RecordSet getSources(SourceGetRequest request)
    {
        String name = request.getName();
        Boolean status = request.getStatus();
        Date updateTimeStart = request.getUpdateTimeStart();
        Date updateTimeEnd = request.getUpdateTimeEnd();
        String updateUserId = request.getUpdateUserId();
        String tenantId = request.getCurrentUserTenantId();
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("status", status);
        params.put("updateTimeStart", updateTimeStart);
        params.put("updateTimeEnd", updateTimeEnd);
        params.put("updateUserId", updateUserId);
        Integer start = request.getStart();
        Integer pageSize = request.getPageSize();
        String sortType = request.getSortType();
        String sortOrder = request.getSortOrder();

        PageHelper.startPage(start, pageSize);
        RecordSet recordSet = edumetaDaoService.getSourcesByParams(params, sortResult(sortType, sortOrder));
        PageInfo<Object> pageInfo = new PageInfo<>(new ArrayList<>(Arrays.asList(recordSet.getValues())));

        Cache cache = cacheManager.getCache(CacheNameEnum.ACCOUNT.getName());
        List<Object> resultList = new ArrayList<>();
        for (Object object : pageInfo.getList())
        {
            Source source = (Source) object;
            Map<String, Object> result = new HashMap<>();
            result.put("id", source.getId());
            result.put("description", source.getDescription());
            result.put("name", source.getName());
            result.put("status", source.getStatus());
            result.put("seq", source.getSeq());
            result.put("updateUserName", cache.get(CacheKeyEnum.USER.getKey() + updateUserId, String.class));
            result.put("updateTime", source.getUpdateTime());
            resultList.add(result);
        }

        return new RecordSet(start, pageSize, recordSet.getTotalCount(), resultList.toArray());
    }

    @Override
    @Cacheable(key = "'source:' + #request.id")
    public RecordSet getSourceDetail(SourceDetailGetRequest request)
    {
        String id = request.getId();
        Source source = edumetaDaoService.getSourceById(id);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", source.getId());
        resultMap.put("name", source.getName());
        resultMap.put("seq", source.getSeq());
        resultMap.put("status", source.getStatus());
        resultMap.put("description", source.getDescription());
        resultMap.put("sourcePackageId", source.getSourcePackageId());
        SourcePackage sourcePackage = edumetaDaoService.getSourcePackageById(source.getSourcePackageId());
        resultMap.put("sourcePackageName", sourcePackage.getName());
        return new RecordSet(0, 1, 1, new Object[]{resultMap});
    }

    @Override
    public Map<String, Object> updateSource(SourceUpdateRequest request)
    {
        String id = request.getId();
        String name = request.getName();
        Integer seq = request.getSeq();
        Boolean status = request.getStatus();
        String description = request.getDescription();
        String sourcePackageId = request.getSourcePackageId();
        String currUserId = request.getCurrentUserId();

        Source source = edumetaDaoService.getSourceById(id);
        if (!TextUtil.isEmpty(name))
        {
            source.setName(name);
        }
        if (!TextUtil.isEmpty(seq))
        {
            source.setSeq(seq);
        }
        if (!TextUtil.isEmpty(status))
        {
            source.setStatus(status);
        }
        if (!TextUtil.isEmpty(description))
        {
            source.setDescription(description);
        }
        if (!TextUtil.isEmpty(sourcePackageId))
        {
            source.setSourcePackageId(sourcePackageId);
        }
        Date now = new Date();
        source.setUpdateTime(now);
        source.setUpdateUserId(currUserId);
        edumetaDaoService.updateSource(source);

        edumetaAsyncTask.refreshSpecialEdumetaCache(CacheKeyEnum.SOURCE, source);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", id);
        return resultMap;
    }

    @Override
    public Map<String, Object> createSource(SourceCreateRequest request)
    {
        String name = request.getName();
        String description = request.getDescription();
        String sourcePackageId = request.getSourcePackageId();
        Integer seq = request.getSeq();
        Boolean status = request.getStatus();
        String currUserId = request.getCurrentUserId();
        String currTenantId = request.getCurrentUserTenantId();

        Source source = new Source();
        String id = PrimaryKeyUtil.nextId(
                ServiceIdEnum.EDUMETA.getIsPublic(),
                currTenantId,
                ServiceIdEnum.EDUMETA.getServiceId());
        source.setId(id);
        source.setStatus(status);
        source.setSeq(seq);
        source.setName(name);
        source.setSourcePackageId(sourcePackageId);
        source.setDescription(description);
        Date now = new Date();
        source.setCreateTime(now);
        source.setUpdateTime(now);
        source.setCreateUserId(currUserId);
        source.setUpdateUserId(currUserId);
        edumetaDaoService.createSource(source);

        edumetaAsyncTask.refreshSpecialEdumetaCache(CacheKeyEnum.SOURCE, source);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", id);
        return resultMap;
    }

    @Override
    public RecordSet getSourceTypes(SourceTypeGetRequest request)
    {
        String name = request.getName();
        Boolean status = request.getStatus();
        Date updateTimeStart = request.getUpdateTimeStart();
        Date updateTimeEnd = request.getUpdateTimeEnd();
        String updateUserId = request.getUpdateUserId();
        String tenantId = request.getCurrentUserTenantId();
        Map<String, Object> params = new HashMap<>();
        params.put("tenantId", tenantId);
        params.put("name", name);
        params.put("status", status);
        params.put("updateTimeStart", updateTimeStart);
        params.put("updateTimeEnd", updateTimeEnd);
        params.put("updateUserId", updateUserId);
        Integer start = request.getStart();
        Integer pageSize = request.getPageSize();
        String sortType = request.getSortType();
        String sortOrder = request.getSortOrder();

        PageHelper.startPage(start, pageSize);
        RecordSet recordSet = edumetaDaoService.getSourcePackagesByParams(params, sortResult(sortType, sortOrder));
        PageInfo<Object> pageInfo = new PageInfo<>(new ArrayList<>(Arrays.asList(recordSet.getValues())));

        Cache cache = cacheManager.getCache(CacheNameEnum.ACCOUNT.getName());
        List<Object> resultList = new ArrayList<>();
        RecordSet sourceRecordSet;
        for (Object object : pageInfo.getList())
        {
            SourcePackage sourcePackage = (SourcePackage) object;
            Map<String, Object> result = new HashMap<>();
            result.put("id", sourcePackage.getId());
            result.put("description", sourcePackage.getDescription());
            result.put("name", sourcePackage.getName());
            result.put("status", sourcePackage.getStatus());
            result.put("seq", sourcePackage.getSeq());
            params = new HashMap<>();
            params.put("sourcePackageId", sourcePackage.getId());
            sourceRecordSet = edumetaDaoService.getSourcesByParams(params, null);
            result.put("sourceNum", sourceRecordSet.getTotalCount());
            result.put("updateUserName", cache.get(CacheKeyEnum.USER.getKey() + updateUserId, String.class));
            result.put("updateTime", sourcePackage.getUpdateTime());
            resultList.add(result);
        }

        return new RecordSet(start, pageSize, recordSet.getTotalCount(), resultList.toArray());
    }

    @Override
    @Cacheable(key = "'sourcetype:' + #request.id")
    public RecordSet getSourceTypeDetail(SourceTypeDetailGetRequest request)
    {
        String id = request.getId();
        SourcePackage sourcePackage = edumetaDaoService.getSourcePackageById(id);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", sourcePackage.getId());
        resultMap.put("name", sourcePackage.getName());
        resultMap.put("seq", sourcePackage.getSeq());
        resultMap.put("status", sourcePackage.getStatus());
        resultMap.put("description", sourcePackage.getDescription());

        Map<String, Object> params = new HashMap<>();
        params.put("sourcePackageId", sourcePackage.getId());
        RecordSet sourceRecordSet = edumetaDaoService.getSourcesByParams(params, null);
        List<Object> sourceList = new ArrayList<>();
        if (sourceRecordSet.getTotalCount() > 0)
        {
            Map<String, Object> sourceMap;
            for (Object object : sourceRecordSet.getValues())
            {
                Source source = (Source) object;
                sourceMap = new HashMap<>();
                sourceMap.put("id", source.getId());
                sourceMap.put("name", source.getName());
                sourceMap.put("seq", source.getSeq());
                sourceMap.put("status", source.getStatus());
                sourceMap.put("description", source.getDescription());
                sourceList.add(sourceMap);
            }
        }
        resultMap.put("sourceInfo", sourceList);
        return new RecordSet(0, 1, 1, new Object[]{resultMap});
    }

    @Override
    public Map<String, Object> updateSourceType(SourceTypeUpdateRequest request)
    {
        String id = request.getId();
        String name = request.getName();
        Integer seq = request.getSeq();
        Boolean status = request.getStatus();
        String description = request.getDescription();
        String currUserId = request.getCurrentUserId();

        SourcePackage sourcePackage = edumetaDaoService.getSourcePackageById(id);
        if (!TextUtil.isEmpty(name))
        {
            sourcePackage.setName(name);
        }
        if (!TextUtil.isEmpty(seq))
        {
            sourcePackage.setSeq(seq);
        }
        if (!TextUtil.isEmpty(status))
        {
            sourcePackage.setStatus(status);
        }
        if (!TextUtil.isEmpty(description))
        {
            sourcePackage.setDescription(description);
        }
        Date now = new Date();
        sourcePackage.setUpdateTime(now);
        sourcePackage.setUpdateUserId(currUserId);
        edumetaDaoService.updateSourcePackage(sourcePackage);

        Map<String, Object> params = new HashMap<>();
        params.put("sourcePackageId", sourcePackage.getId());
        RecordSet sourceRecordSet = edumetaDaoService.getSourcesByParams(params, null);
        if (sourceRecordSet.getTotalCount() > 0)
        {
            for (Object object : sourceRecordSet.getValues())
            {
                Source source = (Source) object;
                source.setSourcePackageId(null);
                source.setUpdateTime(now);
                source.setUpdateUserId(currUserId);
                edumetaDaoService.updateSource(source);
            }
        }
        List<String> sourceIds = request.getSourceIds();
        updateSource(currUserId, id, now, sourceIds);

        edumetaAsyncTask.refreshSpecialEdumetaCache(CacheKeyEnum.SOURCETYPE, sourcePackage);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", id);
        return resultMap;
    }

    @Override
    public Map<String, Object> createSourceType(SourceTypeCreateRequest request)
    {
        String name = request.getName();
        String description = request.getDescription();
        Integer seq = request.getSeq();
        Boolean status = request.getStatus();
        String currUserId = request.getCurrentUserId();
        String currTenantId = request.getCurrentUserTenantId();

        SourcePackage sourcePackage = new SourcePackage();
        String id = PrimaryKeyUtil.nextId(
                ServiceIdEnum.EDUMETA.getIsPublic(),
                currTenantId,
                ServiceIdEnum.EDUMETA.getServiceId());
        sourcePackage.setId(id);
        sourcePackage.setStatus(status);
        sourcePackage.setSeq(seq);
        sourcePackage.setName(name);
        sourcePackage.setDescription(description);
        Date now = new Date();
        sourcePackage.setCreateTime(now);
        sourcePackage.setUpdateTime(now);
        sourcePackage.setCreateUserId(currUserId);
        sourcePackage.setUpdateUserId(currUserId);
        edumetaDaoService.createSourcePackage(sourcePackage);

        List<String> sourceIds = request.getSourceIds();
        updateSource(currUserId, id, now, sourceIds);

        edumetaAsyncTask.refreshSpecialEdumetaCache(CacheKeyEnum.SOURCETYPE, sourcePackage);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", id);
        return resultMap;
    }

    @Override
    public RecordSet getKnowledgePoints(KnowledgePointGetRequest knowledgePointGetRequest)
    {
        String educationalStageId = knowledgePointGetRequest.getEducationalStageId();
        String subjectId = knowledgePointGetRequest.getSubjectId();

        List<Object> results = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("educationalStageId", educationalStageId);
        params.put("subjectId", subjectId);
        params.put("depth", 0);
        RecordSet recordSet = edumetaDaoService.getKnowledgePointsByParams(params, null);
        if (recordSet.getTotalCount() > 0)
        {
            params = new HashMap<>();
            params.put("educationalStageId", educationalStageId);
            params.put("subjectId", subjectId);
            Map<String, Object> resultMap;
            for (Object object : recordSet.getValues())
            {
                KnowledgePoint knowledgePoint = (KnowledgePoint) object;
                resultMap = new HashMap<>();
                resultMap.put("id", knowledgePoint.getId());
                resultMap.put("name", knowledgePoint.getName());
                results.add(resultMap);
                params.put("parentId", knowledgePoint.getId());
                RecordSet kpRecordSet = edumetaDaoService.getKnowledgePointsByParams(params, null);
                resultMap.put("subKps", getSubKnowlegePoint(null, knowledgePoint.getId(), new ArrayList<>(Arrays.asList(kpRecordSet.getValues()))));
                results.add(resultMap);
            }
        }
        return new RecordSet(0, results.size(), results.size(), results.toArray());
    }

    private List<Object> getSubKnowlegePoint(Cache cache, String parentId, List<Object> subKnowledgePoints)
    {
        List<Object> results = new ArrayList<>();
        if (!TextUtil.isEmpty(subKnowledgePoints))
        {
            List<Object> subResults;
            Map<String, Object> reuslt;
            RecordSet recordSet;
            Map<String, Object> params = new HashMap<>();
            params.put("parentId", parentId);
            for (Object object : subKnowledgePoints)
            {
                KnowledgePoint subKnowledgePoint = (KnowledgePoint) object;
                recordSet = edumetaDaoService.getKnowledgePointsByParams(params, null);
                subResults = getSubKnowlegePoint(cache, subKnowledgePoint.getId(), new ArrayList<>(Arrays.asList(recordSet.getValues())));

                reuslt = new HashMap<>();
                reuslt.put("id", subKnowledgePoint.getId());
                reuslt.put("name", subKnowledgePoint.getName());
                if (cache != null)
                {
                    reuslt.put("operateTime", subKnowledgePoint.getUpdateTime());
                    reuslt.put("operateUserName", cache.get(CacheKeyEnum.USER.getKey() + subKnowledgePoint.getUpdateUserId(), String.class));
                }
                reuslt.put("subKps", subResults);
                results.add(reuslt);
                if (TextUtil.isEmpty(subResults))
                {
                    break;
                }
            }
        }
        return results;
    }

    @Override
    @Cacheable(key = "'knowlegepoint:' + #request.id")
    public RecordSet getKnowledgePointDetail(KnowledgePointDetailGetRequest request)
    {
        String id = request.getId();
        KnowledgePoint knowledgePoint = edumetaDaoService.getKnowledgePointById(id);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", knowledgePoint.getId());
        resultMap.put("name", knowledgePoint.getName());
        if (!TextUtil.isEmpty(knowledgePoint.getParentId()))
        {
            KnowledgePoint parentKnowledgePoint = edumetaDaoService.getKnowledgePointById(knowledgePoint.getParentId());
            resultMap.put("parentId", parentKnowledgePoint.getId());
            resultMap.put("parentName", parentKnowledgePoint.getName());
        }
        Cache cache = cacheManager.getCache(CacheNameEnum.ACCOUNT.getName());
        resultMap.put("operateTime", knowledgePoint.getUpdateTime());
        resultMap.put("operateUserName", cache.get(CacheKeyEnum.USER.getKey() + knowledgePoint.getUpdateUserId(), String.class));

        Map<String, Object> params = new HashMap<>();
        params.put("parentId", knowledgePoint.getId());
        RecordSet recordSet = edumetaDaoService.getKnowledgePointsByParams(params, null);
        resultMap.put("subKps", getSubKnowlegePoint(cache, knowledgePoint.getId(), new ArrayList<>(Arrays.asList(recordSet.getValues()))));
        return new RecordSet(0, 1, 1, new Object[]{resultMap});
    }

    @Override
    public Map<String, Object> exchangeKnowledgePoint(KnowledgePointExchangeRequest knowledgePointExchangeRequest)
    {
        String id = knowledgePointExchangeRequest.getId();
        String parentId = knowledgePointExchangeRequest.getParentId();
        Boolean keepSubIds = knowledgePointExchangeRequest.getKeepSubIds();
        String currUserId = knowledgePointExchangeRequest.getCurrentUserId();

        KnowledgePoint knowledgePoint = edumetaDaoService.getKnowledgePointById(id);
        String oldParentId = knowledgePoint.getParentId();

        Date now = new Date();
        knowledgePoint.setParentId(parentId);
        knowledgePoint.setUpdateTime(now);
        knowledgePoint.setUpdateUserId(currUserId);
        edumetaDaoService.updateKnowledgePoint(knowledgePoint);

        if (!TextUtil.isEmpty(keepSubIds) && !keepSubIds)
        {
            Map<String, Object> params = new HashMap<>();
            params.put("parentId", id);
            RecordSet recordSet = edumetaDaoService.getKnowledgePointsByParams(params, null);
            if (recordSet.getTotalCount() > 0)
            {
                for (Object object : recordSet.getValues())
                {
                    KnowledgePoint subKnowledgePoint = (KnowledgePoint) object;
                    subKnowledgePoint.setParentId(oldParentId);
                    subKnowledgePoint.setUpdateTime(now);
                    subKnowledgePoint.setUpdateUserId(currUserId);
                    edumetaDaoService.updateKnowledgePoint(knowledgePoint);
                }
            }
        }

        if (!TextUtil.isEmpty(parentId))
        {
            KnowledgePoint parentKnowledgePoint = edumetaDaoService.getKnowledgePointById(parentId);
            edumetaAsyncTask.refreshSpecialEdumetaCache(CacheKeyEnum.KNOWLEDGEPOINT, parentKnowledgePoint);
        }
        edumetaAsyncTask.refreshSpecialEdumetaCache(CacheKeyEnum.KNOWLEDGEPOINT, knowledgePoint);

        Map<String, Object> result = new HashMap<>();
        result.put("id", id);
        return result;
    }

    @Override
    public Map<String, Object> createKnowledgePoint(KnowledgePointCreateRequest request)
    {
        String name = request.getName();
        String educationalStageId = request.getEducationalStageId();
        String parentId = request.getParentId();
        String subjectId = request.getSubjectId();
        String url = request.getUrl();

        String currUserId = request.getCurrentUserId();
        String currTenantId = request.getCurrentUserTenantId();

        KnowledgePoint knowledgePoint = new KnowledgePoint();
        if (!TextUtil.isEmpty(parentId))
        {
            KnowledgePoint parentKnowledgePoint = edumetaDaoService.getKnowledgePointById(parentId);
            Integer parentDepth = parentKnowledgePoint.getDepth();
            knowledgePoint.setDepth(parentDepth + 1);
            knowledgePoint.setParentId(parentId);
        } else
        {
            knowledgePoint.setDepth(0);
        }
        String newId = PrimaryKeyUtil.nextId(
                ServiceIdEnum.EDUMETA.getIsPublic(),
                currTenantId,
                ServiceIdEnum.EDUMETA.getServiceId());
        knowledgePoint.setId(newId);
        knowledgePoint.setName(name);

        Subject subject = edumetaDaoService.getSubjectById(subjectId);
        EducationalStage educationalStage = edumetaDaoService.getEducationalStageById(educationalStageId);

        knowledgePoint.setSubjectId(subject.getCoreId());
        knowledgePoint.setEducationalStageId(educationalStage.getCoreId());
        knowledgePoint.setUrl(url);

        Date now = new Date();
        knowledgePoint.setCreateTime(now);
        knowledgePoint.setUpdateTime(now);
        knowledgePoint.setCreateUserId(currUserId);
        knowledgePoint.setUpdateUserId(currUserId);
        edumetaDaoService.createKnowledgePoint(knowledgePoint);

        if (!TextUtil.isEmpty(parentId))
        {
            KnowledgePoint parentKnowledgePoint = edumetaDaoService.getKnowledgePointById(parentId);
            edumetaAsyncTask.refreshSpecialEdumetaCache(CacheKeyEnum.KNOWLEDGEPOINT, parentKnowledgePoint);
        }
        edumetaAsyncTask.refreshSpecialEdumetaCache(CacheKeyEnum.KNOWLEDGEPOINT, knowledgePoint);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", newId);
        return resultMap;
    }

    @Override
    public Map<String, Object> updateKnowledgePoint(KnowledgePointUpdateRequest knowledgePointUpdateRequest)
    {
        String id = knowledgePointUpdateRequest.getId();
        String parentId = knowledgePointUpdateRequest.getParentId();
        String educationalStageId = knowledgePointUpdateRequest.getEducationalStageId();
        String subjectId = knowledgePointUpdateRequest.getSubjectId();
        String currUserId = knowledgePointUpdateRequest.getCurrentUserId();
        String name = knowledgePointUpdateRequest.getName();

        KnowledgePoint knowledgePoint = edumetaDaoService.getKnowledgePointById(id);
//        String oldParentId = knowledgePoint.getParentId();

        Date now = new Date();
        if (!TextUtil.isEmpty(parentId))
        {
            knowledgePoint.setParentId(parentId);
        }
        if (!TextUtil.isEmpty(name))
        {
            knowledgePoint.setName(name);
        }
        if (!TextUtil.isEmpty(educationalStageId))
        {
            EducationalStage educationalStage = edumetaDaoService.getEducationalStageById(educationalStageId);
            knowledgePoint.setEducationalStageId(educationalStage.getCoreId());
        }
        if (!TextUtil.isEmpty(subjectId))
        {
            Subject subject = edumetaDaoService.getSubjectById(subjectId);
            knowledgePoint.setSubjectId(subject.getCoreId());
        }
        knowledgePoint.setUpdateTime(now);
        knowledgePoint.setUpdateUserId(currUserId);
        edumetaDaoService.updateKnowledgePoint(knowledgePoint);

        if (!TextUtil.isEmpty(parentId))
        {
            KnowledgePoint parentKnowledgePoint = edumetaDaoService.getKnowledgePointById(parentId);
            edumetaAsyncTask.refreshSpecialEdumetaCache(CacheKeyEnum.KNOWLEDGEPOINT, parentKnowledgePoint);
        }
        edumetaAsyncTask.refreshSpecialEdumetaCache(CacheKeyEnum.KNOWLEDGEPOINT, knowledgePoint);

        Map<String, Object> result = new HashMap<>();
        result.put("id", id);
        return result;
    }

    @Override
    public void deleteKnowledgePoint(KnowledgePointDeleteRequest knowledgePointDeleteRequest)
    {
        edumetaDaoService.deleteKnowledgePointById(knowledgePointDeleteRequest.getId(), knowledgePointDeleteRequest.getCurrentUserId());
        edumetaAsyncTask.removeEdumetaCache(CacheKeyEnum.KNOWLEDGEPOINT.getKey() + knowledgePointDeleteRequest.getId());
    }

    private void updateSource(String currUserId, String id, Date now, List<String> sourceIds)
    {
        if (!TextUtil.isEmpty(sourceIds))
        {
            Source source;
            for (String sourceId : sourceIds)
            {
                source = edumetaDaoService.getSourceById(sourceId);
                source.setSourcePackageId(id);
                source.setUpdateTime(now);
                source.setUpdateUserId(currUserId);
                edumetaDaoService.updateSource(source);
            }
        }
    }

    private void setResult(Cache cache, List<Object> resultList, String id, String name, Boolean status, Integer seq, Integer coreId, String updateUserId, Date updateTime)
    {
        Map<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("status", status);
        result.put("seq", seq);
        if (!TextUtil.isEmpty(coreId))
        {
            result.put("coreId", coreId);
        }
        result.put("updateUserName", cache.get(CacheKeyEnum.USER.getKey() + updateUserId, String.class));
        result.put("updateTime", updateTime);
        resultList.add(result);
    }

    private Comparator sortResult(String sortType, String sortOrder)
    {
        if (!TextUtil.isEmpty(sortType) && !TextUtil.isEmpty(sortOrder))
        {
            switch (sortType.toUpperCase().trim())
            {
            case "SEQ":
                if ("ASC".equals(sortOrder.toUpperCase()))
                {
                    return new SeqAscComparator();
                } else
                {
                    return new SeqDescComparator();
                }
            case "UPDATETIME":
                if ("ASC".equals(sortOrder.toUpperCase()))
                {
                    return new UpdateTimeAscComparator();
                } else
                {
                    return new UpdateTimeDescComparator();
                }
            default:
                return new UpdateTimeDescComparator();
            }
        }
        return new UpdateTimeDescComparator();
    }

}
