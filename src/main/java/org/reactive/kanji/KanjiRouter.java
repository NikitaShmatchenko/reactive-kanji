package org.reactive.kanji;

import org.reactive.kanji.handler.api.KanjiHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class KanjiRouter {

    @Bean
    public RouterFunction<ServerResponse> kanjiRouts(KanjiHandler kanjiHandler) {
        return route()
                .GET("/kanji/{character}", accept(APPLICATION_JSON), kanjiHandler::getKanji)
                .GET("/kanji", accept(APPLICATION_JSON), kanjiHandler::getAllKanjis)
                .POST("/kanji", accept(APPLICATION_JSON), kanjiHandler::saveKanji)
                .build();
    }
}
