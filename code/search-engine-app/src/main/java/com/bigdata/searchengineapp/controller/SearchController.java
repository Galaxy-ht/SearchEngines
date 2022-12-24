package com.bigdata.searchengineapp.controller;


import com.alibaba.fastjson.JSONObject;
import com.bigdata.searchengineapp.entity.Search;
import com.bigdata.searchengineapp.entity.cSearch;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SearchController {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @GetMapping("/search")
    public JSONObject getSearchResults(@RequestParam String keyword,
                                         @RequestParam(defaultValue = "0") Integer page,
                                         @RequestParam(defaultValue = "10") Integer size
                                         ) {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        HighlightBuilder.Field field1 = new HighlightBuilder.Field("title").preTags("<font style='color:red'>").postTags("</font>");
        HighlightBuilder.Field field2 = new HighlightBuilder.Field("content").preTags("<font style='color:red'>").postTags("</font>");
        boolQueryBuilder.should(QueryBuilders.matchQuery("title", keyword));
        boolQueryBuilder.should(QueryBuilders.matchQuery("content", keyword));

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withHighlightFields(field1, field2);
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);


        PageRequest pageRequest = PageRequest.of(page, size);
        nativeSearchQueryBuilder.withPageable(pageRequest);
        NativeSearchQuery nativeSearchQuery = nativeSearchQueryBuilder.build();
        SearchHits<cSearch> searchHits = elasticsearchRestTemplate.search(nativeSearchQuery, cSearch.class, IndexCoordinates.of("search"));
        long count = elasticsearchRestTemplate.count(nativeSearchQuery, cSearch.class, IndexCoordinates.of("search"));
        List<cSearch> searchMatches = new ArrayList<>();
        searchHits.forEach(searchHit->{
            cSearch content = searchHit.getContent();
            List<String> hTitle = searchHit.getHighlightField("title");
            List<String> hContent = searchHit.getHighlightField("content");
            if(hTitle.size() > 0) content.setTitle(hTitle.get(0));
            if(hContent.size() > 0) content.setContent(hContent.get(0));
            searchMatches.add(content);
        });
        JSONObject data = new JSONObject();
        data.put("data", searchMatches);
        data.put("total", count);
        return data;
    }

    @GetMapping("/search-suggest")
    public List<Map<String, String>> suggest(@RequestParam String prefix){
        CompletionSuggestionBuilder completionSuggestionBuilder = new CompletionSuggestionBuilder("titlecompletion")
                .prefix(prefix)
                .skipDuplicates(true)
                .size(100);
        SuggestBuilder suggestBuilder = new SuggestBuilder()
                .addSuggestion("title_suggest", completionSuggestionBuilder);
        Suggest suggest = elasticsearchRestTemplate.suggest(suggestBuilder, Search.class).getSuggest();
        CompletionSuggestion suggest_text = suggest.getSuggestion("title_suggest");

        List<Map<String, String>> res = new ArrayList<>();
        for (CompletionSuggestion.Entry entry : suggest_text.getEntries()) {
            for (CompletionSuggestion.Entry.Option option : entry.getOptions()) {
                Map<String, String> data = new HashMap<>();
                data.put("value", option.getText().toString());
                res.add(data);
            }
        }
        return res;
    }
}
