# Beskrivelse av prosjektet
## Hva er det?
Prosjektet er et spill inspirert av [Space Invaders](https://en.wikipedia.org/wiki/Space_Invaders) og lignende Arcade-spill. Spilleren er en figur nederst på skjermen som kan bevege seg til høyre og venstre. Målet med spillet er å sanke så mange poeng som mulig. Man får poeng av å ta imot blobbene som regner ned. Hvis man ikke tar imot en blob, mister man ett liv.

### Bilde av applikasjonen:
![](./app.png)

## Logikk og struktur
Spill-logikken finner vi i pakken [`hovedprosjekt.Model`](./src/main/java/hovedprosjekt/Model), hvor klassen [`Computer`](./src/main/java/hovedprosjekt/Model/Computer.java) er den viktigste. Denne klassen er ansvarlig for å holde orden på spilleren og de andre entitetene i spillet, og for å sjekke etter kollisjoner og kalkulere liv og score. Spilltilstanden består av et spiller-objekt, en liste over entiteter, antall liv og nåværende score.

Spillet er tikk-basert. Slik det er satt opp akkurat nå, kjøres en `while`-løkke så lenge spillet er aktivt. Der kjøres alt av kalkulasjoner og applikasjonen sover i 10 millisekunder før neste kjøring. Dette fungerer greit når belastningen av kalkulasjonene er såpass konsistente som her. Hvis det er mye variasjon kan spillet virke hakkete, og delta tid ville vært en bedre løsning.

Vi representerer blobbene ved hjelp av listen over entiteter. Dette er en `List` av `Entity`-objekter. Blobbene i spillet er av klassen `NormalBlob`, som implementerer `Entity`. Når `updateGUI` kjøres hvert tikk, itereres det gjennom denne lista, og blobbene tegnes opp på der koordinatene tilsier. Tanken bak å lage `Entity` som en abstrakt klasse, er at man kan lage andre blobber også, for eksempel noen som gir mer poeng, noen som gir deg flere liv osv. Når avstanden mellom spilleren og en blob er mindre enn 20 (radius i blobben er 5 og spilleren er 30x30, 15+5=20) anser spillet blobben som tatt imot, og den fjernes fra listen. 

## Krav
* Det var et krav at tilstanden i objektene skal være innkapslet. Dette har jeg gjort ved å gjøre alle felter `private` og brukt getter og setter-metoder der det er behov for det. Videre skulle man validere tilstanden. Dette har jeg gjort ved å legge inn sjekker i de `set`-metodene hvor det er passelig.

* Det skulle implementeres et grensesnitt. Dette har jeg gjort med `EntityListIterator`.

* Det skulle være minst 2 interagerende klasser. Jeg har 8.

* Minst 1 klasse skulle ha funksjonalitet utover ren datalagring. Se feks på `Computer` eller `EntityListIterator`. 

* Appen skulle lages etter Model-View-Controller prinsippet. Dette har jeg gjort ved å legge det meste av logikken i `Model`, mens `Controller` fungerer som et bindeledd mellom `Model` og `View`. 

* Minst 1 ny klasse skulle håndtere lesing og skriving til fil. Se `HighScore`.

* Det skal være implementert hensiktsmessig feilhåndtering i alle utsatte deler av appen. I denne appen er filhåndteringen en utsatt del hvor det kan oppstå feil, og disse blir skrevet ut. Game-loopen er også utsatt, og her blir feil også fanget.

* Det skal være minst 6 enhetstester, hvor minst 1 tester fillagring. Jeg har over 6 tester og `testHighScore` tester fillagring.

# Om klassene
Appen består av 8 klasser gitt at vi ikke regner med Controller eller App-klassene. 

## Vars
Denne klassen inneholder en enum for spillerbevegelser, bestående av verdiene `LEFT`, `NONE` og `RIGHT`. 

## Value
Denne klassen ble laget som en wrapper for en `int`. Jeg trengte en klasse som kunne holde på et tall og samtidig være mutable. Ettersom `int` ikke er en klasse (men en primitiv type) og `Integer` er immutable, måtte jeg lage min egen klasse. Value-klassen har tre felter; `value`, `maxValue` og `minValue`. Følgelig har man også metodene `getValue`, `getMinValue`, `getMaxValue`, `setMinValue`, `setMaxValue` og `incrementValue`. Disse er stort sett selvforklarende, men `incrementValue` tar inn en `int` – som kan være positiv eller negativ – og inkrementerer `value` med denne verdien.

## Entity
Dette er en abstrakt klasse som kan brukes for å implementere entiteter i spillet. Klassen har to felter; `xPos` og `yPos`, som representerer koordinatene til entiteten. Videre er det to metoder; `getPosition` og `setPosition` for å behandle de to koordinat-feltene. 

## NormalBlob
Denne klassen implementerer (extends) `Entity`, og har i tillegg et felt `points`. Dette er antall poeng en spiller får for å fange denne entiteten. Klassen har to nye metoder; `setPoints` og `getPoints` for å interagere med dette feltet.

## Player
Denne klassen implementerer også `Entity`, og den har feltene `movement` og `speed`. `movement` er en enum fra klassen `Vars` og `speed` er en `int`. `Player` har to konstruktører, én hvor du kan oppgi x og y-koordinatene til objektet, og én som ikke tar inn noen parametere. Det er også metoder for `getMovement` og `setMovement` (selvforklarende), samt `moveLeft` og `moveRight` som endrer x-koordinaten (legger til eller trekker fra `speed`) til entiteten i den bestemte retningen.

## EntityListIterator
Denne klassen implenterer `Iterator`-grensesnittet og har derfor metodene `next` og `hasNext`. Ettersom jeg fjerner elementer fra lista når jeg bruker denne iteratoren, starter den å iterere fra bakerste element i lista. Elementene fra iteratoren blir derfor ikke forskjøvet når jeg fjerner elementer fra lista. 

## HighScore
Denne klassen håndterer lesing og skriving til fil for å lagre highscore. Den har metodene `getHighScore` og `setHighScore`. Denne funksjonaliteten kunne uten problem vært i `Computer`, men det var et krav i oppgaven at filhåndteringen skulle foregå i en egen klasse. 

## Computer
Denne klassen inneholder det meste av logikken til spillet. Her brukes de andre klassene som støtteklasser for at ting skal fungere som ønsket. Det er et `player`-felt for et `Player`-objekt. Dette objektet brukes for å lagre koordinatene til spilleren, og for å bevege spilleren når det er ønskelig. Det er et felt for en liste av `Entity`-objekter; den inneholder alle «blobbene» som i ethvert øyeblikk befinner seg i spillet. Det er et felt for score og et felt for liv, som bruker `Value` som er wrapper for et tall. `width` og `height` refererer til dimensjonene på canvas-objektet i FXML, som er satt til 500 x 500. Når det kommer til metoder, så er det en `reset`-metode. Denne tømmer lista over entiteter, setter score til 0, liv til 5 og endrer spillerens koordinater til midt på. Det er også gettere for alle feltene, samt en `getHighScore` og en `setHighScore` som delegerer videre til et `HighScore`-objekt. Det er også metoder for å lage nye «blobber», sjekke om en entitet kolliderer med spilleren og regne ut nye koordinater til blobbene. Det er også en metode som kjøres hvert tikk, hvor det er en sjanse for at nye «blobber» lages og hvor utregning av nye koordinater, kollisjoner, samt endringer i score og liv foregår. 

**All kode har også java-docs som beskriver hvordan klassene og metodene fungerer, i tillegg til kommentarer i koden der det er relevant.**
