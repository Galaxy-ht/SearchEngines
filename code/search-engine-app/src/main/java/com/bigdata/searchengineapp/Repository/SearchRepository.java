package com.bigdata.searchengineapp.Repository;

import com.bigdata.searchengineapp.entity.Search;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SearchRepository extends ElasticsearchRepository<Search, String> {
    Page<Search> findByTitle(String title, Pageable pageable);
}
