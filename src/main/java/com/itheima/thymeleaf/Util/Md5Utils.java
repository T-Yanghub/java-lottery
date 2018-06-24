package com.itheima.thymeleaf.Util;

import org.springframework.util.DigestUtils;

/**
 * ClassName:Md5Utils
 * Description:
 */
public class Md5Utils {
    public static String encodePwd(String pwd){

            pwd = DigestUtils.md5DigestAsHex(pwd.getBytes());

        return pwd;
    }
}
