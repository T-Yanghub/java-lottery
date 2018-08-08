package com.itheima.thymeleaf.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

/**
 * ClassName:User
 * Description:
 */
@Entity
@Data
public class User {
    //用户的编号
    @Id
    private String uid;
    //邮箱
    @Column(unique =true)
    private String email;
    //用户密码
    private String password;
    //用户名
    private String username;
    //手机号码
    private String mobile;
    //用户的头像
    private String headimg;
    //用户的状态:0 未激活, 1,已激活, 2: 已封号
    private Integer state;
    // 账户余额
    private double money;
}
