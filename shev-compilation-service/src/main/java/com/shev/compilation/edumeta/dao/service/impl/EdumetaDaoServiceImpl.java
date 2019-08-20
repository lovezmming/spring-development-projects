package com.shev.compilation.edumeta.dao.service.impl;

import com.shev.compilation.common.datasource.BaseDao;
import com.shev.compilation.common.datasource.dynamic.DataSourceMasterQuery;
import com.shev.compilation.common.datasource.dynamic.DataSourceQuery;
import com.shev.compilation.common.datasource.dynamic.DataSourceUpdate;
import com.shev.compilation.common.support.compare.UpdateTimeDescComparator;
import com.shev.compilation.common.support.web.RecordSet;
import com.shev.compilation.edumeta.dao.custom.*;
import com.shev.compilation.edumeta.dao.mapper.*;
import com.shev.compilation.edumeta.dao.service.EdumetaDaoService;
import com.shev.compilation.edumeta.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class EdumetaDaoServiceImpl extends BaseDao implements EdumetaDaoService
{

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private EducationalStageMapper educationalStageMapper;

    @Autowired
    private DifficultyMapper difficultyMapper;

    @Autowired
    private FormTypeMapper formTypeMapper;

    @Autowired
    private SemanticTypeMapper semanticTypeMapper;

    @Autowired
    private SignificanceMapper significanceMapper;

    @Autowired
    private CompletenessMapper completenessMapper;

    @Autowired
    private ErrorMetaMapper errorMetaMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SourceMapper sourceMapper;

    @Autowired
    private SourcePackageMapper sourcePackageMapper;

    @Autowired
    private KnowledgePointMapper knowledgePointMapper;

    private Comparator getComparator(Comparator<Object> comparator)
    {
        return comparator != null ? comparator : new UpdateTimeDescComparator();
    }

    @Override
    public Subject getSubjectById(String id)
    {
        return (Subject) this.queryById(SubjectMapper.class, id);
    }

    @Override
    public RecordSet getSubjectsByParams(Map<String, Object> params, Comparator<Object> comparator)
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        List<Object> subjects = this.query(SubjectCustomDao.class, "getSubjectsByParams", params, getComparator(comparator));
        return new RecordSet(0, subjects.size(), subjects.size(), subjects.toArray());
    }

    @Override
    public void updateSubject(Subject subject)
    {
        this.update(SubjectMapper.class, subject.getId(), subject);
    }

    @Override
    @DataSourceUpdate
    public void createSubject(Subject subject)
    {
        subjectMapper.insert(subject);
    }

    @Override
    public RecordSet getEducationalStagesByParams(Map<String, Object> params, Comparator<Object> comparator)
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        List<Object> educationalStages = this.query(EducationalStageCustomDao.class, "getEducationalStagesByParams", params, getComparator(comparator));
        return new RecordSet(0, educationalStages.size(), educationalStages.size(), educationalStages.toArray());
    }

    @Override
    public EducationalStage getEducationalStageById(String id)
    {
        return (EducationalStage) this.queryById(EducationalStageMapper.class, id);
    }

    @Override
    public void updateEducationalStage(EducationalStage educationalStage)
    {
        this.update(EducationalStageMapper.class, educationalStage.getId(), educationalStage);
    }

    @Override
    @DataSourceUpdate
    public void createEducationalStage(EducationalStage educationalStage)
    {
        educationalStageMapper.insert(educationalStage);
    }

    @Override
    public RecordSet getDifficultiesByParams(Map<String, Object> params, Comparator<Object> comparator)
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        List<Object> difficulties = this.query(DifficultyCustomDao.class, "getDifficultiesByParams", params, getComparator(comparator));
        return new RecordSet(0, difficulties.size(), difficulties.size(), difficulties.toArray());
    }

    @Override
    public Difficulty getDifficultyById(String id)
    {
        return (Difficulty) this.queryById(DifficultyMapper.class, id);
    }

    @Override
    public void updateDifficulty(Difficulty difficulty)
    {
        this.update(DifficultyMapper.class, difficulty.getId(), difficulty);
    }

    @Override
    @DataSourceUpdate
    public void createDifficulty(Difficulty difficulty)
    {
        difficultyMapper.insert(difficulty);
    }

    @Override
    public RecordSet getFormTypesByParams(Map<String, Object> params, Comparator<Object> comparator)
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        List<Object> formTypes = this.query(FormTypeCustomDao.class, "getFormTypesByParams", params, getComparator(comparator));
        return new RecordSet(0, formTypes.size(), formTypes.size(), formTypes.toArray());
    }

    @Override
    public FormType getFormTypeById(String id)
    {
        return (FormType) this.queryById(FormTypeMapper.class, id);
    }

    @Override
    public void updateFormType(FormType formType)
    {
        this.update(FormTypeMapper.class, formType.getId(), formType);
    }

    @Override
    @DataSourceUpdate
    public void createFormType(FormType formType)
    {
        formTypeMapper.insert(formType);
    }

    @Override
    public RecordSet getSemanticTypesByParams(Map<String, Object> params, Comparator<Object> comparator)
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        List<Object> semanticTypes = this.query(SemanticTypeCustomDao.class, "getSemanticTypesByParams", params, getComparator(comparator));
        return new RecordSet(0, semanticTypes.size(), semanticTypes.size(), semanticTypes.toArray());
    }

    @Override
    public SemanticType getSemanticTypeById(String id)
    {
        return (SemanticType) this.queryById(SemanticTypeMapper.class, id);
    }

    @Override
    public void updateSemanticType(SemanticType semanticType)
    {
        this.update(SemanticTypeMapper.class, semanticType.getId(), semanticType);
    }

    @Override
    @DataSourceUpdate
    public void createSemanticType(SemanticType semanticType)
    {
        semanticTypeMapper.insert(semanticType);
    }

    @Override
    public RecordSet getSignificancesByParams(Map<String, Object> params, Comparator<Object> comparator)
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        List<Object> significances = this.query(SignificanceCustomDao.class, "getSignificancesByParams", params, getComparator(comparator));
        return new RecordSet(0, significances.size(), significances.size(), significances.toArray());
    }

    @Override
    public Significance getSignificanceById(String id)
    {
        return (Significance) this.queryById(SignificanceMapper.class, id);
    }

    @Override
    public void updateSignificance(Significance significance)
    {
        this.update(SignificanceMapper.class, significance.getId(), significance);
    }

    @Override
    @DataSourceUpdate
    public void createSignificance(Significance significance)
    {
        significanceMapper.insert(significance);
    }

    @Override
    public RecordSet getCompletenessesByParams(Map<String, Object> params, Comparator<Object> comparator)
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        List<Object> completenesses = this.query(CompletenessCustomDao.class, "getCompletenessesByParams", params, getComparator(comparator));
        return new RecordSet(0, completenesses.size(), completenesses.size(), completenesses.toArray());
    }

    @Override
    public Completeness getCompletenessById(String id)
    {
        return (Completeness) this.queryById(CompletenessMapper.class, id);
    }

    @Override
    public void updateCompleteness(Completeness completeness)
    {
        this.update(CompletenessMapper.class, completeness.getId(), completeness);
    }

    @Override
    @DataSourceUpdate
    public void createCompleteness(Completeness completeness)
    {
        completenessMapper.insert(completeness);
    }

    @Override
    public RecordSet getErrorMetasByParams(Map<String, Object> params, Comparator<Object> comparator)
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        List<Object> errorMetas = this.query(ErrorMetaCustomDao.class, "getErrorMetasByParams", params, getComparator(comparator));
        return new RecordSet(0, errorMetas.size(), errorMetas.size(), errorMetas.toArray());
    }

    @Override
    public ErrorMeta getErrorMetaById(String id)
    {
        return (ErrorMeta) queryById(ErrorMetaMapper.class, id);
    }

    @Override
    public void updateErrorMeta(ErrorMeta errorMeta)
    {
        this.update(ErrorMetaMapper.class, errorMeta.getId(), errorMeta);
    }

    @Override
    @DataSourceUpdate
    public void createErrorMeta(ErrorMeta errorMeta)
    {
        errorMetaMapper.insert(errorMeta);
    }

    @Override
    public RecordSet getCategoriesByParams(Map<String, Object> params, Comparator<Object> comparator)
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        List<Object> categories = this.query(CategoryCustomDao.class, "getCategoriesByParams", params, getComparator(comparator));
        return new RecordSet(0, categories.size(), categories.size(), categories.toArray());
    }

    @Override
    public Category getCategoryById(String id)
    {
        return (Category) this.queryById(CategoryMapper.class, id);
    }

    @Override
    public void updateCategory(Category category)
    {
        this.update(CategoryMapper.class, category.getId(), category);
    }

    @Override
    @DataSourceUpdate
    public void createCategory(Category category)
    {
        categoryMapper.insert(category);
    }

    @Override
    public RecordSet getSourcesByParams(Map<String, Object> params, Comparator<Object> comparator)
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        List<Object> sources = this.query(SourceCustomDao.class, "getSourcesByParams", params, getComparator(comparator));
        return new RecordSet(0, sources.size(), sources.size(), sources.toArray());
    }

    @Override
    public Source getSourceById(String id)
    {
        return (Source) this.queryById(SourceMapper.class, id);
    }

    @Override
    public void updateSource(Source source)
    {
        this.update(SourceMapper.class, source.getId(), source);
    }

    @Override
    @DataSourceUpdate
    public void createSource(Source source)
    {
        sourceMapper.insert(source);
    }

    @Override
    public RecordSet getSourcePackagesByParams(Map<String, Object> params, Comparator<Object> comparator)
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        List<Object> sourcePackages = this.query(SourcePackageCustomDao.class, "getSourcePackagesByParams", params, getComparator(comparator));
        return new RecordSet(0, sourcePackages.size(), sourcePackages.size(), sourcePackages.toArray());
    }

    @Override
    public SourcePackage getSourcePackageById(String id)
    {
        return (SourcePackage) this.queryById(SourcePackageMapper.class, id);
    }

    @Override
    public void updateSourcePackage(SourcePackage sourcePackage)
    {
        this.update(SourcePackageMapper.class, sourcePackage.getId(), sourcePackage);
    }

    @Override
    @DataSourceUpdate
    public void createSourcePackage(SourcePackage sourcePackage)
    {
        sourcePackageMapper.insert(sourcePackage);
    }

    @Override
    public RecordSet getKnowledgePointsByParams(Map<String, Object> params, Comparator<Object> comparator)
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        List<Object> knowledgePoints = this.query(KnowledgePointCustomDao.class, "getKnowledgePointsByParams", params, getComparator(comparator));
        return new RecordSet(0, knowledgePoints.size(), knowledgePoints.size(), knowledgePoints.toArray());
    }

    @Override
    public KnowledgePoint getKnowledgePointById(String id)
    {
        return (KnowledgePoint) this.queryById(KnowledgePointMapper.class, id);
    }

    @Override
    public void updateKnowledgePoint(KnowledgePoint knowledgePoint)
    {
        this.update(KnowledgePointMapper.class, knowledgePoint.getId(), knowledgePoint);
    }

    @Override
    @DataSourceUpdate
    public void createKnowledgePoint(KnowledgePoint knowledgePoint)
    {
        knowledgePointMapper.insert(knowledgePoint);
    }

    @Override
    public void deleteKnowledgePoint(KnowledgePoint knowledgePoint)
    {
        deleteKnowledgePointById(knowledgePoint.getId(), null);
    }

    @Override
    public void deleteKnowledgePointById(String id, String currentUserId)
    {
        this.delete(KnowledgePointMapper.class, id);
    }

}
