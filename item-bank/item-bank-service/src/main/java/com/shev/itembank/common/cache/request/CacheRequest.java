package com.shev.itembank.common.cache.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CacheRequest
{
    String cacheKey;

    Object cacheValue;
}
