package com.shev.compilation.user.dao.mapper;

import com.shev.compilation.user.entity.UserLoginDelete;
import java.util.List;

public interface UserLoginDeleteMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_login_delete
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_login_delete
     *
     * @mbg.generated
     */
    int insert(UserLoginDelete record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_login_delete
     *
     * @mbg.generated
     */
    UserLoginDelete selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_login_delete
     *
     * @mbg.generated
     */
    List<UserLoginDelete> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_login_delete
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(UserLoginDelete record);
}