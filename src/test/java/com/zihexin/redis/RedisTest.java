package com.zihexin.redis;

import com.zihexin.mysql.entity.Department;
import com.zihexin.mysql.entity.Role;
import com.zihexin.mysql.entity.User;
import com.zihexin.redis.repository.UserRedis;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2018/7/5.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    UserRedis userRedis;

    @Before
    public void addRedis(){
        Department department = new Department();
        department.setName("在线发展");
        department.setCreatedate(new Date());

        Role role = new Role();
        role.setName("admin");

        User user = new User();
        user.setId(123456L);
        user.setName("ggq");
        user.setCreatedate(new Date());
        user.setDepartment(department);
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);

        userRedis.add(String.valueOf(user.getId()),1L,user);
    }

    @Test
    public void getRedis(){
        User user = userRedis.get("123456");
        Assert.assertNotNull(user);
        System.out.println(user);
    }

    @Test
    public void delRedis(){
        userRedis.delete("123456");
    }
}
