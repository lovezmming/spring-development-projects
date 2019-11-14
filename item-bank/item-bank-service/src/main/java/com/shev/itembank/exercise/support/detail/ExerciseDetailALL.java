package com.shev.itembank.exercise.support.detail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shev.itembank.common.Enum.ConstantEnum;
import com.shev.itembank.common.search.service.index.IndexManageService;
import com.shev.itembank.common.search.service.search.SearchManageService;
import com.shev.itembank.edumeta.mapper.CityMapper;
import com.shev.itembank.edumeta.mapper.KnowledgePointMapper;
import com.shev.itembank.edumeta.mapper.ProficiencyMapper;
import com.shev.itembank.edumeta.mapper.TextbookStructureCategoryMapper;
import com.shev.itembank.exercise.custom.ExerciseAnswerOptionAddInfoCustomMapper;
import com.shev.itembank.exercise.custom.ExerciseExerciseAbilityRelationCustomMapper;
import com.shev.itembank.exercise.custom.ExerciseFinalAnswerAddIfnoCustomMapper;
import com.shev.itembank.exercise.custom.ExerciseTextbookStructureCategoryRelationCustomMapper;
import com.shev.itembank.exercise.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shev.itembank.common.base.result.RecordSet;
import com.shev.itembank.common.base.util.TextUtil;
import com.shev.itembank.edumeta.entity.City;
import com.shev.itembank.edumeta.entity.KnowledgePoint;
import com.shev.itembank.edumeta.entity.Proficiency;
import com.shev.itembank.edumeta.entity.TextbookStructureCategory;
import com.shev.itembank.exercise.entity.*;
import com.shev.itembank.exercise.support.ExerciseDetail;

@Deprecated
@Service("ExerciseDetailALLQuery")
public class ExerciseDetailALL extends ExerciseDetail
{

    @Autowired
    private ExerciseMapper exerciseMapper;

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getExerciseDetail(Boolean isPublic, String tenantId, Exercise exercise, String imagePrefix) throws Exception
    {
        System.out.println("begin "+System.currentTimeMillis());
    }
}
