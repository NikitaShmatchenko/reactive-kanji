package org.reactive.kanji.handler.impl;

import lombok.RequiredArgsConstructor;
import org.reactive.kanji.handler.api.KanjiHandler;
import org.reactive.kanji.service.api.KanjiService;
import org.reactive.kanji.service.api.search.DictionaryInfoSearchService;
import org.reactive.kanji.service.model.Kanji;
import org.reactive.kanji.service.model.response.KanjiResponse;
import org.reactive.kanji.service.model.search.DictionaryInfo;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class KanjiHandlerImpl implements KanjiHandler {

    private static final String CHARACTER_PATH_VARIABLE = "character";
    private static final String TOO_MANY_CHARACTERS_ERROR = "The path variable contains more than 1 character!";

    private final KanjiService kanjiService;
    private final DictionaryInfoSearchService dictionaryInfoSearchService;
    private final Validator validator;

    public Mono<ServerResponse> saveKanji(ServerRequest request) {
        Mono<Kanji> kanji = request.body(toMono(Kanji.class))
                .doOnNext(this::validate);

        return ok()
                .body(kanjiService.save(kanji), Kanji.class);
    }

    public Mono<ServerResponse> getAllKanjis(ServerRequest request) {
        return ok()
                .body(kanjiService.getAllKanjis(), Kanji.class);
    }

    public Mono<ServerResponse> getKanji(ServerRequest request) {
        String character = Optional.of(request)
                .map(req -> req.pathVariable(CHARACTER_PATH_VARIABLE))
                .filter(pathVar -> pathVar.length() == 1)
                .orElseThrow(() -> new ServerWebInputException(TOO_MANY_CHARACTERS_ERROR));

        Mono<Kanji> kanji = kanjiService.getKanjiByCharacter(character);
        Mono<DictionaryInfo> dictionaryInfo = dictionaryInfoSearchService.searchKanji(character);

        return ok()
                .body(kanji.zipWith(dictionaryInfo, KanjiResponse::of), KanjiResponse.class);
    }

    private void validate(Kanji kanji) {
        Errors errors = new BeanPropertyBindingResult(kanji, "kanji");
        validator.validate(kanji, errors);
        if (errors.hasErrors()) {
            throw new ServerWebInputException(errors.toString());
        }
    }
}
