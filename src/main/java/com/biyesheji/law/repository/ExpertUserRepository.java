package com.biyesheji.law.repository;

import com.biyesheji.law.pojo.ExpertUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpertUserRepository extends JpaRepository<ExpertUser,Integer> {
    List<ExpertUser> findAllByStatus(int status);
}
