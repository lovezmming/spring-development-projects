package com.shev.compilation.user.request;

import com.shev.compilation.common.support.HttpServletBaseRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserPreferenceUpdateRequest extends HttpServletBaseRequest
{

    @NotNull
    private String id;

    private Boolean retainSubjectStage;

    private Boolean retainExerciseType;

    private Boolean retainCheckInfo;

    private Boolean autoBackgroundPicture;
}
