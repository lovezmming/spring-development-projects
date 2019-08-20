package com.shev.compilation.user.request;

import com.shev.compilation.common.support.HttpServletBaseRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRecordGetRequest extends HttpServletBaseRequest
{

    private String type;

    private Integer exerciseErrorMeta;

    private Integer pageErrorMeta;

    private String educationStageId;

    private String subjectId;

    private String categoryId;

    private String sourceId;

    private String sourceTypeId;

    private String operateType;

}
