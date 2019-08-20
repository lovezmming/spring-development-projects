package com.shev.compilation.user.request;

import com.shev.compilation.common.support.HttpServletBaseRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserGetRequest extends HttpServletBaseRequest
{
    private String name;

    private String phoneNumber;

    private String idNumber;

    private String updateUserId;

    private Integer status;

    private Date updateTimeStart;

    private Date updateTimeEnd;

}
