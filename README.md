# reactive-kanji
# Kanji learning app WIP. Spring WebFlux, R2DBC.

## Board: https://trello.com/b/kHsqJV6c/rocket

## API:

### GET: /kanji/{character} 
#### Example: http://localhost:8080/kanji/日
```text
Desc: get kanji data(story, meaning and character) by character saved by user previously
and dictionary data for this character from https://kanjiapi.dev/
{character} path parameter can only be 1 character long. 
```
### GET: /kanji
```text
Desc: Get all kanji data(story, meaning and character) saved by user.
```
### POST: /kanji
```text
Save kanji data(story, meaning and character). If you want to create then you should not provide an id.
Id is only specified when updating. (LAME(WILL BE REWORKED LATER)) 
```
#### Example body for updating:
{
    "id": 1,
    "character": "日",
    "meaning": "sun",
    "story": "bright sun"
}

