package com.biyesheji.law.service.impl;

import com.biyesheji.law.pojo.HelpGuide;
import com.biyesheji.law.repository.HelpGuideRepository;
import com.biyesheji.law.service.HelpGuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelpGuideServiceImpl implements HelpGuideService {
    private final HelpGuideRepository helpguideRepository;

    @Autowired
    public HelpGuideServiceImpl(HelpGuideRepository helpguideRepository) {
        this.helpguideRepository = helpguideRepository;
    }


    @Override
    public void addQuestion(HelpGuide helpguide) {
        helpguideRepository.save(helpguide);
    }

    @Override
    public void delete(int HelpguideId) {
        helpguideRepository.deleteById(HelpguideId);
    }

    @Override
    public void updateQuestion(HelpGuide helpguide) {
        helpguideRepository.save(helpguide);
    }
}
