package com.itheima.thymeleaf.dao;

import com.itheima.thymeleaf.bean.BallHistory;
import com.itheima.thymeleaf.bean.Card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ClassName:IndexDao
 * Description:
 */
@Repository
public interface IndexDao extends JpaRepository<BallHistory,String> {

}
