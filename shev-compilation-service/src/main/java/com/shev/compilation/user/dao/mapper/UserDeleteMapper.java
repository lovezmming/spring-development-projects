package com.shev.compilation.user.dao.mapper;

import com.shev.compilation.user.entity.UserDelete;

import java.util.List;

public interface UserDeleteMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_delete
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_delete
     *
     * @mbg.generated
     */
    int insert(UserDelete record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_delete
     *
     * @mbg.generated
     */
    UserDelete selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_delete
     *
     * @mbg.generated
     */
    List<UserDelete> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_delete
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(UserDelete record);
}