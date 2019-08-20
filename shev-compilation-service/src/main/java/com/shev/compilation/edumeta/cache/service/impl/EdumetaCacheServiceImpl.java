package com.shev.compilation.edumeta.cache.service.impl;

import com.shev.compilation.common.Enum.CacheKeyEnum;
import com.shev.compilation.common.Enum.CacheNameEnum;
import com.shev.compilation.common.support.web.RecordSet;
import com.shev.compilation.common.util.TextUtil;
import com.shev.compilation.edumeta.cache.service.EdumetaCacheService;
import com.shev.compilation.edumeta.dao.service.EdumetaDaoService;
import com.shev.compilation.edumeta.entity.*;
import com.shev.compilation.edumeta.request.KnowledgePointDetailGetRequest;
import com.shev.compilation.edumeta.request.SourceDetailGetRequest;
import com.shev.compilation.edumeta.request.SourceTypeDetailGetRequest;
import com.shev.compilation.edumeta.service.EdumetaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Service
public class EdumetaCacheServiceImpl implements EdumetaCacheService
{
    private static final Logger logger =  LoggerFactory.getLogger(EdumetaCacheServiceImpl.class);

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private EdumetaDaoService edumetaDaoService;

    @Autowired
    private EdumetaService edumetaService;

    @Override
    public void refreshEdumetaCache(CacheKeyEnum cacheKey)
    {
        switch (cacheKey)
        {
        case SUBJECT:
            refreshSubjectCache();
            break;
        case EDUCATIONALSTAGE:
            refreshEducationalStageCache();
            break;
        case DIFFICULTY:
            refreshDifficultyCache();
            break;
        case FORMTYPE:
            refreshFormTypeCache();
            break;
        case SEMANTICTYPE:
            refreshSemanticTypeCache();
            break;
        case SIGNIFICANCE:
            refreshSignificanceCache();
            break;
        case COMPLETENESS:
            refreshCompletenessCache();
            break;
        case ERRORMETA:
            refreshErrormetaCache();
            break;
        case CATEGORY:
            refreshCategoryCache();
            break;
        case SOURCE:
            refreshSourceCache();
            break;
        case SOURCETYPE:
            refreshSourceTypeCache();
            break;
        case KNOWLEDGEPOINT:
            refreshKnowlegepointCache();
            break;
        default:
            break;
        }
    }

    @Override
    public void refreshEdumetaCache(String cacheKey, Object object)
    {
        Cache cache = cacheManager.getCache(CacheNameEnum.EDUMETA.getName());
//        removeEdumetaCache(cache, cacheKey, object);
        putEdumetaCache(cache, cacheKey, object);
    }

    @Override
    public void removeEdumetaCache(String cacheKey)
    {
        Cache cache = cacheManager.getCache(CacheNameEnum.EDUMETA.getName());
        cache.evict(cacheKey);
        logger.info("remove cache name:{}, key:{}", CacheNameEnum.EDUMETA.getName(), cacheKey);
    }

    @Override
    public void refreshSpecialEdumetaCache(CacheKeyEnum cacheKey, Object object)
    {
        switch (cacheKey)
        {
        case SOURCE:
            refreshSourceCache((Source)object);
            break;
            case SOURCETYPE:
            refreshSourceTypeCache((SourcePackage)object);
            break;
        case KNOWLEDGEPOINT:
            refreshKnowlegepointCache((KnowledgePoint)object);
            break;
        default:
            break;
        }
    }

    private void refreshKnowlegepointCache(KnowledgePoint knowledgePoint)
    {
        Cache cache = cacheManager.getCache(CacheNameEnum.EDUMETA.getName());
        String key = CacheKeyEnum.KNOWLEDGEPOINT.getKey() + knowledgePoint.getId();
        cache.evict(key);
        putKnowledgePointCache(cache, key, knowledgePoint);
    }

