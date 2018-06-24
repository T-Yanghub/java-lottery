package com.itheima.thymeleaf.service.impl;

import com.itheima.thymeleaf.bean.BallHistory;
import com.itheima.thymeleaf.dao.IndexDao;
import com.itheima.thymeleaf.service.IndexService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * ClassName:IndexServiceImpl
 * Description:
 */
@Service
public class IndexServiceImpl implements IndexService {

@Autowired
private IndexDao indexDao;

public Page<BallHistory> findAll(Pageable pageable){
    /*List<BallHistory> histories = indexDao.findAll(sort);*/
    Page<BallHistory> page = indexDao.findAll(pageable);
    return  page;
}
public BallHistory detail(String code){
    BallHistory history = indexDao.findOne(code);
    return history;
}
}
