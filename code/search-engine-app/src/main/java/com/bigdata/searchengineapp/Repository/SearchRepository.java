package com.bigdata.searchengineapp.Repository;

import com.bigdata.searchengineapp.entity.Search;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.annotations.HighlightParameters;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface SearchRepository extends ElasticsearchRepository<Search, String> {
    @Highlight(
            fields = {
                    @HighlightField(name = "title")
            },
            parameters = @HighlightParameters(
                    preTags = "<strong><font style='color:red'>",
                    postTags = "</font></strong>",
                    fragmentSize = 500,
                    numberOfFragments = 3
            )
    )
    Page<Search> findByTitle(String title, Pageable pageable);
}
