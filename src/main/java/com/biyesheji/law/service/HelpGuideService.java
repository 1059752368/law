package com.biyesheji.law.service;

import com.biyesheji.law.pojo.HelpGuide;

public interface HelpGuideService {
    void addQuestion(HelpGuide helpguide);//增加问题以及答案
    void delete(int HelpguideId);//删除
    void updateQuestion(HelpGuide helpguide);//修改
}
