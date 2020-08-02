package org.reactive.kanji.service.model.search;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DictionaryInfo {
    private int grade;
    private String heisig_en;
    private List<String> meanings;
    private int strokeCount;
    private int jlpt;
    private List<String> kun_readings;
    private List<String> on_readings;
    private List<String> name_readings;
}
