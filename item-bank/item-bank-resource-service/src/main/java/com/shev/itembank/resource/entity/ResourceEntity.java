package com.shev.itembank.resource.entity;

import java.io.Serializable;
import java.util.List;

public class ResourceEntity implements Serializable
{

    private static final long serialVersionUID = -1109721123921039564L;

    private Resource resource;

    private ResourcePublish resourcePublish;

    private AudioAttribute audioAttribute;

    private List<Resource> subResourceList;

    public Resource getResource()
    {
        return resource;
    }

    public void setResource(Resource resource)
    {
        this.resource = resource;
    }

    public ResourcePublish getResourcePublish()
    {
        return resourcePublish;
    }

    public void setResourcePublish(ResourcePublish resourcePublish)
    {
        this.resourcePublish = resourcePublish;
    }

    public AudioAttribute getAudioAttribute()
    {
        return audioAttribute;
    }

    public void setAudioAttribute(AudioAttribute audioAttribute)
    {
        this.audioAttribute = audioAttribute;
    }

    public List<Resource> getSubResourceList()
    {
        return subResourceList;
    }

    public void setSubResourceList(List<Resource> subResourceList)
    {
        this.subResourceList = subResourceList;
    }

}