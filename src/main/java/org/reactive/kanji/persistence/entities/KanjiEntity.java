package org.reactive.kanji.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("kanji")
public class KanjiEntity {
    @Id
    private Long id;
    private String character;
    private String meaning;
    private String story;
}
