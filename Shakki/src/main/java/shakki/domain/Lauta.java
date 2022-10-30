package shakki.domain;

import java.util.ArrayList;
import java.util.HashMap;
import shakki.nappulat.*;


/**
 * Luokka huolehtii pelilaudan toiminnasta.
 */
public class Lauta {
    
    public Nappula[][] lauta;
    
    public int leveys; // taulukon leveys
    public int pituus; // taulukon pituus
    
    public int ulkoL; // taulukon ylimääräisen leveyden määrä kummallakin puolella
    public int ulkoP; // taulukon ylimääräisen pituuden määrä kummallakin puolella
    
    //ArrayList<Koordinaatit> valkoisenNappulat;
    //ArrayList<Koordinaatit> mustanNappulat;
    
    public ArrayList<Nappula> valkoisenNappulat;
    public ArrayList<Nappula> mustanNappulat;
    
    public ArrayList<TehtySiirto> tehdytSiirrot;
    
    ArrayList<Siirto> valkoisenSiirrot;
    ArrayList<Siirto> mustanSiirrot;

    public int shakitus = 0;
    public int shakittajanX = 0;
    public int shakittajanY = 0;
    
    boolean valkoisenVuoro = true;
    
    // Jos esim. tornin ja vastustajan kuninkaan välillä on kaksi nappulaa ja ensimmäinen on vastustajan, 
    // jälkimmäisen nappulan koordinaatit säilytetään tässä. Jos jälkimmäinen liikkuu pois linjalta, tornin siirrot päivitetään ja
    // jonka jälkeen jäljellä välissä oleva nappula on kiinnitetty.
    public HashMap<Koordinaatit, Nappula> kuninkaanLinjanBlokit;
    
    public int[][] valkoisenHyökätyt;
    public int[][] mustanHyökätyt;
    
