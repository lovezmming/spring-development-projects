package com.shev.itembank.common.search.request;

import com.shev.itembank.common.base.web.HttpServletBasicRequest;

import lombok.Data;

@Data
public class StatisticAllExerciseBySubjectsRequest extends HttpServletBasicRequest
{

    /** serialVersionUID */
    private static final long serialVersionUID = 3258961822592531489L;
    
    private String subjects;

    private Integer educationalStageId;
    
    private Long endTime;
    
    private Long startTime;

}
