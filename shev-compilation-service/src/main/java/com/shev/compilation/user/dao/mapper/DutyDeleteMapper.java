package com.shev.compilation.user.dao.mapper;

import com.shev.compilation.user.entity.DutyDelete;

import java.util.List;

public interface DutyDeleteMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table duty_delete
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table duty_delete
     *
     * @mbg.generated
     */
    int insert(DutyDelete record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table duty_delete
     *
     * @mbg.generated
     */
    DutyDelete selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table duty_delete
     *
     * @mbg.generated
     */
    List<DutyDelete> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table duty_delete
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(DutyDelete record);
}