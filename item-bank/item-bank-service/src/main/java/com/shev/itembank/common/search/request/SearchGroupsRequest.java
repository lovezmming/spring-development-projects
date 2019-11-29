package com.shev.itembank.common.search.request;

import com.shev.itembank.common.base.web.HttpServletBasicRequest;
import lombok.Data;

@Data
public class SearchGroupsRequest extends HttpServletBasicRequest
{

    /** serialVersionUID */
    private static final long serialVersionUID = 595487600801582371L;

    private String name;

    private String userId;

    private Boolean relationFlag;

}
