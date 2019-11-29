package com.shev.itembank.resource.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.shev.itembank.common.Enum.ConstantEnum;
import com.shev.itembank.common.Enum.EnumResourceTypeDefault;
import com.shev.itembank.common.Enum.PartnerEnum;
import com.shev.itembank.resource.custom.AudioAttributeCustomMapper;
import com.shev.itembank.resource.custom.ResourceCustomMapper;
import com.shev.itembank.resource.custom.ResourcePublishCustomMapper;
import com.shev.itembank.resource.entity.Resource;
import com.shev.itembank.resource.entity.ResourceEntity;
import com.shev.itembank.resource.mapper.ResourceMapper;
import com.shev.itembank.resource.service.ResourceService;
import com.shev.itembank.system.custom.PartnerCustomMapper;
import com.shev.itembank.system.entity.Partner;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResourceServiceImpl implements ResourceService
{
    private static final Logger logger = LoggerFactory.getLogger(ResourceServiceImpl.class);

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private PartnerCustomMapper partnerCustomMapper;

    @Autowired
    private ResourcePublishCustomMapper resourcePublishCustomMapper;

    @Autowired
    private ResourceCustomMapper resourceCustomMapper;

    @Autowired
    private AudioAttributeCustomMapper audioAttributeCustomMapper;

    @Override
    public ResourceEntity getResourceById(String id) throws Exception
    {
        ResourceEntity resourceEntity = new ResourceEntity();
        Resource resource = resourceMapper.selectByPrimaryKey(id);
        String resourceType = resource.getResourceType();
        String tenantId = resource.getTenantId();
        String url = resource.getUrl();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tenantId", tenantId);
        params.put("type", PartnerEnum.QINIU.getType());
        List<Partner> partners = partnerCustomMapper.selectByParameter(params);
        Map<String, Object> comment = (Map<String, Object>) JSONObject.parse(partners.get(0).getComment());
        String resourceServer = (String) comment.get("domainName");
        params.put("resourceId", id);
        if (StringUtils.isNotBlank(resourceType)
                && (EnumResourceTypeDefault.Game.getName().equals(resourceType)
                    || EnumResourceTypeDefault.TGBD.getName().equals(resourceType)))
        {
            resourceEntity.setResourcePublish(resourcePublishCustomMapper.selectByParameter(params).get(0));
        }
        if (StringUtils.isNotBlank(resourceType) && EnumResourceTypeDefault.Audio.getName().equals(resourceType))
        {
            resourceEntity.setAudioAttribute(audioAttributeCustomMapper.selectByParameter(params).get(0));
        }
        if (StringUtils.isNotBlank(resourceType) && EnumResourceTypeDefault.Video.getName().equals(resourceType))
        {
            List<Resource> resources = resourceCustomMapper.selectByParameter(params);
            List<Resource> subResourceList = new ArrayList<Resource>();
            for (Resource subResource : resources)
            {
                String subResourceUrl = subResource.getUrl();
                subResourceUrl = subResourceUrl.replace(ConstantEnum.IMAGE_PLACE_HOLDER.getValue(), resourceServer);
                subResource.setUrl(subResourceUrl);
                subResourceList.add(subResource);
            }
            resourceEntity.setSubResourceList(subResourceList);
        }
        url = url.replace(ConstantEnum.IMAGE_PLACE_HOLDER.getValue(), resourceServer);
        resource.setUrl(url);
        resourceEntity.setResource(resource);
        return resourceEntity;
    }
}
