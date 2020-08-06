package org.reactive.kanji.service.impl;

import io.r2dbc.spi.R2dbcDataIntegrityViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.reactive.kanji.persistence.entities.KanjiEntity;
import org.reactive.kanji.persistence.repository.KanjiRepository;
import org.reactive.kanji.service.api.KanjiService;
import org.reactive.kanji.service.mapper.impl.KanjiMapper;
import org.reactive.kanji.service.model.Kanji;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {KanjiServiceImpl.class, KanjiMapper.class})
public class KanjiServiceTest {

    private static final String KANJI_CHARACTER = "火";

    @Autowired
    private KanjiService kanjiService;
    @MockBean
    private KanjiRepository kanjiRepository;

    @Test
    public void saveKanji() {
        String generatedString = UUID.randomUUID().toString();

        Kanji kanji = Kanji.builder()
                .id(1L)
                .character(KANJI_CHARACTER)
                .meaning(generatedString)
                .story(generatedString)
                .build();

        KanjiEntity kanjiEntity = new KanjiEntity(kanji.getId(),
                kanji.getCharacter(),
                kanji.getMeaning(),
                kanji.getStory());

        when(kanjiRepository.save(any(KanjiEntity.class)))
                .thenReturn(Mono.just(kanjiEntity));

        StepVerifier.create(kanjiService.save(Mono.just(kanji)))
                .expectNext(kanji)
                .verifyComplete();

        verify(kanjiRepository).save(any(KanjiEntity.class));
    }

    @Test
    public void saveKanjiAlreadyExistBadRequest() {
        String generatedString = UUID.randomUUID().toString();

        Kanji kanji = Kanji.builder()
                .id(1L)
                .character(KANJI_CHARACTER)
                .meaning(generatedString)
                .story(generatedString)
                .build();

        when(kanjiRepository.save(any(KanjiEntity.class)))
                .thenThrow(new R2dbcDataIntegrityViolationException());

        StepVerifier.create(kanjiService.save(Mono.just(kanji)))
                .expectError(ResponseStatusException.class)
                .verify();

        verify(kanjiRepository).save(any(KanjiEntity.class));
    }

    @Test
    public void getKanjiByCharacter() {
        String generatedString = UUID.randomUUID().toString();

        Kanji kanji = Kanji.builder()
                .id(1L)
                .character(KANJI_CHARACTER)
                .meaning(generatedString)
                .story(generatedString)
                .build();

        KanjiEntity kanjiEntity = new KanjiEntity(kanji.getId(),
                kanji.getCharacter(),
                kanji.getMeaning(),
                kanji.getStory());

        when(kanjiRepository.findByCharacter(anyString()))
                .thenReturn(Mono.just(kanjiEntity));

        StepVerifier.create(kanjiService.save(Mono.just(kanji)))
                .expectNext(kanji)
                .verifyComplete();

        verify(kanjiRepository).findByCharacter(anyString());
    }

    @Test
    public void getKanjiByMeaning() {
        String generatedString = UUID.randomUUID().toString();

        Kanji kanji = Kanji.builder()
                .id(1L)
                .character(KANJI_CHARACTER)
                .meaning(generatedString)
                .story(generatedString)
                .build();

        KanjiEntity kanjiEntity = new KanjiEntity(kanji.getId(),
                kanji.getCharacter(),
                kanji.getMeaning(),
                kanji.getStory());

        when(kanjiRepository.findByMeaning(anyString()))
                .thenReturn(Mono.just(kanjiEntity));

        StepVerifier.create(kanjiService.save(Mono.just(kanji)))
                .expectNext(kanji)
                .verifyComplete();

        verify(kanjiRepository).findByMeaning(anyString());
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

        KanjiEntity kanjiEntity = new KanjiEntity(kanji.getId(),
                kanji.getCharacter(),
                kanji.getMeaning(),
                kanji.getStory());

        Flux<KanjiEntity> kanjiEntityFlux = Flux.just(kanjiEntity, kanjiEntity);

        when(kanjiRepository.findAll())
                .thenReturn(kanjiEntityFlux);

        StepVerifier.create(kanjiService.getAllKanji())
                .expectNextCount(2)
                .verifyComplete();

        verify(kanjiRepository).findAll();
    }
}
