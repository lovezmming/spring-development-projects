package com.shev.itembank.exercise.request;

import com.shev.itembank.common.base.web.HttpServletBasicRequest;
import lombok.Data;

@Data
public class ExerciseGetDetailRequest extends HttpServletBasicRequest
{
    /** serialVersionUID */
    private static final long serialVersionUID = 225144888578668582L;

    private String Id;

    private String ids;

    private String type;

}
