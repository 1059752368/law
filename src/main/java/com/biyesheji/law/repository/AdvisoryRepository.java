package com.biyesheji.law.repository;

import com.biyesheji.law.pojo.Advisory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvisoryRepository extends JpaRepository <Advisory,Integer>{
    List<Advisory> findAllByUserIdAndExpertUserIdOrderByReplyTime(int userId,int expertUserId);


//    @Query(value = "select distinct user_id from advisory order by rand() limit 10 ", nativeQuery = true)
//    List<Question> findTenRandQues();
}