    int id = 0;
    
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
            valkoisenSiirrot = getSiirrot(0, shakitus);
        } else {
            mustanSiirrot = getSiirrot(1, shakitus);
        }
        
    }
    
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
     * @param x 
     * @param y
     * @param uusX
     * @param uusY
     * @return 
     */
    public int teeSiirto(int x, int y, int uusX, int uusY) {
        System.out.println("x: " + x + ", y: " + y + ", uusi x: " + uusX + ", uus Y: " + uusY);
        System.out.println("");
        boolean mahdollinenSiirto = false;
        
        if (valkoisenVuoro) {
            for (Siirto s : valkoisenSiirrot) {
                if (x == s.getX() && y ==s.getY() && uusX == s.getUusX() && uusY == s.getUusY()) {
                    mahdollinenSiirto = true;
                }
            }
        } else {
            for (Siirto s : mustanSiirrot) {
                if (x == s.getX() && y ==s.getY() && uusX == s.getUusX() && uusY == s.getUusY()) {
                    mahdollinenSiirto = true;
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
                syötyNappula.syö();
            }

            lisaaTehtySiirto(x, y, n, null, syötyNappula);
            if (shakitus > 0) {
                lisaaSiirrotShakissa();
            }
            
            shakitus = 0;
            shakittajanX = 0;
            shakittajanY = 0;
                 
            lauta[uusX][uusY] = n;
            lauta[uusX][uusY].asetaKoordinaatit(uusX, uusY);
            lauta[x][y] = null;
            if (Math.abs(n.getNumero()) != 6) lauta[uusX][uusY].paivita();
            
            paivitaTarvittavat(x, y, uusX, uusY, n.getVari());

            n.liikutettu(true);

            valkoisenVuoro = !valkoisenVuoro;
            
            if (shakitus != 0) {
                paivitaShakissa(shakittajanX, shakittajanY, shakitus);
            }

            if (valkoisenVuoro) {
                valkoisenSiirrot = getSiirrot(0, shakitus);
            } else {
                mustanSiirrot = getSiirrot(1, shakitus);
            }
            
            return s; 
        }
        System.out.println("SIIRTO EPÄONNISTUI");
        // siirtoa ei voinut tehdä
        return -1;
    }
    
    public void lisaaSiirrotShakissa() {
        TehtySiirto tehtySiirto = tehdytSiirrot.get(tehdytSiirrot.size() - 1);
        tehtySiirto.shakitus = this.shakitus;
        if (valkoisenVuoro) {
            for (Nappula n : valkoisenNappulat) {
                tehtySiirto.siirrotShakissa.put(n, n.getSiirrotShakissa());
            }
        } else {
            for (Nappula n : mustanNappulat) {
                tehtySiirto.siirrotShakissa.put(n, n.getSiirrotShakissa());
            }
        }
    }
    
    /**
     * Metodi suorittaa kuninkaan ja tornin linnoituksen
     * @param kuningas
     * @param x
     * @param y
     * @param uusX
     * @return 
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
        
        lauta[uusX][y] = kuningas;
        kuningas.asetaKoordinaatit(uusX, y);
        lauta[x][y] = null;
        
        Nappula torni = lauta[torniX][y];
        lauta[torniUusX][y] = torni;
        torni.asetaKoordinaatit(torniUusX, y);
        lauta[torniX][y] = null;
        
        torni.liikutettu(true);
        kuningas.liikutettu(true);
        
        paivitaTarvittavat(x, y, uusX, y, kuningas.getVari());
        paivitaTarvittavat(torniX, y, torniUusX, y, kuningas.getVari());
        
        valkoisenVuoro = !valkoisenVuoro;
        
        return torni.getID() + 50;
    }
    
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
     * @param x
     * @param y
     * @param uusX
     * @param uusY
     * @param vari 
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
        if (vari == 0) {
            if (lauta[uusX + 1][uusY + 1] != null && lauta[uusX + 1][uusY + 1].getNumero() == -1) {
                lauta[uusX + 1][uusY + 1].paivita();
            } else if (lauta[uusX - 1][uusY + 1] != null && lauta[uusX - 1][uusY + 1].getNumero() == -1) {
                lauta[uusX - 1][uusY + 1].paivita();
            }
        } else {
            if (lauta[uusX + 1][uusY - 1] != null && lauta[uusX + 1][uusY - 1].getNumero() == 1) {
                lauta[uusX + 1][uusY - 1].paivita();
            } else if (lauta[uusX - 1][uusY - 1] != null && lauta[uusX - 1][uusY - 1].getNumero() == 1) {
                lauta[uusX - 1][uusY - 1].paivita();
            }
        }
        
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

        valkoisenNappulat.get(0).paivita();
        mustanNappulat.get(0).paivita();
        
    }
    
    public ArrayList<Siirto> getSiirrot(int vari, int shakissa) {
        ArrayList<Siirto> siirrot = new ArrayList<>();
        
        if (vari == 0) {
            if (shakissa > 0) {
                for (int i = 0; i < valkoisenNappulat.size(); i++) {
                    int kiinnitys = valkoisenNappulat.get(i).getKiinnitys();
                    if (kiinnitys == 0) {
                        for (Siirto s : valkoisenNappulat.get(i).getSiirrotShakissa()) {
                            siirrot.add(new Siirto(s.getX(), s.getY(), s.getUusX(), s.getUusY(), s.getSuunta(), s.getVahvuus()));
                        }
                    } else {
                        for (Siirto s : valkoisenNappulat.get(i).getSiirrotShakissa()) {
                            if (voiSiirtää(s.suunta, kiinnitys)) {
                                siirrot.add(new Siirto(s.getX(), s.getY(), s.getUusX(), s.getUusY(), s.getSuunta(), s.getVahvuus()));
                            }
                        }
                    }
                }
            } else {
                for (int i = 0; i < valkoisenNappulat.size(); i++) {
                    int kiinnitys = valkoisenNappulat.get(i).getKiinnitys();
                    if (kiinnitys == 0) {
                        for (Siirto s : valkoisenNappulat.get(i).getSiirrot()) {
                            siirrot.add(new Siirto(s.getX(), s.getY(), s.getUusX(), s.getUusY(), s.getSuunta(), s.getVahvuus()));
                        }
                    } else {
                        for (Siirto s : valkoisenNappulat.get(i).getSiirrot()) {
                            if (voiSiirtää(s.suunta, kiinnitys)) {
                                siirrot.add(new Siirto(s.getX(), s.getY(), s.getUusX(), s.getUusY(), s.getSuunta(), s.getVahvuus()));
                            }
                        }
                    }
                }
            }
        } else {
            if (shakissa > 0) {
                for (int i = 0; i < mustanNappulat.size(); i++) {
                    int kiinnitys = mustanNappulat.get(i).getKiinnitys();
                    if (kiinnitys == 0) { 
                        for (Siirto s : mustanNappulat.get(i).getSiirrotShakissa()) {
                            siirrot.add(new Siirto(s.getX(), s.getY(), s.getUusX(), s.getUusY(), s.getSuunta(), s.getVahvuus()));
                        }
                    } else {
                        for (Siirto s : mustanNappulat.get(i).getSiirrotShakissa()) {
                            if (voiSiirtää(s.suunta, kiinnitys)) {
                                siirrot.add(new Siirto(s.getX(), s.getY(), s.getUusX(), s.getUusY(), s.getSuunta(), s.getVahvuus()));
                            }
                        }
                    }
                }
            } else {
                for (int i = 0; i < mustanNappulat.size(); i++) {
                    int kiinnitys = mustanNappulat.get(i).getKiinnitys();
                    if (kiinnitys == 0) {
                        for (Siirto s : mustanNappulat.get(i).getSiirrot()) {
                            siirrot.add(new Siirto(s.getX(), s.getY(), s.getUusX(), s.getUusY(), s.getSuunta(), s.getVahvuus()));
                        }
                    } else {
                        for (Siirto s : mustanNappulat.get(i).getSiirrot()) {
                            if (voiSiirtää(s.suunta, kiinnitys)) {
                                siirrot.add(new Siirto(s.getX(), s.getY(), s.getUusX(), s.getUusY(), s.getSuunta(), s.getVahvuus()));
                            }
                        }
                    }
                }
            }
        }
        
        siirrot.sort((s1, s2) -> Integer.compare(s1.getVahvuus(), s2.getVahvuus()));
        
        if (vari == 0) {
            valkoisenSiirrot = siirrot;
        } else {
            mustanSiirrot = siirrot;
        }
        
        return siirrot;
    }
    
    public boolean voiSiirtää(int suunta, int kiinnitys) {
        if (suunta > 0 && suunta < 3 && kiinnitys < 3) return true;
        
        if (suunta > 2 && suunta < 5 && kiinnitys > 2 && kiinnitys < 5) return true;
        
        if (suunta > 4 && suunta < 7 && kiinnitys > 4 && kiinnitys < 7) return true;
        
        return suunta > 6 && suunta < 9 && kiinnitys > 6 && kiinnitys < 9;
    }
    
    /**
     * Päivittää siirrot shakki-tilanteessa.
     * @param x
     * @param y
     * @param suunta
     */
    public void paivitaShakissa(int x, int y, int suunta) {
        System.out.println("paivita shakissa: suunta " + suunta);
        
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
                n.paivitaKunShakissa(ruudut, mustanHyökätyt);
            }
        } else {
            for (Nappula n: mustanNappulat) {
                n.paivitaKunShakissa(ruudut, valkoisenHyökätyt);
            }
        }
        
    }
    
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
    
    
    public void lisaaKuninkaanLinjanBlokki(Koordinaatit k, Nappula n) {
        kuninkaanLinjanBlokit.put(k, n);
    }
    
    /**
     * Poistaa nappulan nappuloiden listalta.
     * @param n
     */
    public void poistaNappula(Nappula n) {
        if (n.getVari() == 0) {
            valkoisenNappulat.remove(n);
        } else {
            mustanNappulat.remove(n);
        }
    }
    /**
     * Tuleva peruSiirto, joka korvaa laudan kopioinnin tarpeen.
     */
    public void peruSiirto() {
        System.out.println("Peru siirto koko: " + this.tehdytSiirrot.size());
        
        TehtySiirto siirto = this.tehdytSiirrot.get(this.tehdytSiirrot.size() - 1);
        
        int x = siirto.x; // koordinaatit, johon nappula palautetaan
        int y = siirto.y;
        
        int vanhaX = siirto.nappula.getX(); // koordinaatit, johon nappula oli siirretty
        int vanhaY = siirto.nappula.getY();
        
        int vari = siirto.nappula.getVari();
        
        if (siirto.oliLiikkunut == false) siirto.nappula.liikutettu(false);
        
        lauta[x][y] = siirto.nappula;
        lauta[vanhaX][vanhaY] = null;
        if (siirto.syotyNappula != null) {
            lauta[vanhaX][vanhaY] = siirto.syotyNappula;
            if (siirto.syotyNappula.getVari() == 0) {
                valkoisenNappulat.add(siirto.syotyNappula);
            } else {
                mustanNappulat.add(siirto.syotyNappula);
            }
        } else if (siirto.torni != null) {
            if (siirto.torni.getX() < x) {
                siirto.torni.asetaKoordinaatit(leveys - ulkoL - 1, y);
            } else {
                siirto.torni.asetaKoordinaatit(ulkoL, y);
            }
            siirto.torni.liikutettu(false);
        }
        
        for (Nappula n : siirto.nappulat) {
            n.siirrot = siirto.nappuloidenSiirrot.get(n);
            n.blokit = siirto.nappuloidenBlokit.get(n);
            n.viimeksiPaivitetty = siirto.viimeksiPaivitetty.get(n);
            Nappula kiinnitetty = siirto.kiinnitetyt.get(n);
            if (kiinnitetty != null) {
                n.kiinnitetty = kiinnitetty;
                n.kiinnitysSuunta = siirto.kiinnitykset.get(n);
                kiinnitetty.kiinnitys = n.kiinnitysSuunta;
            }
        }
        if (siirto.shakitus > 0) {
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
        
        valkoisenHyökätyt = siirto.valkoisenHyokatyt;
        mustanHyökätyt = siirto.mustanHyokatyt;
        
        valkoisenVuoro = !valkoisenVuoro;
        
        valkoisenSiirrot = getSiirrot(0, siirto.shakitus);
        mustanSiirrot = getSiirrot(1, siirto.shakitus);
        
        this.tehdytSiirrot.remove(tehdytSiirrot.size() - 1);
        
    }
    
    
    
}
