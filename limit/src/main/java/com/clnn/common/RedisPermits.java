package com.person.limit;

import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * redis令牌桶
 * 该类为令牌桶信息，其中包含了令牌桶的大小，令牌产生速率以及
 * 核心令牌异步更新方法reSync
 */
@Data
public class RedisPermits {

    //最大存储令牌数
    private Long maxPermits;
    //当前存储令牌数
    private Long storedPermits;
    //添加令牌时间间隔
    private Long intervalMillis;
    //下次请求可以获取令牌的起始时间，默认当前系统时间
    private Long nextFreeTicketMillis;

    /**
     *
     * @param permitsPerSecond 每秒放入的令牌数
     * @param maxBurstSeconds 最大存储maxBurstSeconds
     */
    public RedisPermits(Double permitsPerSecond,Integer maxBurstSeconds){
        if(null == maxBurstSeconds) maxBurstSeconds = 60;
        this.maxPermits = (long)(permitsPerSecond * maxBurstSeconds);
        this.storedPermits = permitsPerSecond.longValue();
        this.intervalMillis = (long)(TimeUnit.SECONDS.toMillis(1)/permitsPerSecond);
        this.nextFreeTicketMillis = System.currentTimeMillis();
    }

    /**
     * redis的过期时长
     * @return
     */
    public long expires(){
        long now = System.currentTimeMillis();
        return 2*TimeUnit.MINUTES.toSeconds(1) + TimeUnit.MICROSECONDS.toSeconds(Math.max(nextFreeTicketMillis,now)-now);
    }

    /**
     * 异步更新当前持有的令牌数
     * 若当前时间晚于nextFreeTicketMicros,则计算该段时间内可以生成多少令牌
     * @param now
     * @return
     */
    public boolean reSync(long now){
        if(now>nextFreeTicketMillis){
            storedPermits = Math.min(maxPermits,storedPermits+(now - nextFreeTicketMillis)/intervalMillis);
            nextFreeTicketMillis = now;
            return true;
        }
        return false;
    }
}
