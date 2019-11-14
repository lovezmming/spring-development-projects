package com.shev.itembank.common.search.controller;

import com.shev.itembank.common.base.exception.ValidationException;
import com.shev.itembank.common.base.result.JsonReturn;
import com.shev.itembank.common.base.result.RecordSet;
import com.shev.itembank.common.base.valid.RequestValidate;
import com.shev.itembank.common.base.web.WebBasicController;
import com.shev.itembank.common.search.request.*;
import com.shev.itembank.common.search.service.IndexBizService;
import com.shev.itembank.common.search.service.SearchBizService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/search")
@Api(tags = "索引查询管理接口")
public class SearchController extends WebBasicController
{
    @Autowired
    private SearchBizService searchBizService;
    
    @Autowired
    private IndexBizService indexBizService;

    @RequestValidate
    @PostMapping(value = "/searchExercise", consumes = "application/json")
    @ApiOperation("索引题目接口（POST）")
    public JsonReturn<RecordSet> searchExercise(@Valid @RequestBody SearchExercisePostRequest request, BindingResult result) throws Exception
    {
        if (result.hasErrors())
            throw new ValidationException(result);
                
        return new JsonReturn<RecordSet>(searchBizService.searchExerciseByPost(request));
    }

    @RequestValidate
    @GetMapping("/searchExercise")
    @ApiOperation("索引题目接口（GET）")
    public JsonReturn<RecordSet> searchExerciseByGet(@Valid SearchExerciseRequest request, BindingResult result) throws Exception
    {
        if (result.hasErrors())
            throw new ValidationException(result);
                
        return new JsonReturn<RecordSet>(searchBizService.searchExercise(request));
    }

    @RequestValidate
    @PostMapping(value = "/submitIndexingTask", consumes = "application/json")
    @ApiOperation("刷新题目索引接口")
    public JsonReturn<Map<String, Object>> submitExerciseIndexingTask(@Valid @RequestBody SubmitIndexingTaskRequest request, BindingResult result) throws Exception
    {
        if (result.hasErrors())
            throw new ValidationException(result);

        return new JsonReturn<Map<String, Object>>(indexBizService.submitIndexingTask(request));
    }

    @RequestValidate
    @PostMapping(value = "/submitPaperIndexingTask", consumes = "application/json")
    @ApiOperation("刷新试卷索引接口")
    public JsonReturn<Map<String, Object>> submitPaperIndexingTask(@Valid @RequestBody SubmitIndexingTaskRequest request, BindingResult result) throws Exception
    {
        if (result.hasErrors())
            throw new ValidationException(result);

        return new JsonReturn<Map<String, Object>>(indexBizService.submitPaperIndexingTask(request));
    }


    @RequestValidate
    @PostMapping(value = "/deletePaperIndex", consumes = "application/json")
    @ApiOperation("删除试卷索引接口")
    public JsonReturn<Map<String, Object>> deletePaperIndex(@Valid @RequestBody SubmitIndexingTaskRequest request, BindingResult result) throws Exception
    {
        if (result.hasErrors())
            throw new ValidationException(result);

        indexBizService.deletePaperIndex(request);
        return new JsonReturn<Map<String, Object>>();
    }

    @RequestValidate
    @GetMapping("/searchExercisesByAttributes")
    @ApiOperation("索引题目接口")
    public JsonReturn<RecordSet> searchExercisesByAttributes(@Valid SearchExercisesRequest request, BindingResult result) throws Exception
    {
        if (result.hasErrors())
            throw new ValidationException(result);
        
        return new JsonReturn<RecordSet>(searchBizService.searchExercisesByAttributes(request));
    }

}
