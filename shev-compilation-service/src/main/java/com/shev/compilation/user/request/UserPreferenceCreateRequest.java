package com.shev.compilation.user.request;

import com.shev.compilation.common.support.HttpServletBaseRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPreferenceCreateRequest extends HttpServletBaseRequest
{

    private Boolean retainSubjectStage;

    private Boolean retainExerciseType;

    private Boolean retainCheckInfo;

    private Boolean autoBackgroundPicture;
}
