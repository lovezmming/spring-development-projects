package com.shev.compilation.edumeta.controller;

import com.shev.compilation.common.support.WebBasicController;
import com.shev.compilation.common.support.valid.RequestValidate;
import com.shev.compilation.common.support.web.JsonReturn;
import com.shev.compilation.common.support.web.RecordSet;
import com.shev.compilation.edumeta.request.*;
import com.shev.compilation.edumeta.service.EdumetaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/edumeta")
public class EdumetaController extends WebBasicController
{
    private static final Logger logger = LoggerFactory.getLogger(EdumetaController.class);

    @Autowired
    private EdumetaService edumetaService;

    @RequestValidate
    @GetMapping("/getSubjectList")
    @ResponseBody
    public JsonReturn<RecordSet> getSubjectList(@Valid SubjectGetRequest subjectGetRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        return new JsonReturn<>(edumetaService.getSubjectList(subjectGetRequest));
    }

    @RequestValidate
    @GetMapping("/getSubjectDetail")
    @ResponseBody
    public JsonReturn<RecordSet> getSubjectDetail(@Valid SubjectDetailGetRequest subjectDetailGetRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        return new JsonReturn<>(edumetaService.getSubjectDetail(subjectDetailGetRequest));
    }

    @RequestValidate
    @PostMapping("/updateSubject")
    @ResponseBody
    public JsonReturn<Map<String, Object>> updateSubject(@RequestBody @Valid SubjectUpdateRequest subjectUpdateRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        try
        {
            return new JsonReturn<>(edumetaService.updateSubject(subjectUpdateRequest));
        } catch (Exception e)
        {
            return new JsonReturn<>();
        }
    }

    @RequestValidate
    @PostMapping("/createSubject")
    @ResponseBody
    public JsonReturn<Map<String, Object>> createSubject(@RequestBody @Valid SubjectCreateRequest subjectCreateRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        try
        {
            return new JsonReturn<>(edumetaService.createSubject(subjectCreateRequest));
        } catch (Exception e)
        {
            return new JsonReturn<>();
        }
    }

    @RequestValidate
    @GetMapping("/getEducationalStages")
    @ResponseBody
    public JsonReturn<RecordSet> getEducationalStages(@Valid EducationalStageGetRequest educationalStageGetRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        return new JsonReturn<>(edumetaService.getEducationalStages(educationalStageGetRequest));
    }

    @RequestValidate
    @GetMapping("/getEducationalStageDetail")
    @ResponseBody
    public JsonReturn<RecordSet> getEducationalStageDetail(@Valid EducationalStageDetailGetRequest educationalStageDetailGetRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        return new JsonReturn<>(edumetaService.getEducationalStageDetail(educationalStageDetailGetRequest));
    }

    @RequestValidate
    @PostMapping("/updateEducationalStage")
    @ResponseBody
    public JsonReturn<Map<String, Object>> updateEducationalStage(@RequestBody @Valid EducationalStageUpdateRequest educationalStageUpdateRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        try
        {
            return new JsonReturn<>(edumetaService.updateEducationalStage(educationalStageUpdateRequest));
        } catch (Exception e)
        {
            return new JsonReturn<>();
        }
    }

    @RequestValidate
    @PostMapping("/createEducationalStage")
    @ResponseBody
    public JsonReturn<Map<String, Object>> createEducationalStage(@RequestBody @Valid EducationalStageCreateRequest educationalStageCreateRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        try
        {
            return new JsonReturn<>(edumetaService.createEducationalStage(educationalStageCreateRequest));
        } catch (Exception e)
        {
            return new JsonReturn<>();
        }
    }

    @RequestValidate
    @GetMapping("/getDifficultys")
    @ResponseBody
    public JsonReturn<RecordSet> getDifficultys(@Valid DifficultyGetRequest difficultyGetRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        return new JsonReturn<>(edumetaService.getDifficultys(difficultyGetRequest));
    }

