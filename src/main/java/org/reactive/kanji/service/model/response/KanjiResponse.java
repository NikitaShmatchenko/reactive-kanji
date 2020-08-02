package org.reactive.kanji.service.model.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.reactive.kanji.persistence.entities.Kanji;
import org.reactive.kanji.service.model.search.DictionaryInfo;

@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode
public class KanjiResponse {
    private Kanji kanji;
    private DictionaryInfo dictionaryInfo;
}
