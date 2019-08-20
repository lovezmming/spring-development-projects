package com.shev.compilation.edumeta.dao.service;

import com.shev.compilation.common.support.web.RecordSet;
import com.shev.compilation.edumeta.entity.*;

import java.util.Comparator;
import java.util.Map;

public interface EdumetaDaoService
{

    Subject getSubjectById(String id);

    RecordSet getSubjectsByParams(Map<String, Object> params, Comparator<Object> comparator);

    void updateSubject(Subject subject);

    void createSubject(Subject subject);

    RecordSet getEducationalStagesByParams(Map<String, Object> params, Comparator<Object> comparator);

    EducationalStage getEducationalStageById(String id);

    void updateEducationalStage(EducationalStage educationalStage);

    void createEducationalStage(EducationalStage educationalStage);

    RecordSet getDifficultiesByParams(Map<String, Object> params, Comparator<Object> comparator);

    Difficulty getDifficultyById(String id);

    void updateDifficulty(Difficulty difficulty);

    void createDifficulty(Difficulty difficulty);

    RecordSet getFormTypesByParams(Map<String, Object> params, Comparator<Object> comparator);

    FormType getFormTypeById(String id);

    void updateFormType(FormType formType);

    void createFormType(FormType formType);

    RecordSet getSemanticTypesByParams(Map<String, Object> params, Comparator<Object> comparator);

    SemanticType getSemanticTypeById(String id);

    void updateSemanticType(SemanticType semanticType);

    void createSemanticType(SemanticType semanticType);

    RecordSet getSignificancesByParams(Map<String, Object> params, Comparator<Object> comparator);

    Significance getSignificanceById(String id);

    void updateSignificance(Significance significance);

    void createSignificance(Significance significance);

    RecordSet getCompletenessesByParams(Map<String, Object> params, Comparator<Object> comparator);

    Completeness getCompletenessById(String id);

    void updateCompleteness(Completeness completeness);

    void createCompleteness(Completeness completeness);

    RecordSet getErrorMetasByParams(Map<String, Object> params, Comparator<Object> comparator);

    ErrorMeta getErrorMetaById(String id);

    void updateErrorMeta(ErrorMeta errorMeta);

    void createErrorMeta(ErrorMeta errorMeta);

    RecordSet getCategoriesByParams(Map<String, Object> params, Comparator<Object> comparator);

    Category getCategoryById(String id);

    void updateCategory(Category category);

    void createCategory(Category category);

    RecordSet getSourcesByParams(Map<String, Object> params, Comparator<Object> comparator);

    Source getSourceById(String id);

    SourcePackage getSourcePackageById(String id);

    void updateSource(Source source);

    void createSource(Source source);

    RecordSet getSourcePackagesByParams(Map<String, Object> params, Comparator<Object> comparator);

    void updateSourcePackage(SourcePackage sourcePackage);

    void createSourcePackage(SourcePackage sourcePackage);

    RecordSet getKnowledgePointsByParams(Map<String, Object> params, Comparator<Object> comparator);

    KnowledgePoint getKnowledgePointById(String id);

    void updateKnowledgePoint(KnowledgePoint knowledgePoint);

    void createKnowledgePoint(KnowledgePoint knowledgePoint);

    void deleteKnowledgePoint(KnowledgePoint knowledgePoint);

    void deleteKnowledgePointById(String id, String currentUserId);
}
