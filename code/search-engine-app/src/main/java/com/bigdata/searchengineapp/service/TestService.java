package com.bigdata.searchengineapp.service;

import com.bigdata.searchengineapp.entity.Test;
import org.springframework.stereotype.Service;

/**
 * @author Tao
 * @since 2022/11/22
 */
@Service
public interface TestService {
    public void save(Test test);
}
