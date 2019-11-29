package com.shev.itembank.resource.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.processing.OperationManager;
import com.qiniu.processing.OperationStatus;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.shev.itembank.common.Enum.PartnerEnum;
import com.shev.itembank.common.base.exception.BusinessException;
import com.shev.itembank.common.base.util.TextUtil;
import com.shev.itembank.resource.entity.QiNiuFileInfo;
import com.shev.itembank.resource.entity.Thumb;
import com.shev.itembank.resource.service.UploadService;
import com.shev.itembank.resource.util.ThumbUtil;
import com.shev.itembank.system.custom.PartnerCustomMapper;
import com.shev.itembank.system.entity.Partner;
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
    private PartnerCustomMapper partnerCustomMapper;

    private static final int TRY_COUNT = 10;

    @Override
    public String uploadResource(String tenantId, byte[] data, String foler, String fileName) throws Exception
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tenantId", tenantId);
        params.put("name", PartnerEnum.QINIU.getName());
        params.put("type", PartnerEnum.QINIU.getType());
        List<Partner> partners = partnerCustomMapper.selectByParameter(params);
        if (!TextUtil.isEmpty(partners))
        {
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
        return null;
    }

    @Override
    public String downLoadResource(String tenantId, String foler, String fileName) throws Exception
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tenantId", tenantId);
        params.put("name", PartnerEnum.QINIU.getName());
        params.put("type", PartnerEnum.QINIU.getType());
        List<Partner> partners = partnerCustomMapper.selectByParameter(params);
        if (!TextUtil.isEmpty(partners))
        {
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
        return null;
    }

    @Override
    public String createThumb(String fileName, String tenantId) throws Exception
    {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("tenantId", tenantId);
        paramMap.put("name", PartnerEnum.QINIU.getName());
        paramMap.put("type", PartnerEnum.QINIU.getType());
        List<Partner> partners = partnerCustomMapper.selectByParameter(paramMap);
        Partner partner = partners.get(0);
        Map<String, Object> comment = (Map<String, Object>) JSONObject.parse(partner.getComment());

        Thumb thumb = new Thumb();
        thumb.setBucket(comment.get("bucketName").toString());
        thumb.setFrap(Integer.valueOf(comment.get("frap").toString()));
        thumb.setHeight(Integer.valueOf(comment.get("height").toString()));
        thumb.setWidth(Integer.valueOf(comment.get("width").toString()));
        thumb.setPipeline(comment.get("pipeline").toString());

        thumb.setSoureVideo(fileName);

        String accessKey = partner.getAccessKey();
        String secrrtKey = partner.getSecretKey();

        Auth auth = Auth.create(accessKey, secrrtKey);
        Configuration cfg = new Configuration(Zone.zone0());
        OperationManager operator = new OperationManager(auth, cfg);

        String pfops = ThumbUtil.createPfops(thumb);
        StringMap params = ThumbUtil.createParam(thumb);
        String domain = comment.get("domainName").toString();

        String persistentId = operator.pfop(thumb.getBucket(), thumb.getSoureVideo(), pfops, params);
        int count = 0;
        boolean flag = false;
        while (true)
        {
            OperationStatus operationStatus = operator.prefop(persistentId);
            if (operationStatus.code == 1 || operationStatus.code == 2)
            {
                Thread.sleep(1000);
            } else if (operationStatus.code == 3)
            {
                break;
            } else if (operationStatus.code == 0)
            {
                flag = true;
                break;
            }
            if (count > TRY_COUNT)
            {
                break;
            }
            count++;
        }
        if (flag)
        {
            return domain + "/" + thumb.getDestThumb();
        } else
        {
            throw new BusinessException("Resource.Qiniu.CreateThumb.Fail", "CreateThumb.Fail");
        }
    }

    @Override
    public QiNiuFileInfo getQiNiuFileInfo(String fileKey, String tenantId) throws Exception
    {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("tenantId", tenantId);
        paramMap.put("name", PartnerEnum.QINIU.getName());
        paramMap.put("type", PartnerEnum.QINIU.getType());
        List<Partner> partners = partnerCustomMapper.selectByParameter(paramMap);
        Partner partner = partners.get(0);
        Map<String, Object> comment = (Map<String, Object>) JSONObject.parse(partner.getComment());

        Auth auth = Auth.create(partner.getAccessKey(), partner.getSecretKey());
        Configuration cfg = new Configuration(Zone.zone0());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        QiNiuFileInfo qiNiuFileInfo = null;
        try
        {
            FileInfo fileInfo = bucketManager.stat(String.valueOf(comment.get("bucketName")), fileKey);
            qiNiuFileInfo = new QiNiuFileInfo();
            qiNiuFileInfo.setFsize(fileInfo.fsize);
            qiNiuFileInfo.setHash(fileInfo.hash);
            qiNiuFileInfo.setKey(fileInfo.key);
            qiNiuFileInfo.setMimeType(fileInfo.mimeType);
            qiNiuFileInfo.setPutTime(fileInfo.putTime);
        } catch (QiniuException e)
        {
            Response response = e.response;
            logger.error(response.toString());
            logger.error(response.bodyString());
            logger.error(e.getMessage(), e);
            throw new BusinessException("Resource.Qiniu.GetFileInfo.Fail", response.bodyString());
        }
        return qiNiuFileInfo;
    }

}
