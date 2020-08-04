package org.reactive.kanji.persistence.repository;

import org.reactive.kanji.persistence.entities.KanjiEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface KanjiRepository extends ReactiveCrudRepository<KanjiEntity, Long> {

    Mono<KanjiEntity> findByMeaning(String meaning);

    Mono<KanjiEntity> findByCharacter(String character);
}