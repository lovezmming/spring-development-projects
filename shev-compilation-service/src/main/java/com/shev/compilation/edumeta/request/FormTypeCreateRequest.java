package com.shev.compilation.edumeta.request;

import com.shev.compilation.common.support.HttpServletBaseRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FormTypeCreateRequest extends HttpServletBaseRequest
{
    @NotNull
    private String name;

    private Boolean status;

    private Integer seq;

    private Integer coreId;

}
