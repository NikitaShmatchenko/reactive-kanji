package org.reactive.kanji.handler.api;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface KanjiHandler {

    Mono<ServerResponse> saveKanji(ServerRequest request);

    Mono<ServerResponse> getAllKanjis(ServerRequest request);

    Mono<ServerResponse> getKanji(ServerRequest request);
}
