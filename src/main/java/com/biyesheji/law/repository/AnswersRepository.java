package com.biyesheji.law.repository;

import com.biyesheji.law.pojo.Answers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswersRepository extends JpaRepository<Answers,Integer> {

}
