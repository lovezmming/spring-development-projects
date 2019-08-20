package com.shev.compilation.edumeta.request;

import com.shev.compilation.common.support.HttpServletBaseRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class KnowledgePointGetRequest extends HttpServletBaseRequest
{
    @NotNull
    private String educationalStageId;

    @NotNull
    private String subjectId;

}
