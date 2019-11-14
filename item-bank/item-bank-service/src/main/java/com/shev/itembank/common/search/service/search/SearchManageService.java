package com.shev.itembank.common.search.service.search;

import com.shev.itembank.common.base.result.RecordSet;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface SearchManageService
{

    public RecordSet searchExerciseByKeyword(Boolean isPublic, String tenantId, String knowledgePointId, List<Integer> formType, List<Integer> difficulty, List<Integer> significance, Integer subjectId, String categoryId, String paramKey, String proficiencyId, Integer start, Integer max)
            throws Exception;

    public RecordSet searchPapers(String tenantId, Boolean isPublic, String queryText, Integer examTypeId, Integer subject, Integer educationalStage, Integer year, String cityId, String provinceId, Boolean isSchool, Boolean isContest, Integer gradeId, Integer start, Integer max) throws Exception;

}

