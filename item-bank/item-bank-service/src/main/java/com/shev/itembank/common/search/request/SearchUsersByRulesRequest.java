package com.shev.itembank.common.search.request;

import com.shev.itembank.common.base.web.HttpServletBasicRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SearchUsersByRulesRequest extends HttpServletBasicRequest
{

    /** serialVersionUID */
    private static final long serialVersionUID = 192394216909948565L;

    @NotNull
    private String rules;

    private String[] ll;
    
    private Integer[] dd;

}
