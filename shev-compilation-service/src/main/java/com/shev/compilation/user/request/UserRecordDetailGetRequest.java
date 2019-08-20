package com.shev.compilation.user.request;

import com.shev.compilation.common.support.HttpServletBaseRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserRecordDetailGetRequest extends HttpServletBaseRequest
{

    private String type;

    private String educationStageId;

    private String subjectId;

    private String categoryId;

    private String operateType;

    private String userName;

    private Date operateTimeStart;

    private Date operateTimeEnd;
}
