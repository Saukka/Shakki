package shakki.nappulat;

import java.util.ArrayList;
import shakki.domain.*;


/**
 * Nappula-olion luokka.
 * Luokka säilyttää nappulan olennaiset tiedot ja nappulan pseudosiirrot.
 */
public abstract class Nappula {
    
    Lauta lauta; // nappulan lauta
    
    enum TYYPPI{
        SOTILAS, LAHETTI, RATSU, TORNI, KUNINGATAR, KUNINGAS, EPANAPPULA
    }
    
    TYYPPI tyyppi;
    
    int id = 0; // jokaisella nappulalla on uniikki id
    int numero; // numero kertoo nappulan tyypin
    int arvo; // Arvo kertoo nappulan arvon
    
    int vari; // 0 jos valkoinen, 1 jos musta
    
    public int viimeksiPaivitetty; // milloin viimeksi nappulan siirrot ovat päivitetty.
    
    // Nappulan koordinaatit
    int x;
    int y;
    
    boolean onLiikkunut; // onko nappula liikkunut
    boolean syoty; // onko nappula syöty
    
    public ArrayList<Siirto> siirrot; // nappulan siirrot
    public ArrayList<Koordinaatit> blokit; // nappulan liikkumista rajoittavat koordinaatit.
    public ArrayList<Siirto> siirrotShakissa; // nappulan siirrot shakissa.
    
    public int kiinnitys = 0; // nappulan kiinnitys
    
    public Nappula kiinnitetty = null; // nappulan kiinnittämä nappula
    public int kiinnitysSuunta = 0; 
    
    int kiinnitysEnnen = 0; // kiinnitettävän nappulan kiinnitys ennen kiinnittämistä.
    
    /**
     * Nappulan luominen.
     * @param id jokaisella nappulalla on uniikki id.
     * @param x nappulan x-koordinaatti.
     * @param y nappulan y-koordinaatti.
     * @param vari nappulan väri. 0 = valkoinen, 1 = musta, -1 = ei ole nappula.
     * @param lauta lauta jolla nappula on.
     */
    public Nappula(int id, int x, int y, int vari, Lauta lauta) {
        
        this.id = id;
        this.vari = vari;
        
        this.x = x;
        this.y = y;
        
        onLiikkunut = false;
        
        siirrot = new ArrayList<>();
        blokit = new ArrayList<>();
        siirrotShakissa = new ArrayList<>();
        
        viimeksiPaivitetty = 0;

        this.lauta = lauta;
    }
    
    /**
     * Tarkistetaan onko ruudussa samanvärinen nappula tai ollaanko laudan ulkopuolella.
     * @param n Tarkistettava nappula
     * @return true tai false
     */
    public boolean omaNappula(Nappula n) {
        if (n == null) return false;
        
        if (n.getID() == -1) return true;
        
        return this.vari == n.getVari();
    }
    
    /**
     * Kuningattaren, lähetin ja tornin käyttämä metodi jolla katsotaan siirrot tiettyyn suuntaan.
     * @param xSuunta x-suunnan rvo. Esim. jos mennään oikealle päin, arvo on 1.
     * @param ySuunta y-suunnan arvo. Esim. jos mennään alaspäin, arvo on -1.
     * @param suunta menosuunta
     * @param kuninkaanSuunta kuninkaansuunta. Jos törmätään johonkin nappulaan ja tiedetään että kuningas on samassa suunnassa,
     * ruudujen katsomista jatketaan ja jos seuraava eteen tuleva nappula on kuningas, ensimmäiseksi vastaan tullut nappula kiinnitetään.
     */
    public void katsoRuudut (int xSuunta, int ySuunta, int suunta, int kuninkaanSuunta) {
        
        int x = this.x + xSuunta;
        int y = this.y + ySuunta;
        
        boolean sisalla = x >= lauta.ulkoL && x < 9 && y >= lauta.ulkoP && y < 10;
        
        while (sisalla) {
            
            if (lauta.lauta[x][y] == null) {
                // jos tyhjä ruutu
                this.siirrot.add(new Siirto(this.x, this.y, x, y, suunta, 0, false));
                x += xSuunta;
                y += ySuunta;
                sisalla = x >= lauta.ulkoL && x < 9 && y >= lauta.ulkoP && y < 10;
            } else if (omaNappula(lauta.lauta[x][y])) {
                // jos oma nappula
                blokit.add(new Koordinaatit(x,y));
                return;
            } else {
                // jos vastustajan nappula
                this.siirrot.add(new Siirto(this.x, this.y, x, y, suunta, 0, false));
                int kiinnitettavanX = x;
                int kiinnitettavanY = y;
                
                if (suunta != kuninkaanSuunta) {
                    return;
                }
                if (lauta.lauta[x][y].getTyyppi() == TYYPPI.KUNINGAS) {
                    // shakki
                    shakita(suunta);
                    this.siirrot.add(new Siirto(this.x, this.y, x + xSuunta, y + ySuunta, suunta, 0, false));
                    return;
                }
                
                x += xSuunta;
                y += ySuunta;
                while (true) {
                    if (lauta.lauta[x][y] == null) {
                        x += xSuunta;
                        y += ySuunta;
                        continue;
                    }
                    if (lauta.lauta[x][y].vari != this.vari && lauta.lauta[x][y].tyyppi == tyyppi.KUNINGAS) {
                        kiinnitysEnnen = lauta.lauta[kiinnitettavanX][kiinnitettavanY].kiinnitys;
                        lauta.lauta[kiinnitettavanX][kiinnitettavanY].kiinnitys = suunta;
                        kiinnitetty = lauta.lauta[kiinnitettavanX][kiinnitettavanY];
                        kiinnitysSuunta = suunta;
                        
                        return;
                    }
                    return;
                }
                    
            }
            
        }
        
    }
    /**
     * Ilmoittaa shakista ja päivittää shakkauksen tiedot.
     * @param suunta suunta mistä kuningasta shakataan.
     */
    public void shakita(int suunta) {
        if (lauta.shakitus > 0) {
            // Jos shakitus on jo yli 1, joku toinen nappula on shakittanut kuninkaan.
            // Palautetaan -1 joka tarkoittaa, että kaksi nappulaa shakkaa kuningasta.
            lauta.shakitus = -1;
            return;
        }
        lauta.shakitus = suunta;
        lauta.shakittajanX = x;
        lauta.shakittajanY = y;
        
    }
    
