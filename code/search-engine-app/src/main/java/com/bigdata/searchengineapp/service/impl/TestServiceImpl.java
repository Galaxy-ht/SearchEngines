package com.bigdata.searchengineapp.service.impl;

import com.bigdata.searchengineapp.dao.TestDao;
import com.bigdata.searchengineapp.entity.Test;
import com.bigdata.searchengineapp.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Tao
 * @since 2022/11/22
 */
@Service
public class TestServiceImpl implements TestService {
    @Autowired
    TestDao testDao;

    @Override
    public void save(Test test) {
        testDao.save(test);
    }
}
