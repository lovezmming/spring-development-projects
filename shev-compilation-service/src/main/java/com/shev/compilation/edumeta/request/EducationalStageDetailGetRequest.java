package com.shev.compilation.edumeta.request;

import com.shev.compilation.common.support.HttpServletBaseRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EducationalStageDetailGetRequest extends HttpServletBaseRequest
{
    private String id;
}
