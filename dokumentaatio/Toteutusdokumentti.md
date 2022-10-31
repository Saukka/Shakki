# Toteutus

## Shakkilaudan toiminta

Shakkilaudan toiminta perustuu 10x12 Nappula-olio -taulukkoon. Nappuloilla on tarvittavat tiedot tallessa kuten nappulan koordinaatit, tyyppi, numero, arvo, id ja nappulan väri. Nappuloilla on tallessa nappulan mahdolliset siirrot, siirrot shakissa, sekä nappulan blokit, eli nappulan liikkumista rajoittavien nappuloiden koordinaatit. 
    
Kun laudalla suoritetaan siirto, tarkistetaan mihin nappuloihin siirto mahdollisesti vaikuttaa, ja päivitetään näiden nappuloiden siirrot.
    
Pelattava shakkilauta on vain 8x8, eli reunoissa on yhden, ja päädyissä kahden paksuiset epänappula-rivit, jotka estävät nappuloiden liikkumista laudan ulkopuolelle, ja poistavat tarpeen aina varmistaa, onko katsottavat koordinaatit laudan sisällä.
    
Siirtojen toiminta: nappuloiden siirrot-listalle lisätään nappulan pseudo-lailliset siirrot. Kuningattaren, tornin ja lähetin siirtoja päivittäessä katsotaan samalla, kiinnittääkö nappula toisen nappulan. Kiinnitys tapahtuu, jos esim. tornin linjan ja vastustajan kuninkaan välillä on vain yksi nappula, ja se on vastustajan. Tällöin nappula ei voi liikkua pois linjalta. Kiinnitetyn nappulan kaikki siirrot ovat tallessa, mutta kun siirtoja haetaan käytettäväksi, jos nappula on kiinnitetty, katsotaan onko siirrot laillisia katsomalla siirron ja kiinnityksen suuntia.
    

## Tekoälyn toiminta

Tekoäly vastaa mustan armeijan siirroista. Tekoäly käyttää minimax-algoritmia alpha-beta pruningilla. Tekoäly laskee neljän siirron syvyyteen kaikki mahdolliset sirrot, vuorotellen laskien mustan ja valkoisen siirron. Tämän jälkeen pelitilanne arvioidaan ja tekoäly valitsee siirron joka on arvioitu johtavan parhaaseen tulokseeen, kun molemmat musta ja valkoinen valitsee parhaan mahdollisen siirron.
    
Tekoälyn "älykkyys" jäi harmillisen heikoksi, kun keskityin enemmän laudan toimintaan ja optimoimiseen, ja aloin viimeisellä viikolla muuttamaan täysin siirtojen toiminnan järkevämmäksi.
