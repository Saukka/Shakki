package shakki.domain;

import java.util.ArrayList;
import shakki.nappulat.*;


/**
 * Luokka huolehtii pelilaudan toiminnasta.
 */
public class Lauta {
    Nappula[][] lauta;
    
    int leveys; // taulukon leveys
    int pituus; // taulukon pituus
    
    int ulkoL; // taulukon ylimääräisen leveyden määrä kummallakin puolella
    int ulkoP; // taulukon ylimääräisen pituuden määrä kummallakin puolella
    
    ArrayList<Koordinaatit> valkoisenNappulat;
    ArrayList<Koordinaatit> mustanNappulat;
    
    ArrayList<Siirto> tehdytSiirrot;
    
    public Lauta() {

        leveys = 10;
        pituus = 12;
        
        ulkoL = 1;
        ulkoP = 2;
        
        lauta = new Nappula[leveys][pituus];
        
        valkoisenNappulat = new ArrayList<>();
        mustanNappulat = new ArrayList<>();
        tehdytSiirrot = new ArrayList<>();
        
        asetaLauta();
    }
    
    /**
     * Metodi asettaa nappulat laudalle ja lisää nappuloiden koordinaatit listoille.
     */
    
    public void asetaLauta() {
        
        Nappula[] vNappulat = new Nappula[]{new Torni(1, ulkoL, ulkoP, 0), new Ratsu(2, ulkoL + 1, ulkoP, 0), new Lähetti(3, ulkoL + 2, ulkoP, 0), 
            new Kuningatar(4, ulkoL + 3, ulkoP, 0), new Kuningas (5, ulkoL + 4, ulkoP, 0), new Lähetti(6, ulkoL + 5, ulkoP, 0), new Ratsu(7, ulkoL + 6, ulkoP, 0), new Torni(8, ulkoL + 7, ulkoP, 0)};
             
        Nappula[] mNappulat = new Nappula[]{new Torni(17, ulkoL, pituus - ulkoP - 1, 1), new Ratsu(18, ulkoL + 1, pituus - ulkoP - 1, 1), new Lähetti(19, ulkoL + 2, pituus - ulkoP - 1, 1), new Kuningatar(20, ulkoL + 3, pituus - ulkoP - 1, 1)
                , new Kuningas (21, ulkoL + 4, pituus - ulkoP - 1, 1), new Lähetti(22, ulkoL + 5, pituus - ulkoP - 1, 1), new Ratsu(23, ulkoL + 6, pituus - ulkoP - 1, 1), new Torni(24, ulkoL + 7, pituus - ulkoP - 1, 1)};
        
        
        for (int i = 0; i < 8; i++) {
            
            lauta[ulkoL + i][ulkoP] = vNappulat[i];
            lauta[ulkoL + i][pituus - ulkoP - 1] = mNappulat[i];
            valkoisenNappulat.add(new Koordinaatit(ulkoL + i, ulkoP));
            mustanNappulat.add(new Koordinaatit(ulkoL + i, pituus - ulkoP - 1));
        }
        
        for (int i = 0; i < 8; i++) {
            lauta[ulkoL + i][ulkoP + 1] = new Sotilas(i + 9, ulkoL + i, ulkoP + 1, 0);
            lauta[ulkoL + i][pituus - ulkoP - 2] = new Sotilas(i + 25, ulkoL + i, pituus - ulkoP - 2, 1);
            valkoisenNappulat.add(new Koordinaatit(ulkoL + i, ulkoP + 1));
            mustanNappulat.add(new Koordinaatit(ulkoL + i, pituus - ulkoP - 2));
        }
        
        for (int i = 0; i < leveys; i++) {
            lauta[i][0] = new EpäNappula(i, 0);
            lauta[i][1] = new EpäNappula(i, 1);
            lauta[i][pituus - 1] = new EpäNappula(i, pituus - 1);
            lauta[i][pituus - 2] = new EpäNappula(i, pituus - 2);
            
            if (i > 1) {
                lauta[0][i] = new EpäNappula(0, i);
                lauta[leveys - 1][i] = new EpäNappula(leveys - 1, i);
            }
            
        }
        
        for (int i = ulkoL; i < 8 + ulkoL; i++) {
            lauta[i][ulkoP].paivitaSiirrot(lauta, 0);
            lauta[i][ulkoP + 1].paivitaSiirrot(lauta, 0);
            lauta[i][pituus - ulkoP - 1].paivitaSiirrot(lauta, 0);
            lauta[i][pituus - ulkoP - 2].paivitaSiirrot(lauta, 0);
        }
        
        
    }
    /**
     * Metodi suorittaa nappuloiden siirrot. 
     * Metodille annetaan nappulan koordinaatit ja koordinaatit mihin nappula siirretään.
     * @param x 
     * @param y
     * @param uusX
     * @param uusY
     * @return 
     */
    public int teeSiirto(int x, int y, int uusX, int uusY) {
        
        //System.out.println("SIIRTO: x: " + x + ", y: " + y + ", uus X: " + uusX + ", uus Y: " + uusY);

        for (int i = 0; i < lauta[x][y].getSiirrot().size(); i++) {
            if (uusX == lauta[x][y].getSiirrot().get(i).getX() && uusY == lauta[x][y].getSiirrot().get(i).getY()) {
                
                Nappula n = lauta[x][y];
                
                // Tarkistetaan onko kyseessä linnoitus (tornitus), jolloin käytetään eri metodia
                if (Math.abs(n.getNumero()) == 6 && Math.abs(x - uusX) == 2) {
                    int s = teeLinnoitus(n, x, y, uusX);
                    return s;
                }
                
                int s = 0;
                
                Nappula syotyNappula = null;
                
                if (lauta[uusX][uusY] != null) {
                    s = lauta[uusX][uusY].getID();
                    lauta[uusX][uusY].syo();
                    poistaNappula(uusX, uusY);
                    syotyNappula = lauta[uusX][uusY];
                }
                
                lauta[uusX][uusY] = n;
                lauta[uusX][uusY].SetKoordinaatit(uusX, uusY);
                lauta[x][y] = null;
                lauta[uusX][uusY].paivitaSiirrot(lauta, 0);
                
                paivitaKoordinaatit(x, y, uusX, uusY, n.getVari());
                paivitaTarvittavat(x, y, uusX, uusY, n.getVari());
                
                tehdytSiirrot.add(new Siirto(x, y, n, null, syotyNappula, n.onkoLiikkunut()));
                n.liikutettu(true);
                
                return s;
                
            }
        }
        
        // siirtoa ei voinut tehdä
        return -1;
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
        kuningas.SetKoordinaatit(uusX, y);
        lauta[x][y] = null;
        
        Nappula torni = lauta[torniX][y];
        lauta[torniUusX][y] = torni;
        torni.SetKoordinaatit(torniUusX, y);
        lauta[torniX][y] = null;
        
        torni.liikutettu(true);
        kuningas.liikutettu(true);
        
        lauta[torniUusX][y].paivitaSiirrot(lauta, 0);
        lauta[uusX][y].paivitaSiirrot(lauta, 0);
        
        paivitaKoordinaatit(x, y, uusX, y, kuningas.getVari());
        paivitaKoordinaatit(torniX, y, torniUusX, y, kuningas.getVari());
        
        paivitaTarvittavat(x, y, uusX, y, kuningas.getVari());
        paivitaTarvittavat(torniX, y, torniUusX, y, kuningas.getVari());
        
        tehdytSiirrot.add(new Siirto(x, y, kuningas, torni, null, kuningas.onkoLiikkunut()));
        
        return torni.getID() + 50;
    }
    
