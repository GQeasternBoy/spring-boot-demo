package com.zihexin.mail;

import com.zihexin.SpringBootDemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Administrator on 2018/6/15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceImplTest {

    @Autowired
    MailServiceImpl mailService;

    @Test
    public void sendSimpleMailTest(){
            mailService.sendSimpleMail("945971254@qq.com","spring boot mail","this is my spring boot mail test");
    }
}
