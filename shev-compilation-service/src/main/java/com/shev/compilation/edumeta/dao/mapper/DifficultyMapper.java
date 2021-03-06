package com.shev.compilation.edumeta.dao.mapper;

import com.shev.compilation.edumeta.entity.Difficulty;

import java.util.List;

public interface DifficultyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table difficulty
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table difficulty
     *
     * @mbg.generated
     */
    int insert(Difficulty record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table difficulty
     *
     * @mbg.generated
     */
    Difficulty selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table difficulty
     *
     * @mbg.generated
     */
    List<Difficulty> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table difficulty
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Difficulty record);
}