    /**
     * Palauttaa halutun kuninkaan suunnan, -1 jos ei ole suoraa suuntaa.
     * @param vari
     * @return 
     */
    public int kuninkaanSuunta(int vari) {
        
        int kuninkaanX;
        int kuninkaanY;
        
        if (vari == 0) {
            kuninkaanX = lauta.valkoisenNappulat.get(0).getX();
            kuninkaanY = lauta.valkoisenNappulat.get(0).getY();
        } else {
            kuninkaanX = lauta.mustanNappulat.get(0).getX();
            kuninkaanY = lauta.mustanNappulat.get(0).getY();
        }
        
        if (kuninkaanX == this.x) {
            if (kuninkaanY > this.y) return 4;
            return 3;
        }
        if (kuninkaanY == this.y) {
            if (kuninkaanX > this.x) return 1;
            return 2;
        }
        if (Math.abs(kuninkaanX - this.x) == Math.abs(kuninkaanY - this.y)) {
            if (kuninkaanX < this.x) {
                if (kuninkaanY > this.y) return 6;
                return 7;
            }
            if (kuninkaanY > this.y) return 8;
            return 5;
        }
        return -1;
    }
    
    /**
     * Päivittää siirrot jos kuningas on nappulan linjalla tai jos nappulalla on kiinnitys.
     */
    public void paivitaJos() {
        
        if (kiinnitysSuunta != 0) {
            paivita();
        }
        
        int suunta = kuninkaanSuunta(this.vari - 1);
        
        if (this.tyyppi == tyyppi.TORNI && suunta > 0 && suunta < 5) {
            paivita();
            return;
        }
        if (this.tyyppi == tyyppi.LAHETTI && suunta > 4) {
            paivita();
            return;
        }
        if (this.tyyppi == tyyppi.KUNINGATAR && suunta > 0) {
            paivita();
        }
    }
    
    public void paivitaSiirrot() {}
    
    /**
     * Päivittää nappulan siirrot ja lisää siirtojen sekä mahdollisen kiinnityksen tiedot tehdytsiirto-listaan.
     */
    public void paivita() {
        
        if (viimeksiPaivitetty == this.lauta.tehdytSiirrot.size()) return;
        
        if (tyyppi != TYYPPI.SOTILAS) {
            if (this.vari == 0) {
                päivitäHyökätyt(-1, lauta.valkoisenHyökätyt);
            } else {
                päivitäHyökätyt(-1, lauta.mustanHyökätyt);
            }
        }
        
        TehtySiirto tehtySiirto = this.lauta.tehdytSiirrot.get(this.lauta.tehdytSiirrot.size() - 1);
        
        tehtySiirto.lisaaNappula(this);
        tehtySiirto.lisaaSiirrot(this, siirrot);
        tehtySiirto.lisaaBlokit(this, blokit);
        tehtySiirto.lisaaViimeksiPaivitetty(this, viimeksiPaivitetty);
        
        if (kiinnitetty != null) {
            tehtySiirto.lisaaVanhaKiinnitys(this, kiinnitetty, kiinnitysSuunta);
            kiinnitetty.kiinnitys = 0;
            kiinnitysSuunta = 0;
            kiinnitetty = null;
        }
        
        this.blokit.clear();
        this.siirrot.clear();
        paivitaSiirrot();
        viimeksiPaivitetty = this.lauta.tehdytSiirrot.size();
        
        if (kiinnitetty != null) {
            tehtySiirto.lisaaUusiKiinnitys(this, kiinnitetty, kiinnitysEnnen);
        }
        
        if (tyyppi != TYYPPI.SOTILAS) {
            if (this.vari == 0) {
                päivitäHyökätyt(1, lauta.valkoisenHyökätyt);
            } else {
                päivitäHyökätyt(1, lauta.mustanHyökätyt);
            }
        }

    }
    
