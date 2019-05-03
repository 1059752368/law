package com.biyesheji.law.repository;

import com.biyesheji.law.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByLoginName(String loginName);
//    User findUserById(int id);
    User findUserByLoginNameAndPwd(String loginName, String pwd);

    List<User> findAllByType(int type);
}
