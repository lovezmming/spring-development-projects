package com.shev.itembank.paper.request;

import com.shev.itembank.common.base.web.HttpServletBasicRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GetPaperDetailRequest extends HttpServletBasicRequest
{

    /** serialVersionUID */
    private static final long serialVersionUID = -7745543211886623042L;

    @NotNull
    private String id;

}
