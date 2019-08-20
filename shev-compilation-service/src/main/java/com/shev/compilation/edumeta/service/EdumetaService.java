package com.shev.compilation.edumeta.service;

import com.shev.compilation.common.support.web.RecordSet;
import com.shev.compilation.edumeta.request.*;

import java.util.Map;

public interface EdumetaService
{
    RecordSet getSubjectList(SubjectGetRequest subjectGetRequest);

    RecordSet getSubjectDetail(SubjectDetailGetRequest subjectDetailGetRequest);

    Map<String, Object> updateSubject(SubjectUpdateRequest subjectUpdateRequest);

    Map<String, Object> createSubject(SubjectCreateRequest subjectCreateRequest);

    RecordSet getEducationalStages(EducationalStageGetRequest educationalStageGetRequest);

    RecordSet getEducationalStageDetail(EducationalStageDetailGetRequest educationalStageDetailGetRequest);

    Map<String, Object> updateEducationalStage(EducationalStageUpdateRequest educationalStageUpdateRequest);

    Map<String, Object> createEducationalStage(EducationalStageCreateRequest educationalStageCreateRequest);

    RecordSet getDifficultys(DifficultyGetRequest difficultyGetRequest);

    RecordSet getDifficultyDetail(DifficultyDetailGetRequest difficultyDetailGetRequest);

    Map<String, Object> updateDifficulty(DifficultyUpdateRequest difficultyUpdateRequest);

    Map<String, Object> createDifficulty(DifficultyCreateRequest difficultyCreateRequest);

    RecordSet getFormTypes(FormTypeGetRequest formTypeGetRequest);

    RecordSet getFormTypeDetail(FormTypeDetailGetRequest formTypeDetailGetRequest);

    Map<String, Object> updateFormType(FormTypeUpdateRequest formTypeUpdateRequest);

    Map<String, Object> createFormType(FormTypeCreateRequest formTypeCreateRequest);

    RecordSet getSemanticTypes(SemanticTypeGetRequest semanticTypeGetRequest);

    RecordSet getSemanticTypeDetail(SemanticTypeDetailGetRequest semanticTypeDetailGetRequest);

    Map<String, Object> updateSemanticType(SemanticTypeUpdateRequest semanticTypeUpdateRequest);

    Map<String, Object> createSemanticType(SemanticTypeCreateRequest semanticTypeCreateRequest);

    RecordSet getSignificances(SignificanceGetRequest significanceGetRequest);

    RecordSet getSignificanceDetail(SignificanceDetailGetRequest significanceDetailGetRequest);

    Map<String, Object> updateSignificance(SignificanceUpdateRequest significanceUpdateRequest);

    Map<String, Object> createSignificance(SignificanceCreateRequest significanceCreateRequest);

    RecordSet getCompleteness(CompletenessGetRequest completenessGetRequest);

    RecordSet getCompletenessDetail(CompletenessDetailGetRequest completenessDetailGetRequest);

    Map<String, Object> updateCompleteness(CompletenessUpdateRequest completenessUpdateRequest);

    Map<String, Object> createCompleteness(CompletenessCreateRequest completenessCreateRequest);

    RecordSet getErrorMetas(ErrorMetaGetRequest errorMetaGetRequest);

    RecordSet getErrorMetaDetail(ErrorMetaDetailGetRequest errorMetaDetailGetRequest);

    Map<String, Object> updateErrorMeta(ErrorMetaUpdateRequest errorMetaUpdateRequest);

    Map<String, Object> createErrorMeta(ErrorMetaCreateRequest errorMetaCreateRequest);

    RecordSet getCategorys(CategoryGetRequest categoryGetRequest);

    RecordSet getCategoryDetail(CategoryDetailGetRequest categoryDetailGetRequest);

    Map<String, Object> updateCategory(CategoryUpdateRequest categoryUpdateRequest);

    Map<String, Object> createCategory(CategoryCreateRequest categoryCreateRequest);

    RecordSet getSources(SourceGetRequest sourceGetRequest);

    RecordSet getSourceDetail(SourceDetailGetRequest sourceDetailGetRequest);

    Map<String, Object> updateSource(SourceUpdateRequest sourceUpdateRequest);

    Map<String, Object> createSource(SourceCreateRequest sourceCreateRequest);

    RecordSet getSourceTypes(SourceTypeGetRequest sourceTypeGetRequest);

    RecordSet getSourceTypeDetail(SourceTypeDetailGetRequest sourceTypeDetailGetRequest);

    Map<String, Object> updateSourceType(SourceTypeUpdateRequest sourceTypeUpdateRequest);

    Map<String, Object> createSourceType(SourceTypeCreateRequest sourceTypeCreateRequest);

    RecordSet getKnowledgePoints(KnowledgePointGetRequest knowledgePointGetRequest);

    RecordSet getKnowledgePointDetail(KnowledgePointDetailGetRequest knowledgePointDetailGetRequest);

    Map<String, Object> exchangeKnowledgePoint(KnowledgePointExchangeRequest knowledgePointExchangeRequest);

    Map<String, Object> createKnowledgePoint(KnowledgePointCreateRequest knowledgePointCreateRequest);

    Map<String, Object> updateKnowledgePoint(KnowledgePointUpdateRequest knowledgePointUpdateRequest);

    void deleteKnowledgePoint(KnowledgePointDeleteRequest knowledgePointDeleteRequest);

}
