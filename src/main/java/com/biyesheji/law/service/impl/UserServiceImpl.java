package com.biyesheji.law.service.impl;

import com.biyesheji.law.repository.UserRepository;
import com.biyesheji.law.pojo.User;
import com.biyesheji.law.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> list(){
        return userRepository.findAll();
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findUserByLoginName(String loginName) {
        return userRepository.findUserByLoginName(loginName);
    }

    @Override
    public Map<String, Integer> login(String loginName, String password) {

        Map<String, Integer> map = new HashMap<>();
        User userByLoginName = userRepository.findUserByLoginName(loginName);
        if (userByLoginName == null) {
            map.put("status", 0);
            return map;
        }
        User userByLoginNameAndPwd = userRepository.findUserByLoginNameAndPwd(loginName, password);
        if (userByLoginNameAndPwd == null) {
            map.put("status", 1);
            return map;
        }
        map.put("status", 2);
        map.put("userId", userByLoginNameAndPwd.getId());
        map.put("userType", userByLoginNameAndPwd.getType());
        return map;
    }

}
