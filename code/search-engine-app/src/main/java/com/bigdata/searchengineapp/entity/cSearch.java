package com.bigdata.searchengineapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class cSearch {
    private String id;
    private String titlecompletion;
    private String title;
    private String content;
    private String url;
}