    @RequestValidate
    @GetMapping("/getDifficultyDetail")
    @ResponseBody
    public JsonReturn<RecordSet> getDifficultyDetail(@Valid DifficultyDetailGetRequest difficultyDetailGetRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        return new JsonReturn<>(edumetaService.getDifficultyDetail(difficultyDetailGetRequest));
    }

    @RequestValidate
    @PostMapping("/updateDifficulty")
    @ResponseBody
    public JsonReturn<Map<String, Object>> updateDifficulty(@RequestBody @Valid DifficultyUpdateRequest difficultyUpdateRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        try
        {
            return new JsonReturn<>(edumetaService.updateDifficulty(difficultyUpdateRequest));
        } catch (Exception e)
        {
            return new JsonReturn<>();
        }
    }

    @RequestValidate
    @PostMapping("/createDifficulty")
    @ResponseBody
    public JsonReturn<Map<String, Object>> createDifficulty(@RequestBody @Valid DifficultyCreateRequest difficultyCreateRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        try
        {
            return new JsonReturn<>(edumetaService.createDifficulty(difficultyCreateRequest));
        } catch (Exception e)
        {
            return new JsonReturn<>();
        }
    }

    @RequestValidate
    @GetMapping("/getFormTypes")
    @ResponseBody
    public JsonReturn<RecordSet> getFormTypes(@Valid FormTypeGetRequest formTypeGetRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        return new JsonReturn<>(edumetaService.getFormTypes(formTypeGetRequest));
    }

    @RequestValidate
    @GetMapping("/getFormTypeDetail")
    @ResponseBody
    public JsonReturn<RecordSet> getFormTypeDetail(@Valid FormTypeDetailGetRequest formTypeDetailGetRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        return new JsonReturn<>(edumetaService.getFormTypeDetail(formTypeDetailGetRequest));
    }

    @RequestValidate
    @PostMapping("/updateFormType")
    @ResponseBody
    public JsonReturn<Map<String, Object>> updateFormType(@RequestBody @Valid FormTypeUpdateRequest formTypeUpdateRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        try
        {
            return new JsonReturn<>(edumetaService.updateFormType(formTypeUpdateRequest));
        } catch (Exception e)
        {
            return new JsonReturn<>();
        }
    }

    @RequestValidate
    @PostMapping("/createFormType")
    @ResponseBody
    public JsonReturn<Map<String, Object>> createFormType(@RequestBody @Valid FormTypeCreateRequest formTypeCreateRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        try
        {
            return new JsonReturn<>(edumetaService.createFormType(formTypeCreateRequest));
        } catch (Exception e)
        {
            return new JsonReturn<>();
        }
    }

    @RequestValidate
    @GetMapping("/getSemanticTypes")
    @ResponseBody
    public JsonReturn<RecordSet> getSemanticTypes(@Valid SemanticTypeGetRequest semanticTypeGetRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        return new JsonReturn<>(edumetaService.getSemanticTypes(semanticTypeGetRequest));
    }

    @RequestValidate
    @GetMapping("/getSemanticTypeDetail")
    @ResponseBody
    public JsonReturn<RecordSet> getSemanticTypeDetail(@Valid SemanticTypeDetailGetRequest semanticTypeDetailGetRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        return new JsonReturn<>(edumetaService.getSemanticTypeDetail(semanticTypeDetailGetRequest));
    }

    @RequestValidate
    @PostMapping("/updateSemanticType")
    @ResponseBody
    public JsonReturn<Map<String, Object>> updateSemanticType(@RequestBody @Valid SemanticTypeUpdateRequest semanticTypeUpdateRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        try
        {
            return new JsonReturn<>(edumetaService.updateSemanticType(semanticTypeUpdateRequest));
        } catch (Exception e)
        {
            return new JsonReturn<>();
        }
    }

