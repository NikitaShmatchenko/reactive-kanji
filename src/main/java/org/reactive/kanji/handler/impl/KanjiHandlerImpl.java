package org.reactive.kanji.handler.impl;

import lombok.RequiredArgsConstructor;
import org.reactive.kanji.handler.api.KanjiHandler;
import org.reactive.kanji.service.api.KanjiService;
import org.reactive.kanji.service.api.search.DictionaryInfoSearchService;
import org.reactive.kanji.service.model.Kanji;
import org.reactive.kanji.service.model.response.KanjiResponse;
import org.reactive.kanji.service.model.search.DictionaryInfo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class KanjiHandlerImpl implements KanjiHandler {

    private static final String CHARACTER_PATH_VARIABLE = "character";
    private static final String TOO_MANY_CHARACTERS_ERROR = "The path variable contains more than 1 character!";

    private final KanjiService kanjiService;
    private final DictionaryInfoSearchService dictionaryInfoSearchService;

    public Mono<ServerResponse> saveKanji(ServerRequest request) {
        return request.body(toMono(Kanji.class))
                .doOnNext(kanjiService::save)
                .then(ok().build());
    }

    public Mono<ServerResponse> getAllKanjis(ServerRequest request) {
        return ok()
                .body(kanjiService.getAllKanjis(), Kanji.class);
    }


    public Mono<ServerResponse> getKanji(ServerRequest request) {
        String character = request.pathVariable(CHARACTER_PATH_VARIABLE);
        if (character.length() > 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, TOO_MANY_CHARACTERS_ERROR);
        }

        Mono<Kanji> kanji = kanjiService.getKanjiByCharacter(character);
        Mono<DictionaryInfo> dictionaryInfo = dictionaryInfoSearchService.searchKanji(character);

        return ok()
                .body(kanji.zipWith(dictionaryInfo, KanjiResponse::of), KanjiResponse.class);
    }
}
