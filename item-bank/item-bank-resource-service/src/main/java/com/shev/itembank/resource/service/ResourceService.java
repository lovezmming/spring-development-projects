package com.shev.itembank.resource.service;

import com.shev.itembank.resource.entity.ResourceEntity;

public interface ResourceService
{
    public ResourceEntity getResourceById(String id) throws Exception;
}
