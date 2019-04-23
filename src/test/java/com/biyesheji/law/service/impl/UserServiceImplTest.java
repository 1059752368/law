package com.biyesheji.law.service.impl;

import com.biyesheji.law.pojo.User;
import com.biyesheji.law.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Test
    public void list() {
        System.out.println(userService.list());
    }

    @Test
    public void findUserByLoginName() {
        User user = userService.findUserByLoginName("123456");
        System.out.println(user);
    }
}