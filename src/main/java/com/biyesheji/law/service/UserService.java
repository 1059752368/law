package com.biyesheji.law.service;

import com.biyesheji.law.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> list();
    User addUser(User user);
    User findUserByLoginName(String loginName);
    Map<String, Integer> login(String loginName, String password);
}
