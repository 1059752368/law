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

    public List<User> list() {
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
    public Map<String, String> login(String loginName, String password) {

        Map<String, String> map = new HashMap<>();
        User userByLoginName = userRepository.findUserByLoginName(loginName);
        if (userByLoginName == null) {
            map.put("status", "" + 0);
            return map;
        }
        User userByLoginNameAndPwd = userRepository.findUserByLoginNameAndPwd(loginName, password);
        if (userByLoginNameAndPwd == null) {
            map.put("status", "" + 1);
            return map;
        }
        map.put("status", "" + 2);
        map.put("userId", "" + userByLoginNameAndPwd.getId());
        map.put("userType", "" + userByLoginNameAndPwd.getType());
        map.put("userPhoto", userByLoginNameAndPwd.getPhotoId());
        return map;
    }

    @Override
    public User findUserById(int id) {
        try {
            return userRepository.findById(id).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }


}
