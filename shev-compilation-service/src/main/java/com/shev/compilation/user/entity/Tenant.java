package com.shev.compilation.user.entity;

import java.io.Serializable;
import java.util.Date;

public class Tenant implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tenant.ID
     *
     * @mbg.generated
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tenant.CORE_ID
     *
     * @mbg.generated
     */
    private String coreId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tenant.NAME
     *
     * @mbg.generated
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tenant.DOMAIN_NAME
     *
     * @mbg.generated
     */
    private String domainName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tenant.COMMENT
     *
     * @mbg.generated
     */
    private String comment;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tenant.CREATE_TIME
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tenant.UPDATE_TIME
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tenant
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tenant.ID
     *
     * @return the value of tenant.ID
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tenant.ID
     *
     * @param id the value for tenant.ID
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tenant.CORE_ID
     *
     * @return the value of tenant.CORE_ID
     *
     * @mbg.generated
     */
    public String getCoreId() {
        return coreId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tenant.CORE_ID
     *
     * @param coreId the value for tenant.CORE_ID
     *
     * @mbg.generated
     */
    public void setCoreId(String coreId) {
        this.coreId = coreId == null ? null : coreId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tenant.NAME
     *
     * @return the value of tenant.NAME
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tenant.NAME
     *
     * @param name the value for tenant.NAME
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tenant.DOMAIN_NAME
     *
     * @return the value of tenant.DOMAIN_NAME
     *
     * @mbg.generated
     */
    public String getDomainName() {
        return domainName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tenant.DOMAIN_NAME
     *
     * @param domainName the value for tenant.DOMAIN_NAME
     *
     * @mbg.generated
     */
    public void setDomainName(String domainName) {
        this.domainName = domainName == null ? null : domainName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tenant.COMMENT
     *
     * @return the value of tenant.COMMENT
     *
     * @mbg.generated
     */
    public String getComment() {
        return comment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tenant.COMMENT
     *
     * @param comment the value for tenant.COMMENT
     *
     * @mbg.generated
     */
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tenant.CREATE_TIME
     *
     * @return the value of tenant.CREATE_TIME
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tenant.CREATE_TIME
     *
     * @param createTime the value for tenant.CREATE_TIME
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tenant.UPDATE_TIME
     *
     * @return the value of tenant.UPDATE_TIME
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tenant.UPDATE_TIME
     *
     * @param updateTime the value for tenant.UPDATE_TIME
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenant
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
        sb.append(", coreId=").append(coreId);
        sb.append(", name=").append(name);
        sb.append(", domainName=").append(domainName);
        sb.append(", comment=").append(comment);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}