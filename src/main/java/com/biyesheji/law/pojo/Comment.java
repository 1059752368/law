package com.biyesheji.law.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.jws.soap.SOAPBinding;
import javax.persistence.*;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "comment_")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer questionId;
    private Integer userId;
    private String content;
    @Basic(optional = false)
    @Column(name = "reply_time", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date replyTime;

    @Transient
    private User user;
}
