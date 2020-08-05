package org.reactive.kanji.service.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public class Kanji {
    private Long id;
    private String character;
    private String meaning;
    private String story;
}
