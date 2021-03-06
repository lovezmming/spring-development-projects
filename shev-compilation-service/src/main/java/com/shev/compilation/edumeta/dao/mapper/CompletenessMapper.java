package com.shev.compilation.edumeta.dao.mapper;

import com.shev.compilation.edumeta.entity.Completeness;

import java.util.List;

public interface CompletenessMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table completeness
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table completeness
     *
     * @mbg.generated
     */
    int insert(Completeness record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table completeness
     *
     * @mbg.generated
     */
    Completeness selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table completeness
     *
     * @mbg.generated
     */
    List<Completeness> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table completeness
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Completeness record);
}