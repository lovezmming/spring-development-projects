package com.shev.compilation.common.qiniu;

public interface UploadService
{
    public String uploadResource(String tenantId, String name, String type, byte[] content, String foler, String fileName) throws Exception;

    public String downLoadResource(String tenantId, String name, String type, String foler, String fileName) throws Exception;
}
