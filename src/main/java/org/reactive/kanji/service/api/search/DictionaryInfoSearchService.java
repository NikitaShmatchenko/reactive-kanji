package org.reactive.kanji.service.api.search;

import org.reactive.kanji.service.model.search.DictionaryInfo;
import reactor.core.publisher.Mono;

public interface DictionaryInfoSearchService {

    Mono<DictionaryInfo> searchKanji(String kanji);
}
