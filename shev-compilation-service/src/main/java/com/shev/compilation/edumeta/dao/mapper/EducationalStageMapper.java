package com.shev.compilation.edumeta.dao.mapper;

import com.shev.compilation.edumeta.entity.EducationalStage;

import java.util.List;

public interface EducationalStageMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table educational_stage
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table educational_stage
     *
     * @mbg.generated
     */
    int insert(EducationalStage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table educational_stage
     *
     * @mbg.generated
     */
    EducationalStage selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table educational_stage
     *
     * @mbg.generated
     */
    List<EducationalStage> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table educational_stage
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(EducationalStage record);
}