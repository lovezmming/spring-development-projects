package com.shev.itembank.common.search.request;

import com.shev.itembank.common.base.web.HttpServletBasicRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class SearchExercisePostRequest extends HttpServletBasicRequest
{

    /** serialVersionUID */
    private static final long serialVersionUID = -6858137034373053124L;

    @NotNull
    private String type;
    
    @NotNull
    private String referenceId;
    
    private List<Integer> formType;
    
    private List<Integer> difficulty;
    
    private List<Integer> significance;

    private String subjectId;

    private String categoryId;

    private String paramKey;

    private String proficiencyId;

}
