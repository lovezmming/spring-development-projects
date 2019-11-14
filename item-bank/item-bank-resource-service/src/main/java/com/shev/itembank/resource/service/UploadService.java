package com.shev.itembank.resource.service;

import com.shev.itembank.resource.entity.QiNiuFileInfo;

public interface UploadService
{
    public String uploadResource(String tenantId, byte[] content, String foler, String fileName) throws Exception;

    public String downLoadResource(String tenantId, String foler, String fileName) throws Exception;

    public String createThumb(String fileName, String tenantId) throws Exception;

    public QiNiuFileInfo getQiNiuFileInfo(String fileKey, String tenantId) throws Exception;
}
