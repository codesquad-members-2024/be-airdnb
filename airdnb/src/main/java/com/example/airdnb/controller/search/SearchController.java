package com.example.airdnb.controller.search;

import com.example.airdnb.domain.search.SearchCondition;
import com.example.airdnb.dto.search.SearchConditionRequest;
import com.example.airdnb.service.search.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {
    private final SearchService searchService;

    @GetMapping
    public void search(@RequestBody SearchConditionRequest searchConditionRequest) {
        SearchCondition searchCondition = searchConditionRequest.toEntity();

        log.info(searchCondition.toString());
    }
}
