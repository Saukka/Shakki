# Testaus

Ohjelman testit keskittyvät sovelluslogiikkaan eli laudan ja tekoälyn luokkiin. 

Testit testaavat ohjelman tärkeimpiä asioita, kuten että lauta asetetaan oikein, nappuloiden siirtäminen sekä poistaminen toimivat halutulla tavalla. Tekoälyä testataan varmistamalla, että se saa tavallisessa tilanteessa siirron aikaiseksi, että se voittaa kuninkaalla ja tornilla, ja että se löytää asetetussa tilanteessa matin kahdella siirrolla.


## pakettien testaus

<img width="700" alt="Pakettien testaus" src="https://user-images.githubusercontent.com/80990021/200085747-726b40b1-c41c-4f5a-a9da-23ad63ac3cef.png">

## shakki.domainin eli sovelluslogiikan testaus

<img width="700" alt="shakki.domain testaus" src="https://user-images.githubusercontent.com/80990021/200085784-4aeb75d6-87b0-4dbe-b461-ce71c2b95089.png">

## laudan testaus

<img width="700" alt="Laudan testaus" src="https://user-images.githubusercontent.com/80990021/200085852-d9a71bb7-75fe-4e52-9f30-94dcf5233453.png">

Tärkeä shakkilaudan testi on mahdollisten tilanteiden määrä tietyn määrän siirtojen jälkeen. Kahden siirron jälkeen tilanteita on 400, kolmen jälkeen 8,902, neljän 197,281, viiden 4,865,609, ja kuuden jälkeen 119,060,324 mahdollista variaatiota. (lähde: https://www.chessprogramming.org/Perft_Results) 
Koodi testaa näitä lukuja seuraavalla testillä ja testi onnistuu.
<img width="500" alt="Screenshot 2022-11-02 at 0 45 32" src="https://user-images.githubusercontent.com/80990021/199356542-2df5b541-766f-4ffd-b771-698899862549.png">
