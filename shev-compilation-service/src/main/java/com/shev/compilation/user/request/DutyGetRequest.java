package com.shev.compilation.user.request;

import com.shev.compilation.common.support.HttpServletBaseRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DutyGetRequest extends HttpServletBaseRequest
{
    private String name;

    private String updateUserId;

    private Boolean status;

    private Date updateTimeStart;

    private Date updateTimeEnd;

}
