package com.zihexin.mysql;

import com.zihexin.SpringBootDemoApplication;
import com.zihexin.config.JpaConfiguration;
import com.zihexin.mysql.dao.DepartmentRepository;
import com.zihexin.mysql.dao.RoleRepository;
import com.zihexin.mysql.dao.UserRepository;
import com.zihexin.mysql.entity.Department;
import com.zihexin.mysql.entity.Role;
import com.zihexin.mysql.entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/7/3.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootDemoApplication.class)
public class MysqlTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    RoleRepository roleRepository;

    @Before
    public void initData(){

        userRepository.deleteAll();
        departmentRepository.deleteAll();
        roleRepository.deleteAll();

        Department department = new Department();
        department.setName("在线发展部");
        departmentRepository.save(department);
        Assert.assertNotNull(department.getId());

        Role role = new Role();
        role.setName("admin");
        roleRepository.save(role);
        Assert.assertNotNull(role.getId());

        User user = new User();
        user.setName("ggq");
        user.setCreatedate(new Date());
        user.setDepartment(department);
        ArrayList<Role> list = new ArrayList<>();
        list.add(role);
        user.setRoles(list);

        userRepository.save(user);
        Assert.assertNotNull(user.getId());
    }

    @Test
    public void findPage(){
//        List<User> all = userRepository.findAll();
//        System.out.println(all);
        Pageable pageable = new PageRequest(0,10,new Sort(Sort.Direction.ASC,"id"));
        Page<User> page = userRepository.findAll(pageable);
        Assert.assertNotNull(page);

        for (User user : page){
            System.out.println(user);
        }
    }
}
