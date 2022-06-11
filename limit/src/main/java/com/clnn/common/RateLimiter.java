package com.clnn.common;

import com.clnn.config.DistributedLock;
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
    /**
     * 分布式同步锁
     */
    private DistributedLock syncLock;

    public RateLimiter(String key,Double permitsPerSecond,Integer maxBurstSeconds,DistributedLock syncLock){
        this.key = key;
        this.lockKey = "DISTRIBUTED_LOCK:"+key;
        this.permitsPerSecond = permitsPerSecond;
        this.maxBurstSeconds = maxBurstSeconds;
        this.syncLock = syncLock;
    }

    /**
     * 生成并存储默认令牌桶
     * @return
     */
    private RedisPermits putDefaultPermits(){
        return null;
    }
}
