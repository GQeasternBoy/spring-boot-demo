package com.zihexin.redis.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zihexin.mysql.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/7/4.
 */
@Repository
public class UserRedis {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public void add(String key, Long time, User user){
        Gson gson = new Gson();
        redisTemplate.opsForValue().set(key,gson.toJson(user),time, TimeUnit.MINUTES);
    }

    public void  add(String key, Long time, List<User> users){
        Gson gson = new Gson();
        redisTemplate.opsForValue().set(key,gson.toJson(users),time,TimeUnit.MINUTES);
    }

    public User get(String key){
        User user = null;
        String userJson = redisTemplate.opsForValue().get(key);
        if(StringUtils.isNotEmpty(userJson)){
            Gson gson = new Gson();
            user = gson.fromJson(userJson,User.class);
        }
        return user;
    }

    public List<User> getList(String key){
        List<User> users = null;
        String listJson = redisTemplate.opsForValue().get(key);
        if(StringUtils.isNotEmpty(listJson)){
            Gson gson = new Gson();
            users = gson.fromJson(listJson, new TypeToken<List<User>>() {
            }.getType());
        }
        return users;
    }

    public void delete(String key){
        redisTemplate.opsForValue().getOperations().delete(key);
    }

    public static void main(String[] args) {
        Gson gson = new Gson();
        int i = gson.fromJson("100",int.class);
        double d = gson.fromJson("99.99",double.class);
        double c = gson.fromJson("\"99.99\"",double.class);
        System.out.println("i:"+i+",d:"+d+",c:"+c);
    }
}
