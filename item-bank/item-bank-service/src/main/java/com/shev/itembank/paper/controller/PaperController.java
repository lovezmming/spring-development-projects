package com.shev.itembank.paper.controller;

import com.shev.itembank.common.base.exception.ValidationException;
import com.shev.itembank.common.base.result.JsonReturn;
import com.shev.itembank.common.base.result.RecordSet;
import com.shev.itembank.common.base.valid.RequestValidate;
import com.shev.itembank.common.base.web.WebBasicController;
import com.shev.itembank.paper.request.GetPaperDetailRequest;
import com.shev.itembank.paper.request.GetPaperListRequest;
import com.shev.itembank.paper.service.PaperBusinessManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/paper")
@Api(tags = "试卷管理接口")
public class PaperController extends WebBasicController
{

    @Autowired
    private PaperBusinessManageService paperBusinessManageService;

    @RequestValidate
    @GetMapping("/getPaperList")
    @ApiOperation("获取试卷列表接口")
    public JsonReturn<RecordSet> getPaperList(@Valid GetPaperListRequest request, BindingResult result) throws Exception
    {
        if (result.hasErrors())
        {
            throw new ValidationException(result);
        }
        return new JsonReturn<RecordSet>(paperBusinessManageService.getPaperList(request));
    }

    @RequestValidate
    @GetMapping("/getPaperDetail")
    @ApiOperation("获取试卷详情接口")
    public JsonReturn<RecordSet> getPaperDetail(@Valid GetPaperDetailRequest request, BindingResult result) throws Exception
    {
        if (result.hasErrors())
        {
            throw new ValidationException(result);
        }
        return new JsonReturn<RecordSet>(paperBusinessManageService.getPaperDetail(request));
    }

}
