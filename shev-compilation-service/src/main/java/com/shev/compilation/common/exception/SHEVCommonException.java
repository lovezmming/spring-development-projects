package com.shev.compilation.common.exception;

public class SHEVCommonException extends Exception
{

    /** serialVersionUID */
    private static final long serialVersionUID = 4900768045942035709L;

    private String errorCode;

    private String errorMessage;

    public SHEVCommonException()
    {
    }

    public SHEVCommonException(String errorCode, String errorMessage)
    {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }
}
