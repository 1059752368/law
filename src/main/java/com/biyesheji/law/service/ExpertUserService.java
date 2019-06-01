package com.biyesheji.law.service;

import com.biyesheji.law.pojo.ExpertUser;
import com.biyesheji.law.pojo.User;

import java.util.List;

public interface ExpertUserService {
    ExpertUser addExpertUser(ExpertUser expertUser);
    ExpertUser findOneExpertUser(int id);
    List<ExpertUser> findAllExperts(int status);
    List<ExpertUser> findAllCertificationUser(int status);
    int setExperts(int id);

//    List<User> aboutMe(int expertId);
}
