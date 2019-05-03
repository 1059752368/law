package com.biyesheji.law.service;

import com.biyesheji.law.pojo.ExpertUser;

public interface ExpertUserService {
    ExpertUser addExpertUser(ExpertUser expertUser);
    ExpertUser findOneExpertUser(int id);
}
