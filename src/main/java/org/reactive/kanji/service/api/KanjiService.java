package org.reactive.kanji.service.api;

import org.reactive.kanji.persistence.entities.Kanji;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface KanjiService {

    Disposable save(Kanji kanji);

    Mono<Kanji> getKanjiByCharacter(String character);

    Mono<Kanji> getKanjiByMeaning(String meaning);

    Flux<Kanji> getAllKanjis();
}
