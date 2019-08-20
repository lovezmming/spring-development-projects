package com.shev.compilation.user.request;

import com.shev.compilation.common.support.HttpServletBaseRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DutyCreateRequest extends HttpServletBaseRequest
{
    private String name;

    private Boolean status;

    private List<String> permissionIds;

}
