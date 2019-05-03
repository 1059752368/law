package com.biyesheji.law.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "advisory")
public class Advisory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private Integer userId;
    private Integer expertUserId;
    private String content;

    @Basic(optional = false)
    @Column(name = "reply_time", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date replyTime;

    @Transient
    private User user;
    @Transient
    private ExpertUser expertUser;
}
