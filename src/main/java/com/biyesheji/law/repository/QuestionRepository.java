package com.biyesheji.law.repository;

import com.biyesheji.law.pojo.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Integer> {
    @Query(value = "select * from question order by rand() limit 10 ", nativeQuery = true)
    List<Question> findTenRandQues();

    //Question findQuestionById(Integer Id);
}
