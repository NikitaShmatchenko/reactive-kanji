package org.reactive.kanji.service.api;

import org.reactive.kanji.service.model.Kanji;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface KanjiService {

    Mono<Kanji> save(Mono<Kanji> kanji);

    Mono<Kanji> getKanjiByCharacter(String character);

    Mono<Kanji> getKanjiByMeaning(String meaning);

    Flux<Kanji> getAllKanji();
}
