package com.shev.compilation.user.request;

import com.shev.compilation.common.support.HttpServletBaseRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UserCreateRequest extends HttpServletBaseRequest
{

    private String name;

    private String type;

    private Boolean gender;

    private Boolean status = true;

    private String phoneNumber;

    @NotNull
    private String idNumber;

    @NotNull
    private String userName;

    @NotNull
    private String passWord;

    private Date birthDay;

    private String educationStageId;

    private String subjectId;

    private String categoryId;

    private List<String> permissionIds;

    private List<String> dutyIds;

}
