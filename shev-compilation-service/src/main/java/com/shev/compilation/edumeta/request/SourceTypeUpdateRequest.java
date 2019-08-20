package com.shev.compilation.edumeta.request;

import com.shev.compilation.common.support.HttpServletBaseRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class SourceTypeUpdateRequest extends HttpServletBaseRequest
{
    @NotNull
    private String id;

    private String name;

    private String description;

    private Boolean status;

    private Integer seq;

    private List<String> sourceIds;

}
