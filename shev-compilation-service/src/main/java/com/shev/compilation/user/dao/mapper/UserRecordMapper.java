package com.shev.compilation.user.dao.mapper;

import com.shev.compilation.user.entity.UserRecord;

import java.util.List;

public interface UserRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_record
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_record
     *
     * @mbg.generated
     */
    int insert(UserRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_record
     *
     * @mbg.generated
     */
    UserRecord selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_record
     *
     * @mbg.generated
     */
    List<UserRecord> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_record
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(UserRecord record);
}