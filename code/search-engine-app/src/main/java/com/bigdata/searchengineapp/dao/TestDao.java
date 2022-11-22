package com.bigdata.searchengineapp.dao;

import com.bigdata.searchengineapp.entity.Test;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Tao
 * @since 2022/11/22
 */
@Component
public class TestDao {
    @Resource
    private MongoTemplate mongoTemplate;

    public void save(Test test) {
        mongoTemplate.save(test);
    }
}
