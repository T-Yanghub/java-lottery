package com.itheima.thymeleaf.service;

import com.itheima.thymeleaf.bean.BallHistory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * ClassName:IndexService
 * Description:
 */
public interface IndexService {

    public Page<BallHistory> findAll(Pageable pageable);
    public BallHistory detail(String code);
}
