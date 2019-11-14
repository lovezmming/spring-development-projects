package com.shev.itembank.common.search.request;

import com.shev.itembank.common.base.web.HttpServletBasicRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class StatisticBasicRequest extends HttpServletBasicRequest
{

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    private Long baselineTime;
    
    @NotNull
    private String timeUnit;
    
    @NotNull
    private Integer steps;

}
