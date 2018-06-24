package com.itheima.thymeleaf.dao;

import com.itheima.thymeleaf.bean.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ClassName:UserDao
 * Description:
 */
@Repository
public interface UserDao extends JpaRepository<User,String> {

    public User findByEmailAndPassword(String email,String password);
}
