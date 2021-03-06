package com.shev.compilation.user.entity;

import java.io.Serializable;
import java.util.Date;

public class UserRecord implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_record.ID
     *
     * @mbg.generated
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_record.USER_ID
     *
     * @mbg.generated
     */
    private String userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_record.TYPE
     *
     * @mbg.generated
     */
    private String type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_record.SOURCE_ID
     *
     * @mbg.generated
     */
    private String sourceId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_record.SOURCE_TYPE_ID
     *
     * @mbg.generated
     */
    private String sourceTypeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_record.OPERATE_TYPE
     *
     * @mbg.generated
     */
    private String operateType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_record.EDUCATIONAL_STAGE_ID
     *
     * @mbg.generated
     */
    private String educationalStageId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_record.SUBJECT_ID
     *
     * @mbg.generated
     */
    private String subjectId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_record.CATEGORY_ID
     *
     * @mbg.generated
     */
    private String categoryId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_record.REFENCE_ID
     *
     * @mbg.generated
     */
    private String refenceId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_record.ERROR_META
     *
     * @mbg.generated
     */
    private Integer errorMeta;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_record.TENANT_ID
     *
     * @mbg.generated
     */
    private String tenantId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_record.CREATE_TIME
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_record.UPDATE_TIME
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table user_record
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_record.ID
     *
     * @return the value of user_record.ID
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_record.ID
     *
     * @param id the value for user_record.ID
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_record.USER_ID
     *
     * @return the value of user_record.USER_ID
     *
     * @mbg.generated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_record.USER_ID
     *
     * @param userId the value for user_record.USER_ID
     *
     * @mbg.generated
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_record.TYPE
     *
     * @return the value of user_record.TYPE
     *
     * @mbg.generated
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_record.TYPE
     *
     * @param type the value for user_record.TYPE
     *
     * @mbg.generated
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_record.SOURCE_ID
     *
     * @return the value of user_record.SOURCE_ID
     *
     * @mbg.generated
     */
    public String getSourceId() {
        return sourceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_record.SOURCE_ID
     *
     * @param sourceId the value for user_record.SOURCE_ID
     *
     * @mbg.generated
     */
    public void setSourceId(String sourceId) {
        this.sourceId = sourceId == null ? null : sourceId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_record.SOURCE_TYPE_ID
     *
     * @return the value of user_record.SOURCE_TYPE_ID
     *
     * @mbg.generated
     */
    public String getSourceTypeId() {
        return sourceTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_record.SOURCE_TYPE_ID
     *
     * @param sourceTypeId the value for user_record.SOURCE_TYPE_ID
     *
     * @mbg.generated
     */
    public void setSourceTypeId(String sourceTypeId) {
        this.sourceTypeId = sourceTypeId == null ? null : sourceTypeId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_record.OPERATE_TYPE
     *
     * @return the value of user_record.OPERATE_TYPE
     *
     * @mbg.generated
     */
    public String getOperateType() {
        return operateType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_record.OPERATE_TYPE
     *
     * @param operateType the value for user_record.OPERATE_TYPE
     *
     * @mbg.generated
     */
    public void setOperateType(String operateType) {
        this.operateType = operateType == null ? null : operateType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_record.EDUCATIONAL_STAGE_ID
     *
     * @return the value of user_record.EDUCATIONAL_STAGE_ID
     *
     * @mbg.generated
     */
    public String getEducationalStageId() {
        return educationalStageId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_record.EDUCATIONAL_STAGE_ID
     *
     * @param educationalStageId the value for user_record.EDUCATIONAL_STAGE_ID
     *
     * @mbg.generated
     */
    public void setEducationalStageId(String educationalStageId) {
        this.educationalStageId = educationalStageId == null ? null : educationalStageId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_record.SUBJECT_ID
     *
     * @return the value of user_record.SUBJECT_ID
     *
     * @mbg.generated
     */
    public String getSubjectId() {
        return subjectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_record.SUBJECT_ID
     *
     * @param subjectId the value for user_record.SUBJECT_ID
     *
     * @mbg.generated
     */
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId == null ? null : subjectId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_record.CATEGORY_ID
     *
     * @return the value of user_record.CATEGORY_ID
     *
     * @mbg.generated
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_record.CATEGORY_ID
     *
     * @param categoryId the value for user_record.CATEGORY_ID
     *
     * @mbg.generated
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId == null ? null : categoryId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_record.REFENCE_ID
     *
     * @return the value of user_record.REFENCE_ID
     *
     * @mbg.generated
     */
    public String getRefenceId() {
        return refenceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_record.REFENCE_ID
     *
     * @param refenceId the value for user_record.REFENCE_ID
     *
     * @mbg.generated
     */
    public void setRefenceId(String refenceId) {
        this.refenceId = refenceId == null ? null : refenceId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_record.ERROR_META
     *
     * @return the value of user_record.ERROR_META
     *
     * @mbg.generated
     */
    public Integer getErrorMeta() {
        return errorMeta;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_record.ERROR_META
     *
     * @param errorMeta the value for user_record.ERROR_META
     *
     * @mbg.generated
     */
    public void setErrorMeta(Integer errorMeta) {
        this.errorMeta = errorMeta;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_record.TENANT_ID
     *
     * @return the value of user_record.TENANT_ID
     *
     * @mbg.generated
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_record.TENANT_ID
     *
     * @param tenantId the value for user_record.TENANT_ID
     *
     * @mbg.generated
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId == null ? null : tenantId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_record.CREATE_TIME
     *
     * @return the value of user_record.CREATE_TIME
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_record.CREATE_TIME
     *
     * @param createTime the value for user_record.CREATE_TIME
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_record.UPDATE_TIME
     *
     * @return the value of user_record.UPDATE_TIME
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_record.UPDATE_TIME
     *
     * @param updateTime the value for user_record.UPDATE_TIME
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_record
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
        sb.append(", userId=").append(userId);
        sb.append(", type=").append(type);
        sb.append(", sourceId=").append(sourceId);
        sb.append(", sourceTypeId=").append(sourceTypeId);
        sb.append(", operateType=").append(operateType);
        sb.append(", educationalStageId=").append(educationalStageId);
        sb.append(", subjectId=").append(subjectId);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", refenceId=").append(refenceId);
        sb.append(", errorMeta=").append(errorMeta);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}