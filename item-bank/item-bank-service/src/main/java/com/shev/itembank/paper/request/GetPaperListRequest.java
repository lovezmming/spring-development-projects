package com.shev.itembank.paper.request;

import com.shev.itembank.common.base.web.HttpServletBasicRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GetPaperListRequest extends HttpServletBasicRequest
{

    /** serialVersionUID */
    private static final long serialVersionUID = -1435074652059737220L;

    private String queryText;

    private String searchType;

    private Integer examTypeId;

    @NotNull
    private Integer subject;

    @NotNull
    private Integer educationalStage;

    private Integer year;

    private String cityId;

    private String provinceId;

    private Boolean isSchool;

    private Boolean isContest;

    private Integer gradeId;

    private Integer pageNumber;

    private Integer pageSize;

}
