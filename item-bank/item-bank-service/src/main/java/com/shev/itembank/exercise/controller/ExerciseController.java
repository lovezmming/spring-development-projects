package com.shev.itembank.exercise.controller;

import com.shev.itembank.common.base.exception.ValidationException;
import com.shev.itembank.common.base.result.JsonReturn;
import com.shev.itembank.common.base.result.RecordSet;
import com.shev.itembank.common.base.valid.RequestValidate;
import com.shev.itembank.common.base.web.HttpServletBasicRequest;
import com.shev.itembank.common.base.web.WebBasicController;
import com.shev.itembank.exercise.request.*;
import com.shev.itembank.exercise.service.ExerciseBusinessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/exercise")
@Api(tags = "题目管理接口")
public class ExerciseController extends WebBasicController
{
    @Autowired
    private ExerciseBusinessService exerciseBusinessService;

    @RequestValidate
    @GetMapping("/getExerciseDetail")
    @ApiOperation("获取题目详情接口")
    public JsonReturn<RecordSet> getExerciseDetail(@Valid ExerciseGetDetailRequest request, BindingResult result) throws Exception
    {
        if (result.hasErrors())
            throw new ValidationException(result);

        return new JsonReturn<RecordSet>(exerciseBusinessService.getExerciseDetail(request));
    }

    @RequestValidate
    @PostMapping(value = "/searchByKeyword" , consumes = "application/json")
    @ApiOperation("根据关键字索引题目接口")
    public JsonReturn<RecordSet> searchByKeyword(@RequestBody @Valid ExerciseSearchByKeywordRequest request, BindingResult result) throws Exception
    {
        if (result.hasErrors())
            throw new ValidationException(result);

        return new JsonReturn<RecordSet>(exerciseBusinessService.searchByKeyword(request));
    }

}
