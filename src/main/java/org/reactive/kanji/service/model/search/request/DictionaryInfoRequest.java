package org.reactive.kanji.service.model.search.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
@EqualsAndHashCode
public class DictionaryInfoRequest {
    private String kanji;
}
