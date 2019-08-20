package com.shev.compilation.edumeta.request;

import com.shev.compilation.common.support.HttpServletBaseRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class KnowledgePointExchangeRequest extends HttpServletBaseRequest
{
    @NotNull
    private String id;

    @NotNull
    private String parentId;

    private Boolean keepSubIds;

}
