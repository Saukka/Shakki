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
       
    public Lauta() {

        leveys = 10;
        pituus = 12;
        
        ulkoL = 1;
        ulkoP = 2;
        
        lauta = new Nappula[leveys][pituus];
        
        valkoisenNappulat = new ArrayList<>();
        mustanNappulat = new ArrayList<>();
        
        asetaLauta();
    }
    
    /**
     * Metodi asettaa nappulat laudalle.
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
    
    public int teeSiirto(int x, int y, int uusX, int uusY) {
        
        //System.out.println("yritetään siirtoa: x: " + x + ", y; " + y + ", uus X: " + uusX + ", uus Y: " + uusY);
        
        for (int i = 0; i < lauta[x][y].getSiirrot().size(); i++) {
            if (uusX == lauta[x][y].getSiirrot().get(i).getX() && uusY == lauta[x][y].getSiirrot().get(i).getY()) {
                
                Nappula n = lauta[x][y];
                
                int s = 0;
                
                if (lauta[uusX][uusY] != null) {
                    s = lauta[uusX][uusY].getID();
                    poistaNappula(uusX, uusY);
                }
                
                lauta[uusX][uusY] = n;
                lauta[uusX][uusY].SetKoordinaatit(uusX, uusY);
                lauta[x][y] = null;
                lauta[uusX][uusY].paivitaSiirrot(lauta, 0);
                
                if (n.getVari() == 0) {
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
                paivitaTarvittavat(x, y, uusX, uusY, n.getVari());
                
                return s;
                
            }
        }
        
        // siirtoa ei voinut tehdä
        return -1;
    }
    
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
    
    public void peruSiirto() {
        
    }
    
//    public ArrayList<ArrayList<Koordinaatit>> mahdollisetSiirrot(int vari) {
//        ArrayList<ArrayList<Koordinaatit>> lista = new ArrayList<>();
//        
//        if (vari == 0) {
//            for (int i = 0; i < valkoisenNappulat.size(); i++) {
//                lista.add(lauta[valkoisenNappulat.get(i).getX()][valkoisenNappulat.get(i).getY()].getSiirrot());
//            }
//        } else {
//            for (int i = 0; i < mustanNappulat.size(); i++) {
//                lista.add(lauta[mustanNappulat.get(i).getX()][mustanNappulat.get(i).getY()].getSiirrot());
//            }
//        }
//        return lista;
//    }
    
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
    
    
    
}
