package com.redis.service;

import com.redis.config.RedisKey;
import com.redis.config.RedisUtil;
import com.redis.model.UserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class XxUserService {
    @Autowired
    private RedisUtil redisUtil;

    public void createUserInfo(UserInfoDTO userInfo) {
        // save db

        // save redis
        String key = String.format(RedisKey.USER_INFO_PREFIX_KEY, userInfo.getPhone());
        redisUtil.set(key, userInfo);
    }

    public void getUserInfo(UserInfoDTO userInfo) {
        // get redis
        String key = String.format(RedisKey.USER_INFO_PREFIX_KEY, userInfo.getPhone());
        redisUtil.get(key, UserInfoDTO.class);
    }

    public void removeUserInfo(UserInfoDTO userInfo) {
        // delete db

        // delete redis
        String key = String.format(RedisKey.USER_INFO_PREFIX_KEY, userInfo.getPhone());
        redisUtil.delete(key);
    }

}
