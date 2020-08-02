package org.reactive.kanji.service.impl.search;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactive.kanji.service.api.search.DictionaryInfoSearchService;
import org.reactive.kanji.service.api.search.client.SearchOptions;
import org.reactive.kanji.service.model.search.DictionaryInfo;
import org.reactive.kanji.service.model.search.request.DictionaryInfoRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class DictionaryInfoSearchServiceImpl implements DictionaryInfoSearchService {

    private final SearchOptions searchOptions;

    public Mono<DictionaryInfo> searchKanji(String kanji) {
        return searchOptions.search(DictionaryInfoRequest.builder()
                .kanji(kanji)
                .build());
    }
}
