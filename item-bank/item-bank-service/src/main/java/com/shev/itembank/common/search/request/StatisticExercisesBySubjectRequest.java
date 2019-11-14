package com.shev.itembank.common.search.request;

import lombok.Data;

@Data
public class StatisticExercisesBySubjectRequest extends StatisticBasicRequest
{

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    private Integer educationalStageId;
    
    private Integer subjectId;

}
