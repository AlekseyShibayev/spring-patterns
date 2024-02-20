package com.company.app.wildberries.search.domain.service;

import java.util.List;

import com.company.app.wildberries.search.domain.entity.SearchData;
import com.company.app.wildberries.search.domain.repository.SearchDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SearchDataService {

    private final SearchDataRepository searchDataRepository;

    public void saveAll(List<SearchData> list) {
        searchDataRepository.saveAll(list);
    }

    public SearchData getSearchData(String chatName) {
        return searchDataRepository.findByChatName(chatName);
    }

}