    private void refreshKnowlegepointCache()
    {
        Cache cache = cacheManager.getCache(CacheNameEnum.EDUMETA.getName());
        RecordSet recordSet = edumetaDaoService.getKnowledgePointsByParams(null, null);
        if (recordSet.getTotalCount() > 0)
        {
            String key;
            for (Object object : recordSet.getValues())
            {
                KnowledgePoint knowledgePoint = (KnowledgePoint) object;
                key = CacheKeyEnum.KNOWLEDGEPOINT.getKey() + knowledgePoint.getId();
                putKnowledgePointCache(cache, key, knowledgePoint);
            }
        }
    }

    private void putKnowledgePointCache(Cache cache, String key, KnowledgePoint knowledgePoint)
    {
        KnowledgePointDetailGetRequest request = new KnowledgePointDetailGetRequest();
        request.setId(knowledgePoint.getId());
        RecordSet recordSet = edumetaService.getKnowledgePointDetail(request);
        if (recordSet.getTotalCount() > 0)
        {
            cache.put(key, recordSet.firstValue());
            logger.info("add cache name:{}, key:{}, value:{}", CacheNameEnum.EDUMETA.getName(), key, recordSet.firstValue());
        }
    }

    private void refreshSourceTypeCache(SourcePackage sourcePackage)
    {
        Cache cache = cacheManager.getCache(CacheNameEnum.EDUMETA.getName());
        String key = CacheKeyEnum.SOURCETYPE.getKey() + sourcePackage.getId();
        cache.evict(key);
        putSourceTypeCache(cache, key, sourcePackage);
    }

    private void refreshSourceTypeCache()
    {
        Cache cache = cacheManager.getCache(CacheNameEnum.EDUMETA.getName());
        RecordSet recordSet = edumetaDaoService.getSourcePackagesByParams(null, null);
        if (recordSet.getTotalCount() > 0)
        {
            String key;
            for (Object object : recordSet.getValues())
            {
                SourcePackage sourcePackage = (SourcePackage) object;
                key = CacheKeyEnum.SOURCETYPE.getKey() + sourcePackage.getId();
                putSourceTypeCache(cache, key, sourcePackage);
            }
        }
    }

    private void putSourceTypeCache(Cache cache, String key, SourcePackage sourcePackage)
    {
        SourceTypeDetailGetRequest request = new SourceTypeDetailGetRequest();
        request.setId(sourcePackage.getId());
        RecordSet recordSet = edumetaService.getSourceTypeDetail(request);
        if (recordSet.getTotalCount() > 0)
        {
            cache.put(key, recordSet.firstValue());
            logger.info("add cache name:{}, key:{}, value:{}", CacheNameEnum.EDUMETA.getName(), key, recordSet.firstValue());
        }
    }

    private void refreshSourceCache(Source source)
    {
        Cache cache = cacheManager.getCache(CacheNameEnum.EDUMETA.getName());
        String key = CacheKeyEnum.SOURCE.getKey() + source.getId();
        putSourceCache(cache, key, source);
    }

    private void refreshSourceCache()
    {
        Cache cache = cacheManager.getCache(CacheNameEnum.EDUMETA.getName());
        RecordSet recordSet = edumetaDaoService.getSourcesByParams(null, null);
        if (recordSet.getTotalCount() > 0)
        {
            String key;
            for (Object object : recordSet.getValues())
            {
                Source source = (Source) object;
                key = CacheKeyEnum.SOURCE.getKey() + source.getId();
                putSourceCache(cache, key, source);
            }
        }
    }

    private void putSourceCache(Cache cache, String key, Source source)
    {
        SourceDetailGetRequest request = new SourceDetailGetRequest();
        request.setId(source.getId());
        RecordSet recordSet = edumetaService.getSourceDetail(request);
        if (recordSet.getTotalCount() > 0)
        {
            cache.put(key, recordSet.firstValue());
            logger.info("add cache name:{}, key:{}, value:{}", CacheNameEnum.EDUMETA.getName(), key, recordSet.firstValue());
        }
    }

