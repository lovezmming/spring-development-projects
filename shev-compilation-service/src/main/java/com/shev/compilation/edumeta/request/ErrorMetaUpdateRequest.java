package com.shev.compilation.edumeta.request;

import com.shev.compilation.common.support.HttpServletBaseRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ErrorMetaUpdateRequest extends HttpServletBaseRequest
{
    @NotNull
    private String id;

    private String name;

    private Boolean status;

    private Integer coreId;

    private Integer seq;

    private String type;

}
