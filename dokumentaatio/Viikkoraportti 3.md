# Viikko 3


Loin graafisen shakkilaudan ja asetin nappuloiden ikonit paikoilleen. Kamppailin kauan käyttäjän hiiren tarkkailun kanssa, mutta onnistuin viimein ja sain pelaajan siirrot toimimaan. Siirtoja voi tehdä klikkaamalla jotakin ruutua, ja sen jälkeen klikkamalla ruutua mihin haluaa nappulan siirtää. Siirtoja ei kuitenkaan tarkisteta millään tavalla ja pelaaja voi siirtää myös mustia nappuloita. Erroreita tulee helposti, jolloin sovellus jumittuu. Eli itse shakin logiikkaa ei ohjelmassa paljoa vielä ole, jonka vuoksi en oikeastaan edennyt Minimaxin kanssa, ehdin vain tuumailla paperille tekoälyn peliälyä parantavia asioita. Kuten pelkäsin, 8x8 array-lauta aiheuttaa paljon ongelmia siirtojen selvitttämisessä, joten aion siirtyä 12x12 array-lautaan. 

Toin Checkstylen projektiin ja sen voi suorittaa komennolla 

```
mvn jxr:jxr checkstyle:checkstyle 
```
Virheitä tulee tällä hetkellä aika järkyttävä määrä.

Sain testauksen toimimaan koneellani ja aloitin testausdokumentin teon.

Käytetyt tunnit: 21h, yhteensä 30h.


Shakkilaudan tulisi näyttää tältä

<img width="1050" alt="Shakkilauta" src="https://user-images.githubusercontent.com/80990021/192124529-127fb620-ddba-4913-9977-5f2bbc4110e9.png">
