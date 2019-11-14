package com.shev.itembank.system.service.impl;

import com.shev.itembank.common.Enum.TenantAttributeEnum;
import com.shev.itembank.system.custom.TenantAttributeCustomMapper;
import com.shev.itembank.system.service.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shev.itembank.common.base.util.TextUtil;
import com.shev.itembank.common.redis.service.CacheService;
import com.shev.itembank.system.entity.TenantAttribute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PublicServiceImpl implements PublicService
{

    @Autowired
    private CacheService cacheService;

    @Autowired
    private TenantAttributeCustomMapper tenantAttributeCustomMapper;

    @Override
    public Boolean isPublic(String tenantId) throws Exception
    {        
        if(TextUtil.isEmpty(tenantId))
            return Boolean.TRUE;        
        Boolean isPublic = (Boolean) cacheService.get(tenantId + "_isPublic");
        if(isPublic == null)
        {
            isPublic = Boolean.TRUE;
            Map<String, Object> params = new HashMap<>();
            params.put("type", TenantAttributeEnum.ACCESS_PUBLIC_DB.getValue());
            List<TenantAttribute> tenantAttributes = tenantAttributeCustomMapper.selectByParameter(params);
            if(!TextUtil.isEmpty(tenantAttributes))
            {
                TenantAttribute tenantAttribute = tenantAttributes.get(0);
                if(!TextUtil.isEmpty(tenantAttribute.getValue()) && (tenantAttribute.getValue().trim().equalsIgnoreCase("FALSE")))
                    isPublic = Boolean.FALSE;
                else
                    isPublic = Boolean.TRUE;
            }
            cacheService.set(tenantId+"_isPublic", isPublic);
        }        
        return isPublic;
    }
}
