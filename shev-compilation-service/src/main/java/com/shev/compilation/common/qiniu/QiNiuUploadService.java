package com.shev.compilation.common.qiniu;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.shev.compilation.user.dao.custom.PartnerCustomDao;
import com.shev.compilation.user.entity.Partner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("service.upload.qiniu")
@Scope("prototype")
public class QiNiuUploadService implements UploadService
{
    private static final Logger logger = LoggerFactory.getLogger(QiNiuUploadService.class);

    @Autowired
    private PartnerCustomDao partnerCustomDao;

    @Override
    public String uploadResource(String tenantId, String name, String type, byte[] data, String foler, String fileName) throws Exception
    {
        Map<String, Object> params = new HashMap<>();
        params.put("tenantId", tenantId);
        params.put("name", name);
        params.put("type", type);
        List<Partner> partners = partnerCustomDao.getPartnersByParams(params);
        Partner partner = partners.get(0);
        Map<String, Object> comment = (Map<String, Object>) JSONObject.parse(partner.getComment());
        Configuration cfg = new Configuration(Zone.zone0());
        Auth auth = Auth.create(partner.getAccessKey(), partner.getSecretKey());
        String key = foler.endsWith("/") ? foler.concat(fileName) : foler.concat("/").concat(fileName);
        try
        {
            Response response = new UploadManager(cfg).put(data, key, auth.uploadToken(String.valueOf(comment.get("bucketName"))));
            logger.info(response.bodyString());
        } catch (QiniuException e)
        {
            Response response = e.response;
            logger.error(response.toString());
            logger.error(response.bodyString());
            logger.error(e.getMessage(), e);
        }
        return String.valueOf(comment.get("domainName")) + "/" + key;
    }

    @Override
    public String downLoadResource(String tenantId, String name, String type, String foler, String fileName) throws Exception
    {
        Map<String, Object> params = new HashMap<>();
        params.put("tenantId", tenantId);
        params.put("name", name);
        params.put("type", type);
        List<Partner> partners = partnerCustomDao.getPartnersByParams(params);
        Partner partner = partners.get(0);
        Map<String, Object> comment = (Map<String, Object>) JSONObject.parse(partner.getComment());
        Auth auth = Auth.create(partner.getAccessKey(), partner.getSecretKey());
        String key = foler.endsWith("/") ? foler.concat(fileName) : foler.concat("/").concat(fileName);
        String url = comment.get("domainName") + "/" + key;
        Boolean isFileExists = false;
        Configuration cfg = new Configuration(Zone.zone0());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try
        {
            bucketManager.stat(String.valueOf(comment.get("bucketName")), key);
            isFileExists = true;
        } catch (QiniuException e)
        {
            Response response = e.response;
            logger.error(response.toString());
            logger.error(response.bodyString());
            logger.error(e.getMessage(), e);
        }
        return isFileExists == true ? url : null;
    }

}
