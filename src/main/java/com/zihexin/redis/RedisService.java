package com.zihexin.redis;

import com.zihexin.domain.User;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2018/6/20.
 */
@Service
public class RedisService {

    private Set<User> users = new HashSet<>();


    @CachePut(value = "user",key = "'User:'+#user.id")
    public User addUser(User user){
        users.add(user);
        return user;
    }

    @Cacheable(value = "user",key = "'User:'+#id")
    public User addUser(Integer id,String name){
        User user= new User(id,name);
        users.add(user);
        return user;
    }
}