    /**
     * Päivittää nappuloiden siirrot shakissa.
     * @param ruudut shakkauksen linjan ruudut eli nappula voi blokata shakin liikkumalla listan ruutuihin.
     * jos shakitus on -1, shakkia ei voi blokata ja kuningas on ainoa nappula jota voi siirtää.
     */
    public void paivitaKunShakissa(ArrayList<Koordinaatit> ruudut) {
        this.siirrotShakissa.clear();
        
        if (lauta.shakitus == -1) {
            return;
        }
        
        for (Siirto siirto: this.siirrot) {
            boolean mahdollinen = false;
            for (int i = 0; i < ruudut.size(); i++) {
                if (ruudut.get(i).getX() == siirto.getUusX() && ruudut.get(i).getY() == siirto.getUusY()) {
                    mahdollinen = true;
                    break;
                }
            }
            if (mahdollinen) {
                this.siirrotShakissa.add(new Siirto(x, y, siirto.getUusX(), siirto.getUusY(), 0, 0, false));
            }
        }
    }
    
    /**
     * Päivittää halutun värin hyökätyt-taulukonarvoja.
     * @param p -1 jos otetaan arvoja pois, +1 jos lisätään.
     * @param hyokatyt jomman kumman värin hyökätyt-lista.
     */
    public void päivitäHyökätyt(int p, int[][] hyokatyt) {
        for (int i = 0; i < this.siirrot.size(); i++) {
            hyokatyt[this.siirrot.get(i).getUusX()][this.siirrot.get(i).getUusY()] += p;
        }
        for (int i = 0; i < this.blokit.size(); i++) {
            hyokatyt[this.blokit.get(i).getX()][this.blokit.get(i).getY()] += p;
        }
    }
    
    public int getNumero() {
        if (this == null) return 0;
        return this.numero;
    }
    
    public int getArvo() {
        return this.arvo;
    }
    
    public int getID() {
        if (this == null) return 0;
        return this.id;
    }
    
    public int getVari() {
        return this.vari;
    }
    
    public TYYPPI getTyyppi() {
        return this.tyyppi;
    }
    
    public void asetaKoordinaatit(int x, int y, boolean päivitä) {
        this.x = x;
        this.y = y;
        onLiikkunut = true;
    }
    
    public ArrayList<Siirto> getSiirrot() {
        return this.siirrot;
    }
    
    public ArrayList<Siirto> getSiirrotShakissa() {
        return this.siirrotShakissa;
    }
    
    public ArrayList<Koordinaatit> getBlokit() {
        return this.blokit;
    }
    
    public boolean onkoLiikkunut() {
        return this.onLiikkunut;
    }
    
    public void liikutettu(boolean b) {
        this.onLiikkunut = b;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    // Metodin avulla nappula tuodaan takaisin peruSiirto-metodissa.
    public void tuoTakaisin() {
        if (this.vari == 0) {
            päivitäHyökätyt(1, lauta.valkoisenHyökätyt);
        } else {
            päivitäHyökätyt(1, lauta.mustanHyökätyt);
        }
        this.syoty = false;
    }
    /**
     * Metodin avulla nappula syödään.
     */
    public void syö() {
        // Poistetaan nappulan hyökätyt ruudut, eli miinustetaan hyökätyistä ruuduista 1
        if (this.vari == 0) {
            päivitäHyökätyt(-1, lauta.valkoisenHyökätyt);
        } else {
            päivitäHyökätyt(-1, lauta.mustanHyökätyt);
        }
        this.syoty = true;
        
        TehtySiirto tehtySiirto = lauta.tehdytSiirrot.get(lauta.tehdytSiirrot.size() - 1);
        tehtySiirto.lisaaNappula(this);
        tehtySiirto.lisaaSiirrot(this, siirrot);
        tehtySiirto.lisaaBlokit(this, blokit);
        tehtySiirto.lisaaViimeksiPaivitetty(this, viimeksiPaivitetty);
        
        if (kiinnitetty != null) {
            tehtySiirto.lisaaVanhaKiinnitys(this, kiinnitetty, kiinnitysSuunta);
            kiinnitetty.kiinnitys = 0;
            kiinnitetty = null;
            kiinnitysSuunta = 0;
        }
    }
    
    public boolean vahvempiNappula(Nappula n) {
        if (this.vari == 0) {
            return n.arvo < this.arvo * (-1);
        }
        return n.arvo > this.arvo * (-1);
    }
    
    public int getKiinnitys() {
        return this.kiinnitys;
    }
    
    public int nappulanArvio() {
        return 0;
    }
    
    
}
