package com.shev.compilation.edumeta.request;

import com.shev.compilation.common.support.HttpServletBaseRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SourceTypeGetRequest extends HttpServletBaseRequest
{
    private String name;

    private Boolean status;

    private String updateUserId;

    private Date updateTimeStart;

    private Date updateTimeEnd;

    private String sortType;

    private String sortOrder;

}
