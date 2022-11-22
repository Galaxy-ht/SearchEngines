package com.bigdata.searchengineapp.controller;

import com.bigdata.searchengineapp.entity.Test;
import com.bigdata.searchengineapp.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tao
 * @since 2022/11/22
 */
@RestController
public class TestController {
    @Autowired
    TestService testService;

    @GetMapping("/")
    public String Test() {
        Test test = new Test(1, "test");
        testService.save(test);
        return "Hello NoSQL";
    }
}
