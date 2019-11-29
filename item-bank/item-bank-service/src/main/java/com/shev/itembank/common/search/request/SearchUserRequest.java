package com.shev.itembank.common.search.request;

import com.shev.itembank.common.base.web.HttpServletBasicRequest;
import lombok.Data;

@Data
public class SearchUserRequest extends HttpServletBasicRequest
{

    /** serialVersionUID */
    private static final long serialVersionUID = 6657193201080155269L;
    
    private String type;
    
    private String name;
    
    private String classId;
    
    private String gradeId;

}
