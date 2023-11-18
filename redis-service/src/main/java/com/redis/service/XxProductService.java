package com.redis.service;

import com.redis.config.RedisKey;
import com.redis.config.RedisUtil;
import com.redis.model.ProductInfoDTO;
import com.redis.model.UserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class XxProductService {
    @Autowired
    private RedisUtil redisUtil;

    public void createProductInfo(ProductInfoDTO productInfo) {
        // save db
        // save redis
        String key = String.format(RedisKey.PRODUCT_INFO_PREFIX_KEY, productInfo.getId());
        redisUtil.set(key, productInfo, 7, TimeUnit.DAYS);
    }

    public void getProductInfo(String productId) {
        // get redis
        String key = String.format(RedisKey.PRODUCT_INFO_PREFIX_KEY, productId);
        ProductInfoDTO productInfoDTO = redisUtil.get(key, ProductInfoDTO.class);
        if (Objects.nonNull(productInfoDTO)) {
            // 处理业务
            return;
        }

        synchronized (this) {
            productInfoDTO = redisUtil.get(key, ProductInfoDTO.class);
            if (Objects.nonNull(productInfoDTO)) {
                // 处理业务
                return;
            }

            // productInfoDTO = 查询db
            if (Objects.nonNull(productInfoDTO)) {
                redisUtil.set(key, productInfoDTO, 60, TimeUnit.MINUTES);
            } else {
                redisUtil.setEmpty(key);
            }
        }
    }

    public void removeUserInfo(UserInfoDTO userInfo) {
        // delete db

        // delete redis
        String key = String.format(RedisKey.USER_INFO_PREFIX_KEY, userInfo.getPhone());
        redisUtil.delete(key);
    }

}
