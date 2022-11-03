package shakki.domain;

import java.util.ArrayList;
import java.util.HashMap;
import shakki.nappulat.*;


/**
 * Luokka huolehtii pelilaudan toiminnasta. 
 * Shakkilautana toimii 10x12 taulukko. Luokassa on listat nappuloilla
 */
public class Lauta {
    
    public Nappula[][] lauta; // shakkilauta
    
    public int leveys; // taulukon leveys
    public int pituus; // taulukon pituus
    
    public int ulkoL; // taulukon ylimääräisen leveyden määrä kummallakin puolella
    public int ulkoP; // taulukon ylimääräisen pituuden määrä kummallakin puolella
    
    public ArrayList<Nappula> valkoisenNappulat; // lista valkoisen nappuloille
    public ArrayList<Nappula> mustanNappulat; // lista mustan nappuloille
    
    public ArrayList<TehtySiirto> tehdytSiirrot; // lista tehdyistä siirroista
    
    ArrayList<Siirto> valkoisenSiirrot; // lista valkoisen mahdollisista siirroista
    ArrayList<Siirto> mustanSiirrot; // lista mustan mahdollisista siirroista

    public int shakitus = 0; // shakitus-muuttuja kertoo shakituksen suunnan.
    // jos kaksi nappulaa shakittaa samanaikaisesti, muuttujan arvo on -1.
    public int shakittajanX = 0;
    public int shakittajanY = 0;
    
    public boolean valkoisenVuoro = true;
    
    public int[][] valkoisenHyökätyt; //
    public int[][] mustanHyökätyt;
    
    int id = 0; // nappuloiden id
    
    public Lauta() {

        leveys = 10;
        pituus = 12;
        
        ulkoL = 1;
        ulkoP = 2;
        
        lauta = new Nappula[leveys][pituus];
        
        valkoisenNappulat = new ArrayList<>();
        mustanNappulat = new ArrayList<>();
        tehdytSiirrot = new ArrayList<>();
        
        valkoisenHyökätyt = new int[leveys][pituus];
        mustanHyökätyt = new int[leveys][pituus];
        
        valkoisenSiirrot = new ArrayList<>();
        mustanSiirrot = new ArrayList<>();
    }
    
    /**
     * Metodi asettaa nappulat laudalle ja listoille fen-merkkijonon mukaan.
     * @param fen merkkijono, jonka perusteella nappulat asetetaan.
     */
    
