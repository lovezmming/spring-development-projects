package com.shev.compilation.user.controller;

import com.shev.compilation.common.support.WebBasicController;
import com.shev.compilation.common.support.valid.RequestValidate;
import com.shev.compilation.common.support.web.JsonReturn;
import com.shev.compilation.common.support.web.RecordSet;
import com.shev.compilation.user.request.*;
import com.shev.compilation.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController extends WebBasicController
{

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestValidate
    @PostMapping("/admin/register")
    @ResponseBody
    public JsonReturn<Map<String, Object>> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        try
        {
            logger.info("user register !");
            return new JsonReturn<>(userService.register(userRegisterRequest));
        } catch (Exception e)
        {
            e.printStackTrace();
            logger.error("用户注册出错:{}", e.getMessage());
        }
        return new JsonReturn<>();
    }

    @RequestValidate
    @GetMapping("/getUserDetail")
    @ResponseBody
    public JsonReturn<RecordSet> getUserDetail(@Valid UserDetailGetRequest userDetailGetRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        return new JsonReturn<>(userService.getUserDetail(userDetailGetRequest));
    }

    @RequestValidate
    @GetMapping("/getUserList")
    @ResponseBody
    public JsonReturn<RecordSet> getUserList(@Valid UserGetRequest userGetRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        return new JsonReturn<>(userService.getUserList(userGetRequest));
    }

    @RequestValidate
    @PostMapping("/updateUser")
    @ResponseBody
    public JsonReturn<Map<String, Object>> updateUser(@RequestBody @Valid UserUpdateRequest userUpdateRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        try
        {
            return new JsonReturn<>(userService.updateUser(userUpdateRequest));
        } catch (Exception e)
        {
            return new JsonReturn<>();
        }
    }

    @RequestValidate
    @PostMapping("/createUser")
    @ResponseBody
    public JsonReturn<Map<String, Object>> createUser(@RequestBody @Valid UserCreateRequest userCreateRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        try
        {
            return new JsonReturn<>(userService.createUser(userCreateRequest));
        } catch (Exception e)
        {
            return new JsonReturn<>();
        }
    }

    @RequestValidate
    @PostMapping("/deleteUser")
    @ResponseBody
    public JsonReturn<Map<String, Object>> deleteUser(@RequestBody @Valid UserDeleteRequest userDeleteRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        userService.deleteUser(userDeleteRequest);
        return new JsonReturn<>();
    }

    @RequestValidate
    @GetMapping("/getUserRecords")
    @ResponseBody
    public JsonReturn<RecordSet> getUserRecords(@Valid UserRecordGetRequest userRecordGetRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        return new JsonReturn<>(userService.getUserRecords(userRecordGetRequest));
    }

    @RequestValidate
    @GetMapping("/getUserRecordDetail")
    @ResponseBody
    public JsonReturn<RecordSet> getUserRecordDetail(@Valid UserRecordDetailGetRequest userRecordDetailGetRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        return new JsonReturn<>(userService.getUserRecordDetail(userRecordDetailGetRequest));
    }

    @RequestValidate
    @GetMapping("/exportUserRecordDetail")
    @ResponseBody
    public JsonReturn<RecordSet> exportUserRecordDetail(@Valid UserRecordDetailGetRequest userRecordDetailGetRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        try
        {
            return new JsonReturn<>(userService.exportUserRecordDetail(userRecordDetailGetRequest));
        } catch (Exception e)
        {
            return new JsonReturn<>();
        }
    }

    @RequestValidate
    @GetMapping("/getUserPreferenceDetail")
    @ResponseBody
    public JsonReturn<RecordSet> getUserPreferenceDetail(@Valid UserPreferenceDetailGetRequest userPreferenceDetailGetRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        return new JsonReturn<>(userService.getUserPreferenceDetail(userPreferenceDetailGetRequest));
    }

    @RequestValidate
    @PostMapping("/updateUserPreference")
    @ResponseBody
    public JsonReturn<Map<String, Object>> updateUserPreference(@RequestBody @Valid UserPreferenceUpdateRequest userPreferenceUpdateRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        try
        {
            return new JsonReturn<>(userService.updateUserPreference(userPreferenceUpdateRequest));
        } catch (Exception e)
        {
            return new JsonReturn<>();
        }
    }

    @RequestValidate
    @PostMapping("/createUserPreference")
    @ResponseBody
    public JsonReturn<Map<String, Object>> createUserPreference(@RequestBody @Valid UserPreferenceCreateRequest userPreferenceCreateRequest, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new JsonReturn<>();
        }
        try
        {
            return new JsonReturn<>(userService.createUserPreference(userPreferenceCreateRequest));
        } catch (Exception e)
        {
            return new JsonReturn<>();
        }
    }
}
