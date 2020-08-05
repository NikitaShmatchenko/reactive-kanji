package org.reactive.kanji.service.model.validator;

import org.reactive.kanji.service.model.Kanji;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@Primary
public class KanjiValidator implements Validator {

    public boolean supports(Class clazz) {
        return Kanji.class.equals(clazz);
    }

    public void validate(Object obj, Errors e) {
        Kanji kanji = (Kanji) obj;
        if (kanji.getCharacter() == null) {
            ValidationUtils.rejectIfEmpty(e, "character", "character.empty");
        } else if (kanji.getCharacter().length() != 1)
            e.rejectValue("character", "Kanji can only be 1 character.");
    }
}

