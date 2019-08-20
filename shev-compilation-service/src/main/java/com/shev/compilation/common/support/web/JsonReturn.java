package com.shev.compilation.common.support.web;

public class JsonReturn<T>
{
    
    private String result;
    
    private String message;
    
    private T payload;
    
    public JsonReturn() 
    {
        this("success", null, null);
    }
    
    public JsonReturn(String result, String message, T payload)
    {
        setResult(result);
        setMessage(message);
        setPayload(payload);
    }
    
    public JsonReturn(T payload)
    {
        this("success", null, payload);
    }
    
    public JsonReturn(Throwable e)
    {
        this("error", e.getMessage(), null);
    }

    public String getResult()
    {
        return result;
    }

    public void setResult(String result)
    {
        this.result = result;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public T getPayload()
    {
        return payload;
    }

    public void setPayload(T payload)
    {
        this.payload = payload;
    }
}
