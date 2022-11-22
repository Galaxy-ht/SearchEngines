package com.bigdata.searchengineapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Tao
 * @since 2022/11/22
 */

@Data
@AllArgsConstructor
@Document("test")
public class Test {
    @Id
    private Integer id;
    private String content;
}