    @RequestValidate
    @PostMapping("/createSemanticType")
    @ResponseBody
    public JsonReturn<Map<String, Object>> createSemanticType(@RequestBody @Valid SemanticTypeCreateRequest semanticTypeCreateRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        try
        {
            return new JsonReturn<>(edumetaService.createSemanticType(semanticTypeCreateRequest));
        } catch (Exception e)
        {
            return new JsonReturn<>();
        }
    }

    @RequestValidate
    @GetMapping("/getSignificances")
    @ResponseBody
    public JsonReturn<RecordSet> getSignificances(@Valid SignificanceGetRequest significanceGetRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        return new JsonReturn<>(edumetaService.getSignificances(significanceGetRequest));
    }

    @RequestValidate
    @GetMapping("/getSignificanceDetail")
    @ResponseBody
    public JsonReturn<RecordSet> getSignificanceDetail(@Valid SignificanceDetailGetRequest significanceDetailGetRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        return new JsonReturn<>(edumetaService.getSignificanceDetail(significanceDetailGetRequest));
    }

    @RequestValidate
    @PostMapping("/updateSignificance")
    @ResponseBody
    public JsonReturn<Map<String, Object>> updateSignificance(@RequestBody @Valid SignificanceUpdateRequest significanceUpdateRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        try
        {
            return new JsonReturn<>(edumetaService.updateSignificance(significanceUpdateRequest));
        } catch (Exception e)
        {
            return new JsonReturn<>();
        }
    }

    @RequestValidate
    @PostMapping("/createSignificance")
    @ResponseBody
    public JsonReturn<Map<String, Object>> createSignificance(@RequestBody @Valid SignificanceCreateRequest significanceCreateRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        try
        {
            return new JsonReturn<>(edumetaService.createSignificance(significanceCreateRequest));
        } catch (Exception e)
        {
            return new JsonReturn<>();
        }
    }

    @RequestValidate
    @GetMapping("/getCompleteness")
    @ResponseBody
    public JsonReturn<RecordSet> getCompleteness(@Valid CompletenessGetRequest completenessGetRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        return new JsonReturn<>(edumetaService.getCompleteness(completenessGetRequest));
    }

    @RequestValidate
    @GetMapping("/getCompletenessDetail")
    @ResponseBody
    public JsonReturn<RecordSet> getCompletenessDetail(@Valid CompletenessDetailGetRequest completenessDetailGetRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        return new JsonReturn<>(edumetaService.getCompletenessDetail(completenessDetailGetRequest));
    }

    @RequestValidate
    @PostMapping("/updateCompleteness")
    @ResponseBody
    public JsonReturn<Map<String, Object>> updateCompleteness(@RequestBody @Valid CompletenessUpdateRequest completenessUpdateRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        try
        {
            return new JsonReturn<>(edumetaService.updateCompleteness(completenessUpdateRequest));
        } catch (Exception e)
        {
            return new JsonReturn<>();
        }
    }

    @RequestValidate
    @PostMapping("/createCompleteness")
    @ResponseBody
    public JsonReturn<Map<String, Object>> createCompleteness(@RequestBody @Valid CompletenessCreateRequest completenessCreateRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        try
        {
            return new JsonReturn<>(edumetaService.createCompleteness(completenessCreateRequest));
        } catch (Exception e)
        {
            return new JsonReturn<>();
        }
    }

    @RequestValidate
    @GetMapping("/getErrorMetas")
    @ResponseBody
    public JsonReturn<RecordSet> getErrorMetas(@Valid ErrorMetaGetRequest errorMetaGetRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        return new JsonReturn<>(edumetaService.getErrorMetas(errorMetaGetRequest));
    }

    @RequestValidate
    @GetMapping("/getErrorMetaDetail")
    @ResponseBody
    public JsonReturn<RecordSet> getErrorMetaDetail(@Valid ErrorMetaDetailGetRequest errorMetaDetailGetRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        return new JsonReturn<>(edumetaService.getErrorMetaDetail(errorMetaDetailGetRequest));
    }

