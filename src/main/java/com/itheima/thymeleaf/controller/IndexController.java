package com.itheima.thymeleaf.controller;

import com.itheima.thymeleaf.bean.BallHistory;
import com.itheima.thymeleaf.service.IndexService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * ClassName:TestController
 * Description:
 */
@Controller
public class IndexController {
    @Autowired
    private IndexService indexService;



    @GetMapping("show_index")
    public String index(Model model ,int i) {

        System.out.println(i);
        Sort sort = new Sort(Sort.Direction.DESC, "code");
        Pageable pageable  = new PageRequest(i,5,sort);
        Page<BallHistory> histories = indexService.findAll(pageable);

        model.addAttribute("histories",histories);
        return "index";
    }
    @GetMapping("/")
    public String firstTime_index(Model model ) {


        Sort sort = new Sort(Sort.Direction.DESC, "code");
        Pageable pageable  = new PageRequest(0,5,sort);
        Page<BallHistory> histories = indexService.findAll(pageable);

        model.addAttribute("histories",histories);
        return "index";
    }
@GetMapping("to_detail")
    public String detail(String code ,Model model){
        BallHistory history = indexService.detail(code);
        model.addAttribute("history",history);
        return "detail";
    }
}
