package com.shev.compilation.user.request;

import com.shev.compilation.common.support.HttpServletBaseRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDeleteRequest extends HttpServletBaseRequest
{
    private String id;
}
