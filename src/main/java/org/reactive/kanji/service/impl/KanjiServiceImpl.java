package org.reactive.kanji.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactive.kanji.persistence.entities.Kanji;
import org.reactive.kanji.persistence.repository.KanjiRepository;
import org.reactive.kanji.service.api.KanjiService;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class KanjiServiceImpl implements KanjiService {

    private final KanjiRepository kanjiRepository;

    public Disposable save(Kanji kanji) {
        return Mono.just(kanji)
                .flatMap(kanjiRepository::save)
                .subscribe();
    }

    public Mono<Kanji> getKanjiByMeaning(String meaning) {
        return kanjiRepository.findByMeaning(meaning);
    }

    public Mono<Kanji> getKanjiByCharacter(String kanji) {
        return kanjiRepository.findByCharacter(kanji);
    }

    public Flux<Kanji> getAllKanjis() {
        return kanjiRepository.findAll();
    }
}
