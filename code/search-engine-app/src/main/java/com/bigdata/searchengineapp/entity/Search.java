package com.bigdata.searchengineapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.CompletionField;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.suggest.Completion;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Document(indexName = "search")
public class Search {
    @Id
    private String id;
    @CompletionField(analyzer = "completion_analyzer")
    private Completion titlecompletion;
    @Field(analyzer = "text_anlyzer", searchAnalyzer = "ik_smart", type = FieldType.Text)
    private String title;
    @Field(analyzer = "text_anlyzer", searchAnalyzer = "ik_smart", type = FieldType.Text)
    private String content;
    @Field(type = FieldType.Text)
    private String url;
}
