package com.shev.compilation.edumeta.entity;

import java.io.Serializable;
import java.util.Date;

public class Subject implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column subject.ID
     *
     * @mbg.generated
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column subject.CORE_ID
     *
     * @mbg.generated
     */
    private Integer coreId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column subject.TENANT_ID
     *
     * @mbg.generated
     */
    private String tenantId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column subject.DEPARTMENT_ID
     *
     * @mbg.generated
     */
    private String departmentId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column subject.NAME
     *
     * @mbg.generated
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column subject.STATUS
     *
     * @mbg.generated
     */
    private Boolean status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column subject.SEQ
     *
     * @mbg.generated
     */
    private Integer seq;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column subject.CREATE_TIME
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column subject.UPDATE_TIME
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column subject.UPDATE_USER_ID
     *
     * @mbg.generated
     */
    private String updateUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column subject.CREATE_USER_ID
     *
     * @mbg.generated
     */
    private String createUserId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table subject
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column subject.ID
     *
     * @return the value of subject.ID
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column subject.ID
     *
     * @param id the value for subject.ID
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column subject.CORE_ID
     *
     * @return the value of subject.CORE_ID
     *
     * @mbg.generated
     */
    public Integer getCoreId() {
        return coreId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column subject.CORE_ID
     *
     * @param coreId the value for subject.CORE_ID
     *
     * @mbg.generated
     */
    public void setCoreId(Integer coreId) {
        this.coreId = coreId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column subject.TENANT_ID
     *
     * @return the value of subject.TENANT_ID
     *
     * @mbg.generated
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column subject.TENANT_ID
     *
     * @param tenantId the value for subject.TENANT_ID
     *
     * @mbg.generated
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId == null ? null : tenantId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column subject.DEPARTMENT_ID
     *
     * @return the value of subject.DEPARTMENT_ID
     *
     * @mbg.generated
     */
    public String getDepartmentId() {
        return departmentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column subject.DEPARTMENT_ID
     *
     * @param departmentId the value for subject.DEPARTMENT_ID
     *
     * @mbg.generated
     */
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId == null ? null : departmentId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column subject.NAME
     *
     * @return the value of subject.NAME
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column subject.NAME
     *
     * @param name the value for subject.NAME
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column subject.STATUS
     *
     * @return the value of subject.STATUS
     *
     * @mbg.generated
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column subject.STATUS
     *
     * @param status the value for subject.STATUS
     *
     * @mbg.generated
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column subject.SEQ
     *
     * @return the value of subject.SEQ
     *
     * @mbg.generated
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column subject.SEQ
     *
     * @param seq the value for subject.SEQ
     *
     * @mbg.generated
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column subject.CREATE_TIME
     *
     * @return the value of subject.CREATE_TIME
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column subject.CREATE_TIME
     *
     * @param createTime the value for subject.CREATE_TIME
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column subject.UPDATE_TIME
     *
     * @return the value of subject.UPDATE_TIME
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column subject.UPDATE_TIME
     *
     * @param updateTime the value for subject.UPDATE_TIME
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column subject.UPDATE_USER_ID
     *
     * @return the value of subject.UPDATE_USER_ID
     *
     * @mbg.generated
     */
    public String getUpdateUserId() {
        return updateUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column subject.UPDATE_USER_ID
     *
     * @param updateUserId the value for subject.UPDATE_USER_ID
     *
     * @mbg.generated
     */
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId == null ? null : updateUserId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column subject.CREATE_USER_ID
     *
     * @return the value of subject.CREATE_USER_ID
     *
     * @mbg.generated
     */
    public String getCreateUserId() {
        return createUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column subject.CREATE_USER_ID
     *
     * @param createUserId the value for subject.CREATE_USER_ID
     *
     * @mbg.generated
     */
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId == null ? null : createUserId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subject
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
        sb.append(", tenantId=").append(tenantId);
        sb.append(", departmentId=").append(departmentId);
        sb.append(", name=").append(name);
        sb.append(", status=").append(status);
        sb.append(", seq=").append(seq);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateUserId=").append(updateUserId);
        sb.append(", createUserId=").append(createUserId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}