    /**
     * Metodi päivittää koordinaatti-listassa nappulan koordinaatit.
     * @param x
     * @param y
     * @param uusX
     * @param uusY
     * @param vari 
     */
    public void paivitaKoordinaatit(int x, int y, int uusX, int uusY, int vari) {
        
        if (vari == 0) {
            for (int j = 0; j < valkoisenNappulat.size(); j++) {
                if (x == valkoisenNappulat.get(j).getX() && y == valkoisenNappulat.get(j).getY()) {
                    valkoisenNappulat.get(j).setKoordinaatit(uusX, uusY);
                    break;
                }
            } 
        } else {
            for (int j = 0; j < mustanNappulat.size(); j++) {
                if (x == mustanNappulat.get(j).getX() && y == mustanNappulat.get(j).getY()) {
                    mustanNappulat.get(j).setKoordinaatit(uusX, uusY);
                    break;
                }
            }
        }
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
        for (int i = 0; i < valkoisenNappulat.size(); i++) {
            ArrayList<Koordinaatit> siirrot = lauta[valkoisenNappulat.get(i).getX()][valkoisenNappulat.get(i).getY()].getSiirrot();
            for (int j = 0; j < siirrot.size(); j++) {
                if ((x == siirrot.get(j).getX() && y == siirrot.get(j).getY()) || (uusX == siirrot.get(j).getX() && uusY == siirrot.get(j).getY())) {
                    lauta[valkoisenNappulat.get(i).getX()][valkoisenNappulat.get(i).getY()].paivitaSiirrot(lauta, 0);
                    break;
                }
            }
            ArrayList<Koordinaatit> blokit = lauta[valkoisenNappulat.get(i).getX()][valkoisenNappulat.get(i).getY()].getBlokit();
            for (int j = 0; j < blokit.size(); j++) {
                if ((x == blokit.get(j).getX() && y == blokit.get(j).getY()) || (uusX == blokit.get(j).getX() && uusY == blokit.get(j).getY())) {
                    lauta[valkoisenNappulat.get(i).getX()][valkoisenNappulat.get(i).getY()].paivitaSiirrot(lauta, 0);
                    break;
                }
            }
        }
        // Sitten mustat nappulat
        for (int i = 0; i < mustanNappulat.size(); i++) {
            ArrayList<Koordinaatit> siirrot = lauta[mustanNappulat.get(i).getX()][mustanNappulat.get(i).getY()].getSiirrot();
            for (int j = 0; j < siirrot.size(); j++) {
                if ((x == siirrot.get(j).getX() && y == siirrot.get(j).getY()) || (uusX == siirrot.get(j).getX() && uusY == siirrot.get(j).getY())) {
                    lauta[mustanNappulat.get(i).getX()][mustanNappulat.get(i).getY()].paivitaSiirrot(lauta, 0);
                    break;
                }
            }
            ArrayList<Koordinaatit> blokit = lauta[mustanNappulat.get(i).getX()][mustanNappulat.get(i).getY()].getBlokit();
            for (int j = 0; j < blokit.size(); j++) {
                if ((x == blokit.get(j).getX() && y == blokit.get(j).getY()) || (uusX == blokit.get(j).getX() && uusY == blokit.get(j).getY())) {
                    lauta[mustanNappulat.get(i).getX()][mustanNappulat.get(i).getY()].paivitaSiirrot(lauta, 0);
                    break;
                }
            }
        }
        if (vari == 0) {
            if (lauta[uusX + 1][uusY + 1] != null && lauta[uusX + 1][uusY + 1].getID() > 24) {
                lauta[uusX + 1][uusY + 1].paivitaSiirrot(lauta, 0);
            } else if (lauta[uusX - 1][uusY + 1] != null && lauta[uusX - 1][uusY + 1].getID() > 24) {
                lauta[uusX - 1][uusY + 1].paivitaSiirrot(lauta, 0);
            }
        } else {
            if (lauta[uusX + 1][uusY - 1] != null && lauta[uusX + 1][uusY - 1].getArvo() == 10) {
                lauta[uusX + 1][uusY - 1].paivitaSiirrot(lauta, 0);
            } else if (lauta[uusX - 1][uusY - 1] != null && lauta[uusX - 1][uusY - 1].getArvo() == 10) {
                lauta[uusX - 1][uusY - 1].paivitaSiirrot(lauta, 0);
            }
        }
        
        
        
    }
    
