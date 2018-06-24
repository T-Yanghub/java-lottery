package com.itheima.thymeleaf.Util;

import java.util.UUID;

/**
 * ClassName:IdUtils
 * Description:
 */
public class IdUtils {

    public  static String getId(){
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        return id;
    }
}