    public void asetaLauta(String fen) {
        
        int x = ulkoL;
        int y = pituus - ulkoP - 1;
        
        for (int i = 0; i < fen.length(); i++) {
            
            char c = fen.charAt(i);
            
            if (Character.isLetter(c)) {
                lisaaKirjaimella(c, x, y);
                x++;
            } else if (c == '/') {
                y--;
                x = ulkoL;
            } else if (Character.isDigit(c)) {
                x += Character.getNumericValue(c);
            }
            
        }
        tehdytSiirrot.add(new TehtySiirto(0,0,null,null,null,false,0));
        
        for (int i = 0; i < leveys; i++) {
            lauta[i][0] = new EpäNappula(i, 0, this);
            lauta[i][1] = new EpäNappula(i, 1, this);
            lauta[i][pituus - 1] = new EpäNappula(i, pituus - 1, this);
            lauta[i][pituus - 2] = new EpäNappula(i, pituus - 2, this);
            
            if (i > 1) {
                lauta[0][i] = new EpäNappula(0, i, this);
                lauta[leveys - ulkoL][i] = new EpäNappula(leveys - ulkoL, i, this);
            }
            
        }
        
        for (Nappula n : valkoisenNappulat) {
            n.paivita();
        }
        for (Nappula n : mustanNappulat) {
            n.paivita();
        }
        
        if (valkoisenVuoro) {
            valkoisenSiirrot = getSiirrot(0, false);
        } else {
            mustanSiirrot = getSiirrot(1, false);
        }
        
    }
    /**
     * Metodi tarkistaa kirjaimen ja kutsuu lisäänappula-metodia
     * @param k fen-merkkijonossa oleva kirjain
     * @param x x-koordinaatti
     * @param y y-koordinaatti
     */
    public void lisaaKirjaimella(char k, int x, int y) {
        id++;
        int vari = 0;
        
        if (Character.isLowerCase(k)) {
            vari = 1;
        }
        char n = Character.toLowerCase(k);
        
        if (n == 'p') {
            lisaaNappula(1, x, y, vari);
        } else if (n == 'r') {
            lisaaNappula(4, x, y, vari);
        } else if (n == 'n') {
            lisaaNappula(2, x, y, vari);
        } else if (n == 'b') {
            lisaaNappula(3, x, y, vari);
        } else if (n == 'q') {
            lisaaNappula(5, x, y, vari);
        } else if (n == 'k') {
            lisaaNappula(6, x, y, vari);
        }
    }
    /**
     * Metodi lisää numeron perusteella nappulan laudalle 
     * @param numero lisättävä nappula
     * @param x ruudun x-koordinaatti
     * @param y ruudun y-koordinaatti
     * @param vari nappulan väri
     */
    public void lisaaNappula(int numero, int x, int y, int vari) {
        switch (numero) {
            case 1:
                Sotilas sotilas = new Sotilas(id, x, y, vari, this);
                lauta[x][y] = sotilas;
                if (vari == 0) valkoisenNappulat.add(sotilas);
                else mustanNappulat.add(sotilas);
                break;
            case 2:
                Ratsu ratsu = new Ratsu(id, x, y, vari, this);
                lauta[x][y] = ratsu;
                if (vari == 0) valkoisenNappulat.add(ratsu);
                else mustanNappulat.add(ratsu);
                break;
            case 3:
                Lähetti lähetti = new Lähetti(id, x, y, vari, this);
                lauta[x][y] = lähetti;
                if (vari == 0) valkoisenNappulat.add(lähetti);
                else mustanNappulat.add(lähetti);
                break;
            case 4:
                Torni torni = new Torni(id, x, y, vari, this);
                lauta[x][y] = torni;
                if (vari == 0) valkoisenNappulat.add(torni);
                else mustanNappulat.add(torni);
                break;
            case 5:
                Kuningatar kuningatar = new Kuningatar(id, x, y, vari, this);
                lauta[x][y] = kuningatar;
                if (vari == 0) valkoisenNappulat.add(kuningatar);
                else mustanNappulat.add(kuningatar);
                break;
            case 6:
                Kuningas kuningas = new Kuningas(id, x, y, vari, this);
                lauta[x][y] = kuningas;
                if (vari == 0) valkoisenNappulat.add(0, kuningas);
                else mustanNappulat.add(0, kuningas);
                break;
            default:
                break;
        }
    }
    
