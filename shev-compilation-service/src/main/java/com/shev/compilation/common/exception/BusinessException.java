package com.shev.compilation.common.exception;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

public class BusinessException extends SHEVCommonException
{
    /** serialVersionUID */
    private static final long serialVersionUID = -4455631075054848527L;

    public BusinessException()
    {
    }

    public BusinessException(String errorCode, String errorMessage)
    {
        super(errorCode, errorMessage);
    }

    public BusinessException(BindingResult result)
    {
        List<ObjectError> errors = result.getAllErrors();
        if (errors != null && errors.size() > 0)
        {
            ObjectError error = errors.get(0);
            String errorCode = error.getCodes() != null && error.getCodes().length > 0 ? error.getCodes()[0] : error.getCode(); 
            setErrorCode(errorCode);
            setErrorMessage(error.getDefaultMessage());
        }
    }
}
