package com.shev.compilation.user.controller;

import com.shev.compilation.common.support.WebBasicController;
import com.shev.compilation.common.support.valid.RequestValidate;
import com.shev.compilation.common.support.web.JsonReturn;
import com.shev.compilation.common.support.web.RecordSet;
import com.shev.compilation.user.request.*;
import com.shev.compilation.user.service.DutyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/duty")
public class DutyController extends WebBasicController
{

    private static final Logger logger = LoggerFactory.getLogger(DutyController.class);

    @Autowired
    private DutyService dutyService;

    @RequestValidate
    @GetMapping("/getDuties")
    @ResponseBody
    public JsonReturn<RecordSet> getDuties(@Valid DutyGetRequest dutyGetRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        return new JsonReturn<>(dutyService.getDuties(dutyGetRequest));
    }

    @RequestValidate
    @GetMapping("/getDutyDetail")
    @ResponseBody
    public JsonReturn<RecordSet> getDutyDetail(@Valid DutyDetailGetRequest dutyDetailGetRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        return new JsonReturn<>(dutyService.getDutyDetail(dutyDetailGetRequest));
    }

    @RequestValidate
    @PostMapping("/updateDuty")
    @ResponseBody
    public JsonReturn<Map<String, Object>> updateDuty(@RequestBody @Valid DutyUpdateRequest dutyUpdateRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        try
        {
            Map<String, Object> resultMap = dutyService.updateDuty(dutyUpdateRequest);
            return new JsonReturn<>(resultMap);
        } catch (Exception e)
        {
            return new JsonReturn<>();
        }
    }

    @RequestValidate
    @PostMapping("/createDuty")
    @ResponseBody
    public JsonReturn<Map<String, Object>> createDuty(@RequestBody @Valid DutyCreateRequest dutyCreateRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        try
        {
            Map<String, Object> resultMap = dutyService.createDuty(dutyCreateRequest);
            return new JsonReturn<>(resultMap);
        } catch (Exception e)
        {
            return new JsonReturn<>();
        }
    }

    @RequestValidate
    @PostMapping("/deleteDuty")
    @ResponseBody
    public JsonReturn<Map<String, Object>> deleteDuty(@RequestBody @Valid DutyDeleteRequest dutyDeleteRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        dutyService.deleteDuty(dutyDeleteRequest);
        return new JsonReturn<>();
    }

}
