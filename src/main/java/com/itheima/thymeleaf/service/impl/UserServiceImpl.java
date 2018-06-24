package com.itheima.thymeleaf.service.impl;

import com.itheima.thymeleaf.Util.Md5Utils;
import com.itheima.thymeleaf.bean.User;
import com.itheima.thymeleaf.dao.UserDao;
import com.itheima.thymeleaf.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName:UserServiceImpl
 * Description:
 */
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDao userDao;

    @Override
    public void save_user(User user) {
        String password = Md5Utils.encodePwd(user.getPassword());
        user.setPassword(password);
        userDao.save(user);
    }

    @Override
    public User findOne(String email, String password) {
            password = Md5Utils.encodePwd(password);
        User user = userDao.findByEmailAndPassword(email, password);
        return user;
    }
}
