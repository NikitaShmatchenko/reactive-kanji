package org.reactive.kanji.handler.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.reactive.kanji.KanjiRouter;
import org.reactive.kanji.service.api.KanjiService;
import org.reactive.kanji.service.api.search.DictionaryInfoSearchService;
import org.reactive.kanji.service.model.Kanji;
import org.reactive.kanji.service.model.search.DictionaryInfo;
import org.reactive.kanji.service.model.validator.KanjiValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {KanjiHandlerImpl.class, KanjiRouter.class, KanjiValidator.class})
@WebFluxTest
public class KanjiHandlerTest {

    private static final String KANJI_CHARACTER = "火";
    private static final String KANJI_ROUTE = "/kanji";

    @Autowired
    private ApplicationContext context;
    @MockBean
    private KanjiService kanjiService;
    @MockBean
    private DictionaryInfoSearchService dictionaryInfoSearchService;

    private WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        webTestClient = WebTestClient.bindToApplicationContext(context).build();
    }

    @Test
    public void saveKanji() {
        String generatedString = UUID.randomUUID().toString();

        Kanji kanji = Kanji.builder()
                .id(1L)
                .character(KANJI_CHARACTER)
                .meaning(generatedString)
                .story(generatedString)
                .build();

        when(kanjiService.save(any(Mono.class)))
                .thenReturn(Mono.just(kanji));

        webTestClient.post()
                .uri(KANJI_ROUTE)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(kanji), Kanji.class)
                .exchange()
                .expectStatus().isOk();

        verify(kanjiService).save(any(Mono.class));
    }

    @Test
    public void getAllKanji() {
        String generatedString = UUID.randomUUID().toString();

        Kanji kanji = Kanji.builder()
                .id(1L)
                .character(KANJI_CHARACTER)
                .meaning(generatedString)
                .story(generatedString)
                .build();

        when(kanjiService.getAllKanjis())
                .thenReturn(Flux.just(kanji));

        webTestClient.get()
                .uri(KANJI_ROUTE)
                .exchange()
                .expectStatus().isOk();

        verify(kanjiService).getAllKanjis();
    }

    @Test
    public void getKanji() {
        String generatedString = UUID.randomUUID().toString();

        Kanji kanji = Kanji.builder()
                .id(1L)
                .character(KANJI_CHARACTER)
                .meaning(generatedString)
                .story(generatedString)
                .build();

        DictionaryInfo dictionaryInfo = new DictionaryInfo();

        when(kanjiService.getKanjiByCharacter(anyString()))
                .thenReturn(Mono.just(kanji));
        when(dictionaryInfoSearchService.searchKanji(anyString()))
                .thenReturn(Mono.just(dictionaryInfo));

        webTestClient.get()
                .uri(KANJI_ROUTE + "/" + KANJI_CHARACTER)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();

        verify(kanjiService).getKanjiByCharacter(anyString());
        verify(dictionaryInfoSearchService).searchKanji(anyString());
    }
}
