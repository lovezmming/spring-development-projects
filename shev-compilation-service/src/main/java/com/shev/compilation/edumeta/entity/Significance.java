package com.shev.compilation.edumeta.entity;

import java.io.Serializable;
import java.util.Date;

public class Significance implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column significance.ID
     *
     * @mbg.generated
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column significance.CORE_ID
     *
     * @mbg.generated
     */
    private Integer coreId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column significance.NAME
     *
     * @mbg.generated
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column significance.STATUS
     *
     * @mbg.generated
     */
    private Boolean status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column significance.TYPE
     *
     * @mbg.generated
     */
    private String type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column significance.SEQ
     *
     * @mbg.generated
     */
    private Integer seq;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column significance.TENANT_ID
     *
     * @mbg.generated
     */
    private String tenantId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column significance.CREATE_TIME
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column significance.UPDATE_TIME
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column significance.UPDATE_USER_ID
     *
     * @mbg.generated
     */
    private String updateUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column significance.CREATE_USER_ID
     *
     * @mbg.generated
     */
    private String createUserId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table significance
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column significance.ID
     *
     * @return the value of significance.ID
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column significance.ID
     *
     * @param id the value for significance.ID
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column significance.CORE_ID
     *
     * @return the value of significance.CORE_ID
     *
     * @mbg.generated
     */
    public Integer getCoreId() {
        return coreId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column significance.CORE_ID
     *
     * @param coreId the value for significance.CORE_ID
     *
     * @mbg.generated
     */
    public void setCoreId(Integer coreId) {
        this.coreId = coreId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column significance.NAME
     *
     * @return the value of significance.NAME
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column significance.NAME
     *
     * @param name the value for significance.NAME
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column significance.STATUS
     *
     * @return the value of significance.STATUS
     *
     * @mbg.generated
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column significance.STATUS
     *
     * @param status the value for significance.STATUS
     *
     * @mbg.generated
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column significance.TYPE
     *
     * @return the value of significance.TYPE
     *
     * @mbg.generated
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column significance.TYPE
     *
     * @param type the value for significance.TYPE
     *
     * @mbg.generated
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column significance.SEQ
     *
     * @return the value of significance.SEQ
     *
     * @mbg.generated
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column significance.SEQ
     *
     * @param seq the value for significance.SEQ
     *
     * @mbg.generated
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column significance.TENANT_ID
     *
     * @return the value of significance.TENANT_ID
     *
     * @mbg.generated
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column significance.TENANT_ID
     *
     * @param tenantId the value for significance.TENANT_ID
     *
     * @mbg.generated
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId == null ? null : tenantId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column significance.CREATE_TIME
     *
     * @return the value of significance.CREATE_TIME
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column significance.CREATE_TIME
     *
     * @param createTime the value for significance.CREATE_TIME
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column significance.UPDATE_TIME
     *
     * @return the value of significance.UPDATE_TIME
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column significance.UPDATE_TIME
     *
     * @param updateTime the value for significance.UPDATE_TIME
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column significance.UPDATE_USER_ID
     *
     * @return the value of significance.UPDATE_USER_ID
     *
     * @mbg.generated
     */
    public String getUpdateUserId() {
        return updateUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column significance.UPDATE_USER_ID
     *
     * @param updateUserId the value for significance.UPDATE_USER_ID
     *
     * @mbg.generated
     */
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId == null ? null : updateUserId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column significance.CREATE_USER_ID
     *
     * @return the value of significance.CREATE_USER_ID
     *
     * @mbg.generated
     */
    public String getCreateUserId() {
        return createUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column significance.CREATE_USER_ID
     *
     * @param createUserId the value for significance.CREATE_USER_ID
     *
     * @mbg.generated
     */
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId == null ? null : createUserId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table significance
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
        sb.append(", status=").append(status);
        sb.append(", type=").append(type);
        sb.append(", seq=").append(seq);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateUserId=").append(updateUserId);
        sb.append(", createUserId=").append(createUserId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}