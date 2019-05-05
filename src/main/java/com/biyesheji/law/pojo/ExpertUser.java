package com.biyesheji.law.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//全参无参构造器
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "expert_user")
public class ExpertUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String realName;
    private String phoneNumber;
    private String certificationInformation;
    private Integer userId;
    private Integer status;
}
