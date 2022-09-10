# Määrittelydokumentti

Opinto-ohjelmani on tietojenkäsittelytieteen kandiohjelma. Dokumentaatiossa käytän suomen kieltä ja ohjelman kielenä Javaa. Pythonia olen käyttänyt vain vähän, mutta tarvittaessa pystyn myös vertaisarvioida Pythonia käyttävää työtä.

Projektin aiheena on tunnettu lautapeli Shakki, jossa pelaaja voi pelata tietokoneen tekoälyä vastaan syöttämällä ohjelmalle siirtonsa. Tavoitteena on kehittää tehokas tekoäly, joka päihittää keskiverron shakin pelaajan. 

Tekoäly toimii minimax-algoritmilla. Aika- ja tilavaativuus tavoitteet ovat minimaxin tyypilliset O(n^k) (aika) ja O(nk) (tila), jossa n on laillisten siirtojen määrä ja k on puun maksimisyvyys. [Lähde](https://cis.temple.edu/~vasilis/Courses/CIS603/)
