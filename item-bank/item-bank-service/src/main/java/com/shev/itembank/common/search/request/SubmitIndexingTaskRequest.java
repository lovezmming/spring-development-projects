package com.shev.itembank.common.search.request;

import com.shev.itembank.common.base.web.HttpServletBasicRequest;
import lombok.Data;

@Data
public class SubmitIndexingTaskRequest extends HttpServletBasicRequest
{

    /** serialVersionUID */
    private static final long serialVersionUID = 2768226056666283943L;

    private String type;

    private Long time;

}
