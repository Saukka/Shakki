# Testaus

Ohjelman testit keskittyvät sovelluslogiikkaan eli laudan ja tekoälyn luokkiin. 

Testit testaavat ohjelman tärkeimpiä asioita, kuten että lauta asetetaan oikein, nappuloiden siirtäminen sekä poistaminen toimivat halutulla tavalla. Testauksessa testataan myös, että tekoäly saa siirron aikaseksi.


## pakettien testaus
<img width="954" alt="Pakettien testaus" src="https://user-images.githubusercontent.com/80990021/194730109-36133e7b-271a-4bef-82a2-2390539c99e4.png">

## shakki.domainin eli sovelluslogiikan testaus

Tärkeä shakkilaudan testi on mahdollisten tilanteiden määrä tietyn määrän siirtojen jälkeen. Kahden siirron jälkeen tilanteita on 400, jonka koodi saa oikein. Neljän siirron jälkeen tilanteita on 197 742, mutta koodi antaa nyt tilanteiden määräksi 197 281.
<img width="946" alt="shakki.domain testaus" src="https://user-images.githubusercontent.com/80990021/194730146-7bd2a4e3-829a-4113-a5f4-7de8f208d9cc.png">
