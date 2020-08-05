package org.reactive.kanji.handler.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.reactive.kanji.KanjiRouter;
import org.reactive.kanji.service.api.KanjiService;
import org.reactive.kanji.service.api.search.DictionaryInfoSearchService;
import org.reactive.kanji.service.model.Kanji;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.validation.Validator;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {KanjiHandlerImpl.class, KanjiRouter.class})
@WebFluxTest
public class KanjiHandlerTest {

    @Autowired
    private ApplicationContext context;
    @MockBean
    private KanjiService kanjiService;
    @MockBean
    private DictionaryInfoSearchService dictionaryInfoSearchService;
    @MockBean
    private Validator validator;

    private WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        webTestClient = WebTestClient.bindToApplicationContext(context).build();
    }

    @Test
    public void saveKanji() {
        Kanji kanji = Kanji.builder()
                .id(1L)
                .character("火")
                .meaning("fire")
                .story("flame flickers")
                .build();

        when(kanjiService.save(any(Mono.class)))
                .thenReturn(Mono.just(kanji));

        webTestClient.post()
                .uri("/kanji")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(kanji), Kanji.class)
                .exchange()
                .expectStatus().isOk();

        verify(kanjiService).save(any(Mono.class));
    }
}
