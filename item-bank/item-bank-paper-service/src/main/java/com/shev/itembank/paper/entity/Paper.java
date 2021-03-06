package com.shev.itembank.paper.entity;

import java.io.Serializable;
import java.util.Date;

public class Paper implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column paper.ID
     *
     * @mbg.generated
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column paper.SOURCE_ID
     *
     * @mbg.generated
     */
    private String sourceId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column paper.EXAM_TYPE_ID
     *
     * @mbg.generated
     */
    private Integer examTypeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column paper.EDUCATIONAL_STAGE
     *
     * @mbg.generated
     */
    private Integer educationalStage;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column paper.SUBJECT
     *
     * @mbg.generated
     */
    private Integer subject;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column paper.NAME
     *
     * @mbg.generated
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column paper.YEAR
     *
     * @mbg.generated
     */
    private Integer year;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column paper.DIFFICULTY
     *
     * @mbg.generated
     */
    private Integer difficulty;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column paper.IS_SCHOOL
     *
     * @mbg.generated
     */
    private Integer isSchool;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column paper.IS_CONTEST
     *
     * @mbg.generated
     */
    private Integer isContest;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column paper.GRADE_ID
     *
     * @mbg.generated
     */
    private Integer gradeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column paper.ORDINAL_NUMBER
     *
     * @mbg.generated
     */
    private Integer ordinalNumber;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column paper.PAPER_REF
     *
     * @mbg.generated
     */
    private String paperRef;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column paper.PAPER_REF2
     *
     * @mbg.generated
     */
    private String paperRef2;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column paper.CREATE_TIME
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column paper.UPDATE_TIME
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table paper
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column paper.ID
     *
     * @return the value of paper.ID
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column paper.ID
     *
     * @param id the value for paper.ID
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column paper.SOURCE_ID
     *
     * @return the value of paper.SOURCE_ID
     *
     * @mbg.generated
     */
    public String getSourceId() {
        return sourceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column paper.SOURCE_ID
     *
     * @param sourceId the value for paper.SOURCE_ID
     *
     * @mbg.generated
     */
    public void setSourceId(String sourceId) {
        this.sourceId = sourceId == null ? null : sourceId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column paper.EXAM_TYPE_ID
     *
     * @return the value of paper.EXAM_TYPE_ID
     *
     * @mbg.generated
     */
    public Integer getExamTypeId() {
        return examTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column paper.EXAM_TYPE_ID
     *
     * @param examTypeId the value for paper.EXAM_TYPE_ID
     *
     * @mbg.generated
     */
    public void setExamTypeId(Integer examTypeId) {
        this.examTypeId = examTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column paper.EDUCATIONAL_STAGE
     *
     * @return the value of paper.EDUCATIONAL_STAGE
     *
     * @mbg.generated
     */
    public Integer getEducationalStage() {
        return educationalStage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column paper.EDUCATIONAL_STAGE
     *
     * @param educationalStage the value for paper.EDUCATIONAL_STAGE
     *
     * @mbg.generated
     */
    public void setEducationalStage(Integer educationalStage) {
        this.educationalStage = educationalStage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column paper.SUBJECT
     *
     * @return the value of paper.SUBJECT
     *
     * @mbg.generated
     */
    public Integer getSubject() {
        return subject;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column paper.SUBJECT
     *
     * @param subject the value for paper.SUBJECT
     *
     * @mbg.generated
     */
    public void setSubject(Integer subject) {
        this.subject = subject;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column paper.NAME
     *
     * @return the value of paper.NAME
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column paper.NAME
     *
     * @param name the value for paper.NAME
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column paper.YEAR
     *
     * @return the value of paper.YEAR
     *
     * @mbg.generated
     */
    public Integer getYear() {
        return year;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column paper.YEAR
     *
     * @param year the value for paper.YEAR
     *
     * @mbg.generated
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column paper.DIFFICULTY
     *
     * @return the value of paper.DIFFICULTY
     *
     * @mbg.generated
     */
    public Integer getDifficulty() {
        return difficulty;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column paper.DIFFICULTY
     *
     * @param difficulty the value for paper.DIFFICULTY
     *
     * @mbg.generated
     */
    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column paper.IS_SCHOOL
     *
     * @return the value of paper.IS_SCHOOL
     *
     * @mbg.generated
     */
    public Integer getIsSchool() {
        return isSchool;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column paper.IS_SCHOOL
     *
     * @param isSchool the value for paper.IS_SCHOOL
     *
     * @mbg.generated
     */
    public void setIsSchool(Integer isSchool) {
        this.isSchool = isSchool;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column paper.IS_CONTEST
     *
     * @return the value of paper.IS_CONTEST
     *
     * @mbg.generated
     */
    public Integer getIsContest() {
        return isContest;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column paper.IS_CONTEST
     *
     * @param isContest the value for paper.IS_CONTEST
     *
     * @mbg.generated
     */
    public void setIsContest(Integer isContest) {
        this.isContest = isContest;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column paper.GRADE_ID
     *
     * @return the value of paper.GRADE_ID
     *
     * @mbg.generated
     */
    public Integer getGradeId() {
        return gradeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column paper.GRADE_ID
     *
     * @param gradeId the value for paper.GRADE_ID
     *
     * @mbg.generated
     */
    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column paper.ORDINAL_NUMBER
     *
     * @return the value of paper.ORDINAL_NUMBER
     *
     * @mbg.generated
     */
    public Integer getOrdinalNumber() {
        return ordinalNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column paper.ORDINAL_NUMBER
     *
     * @param ordinalNumber the value for paper.ORDINAL_NUMBER
     *
     * @mbg.generated
     */
    public void setOrdinalNumber(Integer ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column paper.PAPER_REF
     *
     * @return the value of paper.PAPER_REF
     *
     * @mbg.generated
     */
    public String getPaperRef() {
        return paperRef;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column paper.PAPER_REF
     *
     * @param paperRef the value for paper.PAPER_REF
     *
     * @mbg.generated
     */
    public void setPaperRef(String paperRef) {
        this.paperRef = paperRef == null ? null : paperRef.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column paper.PAPER_REF2
     *
     * @return the value of paper.PAPER_REF2
     *
     * @mbg.generated
     */
    public String getPaperRef2() {
        return paperRef2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column paper.PAPER_REF2
     *
     * @param paperRef2 the value for paper.PAPER_REF2
     *
     * @mbg.generated
     */
    public void setPaperRef2(String paperRef2) {
        this.paperRef2 = paperRef2 == null ? null : paperRef2.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column paper.CREATE_TIME
     *
     * @return the value of paper.CREATE_TIME
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column paper.CREATE_TIME
     *
     * @param createTime the value for paper.CREATE_TIME
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column paper.UPDATE_TIME
     *
     * @return the value of paper.UPDATE_TIME
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column paper.UPDATE_TIME
     *
     * @param updateTime the value for paper.UPDATE_TIME
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table paper
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
        sb.append(", sourceId=").append(sourceId);
        sb.append(", examTypeId=").append(examTypeId);
        sb.append(", educationalStage=").append(educationalStage);
        sb.append(", subject=").append(subject);
        sb.append(", name=").append(name);
        sb.append(", year=").append(year);
        sb.append(", difficulty=").append(difficulty);
        sb.append(", isSchool=").append(isSchool);
        sb.append(", isContest=").append(isContest);
        sb.append(", gradeId=").append(gradeId);
        sb.append(", ordinalNumber=").append(ordinalNumber);
        sb.append(", paperRef=").append(paperRef);
        sb.append(", paperRef2=").append(paperRef2);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}