package com.shev.compilation.user.dao.mapper;

import com.shev.compilation.user.entity.UserDutyRelationDelete;

import java.util.List;

public interface UserDutyRelationDeleteMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_duty_relation_delete
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_duty_relation_delete
     *
     * @mbg.generated
     */
    int insert(UserDutyRelationDelete record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_duty_relation_delete
     *
     * @mbg.generated
     */
    UserDutyRelationDelete selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_duty_relation_delete
     *
     * @mbg.generated
     */
    List<UserDutyRelationDelete> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_duty_relation_delete
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(UserDutyRelationDelete record);
}