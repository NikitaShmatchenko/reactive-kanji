CREATE TABLE KANJI(
    ID INT PRIMARY KEY AUTO_INCREMENT,
    CHARACTER VARCHAR(1) NOT NULL UNIQUE,
    MEANING VARCHAR(20),
    STORY VARCHAR(500)
);

INSERT INTO KANJI (CHARACTER, MEANING, STORY)
VALUES ('千', 'thousand', 'It takes many drops of ten to get to a thousand.');

INSERT INTO KANJI (CHARACTER, MEANING, STORY)
VALUES ('舌', 'tongue', 'A thousand mouths speak the same TONGUE.');

INSERT INTO KANJI (CHARACTER, MEANING, STORY)
VALUES ('火', 'fire', 'Flame flickers.');

INSERT INTO KANJI (CHARACTER, MEANING, STORY)
VALUES ('炎', 'inflammation', 'An inflammation can often feel like FIRE upon FIRE burning.');

INSERT INTO KANJI (CHARACTER, MEANING, STORY)
VALUES ('災', 'disaster', 'What a disaster! First a FIRE, then on top of it came FLOODs.');