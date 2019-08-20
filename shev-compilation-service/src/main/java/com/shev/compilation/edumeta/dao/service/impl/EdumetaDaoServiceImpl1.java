/*
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EdumetaDaoServiceImpl1 extends BaseDao implements EdumetaDaoService
{

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private CompletenessMapper completenessMapper;

    @Autowired
    private CompletenessCustomDao completenessCustomDao;

    @Autowired
    private SubjectCustomDao subjectCustomDao;

    @Autowired
    private EducationalStageCustomDao educationalStageCustomDao;

    @Autowired
    private EducationalStageMapper educationalStageMapper;

    @Autowired
    private DifficultyCustomDao difficultyCustomDao;

    @Autowired
    private DifficultyMapper difficultyMapper;

    @Autowired
    private FormTypeCustomDao formTypeCustomDao;

    @Autowired
    private FormTypeMapper formTypeMapper;

    @Autowired
    private KnowledgePointMapper knowledgePointMapper;

    @Autowired
    private KnowledgePointCustomDao knowledgePointCustomDao;

    @Autowired
    private SourcePackageMapper sourcePackageMapper;

    @Autowired
    private SourcePackageCustomDao sourcePackageCustomDao;

    @Autowired
    private SourceMapper sourceMapper;

    @Autowired
    private SourceCustomDao sourceCustomDao;

    @Autowired
    private CategoryCustomDao categoryCustomDao;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ErrorMetaCustomDao errorMetaCustomDao;

    @Autowired
    private ErrorMetaMapper errorMetaMapper;

    @Autowired
    private SignificanceCustomDao significanceCustomDao;

    @Autowired
    private SignificanceMapper significanceMapper;

    @Autowired
    private SemanticTypeCustomDao semanticTypeCustomDao;

    @Autowired
    private SemanticTypeMapper semanticTypeMapper;

    @Override
    public Subject getSubjectById(String id)
    {
        return getDataSubjectById(id, true);
    }

    private Subject getDataSubjectById(String id, Boolean isSlave)
    {
        Subject subject = null;
        for (int i = 0; i < slaveCount; i++)
        {
            if (isSlave)
            {
                subject = getSlaveSubjectById(id);
            } else
            {
                subject = getMasterSubjectById(id);
            }
            if (subject != null)
            {
                break;
            }
        }
        return subject;
    }

    @Override
    public RecordSet getSubjectsByParams(Map<String, Object> params)
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        List<Object> subjects = this.queryNoRepeat(SubjectCustomDao.class, "getSubjectsByParams", params, new UpdateTimeDescComparator());
        return new RecordSet(0, subjects.size(), subjects.size(), subjects.toArray());
    }

    @Override
    public void updateSubject(Subject subject)
    {
        getDataSubjectById(subject.getId(), false);
        updateMasterSubject(subject);
    }

    @Override
    @DataSourceUpdate
    public void createSubject(Subject subject)
    {
        subjectMapper.insert(subject);
    }

    @Override
    public List<EducationalStage> getEducationalStagesByParams(Map<String, Object> params)
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        List<EducationalStage> educationalStages = new ArrayList<>();
        for (int i =0; i<slaveCount; i++)
        {
            educationalStages.addAll(getSlaveEducationalStagesByParams(params));
        }
        return educationalStages;
    }

    @Override
    public EducationalStage getEducationalStageById(String id)
    {
        return getDataEducationalStageById(id, true);
    }

    private EducationalStage getDataEducationalStageById(String id, Boolean isSlave)
    {
        EducationalStage educationalStage = null;
        for (int i = 0; i < slaveCount; i++)
        {
            if (isSlave)
            {
                educationalStage = getSlaveEducationalStageById(id);
            } else
            {
                educationalStage = getMasterEducationalStageById(id);
            }
            if (educationalStage != null)
            {
                break;
            }
        }
        return educationalStage;
    }

    @Override
    public void updateEducationalStage(EducationalStage educationalStage)
    {
        getDataEducationalStageById(educationalStage.getId(), false);
        updateMasterEducationalStage(educationalStage);
    }

    @Override
    @DataSourceUpdate
    public void createEducationalStage(EducationalStage educationalStage)
    {
        educationalStageMapper.insert(educationalStage);
    }

    @Override
    public List<Difficulty> getDifficultiesByParams(Map<String, Object> params)
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        List<Difficulty> difficulties = new ArrayList<>();
        for (int i =0; i<slaveCount; i++)
        {
            difficulties.addAll(getSlaveDifficultiesByParams(params));
        }
        return difficulties;
    }

    @Override
    public Difficulty getDifficultyById(String id)
    {
        return getDataDifficultyById(id, true);
    }

    private Difficulty getDataDifficultyById(String id, Boolean isSlave)
    {
        Difficulty difficulty = null;
        for (int i = 0; i < slaveCount; i++)
        {
            if (isSlave)
            {
                difficulty = getSlaveDifficultyById(id);
            } else
            {
                difficulty = getMasterDifficultyById(id);
            }
            if (difficulty != null)
            {
                break;
            }
        }
        return difficulty;
    }

    @Override
    public void updateDifficulty(Difficulty difficulty)
    {
        getDataDifficultyById(difficulty.getId(), false);
        updateMasterDifficulty(difficulty);
    }

    @Override
    @DataSourceUpdate
    public void createDifficulty(Difficulty difficulty)
    {
        difficultyMapper.insert(difficulty);
    }

    @Override
    public List<FormType> getFormTypesByParams(Map<String, Object> params)
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        List<FormType> formTypes = new ArrayList<>();
        for (int i =0; i<slaveCount; i++)
        {
            formTypes.addAll(getSlaveFormTypesByParams(params));
        }
        return formTypes;
    }

    @Override
    public FormType getFormTypeById(String id)
    {
        return getDataFormTypeById(id, true);
    }

    private FormType getDataFormTypeById(String id, Boolean isSlave)
    {
        FormType formType = null;
        for (int i = 0; i < slaveCount; i++)
        {
            if (isSlave)
            {
                formType = getSlaveFormTypeById(id);
            } else
            {
                formType = getMasterFormTypeById(id);
            }
            if (formType != null)
            {
                break;
            }
        }
        return formType;
    }

    @Override
    public void updateFormType(FormType formType)
    {
        getDataFormTypeById(formType.getId(), false);
        updateMasterFormType(formType);
    }

    @Override
    @DataSourceUpdate
    public void createFormType(FormType formType)
    {
        formTypeMapper.insert(formType);
    }

    @Override
    public List<SemanticType> getSemanticTypesByParams(Map<String, Object> params)
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        List<SemanticType> semanticTypes = new ArrayList<>();
        for (int i =0; i<slaveCount; i++)
        {
            semanticTypes.addAll(getSlaveSemanticTypesByParams(params));
        }
        return semanticTypes;
    }

    @Override
    public SemanticType getSemanticTypeById(String id)
    {
        return getDataSemanticTypeById(id, true);
    }

    private SemanticType getDataSemanticTypeById(String id, Boolean isSlave)
    {
        SemanticType semanticType = null;
        for (int i = 0; i < slaveCount; i++)
        {
            if (isSlave)
            {
                semanticType = getSlaveSemanticTypeById(id);
            } else
            {
                semanticType = getMasterSemanticTypeById(id);
            }
            if (semanticType != null)
            {
                break;
            }
        }
        return semanticType;
    }

    @Override
    public void updateSemanticType(SemanticType semanticType)
    {
        getDataSemanticTypeById(semanticType.getId(), false);
        updateMasterSemanticType(semanticType);
    }

    @Override
    @DataSourceUpdate
    public void createSemanticType(SemanticType semanticType)
    {
        semanticTypeMapper.insert(semanticType);
    }

    @Override
    public List<Significance> getSignificancesByParams(Map<String, Object> params)
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        List<Significance> significances = new ArrayList<>();
        for (int i =0; i<slaveCount; i++)
        {
            significances.addAll(getSlaveSignificancesByParams(params));
        }
        return significances;
    }

    @Override
    public Significance getSignificanceById(String id)
    {
        return getDataSignificanceById(id, true);
    }

    private Significance getDataSignificanceById(String id, Boolean isSlave)
    {
        Significance significance = null;
        for (int i = 0; i < slaveCount; i++)
        {
            if (isSlave)
            {
                significance = getSlaveSignificanceById(id);
            } else
            {
                significance = getMasterSignificanceById(id);
            }
            if (significance != null)
            {
                break;
            }
        }
        return significance;
    }

    @Override
    public void updateSignificance(Significance significance)
    {
        getDataSignificanceById(significance.getId(), false);
        updateMasterSignificance(significance);
    }

    @Override
    @DataSourceUpdate
    public void createSignificance(Significance significance)
    {
        significanceMapper.insert(significance);
    }

    @Override
    public List<Completeness> getCompletenessesByParams(Map<String, Object> params)
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        List<Completeness> completenesses = new ArrayList<>();
        for (int i =0; i<slaveCount; i++)
        {
            completenesses.addAll(getSlaveCompletenessesByParams(params));
        }
        return completenesses;
    }

    @Override
    public Completeness getCompletenessById(String id)
    {
        return getDataCompletenessById(id, true);
    }

    private Completeness getDataCompletenessById(String id, Boolean isSlave)
    {
        Completeness completeness = null;
        for (int i = 0; i < slaveCount; i++)
        {
            if (isSlave)
            {
                completeness = getSlaveCompletenessById(id);
            } else
            {
                completeness = getMasterCompletenessById(id);
            }
            if (completeness != null)
            {
                break;
            }
        }
        return completeness;
    }

    @Override
    public void updateCompleteness(Completeness completeness)
    {
        getDataCompletenessById(completeness.getId(), false);
        updateMasterCompleteness(completeness);
    }

    @Override
    @DataSourceUpdate
    public void createCompleteness(Completeness completeness)
    {
        completenessMapper.insert(completeness);
    }

    @Override
    public List<ErrorMeta> getErrorMetasByParams(Map<String, Object> params)
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        List<ErrorMeta> errorMetas = new ArrayList<>();
        for (int i =0; i<slaveCount; i++)
        {
            errorMetas.addAll(getSlaveErrorMetasByParams(params));
        }
        return errorMetas;
    }

    @Override
    public ErrorMeta getErrorMetaById(String id)
    {
        return getDataErrorMetaById(id, true);
    }

    private ErrorMeta getDataErrorMetaById(String id, Boolean isSlave)
    {
        ErrorMeta errorMeta = null;
        for (int i = 0; i < slaveCount; i++)
        {
            if (isSlave)
            {
                errorMeta = getSlaveErrorMetaById(id);
            } else
            {
                errorMeta = getMasterErrorMetaById(id);
            }
            if (errorMeta != null)
            {
                break;
            }
        }
        return errorMeta;
    }

    @Override
    public void updateErrorMeta(ErrorMeta errorMeta)
    {
        getDataErrorMetaById(errorMeta.getId(), false);
        updateMasterErrorMeta(errorMeta);
    }

    @Override
    @DataSourceUpdate
    public void createErrorMeta(ErrorMeta errorMeta)
    {
        errorMetaMapper.insert(errorMeta);
    }

    @Override
    public List<Category> getCategoriesByParams(Map<String, Object> params)
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        List<Category> categories = new ArrayList<>();
        for (int i =0; i<slaveCount; i++)
        {
            categories.addAll(getSlaveCategoriesByParams(params));
        }
        return categories;
    }

    @Override
    public Category getCategoryById(String id)
    {
        return getDataCategoryById(id, true);
    }

    private Category getDataCategoryById(String id, Boolean isSlave)
    {
        Category category = null;
        for (int i = 0; i < slaveCount; i++)
        {
            if (isSlave)
            {
                category = getSlaveCategoryById(id);
            } else
            {
                category = getMasterCategoryById(id);
            }
            if (category != null)
            {
                break;
            }
        }
        return category;
    }

    @Override
    public void updateCategory(Category category)
    {
        getDataCategoryById(category.getId(), false);
        updateMasterCategory(category);
    }

    @Override
    @DataSourceUpdate
    public void createCategory(Category category)
    {
        categoryMapper.insert(category);
    }

    @Override
    public List<Source> getSourcesByParams(Map<String, Object> params)
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        List<Source> sources = new ArrayList<>();
        for (int i =0; i<slaveCount; i++)
        {
            sources.addAll(getSlaveSourcesByParams(params));
        }
        return sources;
    }

    @Override
    public Source getSourceById(String id)
    {
        return getDataSourceById(id, true);
    }

    private Source getDataSourceById(String id, Boolean isSlave)
    {
        Source source = null;
        for (int i = 0; i < slaveCount; i++)
        {
            if (isSlave)
            {
                source = getSlaveSourceById(id);
            } else
            {
                source = getMasterSourceById(id);
            }
            if (source != null)
            {
                break;
            }
        }
        return source;
    }

    @Override
    public void updateSource(Source source)
    {
        getDataSourceById(source.getId(), false);
        updateMasterSource(source);
    }

    @Override
    @DataSourceUpdate
    public void createSource(Source source)
    {
        sourceMapper.insert(source);
    }

    @Override
    public List<SourcePackage> getSourcePackagesByParams(Map<String, Object> params)
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        List<SourcePackage> sourcePackages = new ArrayList<>();
        for (int i =0; i<slaveCount; i++)
        {
            sourcePackages.addAll(getSlaveSourcePackagesByParams(params));
        }
        return sourcePackages;
    }

    @Override
    public SourcePackage getSourcePackageById(String id)
    {
        return getDataSourcePackageById(id, true);
    }

    private SourcePackage getDataSourcePackageById(String id, Boolean isSlave)
    {
        SourcePackage sourcePackage = null;
        for (int i = 0; i < slaveCount; i++)
        {
            if (isSlave)
            {
                sourcePackage = getSlaveSourcePackageById(id);
            } else
            {
                sourcePackage = getMasterSourcePackageById(id);
            }
            if (sourcePackage != null)
            {
                break;
            }
        }
        return sourcePackage;
    }

    @Override
    public void updateSourcePackage(SourcePackage sourcePackage)
    {
        getDataSourcePackageById(sourcePackage.getId(), false);
        updateMasterSourcePackage(sourcePackage);
    }

    @Override
    @DataSourceUpdate
    public void createSourcePackage(SourcePackage sourcePackage)
    {
        sourcePackageMapper.insert(sourcePackage);
    }

    @Override
    public List<KnowledgePoint> getKnowledgePointsByParams(Map<String, Object> params)
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        List<KnowledgePoint> knowledgePoints = new ArrayList<>();
        for (int i =0; i<slaveCount; i++)
        {
            knowledgePoints.addAll(getSlaveKnowledgePointsByParams(params));
        }
        return knowledgePoints;
    }

    @Override
    public KnowledgePoint getKnowledgePointById(String id)
    {
        return getDataKnowledgePointById(id, true);
    }

    private KnowledgePoint getDataKnowledgePointById(String id, Boolean isSlave)
    {
        KnowledgePoint knowledgePoint = null;
        for (int i = 0; i < slaveCount; i++)
        {
            if (isSlave)
            {
                knowledgePoint = getSlaveKnowledgePointById(id);
            } else
            {
                knowledgePoint = getMasterKnowledgePointById(id);
            }
            if (knowledgePoint != null)
            {
                break;
            }
        }
        return knowledgePoint;
    }

    @Override
    public void updateKnowledgePoint(KnowledgePoint knowledgePoint)
    {
        getDataKnowledgePointById(knowledgePoint.getId(), false);
        updateMasterKnowledgePoint(knowledgePoint);
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
        deleteKnowledgePointById(knowledgePoint.getId(), knowledgePoint.getUpdateUserId());
    }

    @Override
    public void deleteKnowledgePointById(String id, String currentUserId)
    {
        getDataKnowledgePointById(id, false);
        deleteMasterKnowledgePointById(id);
    }

    // data source master update
    @DataSourceUpdate
    private void deleteMasterKnowledgePointById(String id)
    {
        knowledgePointMapper.deleteByPrimaryKey(id);
    }

    @DataSourceUpdate
    private void updateMasterKnowledgePoint(KnowledgePoint knowledgePoint)
    {
        knowledgePointMapper.updateByPrimaryKey(knowledgePoint);
    }

    @DataSourceUpdate
    private void updateMasterSourcePackage(SourcePackage sourcePackage)
    {
        sourcePackageMapper.updateByPrimaryKey(sourcePackage);
    }

    @DataSourceUpdate
    private void updateMasterSource(Source source)
    {
        sourceMapper.updateByPrimaryKey(source);
    }

    @DataSourceUpdate
    private void updateMasterCategory(Category category)
    {
        categoryMapper.updateByPrimaryKey(category);
    }

    @DataSourceUpdate
    private void updateMasterErrorMeta(ErrorMeta errorMeta)
    {
        errorMetaMapper.updateByPrimaryKey(errorMeta);
    }

    @DataSourceUpdate
    private void updateMasterCompleteness(Completeness completeness)
    {
        completenessMapper.updateByPrimaryKey(completeness);
    }

    @DataSourceUpdate
    private void updateMasterSignificance(Significance significance)
    {
        significanceMapper.updateByPrimaryKey(significance);
    }

    @DataSourceUpdate
    private void updateMasterSemanticType(SemanticType semanticType)
    {
        semanticTypeMapper.updateByPrimaryKey(semanticType);
    }

    @DataSourceUpdate
    private void updateMasterFormType(FormType formType)
    {
        formTypeMapper.updateByPrimaryKey(formType);
    }

    @DataSourceUpdate
    private void updateMasterDifficulty(Difficulty difficulty)
    {
        difficultyMapper.updateByPrimaryKey(difficulty);
    }

    @DataSourceUpdate
    private void updateMasterEducationalStage(EducationalStage educationalStage)
    {
        educationalStageMapper.updateByPrimaryKey(educationalStage);
    }

    @DataSourceUpdate
    private void updateMasterSubject(Subject subject)
    {
        subjectMapper.updateByPrimaryKey(subject);
    }

    // data source master query
    @DataSourceMasterQuery
    private KnowledgePoint getMasterKnowledgePointById(String id)
    {
        return knowledgePointMapper.selectByPrimaryKey(id);
    }

    @DataSourceMasterQuery
    private SourcePackage getMasterSourcePackageById(String id)
    {
        return sourcePackageMapper.selectByPrimaryKey(id);
    }

    @DataSourceMasterQuery
    private Source getMasterSourceById(String id)
    {
        return sourceMapper.selectByPrimaryKey(id);
    }

    @DataSourceMasterQuery
    private Category getMasterCategoryById(String id)
    {
        return categoryMapper.selectByPrimaryKey(id);
    }

    @DataSourceMasterQuery
    private ErrorMeta getMasterErrorMetaById(String id)
    {
        return errorMetaMapper.selectByPrimaryKey(id);
    }

    @DataSourceMasterQuery
    private Completeness getMasterCompletenessById(String id)
    {
        return completenessMapper.selectByPrimaryKey(id);
    }

    @DataSourceMasterQuery
    private Significance getMasterSignificanceById(String id)
    {
        return significanceMapper.selectByPrimaryKey(id);
    }

    @DataSourceMasterQuery
    private SemanticType getMasterSemanticTypeById(String id)
    {
        return semanticTypeMapper.selectByPrimaryKey(id);
    }

    @DataSourceMasterQuery
    private FormType getMasterFormTypeById(String id)
    {
        return formTypeMapper.selectByPrimaryKey(id);
    }

    @DataSourceMasterQuery
    private Difficulty getMasterDifficultyById(String id)
    {
        return difficultyMapper.selectByPrimaryKey(id);
    }

    @DataSourceMasterQuery
    private EducationalStage getMasterEducationalStageById(String id)
    {
        return educationalStageMapper.selectByPrimaryKey(id);
    }

    @DataSourceMasterQuery
    private Subject getMasterSubjectById(String id)
    {
        return subjectMapper.selectByPrimaryKey(id);
    }

    // data source slave query
    @DataSourceQuery
    private KnowledgePoint getSlaveKnowledgePointById(String id)
    {
        return knowledgePointMapper.selectByPrimaryKey(id);
    }

    @DataSourceQuery
    private SourcePackage getSlaveSourcePackageById(String id)
    {
        return sourcePackageMapper.selectByPrimaryKey(id);
    }

    @DataSourceQuery
    private List<KnowledgePoint> getSlaveKnowledgePointsByParams(Map<String, Object> params)
    {
        return knowledgePointCustomDao.getKnowledgePointsByParams(params);
    }

    @DataSourceQuery
    private List<SourcePackage> getSlaveSourcePackagesByParams(Map<String, Object> params)
    {
        return sourcePackageCustomDao.getSourcePackagesByParams(params);
    }

    @DataSourceQuery
    private List<Source> getSlaveSourcesByParams(Map<String, Object> params)
    {
        return sourceCustomDao.getSourcesByParams(params);
    }

    @DataSourceQuery
    private Source getSlaveSourceById(String id)
    {
        return sourceMapper.selectByPrimaryKey(id);
    }

    @DataSourceQuery
    private List<Category> getSlaveCategoriesByParams(Map<String, Object> params)
    {
        return categoryCustomDao.getCategoriesByParams(params);
    }

    @DataSourceQuery
    private Category getSlaveCategoryById(String id)
    {
        return categoryMapper.selectByPrimaryKey(id);
    }

    @DataSourceQuery
    private List<ErrorMeta> getSlaveErrorMetasByParams(Map<String, Object> params)
    {
        return errorMetaCustomDao.getErrorMetasByParams(params);
    }

    @DataSourceQuery
    private ErrorMeta getSlaveErrorMetaById(String id)
    {
        return errorMetaMapper.selectByPrimaryKey(id);
    }

    @DataSourceQuery
    private List<Completeness> getSlaveCompletenessesByParams(Map<String, Object> params)
    {
        return completenessCustomDao.getCompletenessesByParams(params);
    }

    @DataSourceQuery
    private Completeness getSlaveCompletenessById(String id)
    {
        return completenessMapper.selectByPrimaryKey(id);
    }

    @DataSourceQuery
    private List<Significance> getSlaveSignificancesByParams(Map<String, Object> params)
    {
        return significanceCustomDao.getSignificancesByParams(params);
    }

    @DataSourceQuery
    private Significance getSlaveSignificanceById(String id)
    {
        return significanceMapper.selectByPrimaryKey(id);
    }

    @DataSourceQuery
    private List<SemanticType> getSlaveSemanticTypesByParams(Map<String, Object> params)
    {
        return semanticTypeCustomDao.getSemanticTypesByParams(params);
    }

    @DataSourceQuery
    private SemanticType getSlaveSemanticTypeById(String id)
    {
        return semanticTypeMapper.selectByPrimaryKey(id);
    }

    @DataSourceQuery
    private List<FormType> getSlaveFormTypesByParams(Map<String, Object> params)
    {
        return formTypeCustomDao.getFormTypesByParams(params);
    }

    @DataSourceQuery
    private FormType getSlaveFormTypeById(String id)
    {
        return formTypeMapper.selectByPrimaryKey(id);
    }

    @DataSourceQuery
    private List<Difficulty> getSlaveDifficultiesByParams(Map<String, Object> params)
    {
        return difficultyCustomDao.getDifficultiesByParams(params);
    }

    @DataSourceQuery
    private Difficulty getSlaveDifficultyById(String id)
    {
        return difficultyMapper.selectByPrimaryKey(id);
    }

    @DataSourceQuery
    private List<EducationalStage> getSlaveEducationalStagesByParams(Map<String, Object> params)
    {
        return educationalStageCustomDao.getEducationalStagesByParams(params);
    }

    @DataSourceQuery
    private EducationalStage getSlaveEducationalStageById(String id)
    {
        return educationalStageMapper.selectByPrimaryKey(id);
    }

    @DataSourceQuery
    private Subject getSlaveSubjectById(String id)
    {
        return subjectMapper.selectByPrimaryKey(id);
    }
}
*/
