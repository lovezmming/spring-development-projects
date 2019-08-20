package com.shev.compilation.common.support;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class HttpServletBaseRequest implements Serializable
{
    private static final long serialVersionUID = -7025331035740789979L;

    @NotNull
    private long timestamp;

    @NotNull
    private String currentUserTenantId;

    @NotNull
    private String currentUserId;

    private Integer pageNumber;

    private Integer pageSize;

    private Integer start;

}