    /**
     * Metodi suorittaa nappuloiden siirrot. Metodille annetaan nappulan koordinaatit ja koordinaatit mihin nappula siirretään.
     * @param x siirrettävän nappulan x-koordinaatti.
     * @param y siirrettävän nappulan y-koordinaatti.
     * @param uusX nappulan uusi x-koordinaatti.
     * @param uusY nappulan uusi y-koordinaatti.
     * @return Jos siirrolla syötiin nappula, palautetaan sen id. Jos ei, palautetaan 0. Jos siirto epäonnistui, palautetaan -1.
     */
    public int teeSiirto(int x, int y, int uusX, int uusY) {
        boolean mahdollinenSiirto = false;
        boolean ohestalyönti = false;
        
        if (valkoisenVuoro) {
            for (Siirto s : valkoisenSiirrot) {
                if (x == s.getX() && y ==s.getY() && uusX == s.getUusX() && uusY == s.getUusY()) {
                    mahdollinenSiirto = true;
                    ohestalyönti = s.getOhestalyönti();
                    break;
                }
            }
        } else {
            for (Siirto s : mustanSiirrot) {
                if (x == s.getX() && y ==s.getY() && uusX == s.getUusX() && uusY == s.getUusY()) {
                    mahdollinenSiirto = true;
                    ohestalyönti = s.getOhestalyönti();
                    break;
                }
            }
        }

        if (mahdollinenSiirto) {

            Nappula n = lauta[x][y];

            // Tarkistetaan onko kyseessä linnoitus (tornitus), jolloin käytetään eri metodia
            if (Math.abs(n.getNumero()) == 6 && Math.abs(x - uusX) == 2) {
                int s = teeLinnoitus(n, x, y, uusX);
                return s;
            }

            int s = 0;

            Nappula syötyNappula = null;

            if (lauta[uusX][uusY] != null) {
                syötyNappula = lauta[uusX][uusY];
                s = syötyNappula.getID();
                poistaNappula(syötyNappula);
            }
            int syötävänY = uusY - 1;
            if (ohestalyönti) {
                if (n.getVari() == 1) {
                    syötävänY = uusY + 1;
                }
                syötyNappula = lauta[uusX][syötävänY];
                s = syötyNappula.getID();
                poistaNappula(syötyNappula);
                lauta[uusX][syötävänY] = null;
            }

            lisaaTehtySiirto(x, y, n, null, syötyNappula);
            if (shakitus != 0) {
                lisaaSiirrotShakissa();
            }
            if (syötyNappula != null) {
                syötyNappula.syö();
            }
            
            shakitus = 0;
            shakittajanX = 0;
            shakittajanY = 0;
                 
            lauta[uusX][uusY] = n;
            lauta[uusX][uusY].asetaKoordinaatit(uusX, uusY, true);
            lauta[x][y] = null;
            if (Math.abs(n.getNumero()) != 6) lauta[uusX][uusY].paivita();
            
            paivitaTarvittavat(x, y, uusX, uusY, n.getVari());
            if (ohestalyönti) {
                paivitaTarvittavat(uusX, syötävänY, uusX, syötävänY, n.getVari());
            }
            paivitaKuninkaat(n.getVari());

            n.liikutettu(true);

            valkoisenVuoro = !valkoisenVuoro;
            
            if (shakitus != 0) {
//                if (n.getVari() == 0) {
//                    System.out.println("Valkoisen shakitus x: " + shakittajanX + ", y: " + shakittajanY + ", " + lauta[shakittajanX][shakittajanY].getTyyppi());
//                } else {
//                    System.out.println("Mustan shakitus x: " + shakittajanX + ", y: " + shakittajanY + ", " + lauta[shakittajanX][shakittajanY].getTyyppi());
//                }
                paivitaShakissa(shakittajanX, shakittajanY, shakitus);
            }

            if (valkoisenVuoro) {
                valkoisenSiirrot = getSiirrot(0, true);
            } else {
                mustanSiirrot = getSiirrot(1, true);
            }
            
            return s; 
        }
        
        System.out.println("Siirto epäonnistui");
        // siirtoa ei voitu suorittaa
        return -1;
    }
    
    /**
     * Metodi lisää nappuloiden siirrot shakissa, tehtyjen siirtojen listaan.
     */
    public void lisaaSiirrotShakissa() {
        TehtySiirto tehtySiirto = tehdytSiirrot.get(tehdytSiirrot.size() - 1);
        tehtySiirto.shakitus = this.shakitus;
        if (valkoisenVuoro) {
            for (Nappula n : valkoisenNappulat) {
                tehtySiirto.lisaaSiirrotShakissa(n, n.getSiirrotShakissa());
            }
        } else {
            for (Nappula n : mustanNappulat) {
                tehtySiirto.lisaaSiirrotShakissa(n, n.getSiirrotShakissa());
            }
        }
    }
    
    /**
     * Metodi suorittaa kuninkaan ja tornin linnoituksen.
     * @param kuningas Kuningas-nappula
     * @param x kuninkaan x-koordinaatti.
     * @param y kuninkaan y-koordinaatti.
     * @param uusX kuninkaan uusi x-koordinaatti.
     * @return tornin id+50.
     */
    public int teeLinnoitus(Nappula kuningas, int x, int y, int uusX) {
        
        
        int torniX = 0;
        int torniUusX = 0;
        if (x < uusX) {
            torniUusX = ulkoL + 5;
            torniX = ulkoL + 7;
        } else {
            torniUusX = ulkoL + 3;
            torniX = ulkoL;
        }
        Nappula torni = lauta[torniX][y];
        lisaaTehtySiirto(x, y, kuningas, torni, null);
        
        lauta[uusX][y] = kuningas;
        kuningas.asetaKoordinaatit(uusX, y, true);
        lauta[x][y] = null;
        
        lauta[torniUusX][y] = torni;
        torni.asetaKoordinaatit(torniUusX, y, true);
        lauta[torniX][y] = null;
        
        torni.liikutettu(true);
        kuningas.liikutettu(true);
        
        shakitus = 0;
        shakittajanX = 0;
        shakittajanY = 0;
        
        paivitaTarvittavat(torniX, y, torniUusX, y, kuningas.getVari());
        paivitaTarvittavat(x, y, uusX, y, kuningas.getVari());
        paivitaKuninkaat(kuningas.getVari());
        valkoisenVuoro = !valkoisenVuoro;
        
        if (shakitus != 0) {
            paivitaShakissa(shakittajanX, shakittajanY, shakitus);
        }
        
        if (valkoisenVuoro) {
            getSiirrot(0, true);
        } else {
            getSiirrot(1, true);
        }
        
        return torni.getID() + 50;
    }
    
