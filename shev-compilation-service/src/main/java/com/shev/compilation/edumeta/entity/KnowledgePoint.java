package com.shev.compilation.edumeta.entity;

import java.io.Serializable;
import java.util.Date;

public class KnowledgePoint implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column knowledge_point.ID
     *
     * @mbg.generated
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column knowledge_point.NAME
     *
     * @mbg.generated
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column knowledge_point.SUBJECT_ID
     *
     * @mbg.generated
     */
    private Integer subjectId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column knowledge_point.EDUCATIONAL_STAGE_ID
     *
     * @mbg.generated
     */
    private Integer educationalStageId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column knowledge_point.PARENT_ID
     *
     * @mbg.generated
     */
    private String parentId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column knowledge_point.DEPTH
     *
     * @mbg.generated
     */
    private Integer depth;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column knowledge_point.URL
     *
     * @mbg.generated
     */
    private String url;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column knowledge_point.CREATE_TIME
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column knowledge_point.UPDATE_TIME
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column knowledge_point.UPDATE_USER_ID
     *
     * @mbg.generated
     */
    private String updateUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column knowledge_point.CREATE_USER_ID
     *
     * @mbg.generated
     */
    private String createUserId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table knowledge_point
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column knowledge_point.ID
     *
     * @return the value of knowledge_point.ID
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column knowledge_point.ID
     *
     * @param id the value for knowledge_point.ID
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column knowledge_point.NAME
     *
     * @return the value of knowledge_point.NAME
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column knowledge_point.NAME
     *
     * @param name the value for knowledge_point.NAME
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column knowledge_point.SUBJECT_ID
     *
     * @return the value of knowledge_point.SUBJECT_ID
     *
     * @mbg.generated
     */
    public Integer getSubjectId() {
        return subjectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column knowledge_point.SUBJECT_ID
     *
     * @param subjectId the value for knowledge_point.SUBJECT_ID
     *
     * @mbg.generated
     */
    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column knowledge_point.EDUCATIONAL_STAGE_ID
     *
     * @return the value of knowledge_point.EDUCATIONAL_STAGE_ID
     *
     * @mbg.generated
     */
    public Integer getEducationalStageId() {
        return educationalStageId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column knowledge_point.EDUCATIONAL_STAGE_ID
     *
     * @param educationalStageId the value for knowledge_point.EDUCATIONAL_STAGE_ID
     *
     * @mbg.generated
     */
    public void setEducationalStageId(Integer educationalStageId) {
        this.educationalStageId = educationalStageId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column knowledge_point.PARENT_ID
     *
     * @return the value of knowledge_point.PARENT_ID
     *
     * @mbg.generated
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column knowledge_point.PARENT_ID
     *
     * @param parentId the value for knowledge_point.PARENT_ID
     *
     * @mbg.generated
     */
    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column knowledge_point.DEPTH
     *
     * @return the value of knowledge_point.DEPTH
     *
     * @mbg.generated
     */
    public Integer getDepth() {
        return depth;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column knowledge_point.DEPTH
     *
     * @param depth the value for knowledge_point.DEPTH
     *
     * @mbg.generated
     */
    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column knowledge_point.URL
     *
     * @return the value of knowledge_point.URL
     *
     * @mbg.generated
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column knowledge_point.URL
     *
     * @param url the value for knowledge_point.URL
     *
     * @mbg.generated
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column knowledge_point.CREATE_TIME
     *
     * @return the value of knowledge_point.CREATE_TIME
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column knowledge_point.CREATE_TIME
     *
     * @param createTime the value for knowledge_point.CREATE_TIME
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column knowledge_point.UPDATE_TIME
     *
     * @return the value of knowledge_point.UPDATE_TIME
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column knowledge_point.UPDATE_TIME
     *
     * @param updateTime the value for knowledge_point.UPDATE_TIME
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column knowledge_point.UPDATE_USER_ID
     *
     * @return the value of knowledge_point.UPDATE_USER_ID
     *
     * @mbg.generated
     */
    public String getUpdateUserId() {
        return updateUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column knowledge_point.UPDATE_USER_ID
     *
     * @param updateUserId the value for knowledge_point.UPDATE_USER_ID
     *
     * @mbg.generated
     */
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId == null ? null : updateUserId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column knowledge_point.CREATE_USER_ID
     *
     * @return the value of knowledge_point.CREATE_USER_ID
     *
     * @mbg.generated
     */
    public String getCreateUserId() {
        return createUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column knowledge_point.CREATE_USER_ID
     *
     * @param createUserId the value for knowledge_point.CREATE_USER_ID
     *
     * @mbg.generated
     */
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId == null ? null : createUserId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table knowledge_point
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
        sb.append(", name=").append(name);
        sb.append(", subjectId=").append(subjectId);
        sb.append(", educationalStageId=").append(educationalStageId);
        sb.append(", parentId=").append(parentId);
        sb.append(", depth=").append(depth);
        sb.append(", url=").append(url);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateUserId=").append(updateUserId);
        sb.append(", createUserId=").append(createUserId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}