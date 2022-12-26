package com.bigdata.searchengineapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchHistoryController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/search-history")
    public List<String> getSearchHistory(){
        return redisTemplate.opsForList().range("searchHistory", 0, 4);
    }
}
