# Käyttöohje

### Aloitusruutu

Aloitusruudulla pystyy antamaan pelille minkä tahansa tilanteen fen-merkkijonon avulla. (https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation)
Fen-merkkijonon lopussa välilyöntien jälkeen kerrotaan muitakin asioita kuten kumman siirto on, voiko suorittaa linnoituksen ja kummalle puolelle, siirtojen määrät. Ehdin kuitenkin toteuttaa vain merkkijonon ensimmäisen osuuden eli nappuloiden sijainnit. Peli alkaa siis aina valkoisen siirrolla ja linnoitus on mahdollista jos kuningas ja torni ovat oikeilla paikoillaan.

<img width="1044" alt="Screenshot 2022-10-30 at 20 32 27" src="https://user-images.githubusercontent.com/80990021/199131025-9d38e658-8310-402c-8341-e20af674529f.png">


### Pelaaminen 
    
Pelaaminen toimii normaaleilla shakin säännöillä, paitsi sotilaiden korottamista en ehtinyt lisäämään. Nappuloiden liikuttaminen onnistuu ensiksi painamalla liikutettavaa nappulaa, ja sen jälkeen painamalla ruutua mihin nappulan haluaa siirtää. Painamalla mitä tahansa näppäimistön näppäintä, viimeiset kaksi siirtoa laudalla perutaan, jolloin valkoinen voi yrittää toista siirtoa.
    
<img width="1042" alt="Pelitilanne" src="https://user-images.githubusercontent.com/80990021/194730212-91bc7e97-7450-44c2-af15-a2efe31ae019.png">


### Pelin loppu

Peli päättyy joko shakkimattiin tai pattiin, kun toisella pelaajalla ei ole laillisia siirtoja jäljellä.
