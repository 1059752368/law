package com.biyesheji.law.repository;

import com.biyesheji.law.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findUserByLoginName(String loginName);

    User findUserByLoginNameAndPwd(String loginName, String pwd);
}