    /**
     * Lisätään siirto tehtyjen siirtojen listaan.
     * @param x siirrettävän nappulan (vanha) x-koordinaatti.
     * @param y siirrettävän nappulan (vanha) y-koordinaatti.
     * @param nappula siirrettävä nappula.
     * @param torni jos kyseessä oli linnoitus, torni-nappula. Muuten null.
     * @param syöty siirrossa syöty nappula. Jos nappulaa ei syöty, null.
     */
    public void lisaaTehtySiirto(int x, int y, Nappula nappula, Nappula torni, Nappula syöty) {
        TehtySiirto tehtySiirto = new TehtySiirto(x, y, nappula, torni, syöty, nappula.onkoLiikkunut(), this.shakitus);
        int[][] valkoisen = new int[10][12];
        int[][] mustan = new int[10][12];
        for (int i = ulkoL; i < leveys - ulkoL; i++) {
            for (int j = ulkoP; j < pituus - ulkoP; j++) {
                valkoisen[i][j] = valkoisenHyökätyt[i][j];
                mustan[i][j] = mustanHyökätyt[i][j];
            }
        }
        tehtySiirto.mustanHyokatyt = mustan;
        tehtySiirto.valkoisenHyokatyt = valkoisen;
        tehdytSiirrot.add(tehtySiirto);
    }
    
