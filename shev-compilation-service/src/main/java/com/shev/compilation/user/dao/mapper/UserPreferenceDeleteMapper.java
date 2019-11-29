package com.shev.compilation.user.dao.mapper;

import com.shev.compilation.user.entity.UserPreferenceDelete;

import java.util.List;

public interface UserPreferenceDeleteMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_preference_delete
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_preference_delete
     *
     * @mbg.generated
     */
    int insert(UserPreferenceDelete record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_preference_delete
     *
     * @mbg.generated
     */
    UserPreferenceDelete selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_preference_delete
     *
     * @mbg.generated
     */
    List<UserPreferenceDelete> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_preference_delete
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(UserPreferenceDelete record);
}