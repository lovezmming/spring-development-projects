package com.shev.compilation.user.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserDetailGetRequest
{
    @NotNull
    private String id;

}
