package com.biyesheji.law.service.impl;

import com.biyesheji.law.pojo.ExpertUser;
import com.biyesheji.law.repository.ExpertUserRepository;
import com.biyesheji.law.service.ExpertUserService;
import org.springframework.stereotype.Service;

@Service
public class ExpertUserServiceImpl implements ExpertUserService {
    private final ExpertUserRepository expertUserRepository;

    public ExpertUserServiceImpl(ExpertUserRepository expertUserRepository) {
        this.expertUserRepository = expertUserRepository;
    }

    @Override
    public ExpertUser addExpertUser(ExpertUser expertUser) {
        return expertUserRepository.save(expertUser);
    }

    @Override
    public ExpertUser findOneExpertUser(int id) {
        return expertUserRepository.findById(id).orElse(null);
    }
}
