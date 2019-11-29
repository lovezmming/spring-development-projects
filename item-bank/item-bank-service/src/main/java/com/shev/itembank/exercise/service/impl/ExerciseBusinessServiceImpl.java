package com.shev.itembank.exercise.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.shev.itembank.common.Enum.*;
import com.shev.itembank.common.base.result.RecordSet;
import com.shev.itembank.common.base.util.*;
import com.shev.itembank.common.redis.service.CacheService;
import com.shev.itembank.common.search.service.search.SearchManageService;
import com.shev.itembank.edumeta.entity.*;
import com.shev.itembank.edumeta.mapper.*;
import com.shev.itembank.exercise.custom.*;
import com.shev.itembank.exercise.entity.*;
import com.shev.itembank.exercise.mapper.*;
import com.shev.itembank.exercise.request.*;
import com.shev.itembank.exercise.service.ExerciseBusinessService;
import com.shev.itembank.paper.mapper.PaperMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

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
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> httpEntity = new HttpEntity<String>(requestJson, httpHeaders);
        ResponseEntity<Map> responseEntity = restTemplate.exchange(latexUrl, HttpMethod.POST, httpEntity, Map.class);
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
