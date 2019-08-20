package com.shev.compilation.user.dao.mapper;

import com.shev.compilation.user.entity.DutyPermissionRelation;
import java.util.List;

public interface DutyPermissionRelationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table duty_permission_relation
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table duty_permission_relation
     *
     * @mbg.generated
     */
    int insert(DutyPermissionRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table duty_permission_relation
     *
     * @mbg.generated
     */
    DutyPermissionRelation selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table duty_permission_relation
     *
     * @mbg.generated
     */
    List<DutyPermissionRelation> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table duty_permission_relation
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(DutyPermissionRelation record);
}