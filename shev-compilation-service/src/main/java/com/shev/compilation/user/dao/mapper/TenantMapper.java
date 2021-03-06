package com.shev.compilation.user.dao.mapper;

import com.shev.compilation.user.entity.Tenant;

import java.util.List;

public interface TenantMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenant
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenant
     *
     * @mbg.generated
     */
    int insert(Tenant record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenant
     *
     * @mbg.generated
     */
    Tenant selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenant
     *
     * @mbg.generated
     */
    List<Tenant> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenant
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Tenant record);
}