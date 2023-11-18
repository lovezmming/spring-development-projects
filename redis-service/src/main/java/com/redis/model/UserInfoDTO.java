package com.redis.model;

import lombok.Data;

import java.util.Date;

@Data
public class UserInfoDTO {
    private String name;
    private String phone;
    private int age;
    private Date birthday;
}
