package com.shev.itembank.common.search.request;

import lombok.Data;

@Data
public class StatisticExercisesByUserIdRequest extends StatisticBasicRequest
{
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;
    
    private String userId;

}
