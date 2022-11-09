# Toteutus

## Shakkilaudan toiminta

Shakkilaudan toiminta perustuu 10x12 Nappula-olio -taulukkoon. Nappuloilla on tarvittavat tiedot tallessa kuten nappulan koordinaatit, tyyppi, numero, arvo, id ja nappulan väri. Nappuloilla on tallessa nappulan mahdolliset siirrot, siirrot shakissa, sekä nappulan blokit, eli nappulan liikkumista rajoittavien nappuloiden koordinaatit. 
    
Kun laudalla suoritetaan siirto, tarkistetaan mihin nappuloihin siirto mahdollisesti vaikuttaa, ja päivitetään näiden nappuloiden siirrot.
    
Pelattava shakkilauta on vain 8x8, eli reunoissa on yhden, ja päädyissä kahden paksuiset epänappula-rivit, jotka estävät nappuloiden liikkumista laudan ulkopuolelle, ja poistavat tarpeen aina varmistaa, onko katsottavat koordinaatit laudan sisällä.
    
Siirtojen toiminta: nappuloiden siirrot-listalle lisätään nappulan pseudo-lailliset siirrot. Siirtoja lisättäessä siirrolle annetaan nopeasti vahvuus-arvio siitä, kuinka hyvä siirto oletettavasti on. Kuningattaren, tornin ja lähetin siirtoja päivittäessä katsotaan samalla, kiinnittääkö nappula toisen nappulan. Kiinnitys tapahtuu, jos esim. tornin linjan ja vastustajan kuninkaan välillä on vain yksi nappula, ja se on vastustajan. Tällöin nappula ei voi liikkua pois linjalta. Kiinnitetyn nappulan kaikki siirrot ovat tallessa, mutta kun siirtoja haetaan käytettäväksi, jos nappula on kiinnitetty, katsotaan ovatko siirrot laillisia katsomalla siirron ja kiinnityksen suuntia. 

## Tekoälyn toiminta

Tekoäly vastaa mustan armeijan siirroista. Tekoäly käyttää minimax-algoritmia alpha-beta karsinnalla. Tekoäly laskee neljän siirron syvyyteen kaikki mahdolliset sirrot, vuorotellen laskien mustan ja valkoisen siirron. Tämän jälkeen pelitilanne arvioidaan ja tekoäly valitsee siirron joka on arvioitu johtavan parhaaseen tulokseeen, kun molemmat musta ja valkoinen valitsevat aina parhaan mahdollisen siirron. Kun nappuloita on vähemmän jäljellä, syvyyttä suurennetaan hieman. Parhaassa tapauksessa, eli jos paras siirto on laskettu ensimmäisenä ja algoritmissa tapahtuu optimaalinen määrä karsintaa, algoritmin aikavaativuus on O ( $\sqrt{n^k}$ ). Huonoimmassa tapauksessa, eli jos karsintaa ei tapahdu ollenkaan, aikavaativuus on O(n^k). Näissä n on siirtojen määrä ja k puun maksimisyvyys.

Kun nappuloita alkaa olla vähän jäljellä, heuristisen arvion lisäksi otetaan käyttöön loppupelin arviointi, jolla pyritään saamaan tekoäly voittamaan peli kun sillä on selvä materiaalivahvuus. Arviointi toimii antamalla pisteitä siitä, että valkoisen kuningas on lähellä nurkkaa, ja että kuninkaiden välinen etäisyys on pieni. Näin Tekoäly saa ajettua kuninkaan tilaan ilman monia pakoruutuja ja löytää algoritmilla siirrot mattiin. Käytin loppupelin arviointiin saman tyylistä arviointia kuin Chess 4.x -ohjelman mop-up arviointi https://www.chessprogramming.org/Mop-up_Evaluation
