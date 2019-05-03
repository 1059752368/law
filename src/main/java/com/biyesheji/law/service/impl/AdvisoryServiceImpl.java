package com.biyesheji.law.service.impl;

import com.biyesheji.law.pojo.Advisory;
import com.biyesheji.law.pojo.ExpertUser;
import com.biyesheji.law.pojo.User;
import com.biyesheji.law.repository.AdvisoryRepository;
import com.biyesheji.law.repository.ExpertUserRepository;
import com.biyesheji.law.repository.UserRepository;
import com.biyesheji.law.service.AdvisoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdvisoryServiceImpl implements AdvisoryService {
    private  final AdvisoryRepository advisoryRepository;
    private  final ExpertUserRepository expertUserRepository;
    private  final UserRepository userRepository;

    public AdvisoryServiceImpl(AdvisoryRepository advisoryRepository, ExpertUserRepository expertUserRepository, UserRepository userRepository) {
        this.advisoryRepository = advisoryRepository;
        this.expertUserRepository = expertUserRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Advisory addAdvisory(int userId, int expertUserId, String content) {
        Advisory advisory = new Advisory();
        advisory.setExpertUserId(expertUserId);
        advisory.setUserId(userId);
        advisory.setContent(content);
        advisoryRepository.save(advisory);
        return advisory;
    }

    @Override
    public List<Advisory> historyAdvisories(User user, ExpertUser expertUser) {

        return  advisoryRepository.findAllByUserIdAndExpertUserIdOrderByReplyTime(user.getId(),expertUser.getId());
    }
}
