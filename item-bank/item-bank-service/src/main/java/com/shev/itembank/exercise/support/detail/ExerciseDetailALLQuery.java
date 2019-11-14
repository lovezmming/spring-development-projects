package com.shev.itembank.exercise.support.detail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shev.itembank.exercise.mapper.*;
import com.shev.itembank.resource.entity.ResourceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shev.itembank.common.Enum.ConstantEnum;
import com.shev.itembank.common.Enum.ExerciseRelationEnum;
import com.shev.itembank.common.Enum.TenantAttributeEnum;
import com.shev.itembank.common.base.result.RecordSet;
import com.shev.itembank.common.base.util.TextUtil;
import com.shev.itembank.common.search.service.index.IndexManageService;
import com.shev.itembank.edumeta.entity.City;
import com.shev.itembank.edumeta.entity.KnowledgePoint;
import com.shev.itembank.edumeta.entity.Proficiency;
import com.shev.itembank.edumeta.entity.TextbookStructureCategory;
import com.shev.itembank.edumeta.mapper.CityMapper;
import com.shev.itembank.edumeta.mapper.KnowledgePointMapper;
import com.shev.itembank.edumeta.mapper.ProficiencyMapper;
import com.shev.itembank.edumeta.mapper.TextbookStructureCategoryMapper;
import com.shev.itembank.exercise.custom.*;
import com.shev.itembank.exercise.entity.*;
import com.shev.itembank.exercise.support.ExerciseDetail;
import com.shev.itembank.resource.entity.Resource;
import com.shev.itembank.resource.service.ResourceService;
import com.shev.itembank.system.custom.TenantAttributeCustomMapper;
import com.shev.itembank.system.entity.TenantAttribute;

@Service("ExerciseDetailALL")
public class ExerciseDetailALLQuery extends ExerciseDetail
{

    @Override
    public Map<String, Object> getExerciseDetail(Boolean isPublic, String tenantId, Exercise exercise, String imagePrefix) throws Exception
    {

    }
}
