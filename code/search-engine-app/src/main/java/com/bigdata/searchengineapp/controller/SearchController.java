package com.bigdata.searchengineapp.controller;

import com.bigdata.searchengineapp.Repository.SearchRepository;
import com.bigdata.searchengineapp.entity.Search;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class SearchController {

    @Autowired
    private SearchRepository searchRepository;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;

    @GetMapping("/search")
    public List<Search> getSearchResults(@RequestParam String title,
                                         @RequestParam(defaultValue = "0") Integer page,
                                         @RequestParam(defaultValue = "10") Integer size
                                         ) throws IOException {
        Pageable pageable = PageRequest.of(page, size);
        Page<Search> pageData = searchRepository.findByTitle(title, pageable);
        return pageData.getContent();
    }


}
