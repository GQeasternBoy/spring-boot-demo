package com.zihexin.rest;

import com.zihexin.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by Administrator on 2018/6/22.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    static Map<Integer,User> userMap = Collections.synchronizedMap(new HashMap<>());

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public List<User> getUserList(){
        List<User> userList = new ArrayList<>(userMap.values());
        return userList;
    }

    @RequestMapping(value = "/",method = RequestMethod.POST)
    public String saveUser(@ModelAttribute User user){
        if (StringUtils.isEmpty(String.valueOf(user.getId())) || StringUtils.isBlank(user.getName())){
            return "faild";
        }
        userMap.put(user.getId(),user);
        return "success";
    }


}
