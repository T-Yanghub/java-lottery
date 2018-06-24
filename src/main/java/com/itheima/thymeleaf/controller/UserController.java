package com.itheima.thymeleaf.controller;

import com.itheima.thymeleaf.Util.IdUtils;
import com.itheima.thymeleaf.bean.User;
import com.itheima.thymeleaf.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * ClassName:UserController
 * Description:
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //去注册页面
    @GetMapping("/to_register")
    public String to_register() {

        return "register";
    }

    //注册
    @PostMapping("/do_register")
    public String do_register(User user) {
        System.out.println("hhh");
        user.setMoney(100);
        user.setState(1);
        user.setHeadimg("/static/img/1.png");
        user.setUid(IdUtils.getId());

         userService.save_user(user);
        return "redirect:to_login";
    }
    @GetMapping("/to_login")
    public String to_login(){

        return "login";
    }
    @PostMapping("/do_login")
    public String do_login(String email, String password, HttpSession session, Model model){
        User user = userService.findOne(email, password);
        if (user!=null){
            session.setAttribute("user",user);
            return "redirect:/";
        }
model.addAttribute("msg","密码或邮箱错误");
        return "login";
    }


}
