package com.biyesheji.law.service;

import com.biyesheji.law.pojo.Advisory;
import com.biyesheji.law.pojo.ExpertUser;
import com.biyesheji.law.pojo.User;

import java.util.List;

public interface AdvisoryService {
    Advisory addAdvisory(int userId, int ExpertId,String content);
    List<Advisory> historyAdvisories(User user,ExpertUser expertUser);
}