    /**
     * Metodi katsoo, minkä nappuloiden siirtoihin tehty siirto mahdollisesti vaikuttaa ja päivittää näiden siirrot.
     * @param x tehdyn siirron vanha x-koordinaatti.
     * @param y tehdyn siirron vanha y-koordinaatti.
     * @param uusX tehdyn siirron uusi x-koordinaatti.
     * @param uusY tehdyn siirron uusi x-koordinaatti.
     * @param vari siirretyn nappulan väri.
     */
    public void paivitaTarvittavat(int x, int y, int uusX, int uusY, int vari) {
          
        // Aluksi käydään valkoiset nappulat läpi ja katsotaan mitä siirtoja pitää päivittää
        for (int i = 1; i < valkoisenNappulat.size(); i++) {
            ArrayList<Siirto> siirrot = valkoisenNappulat.get(i).getSiirrot();
            for (int j = 0; j < siirrot.size(); j++) {
                if ((x == siirrot.get(j).getUusX() && y == siirrot.get(j).getUusY()) || (uusX == siirrot.get(j).getUusX() && uusY == siirrot.get(j).getUusY())) {
                    valkoisenNappulat.get(i).paivita();
                    break;
                }
            }
            ArrayList<Koordinaatit> blokit = valkoisenNappulat.get(i).getBlokit();
            for (int j = 0; j < blokit.size(); j++) {
                if ((x == blokit.get(j).getX() && y == blokit.get(j).getY()) || (uusX == blokit.get(j).getX() && uusY == blokit.get(j).getY())) {
                    valkoisenNappulat.get(i).paivita();
                    break;
                }
            }
        }
        // Sitten mustat nappulat
        for (int i = 1; i < mustanNappulat.size(); i++) {
            ArrayList<Siirto> siirrot = mustanNappulat.get(i).getSiirrot();
            for (int j = 0; j < siirrot.size(); j++) {
                if ((x == siirrot.get(j).getUusX() && y == siirrot.get(j).getUusY()) || (uusX == siirrot.get(j).getUusX() && uusY == siirrot.get(j).getUusY())) {
                    mustanNappulat.get(i).paivita();
                    break;
                }
            }
            ArrayList<Koordinaatit> blokit = mustanNappulat.get(i).getBlokit();
            for (int j = 0; j < blokit.size(); j++) {
                if ((x == blokit.get(j).getX() && y == blokit.get(j).getY()) || (uusX == blokit.get(j).getX() && uusY == blokit.get(j).getY())) {
                    mustanNappulat.get(i).paivita();
                    break;
                }
            }
        }
        // Tarkistetaan tarvitseeko vastustajan sotilaita päivittää.
        
        if (vari == 0) {
            if (lauta[uusX + 1][uusY + 1] != null && lauta[uusX + 1][uusY + 1].getNumero() == -1) {
                lauta[uusX + 1][uusY + 1].paivita();
            } 
            if (lauta[uusX - 1][uusY + 1] != null && lauta[uusX - 1][uusY + 1].getNumero() == -1) {
                lauta[uusX - 1][uusY + 1].paivita();
            }
            if (lauta[uusX - 1][uusY] != null && lauta[uusX - 1][uusY].getNumero() == -1) {
                lauta[uusX - 1][uusY].paivita();
            }
            if (lauta[uusX + 1][uusY] != null && lauta[uusX + 1][uusY].getNumero() == -1) {
                lauta[uusX + 1][uusY].paivita();
            }
        } else {
            if (lauta[uusX + 1][uusY - 1] != null && lauta[uusX + 1][uusY - 1].getNumero() == 1) {
                lauta[uusX + 1][uusY - 1].paivita();
            }
            if (lauta[uusX - 1][uusY - 1] != null && lauta[uusX - 1][uusY - 1].getNumero() == 1) {
                lauta[uusX - 1][uusY - 1].paivita();
            }
            if (lauta[uusX - 1][uusY] != null && lauta[uusX - 1][uusY].getNumero() == 1) {
                lauta[uusX - 1][uusY].paivita();
            }
            if (lauta[uusX + 1][uusY] != null && lauta[uusX + 1][uusY].getNumero() == 1) {
                lauta[uusX + 1][uusY].paivita();
            }
        }
        
        // Jos siirron jompi kumpi ruutu on kuninkaan linjalla, tarkistetaan onko linjalla myös 
        if (kuninkaanSuunta(0, x, y) > 0 || kuninkaanSuunta(0, uusX, uusY) > 0) {
            for (Nappula n : mustanNappulat) {
                if (n.getNumero() < -2 && n.getNumero() > -6) {
                    n.paivitaJos();
                }
            }
        }
        
        if (kuninkaanSuunta(1, x, y) > 0 || kuninkaanSuunta(1, uusX, uusY) > 0) {
            for (Nappula n : valkoisenNappulat) {
                if (n.getNumero() > 2 && n.getNumero() < 6) {
                    n.paivitaJos();
                }
            }
        }
        
    }
    /**
     * Metodi päivittää kuninkaiden siirrot. Kuninkaiden siirrot päivitetään muiden nappuloiden jälkeen.
     * @param vari viimeisimmän siirron suorittajan väri
     */
    public void paivitaKuninkaat(int vari) {
        if (vari == 0) {
            valkoisenNappulat.get(0).paivita();
            mustanNappulat.get(0).paivita();
        } else {
            mustanNappulat.get(0).paivita();
            valkoisenNappulat.get(0).paivita();
        }
    }
    /**
     * Palauttaa halutun värin siirrot.
     * @param vari halutun värisen armeijan siirrot
     * @param päivitä päivitetäänkö siirrot samalla.
     * @return siirrot
     */
    public ArrayList<Siirto> getSiirrot(int vari, boolean päivitä) {
        ArrayList<Siirto> siirrot = new ArrayList<>();
        
        if (vari == 0) {
            if (shakitus != 0) {
                for (int i = 0; i < valkoisenNappulat.size(); i++) {
                    for (Siirto s : valkoisenNappulat.get(i).getSiirrotShakissa()) {
                        if (voiSiirtää(s, valkoisenNappulat.get(i).getKiinnitys())) {
                            siirrot.add(new Siirto(s.getX(), s.getY(), s.getUusX(), s.getUusY(), s.getSuunta(), s.getVahvuus(), s.getOhestalyönti()));
                        }  
                    }
                }
            } else {
                for (int i = 0; i < valkoisenNappulat.size(); i++) {
                    for (Siirto s : valkoisenNappulat.get(i).getSiirrot()) {
                        if (voiSiirtää(s, valkoisenNappulat.get(i).getKiinnitys())) {
                            siirrot.add(new Siirto(s.getX(), s.getY(), s.getUusX(), s.getUusY(), s.getSuunta(), s.getVahvuus(), s.getOhestalyönti()));
                        }  
                    }
                }
            }
        } else {
            if (shakitus != 0) {
                for (int i = 0; i < mustanNappulat.size(); i++) {
                    for (Siirto s : mustanNappulat.get(i).getSiirrotShakissa()) {
                        if (voiSiirtää(s, mustanNappulat.get(i).getKiinnitys())) {
                            siirrot.add(new Siirto(s.getX(), s.getY(), s.getUusX(), s.getUusY(), s.getSuunta(), s.getVahvuus(), s.getOhestalyönti()));
                        }   
                    }
                }
            } else {
            for (int i = 0; i < mustanNappulat.size(); i++) {
                    for (Siirto s : mustanNappulat.get(i).getSiirrot()) {
                        if (voiSiirtää(s, mustanNappulat.get(i).getKiinnitys())) {
                            siirrot.add(new Siirto(s.getX(), s.getY(), s.getUusX(), s.getUusY(), s.getSuunta(), s.getVahvuus(), s.getOhestalyönti()));
                        }
                    }
                }
            }
        }
        
        siirrot.sort((s1, s2) -> Integer.compare(s1.getVahvuus(), s2.getVahvuus()));
        
        if (vari == 0 && päivitä) {
            valkoisenSiirrot = siirrot;
        } else if (päivitä){
            mustanSiirrot = siirrot;
        }
        return siirrot;
    }
    
