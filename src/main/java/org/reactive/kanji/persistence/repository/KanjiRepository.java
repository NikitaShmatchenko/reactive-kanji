package org.reactive.kanji.persistence.repository;

import org.reactive.kanji.persistence.entities.Kanji;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface KanjiRepository extends ReactiveCrudRepository<Kanji, Long> {

    Mono<Kanji> findByMeaning(String meaning);

    Mono<Kanji> findByCharacter(String character);
}