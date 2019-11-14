package com.shev.itembank.common.search.request;

import com.shev.itembank.common.base.web.HttpServletBasicRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SearchExercisesRequest extends HttpServletBasicRequest
{

    /** serialVersionUID */
    private static final long serialVersionUID = -6637681320451077476L;

    @NotNull
    private String type;

    private String subjectId;

    private Integer formType;

    private String knowledgePointId;

    private String textbookStructureCategoryId;

    private Integer difficulty;

    private String paramKey;

    private String proficiencyId;

}
