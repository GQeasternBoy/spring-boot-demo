package com.zihexin.redis.redisLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by Administrator on 2018/6/20.
 */
public class RedisLock {

    private static Logger logger = LoggerFactory.getLogger(RedisLock.class);

    private RedisTemplate redisTemplate;

    private static final int DEFAULT_ACQUIRY_RESOLUTION_MILLIS = 100;

    /**
     * Lock key path
     */
    private String lockKey;

    /**
     * 锁超时时间，防止线程在入锁以后，无限的执行等待
     */
    private int expireMsecs = 30 * 1000;

    /**
     * 锁等待时间，防止线程饥饿
     */
    private int timeoutMsecs = 10 * 1000;

    private volatile boolean locked = false;

    public RedisLock(RedisTemplate redisTemplate, String lockKey) {
        this.redisTemplate = redisTemplate;
        this.lockKey = lockKey + "_lock";
    }

    public RedisLock(RedisTemplate redisTemplate, String lockKey, int timeoutMsecs) {
        this(redisTemplate, lockKey);
        this.timeoutMsecs = timeoutMsecs;
    }

    public RedisLock(RedisTemplate redisTemplate, String lockKey, int expireMsecs, int timeoutMsecs) {
        this(redisTemplate, lockKey, timeoutMsecs);
        this.expireMsecs = expireMsecs;
    }

    public String getLockKey() {
        return lockKey;
    }

    private String get(final String key) {
        Object obj = null;

        try {
            obj = redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection redisConnection) {
                    StringRedisSerializer serializer = new StringRedisSerializer();
                    byte[] data = redisConnection.get(serializer.serialize(key));
                    redisConnection.close();
                    if (data == null) {
                        return null;
                    }
                    return serializer.deserialize(data);
                }
            });
        } catch (Exception e) {
            logger.error("get redis error, key :{}", key);
        }
        return obj != null ? obj.toString() : null;
    }

    private boolean setNx(final String key, final String value) {
        Object obj = null;

        try {
            obj = redisTemplate.execute(new RedisCallback() {
                @Override
                public Object doInRedis(RedisConnection redisConnection) {
                    StringRedisSerializer serializer = new StringRedisSerializer();
                    Boolean success = redisConnection.setNX(serializer.serialize(key), serializer.serialize(value));
                    redisConnection.close();
                    return success;
                }
            });
        } catch (Exception e) {
            logger.error("setNX redis error,key :{}", key);
        }
        return obj != null ? (Boolean) obj : false;
    }

    private String getSet(final String key, final String value) {
        Object obj = null;

        try {
            obj = redisTemplate.execute(new RedisCallback() {
                @Override
                public Object doInRedis(RedisConnection redisConnection) {
                    StringRedisSerializer serializer = new StringRedisSerializer();
                    byte[] set = redisConnection.getSet(serializer.serialize(key), serializer.serialize(value));
                    redisConnection.close();
                    return serializer.deserialize(set);
                }
            });
        } catch (Exception e) {
            logger.error("getSet redis error,key :{}", key);
        }
        return obj != null ? (String) obj : null;
    }

    public synchronized boolean lock() throws InterruptedException {
        int timeout = timeoutMsecs;
        while (timeout >= 0) {
            long expires = System.currentTimeMillis() + expireMsecs + 1;
            String expiresStr = String.valueOf(expires);//锁到期时间

            if (setNx(lockKey, expiresStr)) {
                locked = true;
                return true;
            }

            String currentValueStr = get(lockKey);//获取redis中lock时间

            if(currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {//锁过期
                String oldValueStr = getSet(lockKey, expiresStr);

                if (oldValueStr != null && oldValueStr.equals(currentValueStr)){//防止（分布式情况下）覆盖了在这段时间内有其他线程设置了值（概率很低）
                    locked = true;
                    return true;
                }
            }
        }
        timeout -= DEFAULT_ACQUIRY_RESOLUTION_MILLIS;
        Thread.sleep(DEFAULT_ACQUIRY_RESOLUTION_MILLIS);
        return false;
    }

    public synchronized void unlock(){
        if (locked){
            redisTemplate.delete(lockKey);
            locked = false;
        }
    }
}