    /**
     * Metodi tutkii kiinnityksen perusteella voiko nappulaa siirtää. 
     * Jos siirto on ohestalyönti, metodi varmistaa ettei kuningas paljastu siirron jälkeen.
     * @param s siirto
     * @param kiinnitys nappulan kiinnitys
     * @return true tai false
     */
    public boolean voiSiirtää(Siirto s, int kiinnitys) {
        if (s.getOhestalyönti()) {
            int x = s.getX();
            int y = s.getY();
            int uusX = s.getUusX();
            int vari = lauta[x][y].getVari();
            TehtySiirto ts = tehdytSiirrot.get(tehdytSiirrot.size() - 1);
            if (!(ts.nappula.getArvo() == 10 && ts.nappula.getX() == uusX && ts.nappula.getY() == y && Math.abs(ts.y - y) == 2)) {
                return false;
            }
            int kuninkaanSuunta = kuninkaanSuunta(vari, x, y);
            System.out.println(kuninkaanSuunta);
            if (kuninkaanSuunta > 0 && kuninkaanSuunta < 3) {
                int xSuunta = 1;
                if (kuninkaanSuunta == 2) {
                    xSuunta = -1;
                }
                x += xSuunta;
                while (true) {
                    if (x == uusX || lauta[x][y] == null) {
                        x += xSuunta;
                    } else if (lauta[x][y].getArvo() == 1000 && lauta[x][y].getVari() == vari) {
                        x = s.getX();
                        xSuunta *= -1;
                        x += xSuunta;
                        // Katsotaan, ettei oma kuningas jää vastustajan kuningattaren tai tornin linjalle. 
                        while (true) {
                            if (lauta[x][y] == null || x == uusX) {
                                x += xSuunta;
                            } else if (lauta[x][y].getVari() != vari && (lauta[x][y].getArvo() == 90 || lauta[x][y].getArvo() == 50)) {
                                return false;
                            } else {
                                break;
                            }
                        }
                        break;
                    } else {
                        break;
                    }
                }
            }
        }
        int suunta = s.suunta;
        if (kiinnitys == 0) return true;
        
        if (suunta > 0 && suunta < 3 && kiinnitys < 3) return true;
        
        if (suunta > 2 && suunta < 5 && kiinnitys > 2 && kiinnitys < 5) return true;
        
        if (suunta > 4 && suunta < 7 && kiinnitys > 4 && kiinnitys < 7) return true;
        
        return suunta > 6 && suunta < 9 && kiinnitys > 6 && kiinnitys < 9;
    }
    
