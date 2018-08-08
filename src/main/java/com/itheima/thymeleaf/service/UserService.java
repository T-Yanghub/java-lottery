package com.itheima.thymeleaf.service;

import com.itheima.thymeleaf.bean.User;

/**
 * ClassName:UserService
 * Description:
 */
public interface UserService {
    public void save_user(User user);

   public  User findOne(String email,String password);

   public  void  saveWithOutPassword(User user);


}
