package com.shev.compilation.user.request;

import com.shev.compilation.common.support.HttpServletBaseRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class DutyUpdateRequest extends HttpServletBaseRequest
{
    @NotNull
    private String id;

    private String name;

    private Boolean status;

    private List<String> permissionIds;

}