    /**
     * Päivittää siirrot shakki-tilanteessa.
     * @param x shakittajan x-koordinaatti.
     * @param y shakittajan y-koordinaatti.
     * @param suunta shakittajan suunta kuninkaaseen.
     */
    public void paivitaShakissa(int x, int y, int suunta) {
        
        ArrayList<Koordinaatit> ruudut = new ArrayList<>();
        ruudut.add(new Koordinaatit(x, y));
        
        if (suunta == 1) {
            for (int i = x + 1; true; i++) {
                if (lauta[i][y] == null) ruudut.add(new Koordinaatit(i, y));
                else {
                    break;
                }
            }
        } else if (suunta == 2) {
            for (int i = x - 1; true; i--) {
                if (lauta[i][y] == null) ruudut.add(new Koordinaatit(i, y));
                else {
                    break;
                }
            }
        } else if (suunta == 3) {
            for (int i = y - 1; true; i--) {
                if (lauta[x][i] == null) ruudut.add(new Koordinaatit(x, i));
                else {
                    break;
                }
            }
        } else if (suunta == 4) {
            for (int i = y + 1; true; i++) {
                if (lauta[x][i] == null) ruudut.add(new Koordinaatit(x, i));
                else {
                    break;
                }
            }
        } else if (suunta == 5) {
            for (int i = x + 1, j = y - 1; true; i++, j--) {
                if (lauta[i][j] == null) ruudut.add(new Koordinaatit(i, j));
                else {
                    break;
                }
            }
        } else if (suunta == 6) {
            for (int i = x - 1, j = y + 1; true; i--, j++) {
                if (lauta[i][j] == null) ruudut.add(new Koordinaatit(i, j));
                else {
                    break;
                }
            }
        } else if (suunta == 7) {
            for (int i = x - 1, j = y - 1; true; i--, j--) {
                if (lauta[i][j] == null) ruudut.add(new Koordinaatit(i, j));
                else {
                    break;
                }
            }
        } else if (suunta == 8) {
            for (int i = x + 1, j = y + 1; true; i++, j++) {
                if (lauta[i][j] == null) ruudut.add(new Koordinaatit(i, j));
                else {
                    break;
                }
            }
        }  
        
        if (valkoisenVuoro) {
            for (Nappula n: valkoisenNappulat) {
                n.paivitaKunShakissa(ruudut);
            }
        } else {
            for (Nappula n: mustanNappulat) {
                n.paivitaKunShakissa(ruudut);
            }
        }
        
    }
    /**
     * Metodi palauttaa halutun värisen kuninkaan suunnan.
     * @param vari halutun kuninkaan väri
     * @param x x-koordinaatti josta suunta katsotaan
     * @param y y-koordinaatti josta suunta katsotaan
     * @return kuninkaan suunta. -1 jos suuntaa ei ole.
     */
    public int kuninkaanSuunta(int vari, int x, int y) {
        
        int kuninkaanX;
        int kuninkaanY;
        
        if (vari == 0) {
            kuninkaanX = valkoisenNappulat.get(0).getX();
            kuninkaanY = valkoisenNappulat.get(0).getY();
        } else {
            kuninkaanX = mustanNappulat.get(0).getX();
            kuninkaanY = mustanNappulat.get(0).getY();
        }
        if (kuninkaanX == x) {
            if (kuninkaanY > y) return 4;
            return 3;
        }
        if (kuninkaanY == y) {
            if (kuninkaanX > x) return 1;
            return 2;
        }
        if (Math.abs(kuninkaanX - x) == Math.abs(kuninkaanY - y)) {
            if (kuninkaanX < x) {
                if (kuninkaanY > y) return 6;
                return 7;
            }
            if (kuninkaanY > y) return 8;
            return 5;
        }
        return -1;
    }
    
    
    /**
     * Poistaa nappulan nappuloiden listalta.
     * @param n poistettaba nappula
     */
    public void poistaNappula(Nappula n) {
        if (n.getVari() == 0) {
            valkoisenNappulat.remove(n);
        } else {
            mustanNappulat.remove(n);
        }
    }
    /**
     * Metodi peruu viimeisimmän tehdyn siirron.
     */
    public void peruSiirto() {
        
        TehtySiirto siirto = this.tehdytSiirrot.get(this.tehdytSiirrot.size() - 1);
        
        int x = siirto.x; // koordinaatit, johon nappula palautetaan
        int y = siirto.y;
        
        int vanhaX = siirto.nappula.getX(); // koordinaatit, johon nappula oli siirretty
        int vanhaY = siirto.nappula.getY();
        
        int vari = siirto.nappula.getVari();
        
        lauta[x][y] = siirto.nappula;
        siirto.nappula.asetaKoordinaatit(x, y, false);
        if (siirto.oliLiikkunut == false) siirto.nappula.liikutettu(false);
        
        // Palautetaan myös syöty nappula, tai mahdollinen linnoituksessa myös siirtynyt torni.
        lauta[vanhaX][vanhaY] = null;
        if (siirto.syotyNappula != null) {
            lauta[siirto.syotyNappula.getX()][siirto.syotyNappula.getY()] = siirto.syotyNappula;
            if (siirto.syotyNappula.getVari() == 0) {
                valkoisenNappulat.add(siirto.syotyNappula);
            } else {
                mustanNappulat.add(siirto.syotyNappula);
            }
            siirto.syotyNappula.tuoTakaisin();
        } else if (siirto.torni != null) {
            int torniX = siirto.torni.getX();
            if (torniX > x) {
                siirto.torni.asetaKoordinaatit(leveys - ulkoL - 1, y, true);
                lauta[leveys - ulkoL - 1][y] = siirto.torni;
                lauta[torniX][y] = null;
            } else {
                siirto.torni.asetaKoordinaatit(ulkoL, y, true);
                lauta[ulkoL][y] = siirto.torni;
                lauta[torniX][y] = null;
            }
            siirto.torni.liikutettu(false);
        }
        
        if (siirto.nappula.kiinnitetty != null) {
            siirto.nappula.kiinnitetty.kiinnitys = 0;
            siirto.nappula.kiinnitetty = null;
            siirto.nappula.kiinnitysSuunta = 0;
        }
        
        
        for (Nappula n : siirto.nappulat) {
            n.siirrot = siirto.nappuloidenSiirrot.get(n);
            n.blokit = siirto.nappuloidenBlokit.get(n);
            n.viimeksiPaivitetty = siirto.viimeksiPaivitetty.get(n);
            
            Nappula uusiKiinnitetty = siirto.uudetKiinnitetyt.get(n);
            if (uusiKiinnitetty != null) {
                uusiKiinnitetty.kiinnitys = siirto.kiinnitysEnnen.get(n);
                n.kiinnitetty = null;
                n.kiinnitysSuunta = 0;
            }
            
            Nappula vanhaKiinnitetty = siirto.kiinnitetyt.get(n);
            if (vanhaKiinnitetty != null) {
//                System.out.println("");
//                System.out.println("Siirrossa " + this.tehdytSiirrot.size());
//                System.out.println("Kiinnitys palautetaan nappulalle " + n.getTyyppi() + ", kiinnitetty " + vanhaKiinnitetty.getTyyppi() + ", id: " + vanhaKiinnitetty.getID() + ", suunta: " + siirto.kiinnitykset.get(n));
                n.kiinnitetty = vanhaKiinnitetty;
                n.kiinnitysSuunta = siirto.kiinnitykset.get(n);
                vanhaKiinnitetty.kiinnitys = n.kiinnitysSuunta;
            }
        }
        
        if (siirto.shakitus != 0) {
            if (vari == 0) {
                for (Nappula n : valkoisenNappulat) {
                    n.siirrotShakissa = siirto.siirrotShakissa.get(n);
                }
            } else {
                for (Nappula n : mustanNappulat) {
                    n.siirrotShakissa = siirto.siirrotShakissa.get(n);
                }
            }
        }
        shakitus = siirto.shakitus;
        
        valkoisenHyökätyt = siirto.valkoisenHyokatyt;
        mustanHyökätyt = siirto.mustanHyokatyt;
        
        valkoisenVuoro = !valkoisenVuoro;
        
        this.tehdytSiirrot.remove(tehdytSiirrot.size() - 1);
        if (valkoisenVuoro) {
            getSiirrot(0, true);
        } else {
            getSiirrot(1, true);
        }
        
    }
    
    
    
}
