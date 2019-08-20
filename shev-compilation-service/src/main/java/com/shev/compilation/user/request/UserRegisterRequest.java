package com.shev.compilation.user.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class UserRegisterRequest implements Serializable
{
    private static final long serialVersionUID = -9177966824726025374L;

    @NotNull
    private long timestamp;

    private String tenantId;

    @NotNull
    private String name;

    @NotNull
    private String type;

    @NotNull
    private String idNumber;

    @NotNull
    private String userName;

    @NotNull
    private String passWord;

    private Date birthDay;

    private Boolean gender;

    private String phoneNumber;

}
