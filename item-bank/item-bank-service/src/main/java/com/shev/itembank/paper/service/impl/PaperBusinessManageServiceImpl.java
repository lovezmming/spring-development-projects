package com.shev.itembank.paper.service.impl;

import com.shev.itembank.common.base.result.RecordSet;
import com.shev.itembank.common.base.util.DateTimeUtil;
import com.shev.itembank.common.search.service.search.SearchManageService;
import com.shev.itembank.paper.entity.*;
import com.shev.itembank.paper.request.GetPaperDetailRequest;
import com.shev.itembank.paper.request.GetPaperListRequest;
import com.shev.itembank.paper.service.PaperBusinessManageService;
import com.shev.itembank.system.service.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("PaperBusinessManageServiceImpl")
public class PaperBusinessManageServiceImpl implements PaperBusinessManageService
{

    @Autowired
    private SearchManageService searchManageService;

    @Autowired
    private PublicService publicService;

    @Value("${EXERCISE_IMAGE_DOMAIN_NAME:http\\://img.educloudservice.cn/img-new/}")
    private String imagePrefix;

    private Date specialDate = DateTimeUtil.getDateTime("2018-04-16 00:00:00");

    @SuppressWarnings("unchecked")
    @Override
    public RecordSet getPaperList(GetPaperListRequest request) throws Exception
    {
        Boolean isPublic = publicService.isPublic(tenantId);
        Integer examTypeId = request.getExamTypeId();
        Integer subject = request.getSubject();
        Integer educationalStage = request.getEducationalStage();
        Integer year = request.getYear();
        String cityId = request.getCityId();
        String provinceId = request.getProvinceId();
        Boolean isSchool = request.getIsSchool();
        Boolean isContest = request.getIsContest();
        Integer gradeId = request.getGradeId();
        Integer start = request.getStart();
        Integer max = request.getMax();
        RecordSet rs = searchManageService.searchPapers(tenantId, isPublic, queryText, examTypeId, subject, educationalStage, year, cityId, provinceId, isSchool, isContest, gradeId, start, max);
        List<Object> list = new ArrayList<Object>();
        for (Object ob : rs.getValues())
        {
            Map<String, Object> map = new HashMap<String, Object>();
            Map<String, Object> paperMap = (Map<String, Object>) ob;
            map.put("id", paperMap.get("id"));
            list.add(map);
        }
        return new RecordSet(start, max, rs.getTotalCount(), list.toArray());
    }

    @Override
    public RecordSet getPaperDetail(GetPaperDetailRequest request) throws Exception
    {
        return null;
    }

}
