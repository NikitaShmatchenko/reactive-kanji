package org.reactive.kanji.service.impl.search;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.reactive.kanji.service.api.search.DictionaryInfoSearchService;
import org.reactive.kanji.service.api.search.client.SearchOptions;
import org.reactive.kanji.service.model.search.DictionaryInfo;
import org.reactive.kanji.service.model.search.request.DictionaryInfoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DictionaryInfoSearchServiceImpl.class})
public class DictionaryInfoServiceTest {

    private static final String KANJI_CHARACTER = "火";

    @Autowired
    private DictionaryInfoSearchService dictionaryInfoSearchService;
    @MockBean
    private SearchOptions searchOptions;

    @Test
    public void searchKanji() {
        String generatedString = UUID.randomUUID().toString();

        DictionaryInfoRequest.builder()
                .kanji(KANJI_CHARACTER)
                .build();

        DictionaryInfo dictionaryInfo = new DictionaryInfo(1,
                generatedString,
                List.of(generatedString),
                1,
                1,
                List.of(generatedString),
                List.of(generatedString),
                List.of(generatedString));

        when(searchOptions.search(any(DictionaryInfoRequest.class)))
                .thenReturn(Mono.just(dictionaryInfo));

        StepVerifier.create(dictionaryInfoSearchService.searchKanji(KANJI_CHARACTER))
                .expectNext(dictionaryInfo)
                .verifyComplete();

        verify(searchOptions).search(any(DictionaryInfoRequest.class));
    }

    @Test
    public void searchKanjiFailed() {
        DictionaryInfoRequest.builder()
                .kanji(KANJI_CHARACTER)
                .build();

        when(searchOptions.search(any(DictionaryInfoRequest.class)))
                .thenThrow(new RuntimeException());

        StepVerifier.create(dictionaryInfoSearchService.searchKanji(KANJI_CHARACTER))
                .verifyComplete();

        verify(searchOptions).search(any(DictionaryInfoRequest.class));
    }
}