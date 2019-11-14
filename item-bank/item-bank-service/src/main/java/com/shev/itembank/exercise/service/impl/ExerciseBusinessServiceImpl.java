package com.shev.itembank.exercise.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shev.itembank.common.Enum.*;
import com.shev.itembank.common.base.exception.BusinessException;
import com.shev.itembank.common.base.result.RecordSet;
import com.shev.itembank.common.base.util.*;
import com.shev.itembank.common.base.web.HttpServletBasicRequest;
import com.shev.itembank.common.redis.service.CacheService;
import com.shev.itembank.common.search.service.search.SearchManageService;
import com.shev.itembank.edumeta.entity.*;
import com.shev.itembank.edumeta.mapper.*;
import com.shev.itembank.exercise.custom.*;
import com.shev.itembank.exercise.entity.*;
import com.shev.itembank.exercise.mapper.*;
import com.shev.itembank.exercise.request.*;
import com.shev.itembank.exercise.service.ExerciseBusinessService;
import com.shev.itembank.exercise.support.ExerciseDetail;
import com.shev.itembank.exercise.support.event.ExerciseSpEvent;
import com.shev.itembank.paper.custom.PaperSectionExerciseRelationCustomMapper;
import com.shev.itembank.paper.entity.Paper;
import com.shev.itembank.paper.entity.PaperSectionExerciseRelation;
import com.shev.itembank.paper.mapper.PaperMapper;
import com.shev.itembank.resource.entity.AudioAttribute;
import com.shev.itembank.resource.entity.QiNiuFileInfo;
import com.shev.itembank.resource.entity.Resource;
import com.shev.itembank.resource.mapper.AudioAttributeMapper;
import com.shev.itembank.resource.mapper.ResourceMapper;
import com.shev.itembank.resource.service.UploadService;
import com.shev.itembank.system.custom.PartnerCustomMapper;
import com.shev.itembank.system.entity.Partner;
import com.shev.itembank.system.service.PublicService;

@Service("ExerciseBusinessServiceImpl")
public class ExerciseBusinessServiceImpl implements ExerciseBusinessService
{

    private static final Logger logger = LoggerFactory.getLogger(ExerciseBusinessServiceImpl.class);

    @Autowired
    private SearchManageService searchManageService;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private PaperMapper paperMapper;

    @Autowired
    private ExerciseMapper exerciseMapper;

    @Value("${EXERCISE_IMAGE_DOMAIN_NAME:http\\://img.educloudservice.cn/img-new/}")
    private String imagePrefix;

    @Value("${latex.url}")
    private String latexUrl;

    @Override
    public RecordSet searchByKeyword(ExerciseSearchByKeywordRequest request) throws Exception
    {

        return null;
    }

    @Override
    public RecordSet getExerciseDetail(ExerciseGetDetailRequest request) throws Exception
    {
        return null;
    }

    private Map<String, Object> latex2Pic(String tenantId, Integer type, String solutionsXMLStr)
    {
        Map<String, Object> params = new HashMap<>();;
        params.put("tenantId", tenantId);
        params.put("type", type);
        params.put("xmlContent", solutionsXMLStr);
        String requestJson = JSONObject.toJSONString(params);
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(latexUrl, requestJson, Map.class);
        return (Map<String, Object>) responseEntity.getBody();
    }

    public Object updateExercise(UpdateExerciseRequest request) throws Exception
    {
        //发送异步队列消息，对题目索引进行更新
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("exerciseId", exerciseId);
        map.put("isPublic", isPublic);
        map.put("tenantId", tenantId);
        exerciseSpEvent.dealExercise(map, EventEnum.UPDATE);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("id", exerciseId);
        return result;
    }

}
