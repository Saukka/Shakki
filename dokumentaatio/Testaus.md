# Testaus

Ohjelman testit keskittyvät sovelluslogiikkaan eli laudan ja tekoälyn luokkiin. 

Testit testaavat ohjelman tärkeimpiä asioita, kuten että lauta asetetaan oikein, nappuloiden siirtäminen sekä poistaminen toimivat halutulla tavalla. Testauksessa testataan myös, että tekoäly saa siirron aikaseksi.


## pakettien testaus
<img width="700" alt="Pakettien testaus" src="https://user-images.githubusercontent.com/80990021/194730109-36133e7b-271a-4bef-82a2-2390539c99e4.png">

## shakki.domainin eli sovelluslogiikan testaus

Tärkeä shakkilaudan testi on mahdollisten tilanteiden määrä tietyn määrän siirtojen jälkeen. Kahden siirron jälkeen tilanteita on 400, kolmen jälkeen 8,902, neljän 197,281, viiden 4,865,609, ja kuuden jälkeen 119,060,324 mahdollista variaatiota. (lähde: https://www.chessprogramming.org/Perft_Results) 
Koodi testaa näitä lukuja seuraavalla testillä ja testi onnistuu.
<img width="500" alt="Screenshot 2022-11-02 at 0 45 32" src="https://user-images.githubusercontent.com/80990021/199356542-2df5b541-766f-4ffd-b771-698899862549.png">

<img width="700" alt="shakki.domain testaus" src="https://user-images.githubusercontent.com/80990021/194730146-7bd2a4e3-829a-4113-a5f4-7de8f208d9cc.png">
