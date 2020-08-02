package org.reactive.kanji.service.impl.search.client;

import lombok.RequiredArgsConstructor;
import org.reactive.kanji.service.api.search.client.SearchOptions;
import org.reactive.kanji.service.model.search.request.DictionaryInfoRequest;
import org.reactive.kanji.service.model.search.DictionaryInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.function.Function;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor
public class DictionaryInfoSearchClient implements SearchOptions {

    public static final String KANJI_SEGMENT = "kanji";

    private final WebClient webClient;

    @Override
    public Mono<DictionaryInfo> search(DictionaryInfoRequest request) {

        Function<UriBuilder, URI> uriBuilder = builder -> builder
                .pathSegment(KANJI_SEGMENT)
                .pathSegment(String.valueOf(request.getKanji()))
                .build();

        return read(uriBuilder);
    }

    private <T> Mono<T> read(Function<UriBuilder, URI> uriFunction) {

        return webClient.get()
                .uri(uriFunction)
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToMono((Class<T>) DictionaryInfo.class);
    }
}
