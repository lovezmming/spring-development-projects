package com.shev.itembank.common.base.exception;

import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class ValidationException extends SHEVCommonException
{
    public ValidationException()
    {
    }

    public ValidationException(String errorCode, String errorMessage)
    {
        super(errorCode, errorMessage);
    }

    public ValidationException(BindingResult result)
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
