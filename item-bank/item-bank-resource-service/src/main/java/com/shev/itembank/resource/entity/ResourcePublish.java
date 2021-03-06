package com.shev.itembank.resource.entity;

import java.io.Serializable;
import java.util.Date;

public class ResourcePublish implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column resource_publish.ID
     *
     * @mbg.generated
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column resource_publish.RESOURCE_ID
     *
     * @mbg.generated
     */
    private String resourceId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column resource_publish.PUBLISH_URL
     *
     * @mbg.generated
     */
    private String publishUrl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column resource_publish.CREATE_TIME
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column resource_publish.UPDATE_TIME
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table resource_publish
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column resource_publish.ID
     *
     * @return the value of resource_publish.ID
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column resource_publish.ID
     *
     * @param id the value for resource_publish.ID
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column resource_publish.RESOURCE_ID
     *
     * @return the value of resource_publish.RESOURCE_ID
     *
     * @mbg.generated
     */
    public String getResourceId() {
        return resourceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column resource_publish.RESOURCE_ID
     *
     * @param resourceId the value for resource_publish.RESOURCE_ID
     *
     * @mbg.generated
     */
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId == null ? null : resourceId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column resource_publish.PUBLISH_URL
     *
     * @return the value of resource_publish.PUBLISH_URL
     *
     * @mbg.generated
     */
    public String getPublishUrl() {
        return publishUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column resource_publish.PUBLISH_URL
     *
     * @param publishUrl the value for resource_publish.PUBLISH_URL
     *
     * @mbg.generated
     */
    public void setPublishUrl(String publishUrl) {
        this.publishUrl = publishUrl == null ? null : publishUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column resource_publish.CREATE_TIME
     *
     * @return the value of resource_publish.CREATE_TIME
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column resource_publish.CREATE_TIME
     *
     * @param createTime the value for resource_publish.CREATE_TIME
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column resource_publish.UPDATE_TIME
     *
     * @return the value of resource_publish.UPDATE_TIME
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column resource_publish.UPDATE_TIME
     *
     * @param updateTime the value for resource_publish.UPDATE_TIME
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resource_publish
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", resourceId=").append(resourceId);
        sb.append(", publishUrl=").append(publishUrl);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}