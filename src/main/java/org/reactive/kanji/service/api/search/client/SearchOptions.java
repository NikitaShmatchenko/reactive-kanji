package org.reactive.kanji.service.api.search.client;

import org.reactive.kanji.service.model.search.request.DictionaryInfoRequest;
import org.reactive.kanji.service.model.search.DictionaryInfo;
import reactor.core.publisher.Mono;

public interface SearchOptions {

    Mono<DictionaryInfo> search(DictionaryInfoRequest request);
}
