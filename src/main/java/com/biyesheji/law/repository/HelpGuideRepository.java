package com.biyesheji.law.repository;

import com.biyesheji.law.pojo.HelpGuide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HelpGuideRepository extends JpaRepository<HelpGuide,Integer> {

}
