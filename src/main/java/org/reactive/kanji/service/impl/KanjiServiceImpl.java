package org.reactive.kanji.service.impl;

import io.r2dbc.spi.R2dbcDataIntegrityViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactive.kanji.persistence.repository.KanjiRepository;
import org.reactive.kanji.service.api.KanjiService;
import org.reactive.kanji.service.mapper.impl.KanjiMapper;
import org.reactive.kanji.service.model.Kanji;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class KanjiServiceImpl implements KanjiService {

    public static final String ERROR_ENTITY_NOT_EXIST = "An entity matching the given character doesn't exist!";
    public static final String ERROR_ENTITY_ALREADY_EXIST = "An entity matching the given character exists exist!";

    private final KanjiRepository kanjiRepository;
    private final KanjiMapper kanjiMapper;

    //TODO Rework this method to check for kanji existence before saving
    public Mono<Kanji> save(Mono<Kanji> kanji) {
        return kanji
                .map(kanjiMapper::mapToEntity)
                .flatMap(kanjiRepository::save)
                .map(kanjiMapper::mapFromEntity)
                .onErrorResume(R2dbcDataIntegrityViolationException.class, (error) -> {
                    log.error(ERROR_ENTITY_ALREADY_EXIST, error);
                    return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_ENTITY_ALREADY_EXIST));
                });
    }

    public Mono<Kanji> getKanjiByMeaning(String meaning) {
        return kanjiRepository.findByMeaning(meaning)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_ENTITY_NOT_EXIST)))
                .map(kanjiMapper::mapFromEntity);
    }

    public Mono<Kanji> getKanjiByCharacter(String kanji) {
        return kanjiRepository.findByCharacter(kanji)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        ERROR_ENTITY_NOT_EXIST)))
                .map(kanjiMapper::mapFromEntity);
    }

    public Flux<Kanji> getAllKanjis() {
        return kanjiRepository.findAll()
                .map(kanjiMapper::mapFromEntity);
    }
}