    /**
     * Poistaa nappulan nappuloiden koordinaattien listalta.
     * @param x
     * @param y 
     */
    public void poistaNappula(int x, int y) {
        if (lauta[x][y].getID() < 17) {
            for (int i = 0; i < valkoisenNappulat.size(); i++) {
                if (x == valkoisenNappulat.get(i).getX() && y == valkoisenNappulat.get(i).getY()) {
                    valkoisenNappulat.remove(i);
                    break;
                }
            }
        } else {
            for (int i = 0; i < mustanNappulat.size(); i++) {
                if (x == mustanNappulat.get(i).getX() && y == mustanNappulat.get(i).getY()) {
                    mustanNappulat.remove(i);
                    break;
                }
            }
        }

    }
    
    /**
     * Metodi palauttaa kopion laudasta TekoAly-luokalle minimax-algoritmia varten.
     * Tulen todennäköisesti poistamaan metodin tulevaisuudessa, sillä laudan kopiointi kokoajan on melko hidasta
     * minimax-algoritmin aikana.
     * @return 
     */
    public Lauta kopioi() {
        Lauta l = new Lauta();
        
        for (int x = 0; x < leveys; x++) {
            for (int y = 0; y < pituus; y++) {
                if (lauta[x][y] == null) {
                    l.lauta[x][y] = null;
                } else {
                    Nappula n = this.lauta[x][y].kopioi();
                    l.lauta[x][y] = n;
                }
            }
        }
        l.valkoisenNappulat.clear();
        l.mustanNappulat.clear();
        
        for (int i = 0; i < valkoisenNappulat.size(); i++) {
            l.valkoisenNappulat.add(new Koordinaatit(valkoisenNappulat.get(i).getX(), valkoisenNappulat.get(i).getY()));
        }
        
        for (int i = 0; i < mustanNappulat.size(); i++) {
            l.mustanNappulat.add(new Koordinaatit(mustanNappulat.get(i).getX(), mustanNappulat.get(i).getY()));
        }
        
        return l;
    }
    
    
    public int getID (int x, int y) {
        if (lauta[x][y] == null) {
            return 0;
        }
        return lauta[x][y].getID();
    }
    