    @RequestValidate
    @PostMapping("/updateErrorMeta")
    @ResponseBody
    public JsonReturn<Map<String, Object>> updateErrorMeta(@RequestBody @Valid ErrorMetaUpdateRequest errorMetaUpdateRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        try
        {
            return new JsonReturn<>(edumetaService.updateErrorMeta(errorMetaUpdateRequest));
        } catch (Exception e)
        {
            return new JsonReturn<>();
        }
    }

    @RequestValidate
    @PostMapping("/createErrorMeta")
    @ResponseBody
    public JsonReturn<Map<String, Object>> createErrorMeta(@RequestBody @Valid ErrorMetaCreateRequest errorMetaCreateRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        try
        {
            return new JsonReturn<>(edumetaService.createErrorMeta(errorMetaCreateRequest));
        } catch (Exception e)
        {
            return new JsonReturn<>();
        }
    }

    @RequestValidate
    @GetMapping("/getCategorys")
    @ResponseBody
    public JsonReturn<RecordSet> getCategorys(@Valid CategoryGetRequest categoryGetRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        return new JsonReturn<>(edumetaService.getCategorys(categoryGetRequest));
    }

    @RequestValidate
    @GetMapping("/getCategoryDetail")
    @ResponseBody
    public JsonReturn<RecordSet> getCategoryDetail(@Valid CategoryDetailGetRequest categoryDetailGetRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        return new JsonReturn<>(edumetaService.getCategoryDetail(categoryDetailGetRequest));
    }

    @RequestValidate
    @PostMapping("/updateCategory")
    @ResponseBody
    public JsonReturn<Map<String, Object>> updateCategory(@RequestBody @Valid CategoryUpdateRequest categoryUpdateRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        try
        {
            return new JsonReturn<>(edumetaService.updateCategory(categoryUpdateRequest));
        } catch (Exception e)
        {
            return new JsonReturn<>();
        }
    }

    @RequestValidate
    @PostMapping("/createCategory")
    @ResponseBody
    public JsonReturn<Map<String, Object>> createCategory(@RequestBody @Valid CategoryCreateRequest categoryCreateRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        try
        {
            return new JsonReturn<>(edumetaService.createCategory(categoryCreateRequest));
        } catch (Exception e)
        {
            return new JsonReturn<>();
        }
    }

    @RequestValidate
    @GetMapping("/getSources")
    @ResponseBody
    public JsonReturn<RecordSet> getSources(@Valid SourceGetRequest sourceGetRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        return new JsonReturn<>(edumetaService.getSources(sourceGetRequest));
    }

    @RequestValidate
    @GetMapping("/getSourceDetail")
    @ResponseBody
    public JsonReturn<RecordSet> getSourceDetail(@Valid SourceDetailGetRequest sourceDetailGetRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        return new JsonReturn<>(edumetaService.getSourceDetail(sourceDetailGetRequest));
    }

    @RequestValidate
    @PostMapping("/updateSource")
    @ResponseBody
    public JsonReturn<Map<String, Object>> updateSource(@RequestBody @Valid SourceUpdateRequest sourceUpdateRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        try
        {
            return new JsonReturn<>(edumetaService.updateSource(sourceUpdateRequest));
        } catch (Exception e)
        {
            return new JsonReturn<>();
        }
    }

    @RequestValidate
    @PostMapping("/createSource")
    @ResponseBody
    public JsonReturn<Map<String, Object>> createSource(@RequestBody @Valid SourceCreateRequest sourceCreateRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        try
        {
            return new JsonReturn<>(edumetaService.createSource(sourceCreateRequest));
        } catch (Exception e)
        {
            return new JsonReturn<>();
        }
    }

    @RequestValidate
    @GetMapping("/getSourceTypes")
    @ResponseBody
    public JsonReturn<RecordSet> getSourceTypes(@Valid SourceTypeGetRequest sourceTypeGetRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        return new JsonReturn<>(edumetaService.getSourceTypes(sourceTypeGetRequest));
    }