    private void refreshCategoryCache()
    {
        Cache cache = cacheManager.getCache(CacheNameEnum.EDUMETA.getName());
        RecordSet recordSet = edumetaDaoService.getCategoriesByParams(null, null);
        if (recordSet.getTotalCount() > 0)
        {
            String key;
            for (Object object : recordSet.getValues())
            {
                Category category = (Category) object;
                key = CacheKeyEnum.CATEGORY.getKey() + category.getId();
                initEdumetaCache(cache, key, category.getId(), category.getName(), null, category.getSeq(), category.getStatus());
            }
        }
    }

    private void refreshErrormetaCache()
    {
        Cache cache = cacheManager.getCache(CacheNameEnum.EDUMETA.getName());
        RecordSet recordSet = edumetaDaoService.getErrorMetasByParams(null, null);
        if (recordSet.getTotalCount() > 0)
        {
            String key;
            for (Object object : recordSet.getValues())
            {
                ErrorMeta errorMeta = (ErrorMeta) object;
                key = CacheKeyEnum.ERRORMETA.getKey() + errorMeta.getId();
                initEdumetaCache(cache, key, errorMeta.getId(), errorMeta.getName(), errorMeta.getCoreId(), errorMeta.getSeq(), errorMeta.getStatus());
            }
        }
    }

    private void refreshCompletenessCache()
    {
        Cache cache = cacheManager.getCache(CacheNameEnum.EDUMETA.getName());
        RecordSet recordSet = edumetaDaoService.getCompletenessesByParams(null, null);
        if (recordSet.getTotalCount() > 0)
        {
            String key;
            for (Object object : recordSet.getValues())
            {
                Completeness completeness = (Completeness) object;
                key = CacheKeyEnum.COMPLETENESS.getKey() + completeness.getId();
                initEdumetaCache(cache, key, completeness.getId(), completeness.getName(), completeness.getCoreId(), completeness.getSeq(), completeness.getStatus());
            }
        }
    }

    private void refreshSignificanceCache()
    {
        Cache cache = cacheManager.getCache(CacheNameEnum.EDUMETA.getName());
        RecordSet recordSet = edumetaDaoService.getSignificancesByParams(null, null);
        if (recordSet.getTotalCount() > 0)
        {
            String key;
            for (Object object : recordSet.getValues())
            {
                Significance significance = (Significance) object;
                key = CacheKeyEnum.SIGNIFICANCE.getKey() + significance.getId();
                initEdumetaCache(cache, key, significance.getId(), significance.getName(), significance.getCoreId(), significance.getSeq(), significance.getStatus());
            }
        }
    }

    private void refreshSemanticTypeCache()
    {
        Cache cache = cacheManager.getCache(CacheNameEnum.EDUMETA.getName());
        RecordSet recordSet = edumetaDaoService.getSemanticTypesByParams(null, null);
        if (recordSet.getTotalCount() > 0)
        {
            String key;
            for (Object object : recordSet.getValues())
            {
                SemanticType semanticType = (SemanticType) object;
                key = CacheKeyEnum.SEMANTICTYPE.getKey() + semanticType.getId();
                initEdumetaCache(cache, key, semanticType.getId(), semanticType.getName(), semanticType.getCoreId(), semanticType.getSeq(), semanticType.getStatus());
            }
        }
    }

    private void refreshSubjectCache()
    {
        Cache cache = cacheManager.getCache(CacheNameEnum.EDUMETA.getName());
        RecordSet recordSet = edumetaDaoService.getSubjectsByParams(null, null);
        if (recordSet.getTotalCount() > 0)
        {
            Subject subject;
            String key;
            for (Object object : recordSet.getValues())
            {
                subject = (Subject) object;
                key = CacheKeyEnum.SUBJECT.getKey() + subject.getId();
                initEdumetaCache(cache, key, subject.getId(), subject.getName(), subject.getCoreId(), subject.getSeq(), subject.getStatus());
            }
        }
    }

