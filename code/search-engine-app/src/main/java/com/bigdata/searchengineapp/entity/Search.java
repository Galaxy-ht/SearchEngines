package com.bigdata.searchengineapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Document(indexName = "search")
public class Search {
    @Id
    private String id;
    private String title;
    private String content;
    private String url;
}
