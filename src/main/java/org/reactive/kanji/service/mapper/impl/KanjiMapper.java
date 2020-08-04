package org.reactive.kanji.service.mapper.impl;

import org.reactive.kanji.persistence.entities.KanjiEntity;
import org.reactive.kanji.service.mapper.api.EntityMapper;
import org.reactive.kanji.service.model.Kanji;
import org.springframework.stereotype.Component;

@Component
public class KanjiMapper implements EntityMapper<Kanji, KanjiEntity> {

    @Override
    public KanjiEntity mapToEntity(Kanji model) {
        KanjiEntity kanjiEntity = new KanjiEntity();

        kanjiEntity.setId(model.getId());
        kanjiEntity.setCharacter(model.getCharacter());
        kanjiEntity.setMeaning(model.getMeaning());
        kanjiEntity.setStory(model.getStory());

        return kanjiEntity;
    }

    @Override
    public Kanji mapFromEntity(KanjiEntity entity) {
        return Kanji.builder()
                .id(entity.getId())
                .character(entity.getCharacter())
                .meaning(entity.getMeaning())
                .story(entity.getStory())
                .build();
    }

}