    private void refreshEducationalStageCache()
    {
        Cache cache = cacheManager.getCache(CacheNameEnum.EDUMETA.getName());
        RecordSet recordSet = edumetaDaoService.getEducationalStagesByParams(null, null);
        if (recordSet.getTotalCount() > 0)
        {
            String key;
            for (Object object : recordSet.getValues())
            {
                EducationalStage educationalStage = (EducationalStage) object;
                key = CacheKeyEnum.EDUCATIONALSTAGE.getKey() + educationalStage.getId();
                initEdumetaCache(cache, key, educationalStage.getId(), educationalStage.getName(), educationalStage.getCoreId(), educationalStage.getSeq(), educationalStage.getStatus());
            }
        }
    }

    private void refreshDifficultyCache()
    {
        Cache cache = cacheManager.getCache(CacheNameEnum.EDUMETA.getName());
        RecordSet recordSet = edumetaDaoService.getDifficultiesByParams(null, null);
        if (recordSet.getTotalCount() > 0)
        {
            String key;
            for (Object object : recordSet.getValues())
            {
                Difficulty difficulty = (Difficulty) object;
                key = CacheKeyEnum.DIFFICULTY.getKey() + difficulty.getId();
                initEdumetaCache(cache, key, difficulty.getId(), difficulty.getName(), difficulty.getCoreId(), difficulty.getSeq(), difficulty.getStatus());
            }
        }
    }

    private void refreshFormTypeCache()
    {
        Cache cache = cacheManager.getCache(CacheNameEnum.EDUMETA.getName());
        RecordSet recordSet  = edumetaDaoService.getFormTypesByParams(null, null);
        if (recordSet.getTotalCount() > 0)
        {
            String key;
            for (Object object : recordSet.getValues())
            {
                FormType formType = (FormType) object;
                key = CacheKeyEnum.FORMTYPE.getKey() + formType.getId();
                initEdumetaCache(cache, key, formType.getId(), formType.getName(), formType.getCoreId(), formType.getSeq(), formType.getStatus());
            }
        }
    }

    private void initEdumetaCache(Cache cache, String key, String id, String name, Integer coreId, Integer seq, Boolean status)
    {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", id);
        if (!TextUtil.isEmpty(name))
        {
            resultMap.put("name", name);
        }
        if (!TextUtil.isEmpty(seq))
        {
            resultMap.put("seq", seq);
        }
        if (!TextUtil.isEmpty(coreId))
        {
            resultMap.put("coreId", coreId);
        }
        if (!TextUtil.isEmpty(status))
        {
            resultMap.put("status", status);
        }
        cache.put(key, resultMap);
        logger.info("add cache name:{}, key:{}, value:{}", CacheNameEnum.EDUMETA.getName(), key, resultMap);
    }

    private void putEdumetaCache(Cache cache, String key, Object object)
    {
        try
        {
            Map<String, Object> resultMap = new HashMap<>();
            Class clazz = object.getClass();
            Method[] methods = clazz.getMethods();
            for (Method method : methods)
            {
                if ("getId".toUpperCase().equals(method.getName().toUpperCase()))
                {
                    resultMap.put("id", method.invoke(object));
                } else if ("getName".toUpperCase().equals(method.getName().toUpperCase()))
                {
                    resultMap.put("name", method.invoke(object));
                } else if ("getSeq".toUpperCase().equals(method.getName().toUpperCase()))
                {
                    resultMap.put("seq", method.invoke(object));
                } else if ("getStatus".toUpperCase().equals(method.getName().toUpperCase()))
                {
                    resultMap.put("status", method.invoke(object));
                } else if ("getCoreId".toUpperCase().equals(method.getName().toUpperCase()))
                {
                    resultMap.put("coreId", method.invoke(object));
                } else if ("getDescription".toUpperCase().equals(method.getName().toUpperCase()))
                {
                    resultMap.put("description", method.invoke(object));
                }
            }
            cache.put(key, resultMap);
            logger.info("add cache name:{}, key:{}, value:{}", CacheNameEnum.EDUMETA.getName(), key, resultMap);
        } catch (Exception e)
        {
            logger.error("cache key:{} put error:{}", key, e.getMessage());
        }
    }

}
