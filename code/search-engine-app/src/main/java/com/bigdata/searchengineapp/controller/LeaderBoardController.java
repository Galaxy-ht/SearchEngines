package com.bigdata.searchengineapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class LeaderBoardController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/leader-bar")
    public Set<ZSetOperations.TypedTuple<String>> getLeaderBar(){
        return redisTemplate.opsForZSet().reverseRangeWithScores("hotBar", 0, 9);
    }
}
