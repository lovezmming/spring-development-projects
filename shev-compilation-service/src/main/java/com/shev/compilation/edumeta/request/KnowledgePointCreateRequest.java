package com.shev.compilation.edumeta.request;

import com.shev.compilation.common.support.HttpServletBaseRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class KnowledgePointCreateRequest extends HttpServletBaseRequest
{
    @NotNull
    private String name;

    private String parentId;

    @NotNull
    private String educationalStageId;

    @NotNull
    private String subjectId;

    private String url;

}
