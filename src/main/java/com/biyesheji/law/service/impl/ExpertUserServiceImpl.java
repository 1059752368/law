package com.biyesheji.law.service.impl;

import com.biyesheji.law.pojo.ExpertUser;
import com.biyesheji.law.pojo.User;
import com.biyesheji.law.repository.ExpertUserRepository;
import com.biyesheji.law.service.ExpertUserService;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<ExpertUser> findAllExperts(int status) {
        List<ExpertUser> expertsList = expertUserRepository.findAllByStatus(status);
        return expertsList;
    }

    @Override
    public List<ExpertUser> findAllCertificationUser(int status) {
        return expertUserRepository.findAllByStatus(2);
    }

    @Override
    public int setExperts(int id) {
        ExpertUser expertUser = expertUserRepository.findById(id).orElse(null);
        if (expertUser != null) {
            expertUser.setStatus(1);
            expertUserRepository.save(expertUser);
            return 1;
        }
        return 0;
    }
}
