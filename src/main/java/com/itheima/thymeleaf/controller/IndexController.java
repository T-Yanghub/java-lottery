package com.itheima.thymeleaf.controller;

import com.itheima.thymeleaf.bean.BallHistory;
import com.itheima.thymeleaf.bean.Card;
import com.itheima.thymeleaf.service.CardService;
import com.itheima.thymeleaf.service.IndexService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ClassName:TestController
 * Description:
 */
@Controller
public class IndexController {
    @Autowired
    private IndexService indexService;


    @Autowired
    private CardService cardService;



   /* @GetMapping("show_index")
    public String index(Model model ,int i) {

        System.out.println(i);
        Sort sort = new Sort(Sort.Direction.DESC, "code");
        Pageable pageable  = new PageRequest(i,5,sort);
        Page<BallHistory> histories = indexService.findAll(pageable);

        model.addAttribute("histories",histories);

        *//*获取最新的帖子标题*//*
        Card first = cardService.findTopByOrderByTimeDesc();
        String title="还没有帖子哦";
        if (first!=null){
            title = first.getTitle();
        }
        model.addAttribute("title",title);
        System.out.println("title="+title);
        return "index";
    }*/
    @GetMapping("/")
    public String firstTime_index(@RequestParam(defaultValue ="0")int page, Model model ) {


        Sort sort = new Sort(Sort.Direction.DESC, "code");
        Pageable pageable  = new PageRequest(page,10,sort);
        Page<BallHistory> histories = indexService.findAll(pageable);

        System.out.println(histories.getTotalPages());

        model.addAttribute("histories",histories);
       // model.addAttribute("page",page);
        Card first = cardService.findTopByOrderByTimeDesc();
        String title="还没有帖子哦";
        if (first!=null){
            title = first.getTitle();
        }
        model.addAttribute("title",title);
        System.out.println("title="+title);
        return "index";
    }
@GetMapping("to_detail")
    public String detail(String code ,Model model){
        BallHistory history = indexService.detail(code);
        model.addAttribute("history",history);
        return "detail";
    }
}
