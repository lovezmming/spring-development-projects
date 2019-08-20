package com.shev.compilation.edumeta.request;

import com.shev.compilation.common.support.HttpServletBaseRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SourceCreateRequest extends HttpServletBaseRequest
{

    @NotNull
    private String name;

    private String sourcePackageId;

    private String description;

    private Boolean status;

    private Integer seq;

}