    @RequestValidate
    @GetMapping("/getSourceTypeDetail")
    @ResponseBody
    public JsonReturn<RecordSet> getSourceTypeDetail(@Valid SourceTypeDetailGetRequest sourceTypeDetailGetRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        return new JsonReturn<>(edumetaService.getSourceTypeDetail(sourceTypeDetailGetRequest));
    }

    @RequestValidate
    @PostMapping("/updateSourceType")
    @ResponseBody
    public JsonReturn<Map<String, Object>> updateSourceType(@RequestBody @Valid SourceTypeUpdateRequest sourceTypeUpdateRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        try
        {
            return new JsonReturn<>(edumetaService.updateSourceType(sourceTypeUpdateRequest));
        } catch (Exception e)
        {
            return new JsonReturn<>();
        }
    }

    @RequestValidate
    @PostMapping("/createSourceType")
    @ResponseBody
    public JsonReturn<Map<String, Object>> createSourceType(@RequestBody @Valid SourceTypeCreateRequest sourceTypeCreateRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        try
        {
            return new JsonReturn<>(edumetaService.createSourceType(sourceTypeCreateRequest));
        } catch (Exception e)
        {
            return new JsonReturn<>();
        }
    }

    @RequestValidate
    @GetMapping("/getKnowledgePoints")
    @ResponseBody
    public JsonReturn<RecordSet> getKnowledgePoints(@Valid KnowledgePointGetRequest knowledgePointGetRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        return new JsonReturn<>(edumetaService.getKnowledgePoints(knowledgePointGetRequest));
    }

    @RequestValidate
    @GetMapping("/getKnowledgePointDetail")
    @ResponseBody
    public JsonReturn<RecordSet> getKnowledgePointDetail(@Valid KnowledgePointDetailGetRequest knowledgePointDetailGetRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        return new JsonReturn<>(edumetaService.getKnowledgePointDetail(knowledgePointDetailGetRequest));
    }

    @RequestValidate
    @PostMapping("/exchangeKnowledgePoint")
    @ResponseBody
    public JsonReturn<Map<String, Object>> exchangeKnowledgePoint(@RequestBody @Valid KnowledgePointExchangeRequest knowledgePointExchangeRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        try
        {
            return new JsonReturn<>(edumetaService.exchangeKnowledgePoint(knowledgePointExchangeRequest));
        } catch (Exception e)
        {
            return new JsonReturn<>();
        }
    }

    @RequestValidate
    @PostMapping("/createKnowledgePoint")
    @ResponseBody
    public JsonReturn<Map<String, Object>> createKnowledgePoint(@RequestBody @Valid KnowledgePointCreateRequest knowledgePointCreateRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        try
        {
            return new JsonReturn<>(edumetaService.createKnowledgePoint(knowledgePointCreateRequest));
        } catch (Exception e)
        {
            return new JsonReturn<>();
        }
    }

    @RequestValidate
    @PostMapping("/updateKnowledgePoint")
    @ResponseBody
    public JsonReturn<Map<String, Object>> updateKnowledgePoint(@RequestBody @Valid KnowledgePointUpdateRequest knowledgePointUpdateRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        try
        {
            return new JsonReturn<>(edumetaService.updateKnowledgePoint(knowledgePointUpdateRequest));
        } catch (Exception e)
        {
            return new JsonReturn<>();
        }
    }

    @RequestValidate
    @PostMapping("/deleteKnowledgePoint")
    @ResponseBody
    public JsonReturn<Map<String, Object>> deleteKnowledgePoint(@RequestBody @Valid KnowledgePointDeleteRequest knowledgePointDeleteRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        edumetaService.deleteKnowledgePoint(knowledgePointDeleteRequest);
        return new JsonReturn<>();
    }
}