    /**
     * Tuleva peruSiirto, joka korvaa laudan kopioinnin tarpeen.
     */
    public void peruSiirto() {
        
        Siirto siirto = this.tehdytSiirrot.get(this.tehdytSiirrot.size() - 1);
        
        int x = siirto.x; // koordinaatit, johon nappula palautetaan
        int y = siirto.y;
        
        int vanhaX = siirto.nappula.getX(); // koordinaatit, johon nappula oli siirretty
        int vanhaY = siirto.nappula.getY();
        
        int vari = siirto.nappula.getVari();
        
        lauta[x][y] = siirto.nappula;
        if (siirto.oliLiikkunut == false) siirto.nappula.liikutettu(false);
        
        lauta[vanhaX][vanhaY] = null;
        siirto.nappula.SetKoordinaatit(x, y);
        paivitaKoordinaatit(vanhaX, vanhaY, x, y, vari);
        
        if (siirto.syotyNappula != null) {
            lauta[siirto.syotyNappula.getX()][siirto.syotyNappula.getY()] = siirto.syotyNappula;
            if (vari == 0) {
                mustanNappulat.add(new Koordinaatit(vanhaX, vanhaY));
            } else {
                mustanNappulat.add(new Koordinaatit(vanhaX, vanhaY));
            }
        }
        
        if (siirto.torni != null) {
            siirto.torni.liikutettu(false);
            
            int torniX = ulkoL;
            int torniVanhaX = vanhaX + 1;
            
            if (x < vanhaX) {
                torniX += 7;
                torniVanhaX = vanhaX - 1;
            }
            lauta[torniX][y] = siirto.torni;
            lauta[torniVanhaX][y] = null;
            siirto.torni.SetKoordinaatit(torniX, y);
            siirto.torni.paivitaSiirrot(lauta, 0);
            paivitaKoordinaatit(torniVanhaX, y, torniX, y, siirto.torni.getVari());
            paivitaTarvittavat(torniVanhaX, y, torniX, y, siirto.torni.getVari());
            siirto.torni.SetKoordinaatit(torniX, y);
        }
        
        lauta[x][y].paivitaSiirrot(lauta, 0);
        
        paivitaTarvittavat(vanhaX, vanhaY, x, y, vari);

        this.tehdytSiirrot.remove(tehdytSiirrot.size() - 1);
        
        
    }
    
    
    
}
