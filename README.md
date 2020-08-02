# reactive-kanji
# Kanji learning app WIP. Spring WebFlux, R2DBC.

## Board: https://trello.com/b/kHsqJV6c/rocket

## API:

### GET: /kanji/{character} 
#### Example: http://localhost:8080/kanji/日
```text
Desc: get kanji data(story, meaning and character) by character saved by user previously + dictionary data for this character from https://kanjiapi.dev/
```
### GET: /kanji
```text
Desc: Get all kanji data(story, meaning and character) saved by user.
```
### POST: /kanji
```text
Save kanji data(story, meaning and character).
```
#### Example body:
{
    "character": "日",
    "meaning": "sun",
    "story": "bright sun"
}
