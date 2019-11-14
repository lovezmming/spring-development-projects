package com.shev.itembank.resource.entity;

import java.io.Serializable;

public class QiNiuFileInfo implements Serializable
{
    /** serialVersionUID */
    private static final long serialVersionUID = -5196427860603402627L;

    private String key;
    
    private String hash;
    
    private long fsize;
    
    private long putTime;
    
    private String mimeType;

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getHash()
    {
        return hash;
    }

    public void setHash(String hash)
    {
        this.hash = hash;
    }

    public long getFsize()
    {
        return fsize;
    }

    public void setFsize(long fsize)
    {
        this.fsize = fsize;
    }

    public long getPutTime()
    {
        return putTime;
    }

    public void setPutTime(long putTime)
    {
        this.putTime = putTime;
    }

    public String getMimeType()
    {
        return mimeType;
    }

    public void setMimeType(String mimeType)
    {
        this.mimeType = mimeType;
    }

}
