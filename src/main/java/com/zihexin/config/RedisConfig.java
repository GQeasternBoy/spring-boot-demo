package com.zihexin.config;


import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/6/20.
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport{

    /**
     * 注解@Cache key生成规则
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for(Object obj : params){
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

//    @Override
//    public CacheManager cacheManager() {
//        return super.cacheManager();
//    }

    @Bean
    public RedisTemplate<?,?> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<?,?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        //key序列化方式，方法上有非string类型，会报类型转换错误
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);

        JdkSerializationRedisSerializer jdkReisSerializer = new JdkSerializationRedisSerializer();
        redisTemplate.setValueSerializer(jdkReisSerializer);
        redisTemplate.setHashValueSerializer(jdkReisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return super.errorHandler();
    }
}
