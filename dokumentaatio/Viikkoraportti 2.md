# Viikko 2

Satuin huomaamaan, että javafx-toiminnot eivät toimineet Gradle-projektissani. Yritin ratkaista ongelmaa muutaman tunnin tuloksetta. Vaihdoin projektini Maven-projektiksi ja aloitin koodauksen. Tutkin mahdollisia pelilaudan representaatioita ja päädyin simppeliin 8x8 integer-array -esitykseen, mutta saatan tarpeen tulleen vaihtaa 12x10-taulukkoon. En saanut päätettyä mieleistä lähestymistapaa siirtojen toteuttamiseen joten en päässyt ohjelmoinnin kanssa vielä yhtä pitkälle kuin olisin toivonut. Seuraavalla viikolla pyrin edistymään enemmän sekä luomaan graafisen käyttöliittymän.

Aloitin koodin testauksen, mutta yrittäessäni ajaa komentoa 

``` mvn test jacoco:report ```

saan virheen "shakki.domain.LautaTest: Locale provider adapter "CLDR"cannot be instantiated."

Käytetyt tunnit: 6h, yhteensä 9h.
