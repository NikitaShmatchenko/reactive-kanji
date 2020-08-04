package org.reactive.kanji.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactive.kanji.persistence.repository.KanjiRepository;
import org.reactive.kanji.service.api.KanjiService;
import org.reactive.kanji.service.mapper.impl.KanjiMapper;
import org.reactive.kanji.service.model.Kanji;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class KanjiServiceImpl implements KanjiService {

    public static final String ERROR_ENTITY_NOT_EXIST = "An entity matching the given character doesn't exist";

    private final KanjiRepository kanjiRepository;
    private final KanjiMapper kanjiMapper;

    public Disposable save(Kanji kanji) {
        return Mono.just(kanji)
                .map(kanjiMapper::mapToEntity)
                .flatMap(kanjiRepository::save)
                .subscribe();
    }

    public Mono<Kanji> getKanjiByMeaning(String meaning) {
        return kanjiRepository.findByMeaning(meaning)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                ERROR_ENTITY_NOT_EXIST)))
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
