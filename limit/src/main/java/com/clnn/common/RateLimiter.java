package com.person.limit;

import com.person.zookeeper.DistributedLock;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 令牌桶限流器
 */
@Data
@Slf4j
public class RateLimiter {
    /**
     * redis key
     */
    private String key;
    /**
     * redis 分布式锁的key
     */
    private String lockKey;
    /**
     * 每秒存入的令牌数
     */
    private Double permitsPerSecond;
    /**
     * 最大存储maxBurstSeconds秒生成的令牌
     */
    private Integer maxBurstSeconds;
    private DistributedLock syncLock;//
}
