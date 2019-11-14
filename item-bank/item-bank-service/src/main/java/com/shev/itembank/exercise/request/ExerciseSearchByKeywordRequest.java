package com.shev.itembank.exercise.request;

import com.shev.itembank.common.base.web.HttpServletBasicRequest;
import lombok.Data;

import java.util.List;

@Data
public class ExerciseSearchByKeywordRequest extends HttpServletBasicRequest
{

    /** serialVersionUID */
    private static final long serialVersionUID = -1211837281073395486L;

    private String knowledgePointId;
    
    private List<Integer> formType;
    
    private List<Integer> difficulty;
    
    private List<Integer> significance;
    
    private Integer subjectId;

    private String categoryId;

    private String paramKey;

    private String proficiencyId;

}
