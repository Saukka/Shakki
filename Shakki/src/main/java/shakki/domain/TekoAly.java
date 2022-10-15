package shakki.domain;

import java.util.ArrayList;
import java.util.HashMap;
import shakki.nappulat.Nappula;





/**
 * Luokka arvioi peliä ja tekee siirron minimax-algoritmin avulla. 
 */
public class TekoAly {
    
    int syvyys;
    
    Double tilanneArvio = 0.0;
    
    ArrayList<Koordinaatit> parasSiirto;
    
    HashMap<Integer, ArrayList<Koordinaatit>> minArvot;
    
    public TekoAly() {
    }
    
    /**
     * Tekoäly laskee minimax-algoritmia käyttäen siirron.
     * @param l Lauta jolle tekoäly laskee siirron
     * @return Koordinaatit-lista, ensimmäinen arvo kertoo nappulan koordinaatit. Toinen, mihin nappula siirretään
     */
    public ArrayList<Koordinaatit> LaskeSiirto(Lauta l) {
        
        syvyys = 2;
        
        Lauta lauta = l;
        
        this.parasSiirto = new ArrayList<>();
        this.parasSiirto.clear();
        
        minArvot = new HashMap();
        minArvot.clear();
        
        int min = minArvo(lauta, -10000, 10000, syvyys);
        
        return minArvot.get(min);
    }
        
    
        public int minArvo(Lauta pl, int alpha, int beta, int syvyys) {
            if (syvyys == 0) {
                return lautaArvio(pl.lauta, pl.valkoisenNappulat, pl.mustanNappulat);
            }
            
            int v = 10000;
            
            for (int i = 0; i < pl.mustanNappulat.size(); i++) {
                int x = pl.mustanNappulat.get(i).getX();
                int y = pl.mustanNappulat.get(i).getY();
                ArrayList<Koordinaatit> siirrot = pl.lauta[x][y].getSiirrot();
                for (int j = 0; j < siirrot.size(); j++) {
                    Lauta tama = pl.kopioi();
                    tama.teeSiirto(x, y, siirrot.get(j).getX(), siirrot.get(j).getY());
                    int max = maxArvo(tama, alpha, beta, syvyys - 1);
                    //pl.peruSiirto();
                    v = Math.min(v, max);
                    beta = Math.min(beta, v);
                    if (syvyys == this.syvyys && v == max) {
                        ArrayList<Koordinaatit> koordit = new ArrayList<>();
                        koordit.add(new Koordinaatit(x, y));
                        koordit.add(new Koordinaatit(siirrot.get(j).getX(), siirrot.get(j).getY()));
                        minArvot.put(v, koordit);
                    }
                    
                    if (alpha >= beta) return v;
                    
                }
        }
            return v;
            
        }
        
        public int maxArvo(Lauta pl, int alpha, int beta, int syvyys) {
            if (syvyys == 0) {
                return lautaArvio(pl.lauta, pl.valkoisenNappulat, pl.mustanNappulat);
            }
            
            int v = -10000;
            
            for (int i = 0; i < pl.valkoisenNappulat.size(); i++) {
                int x = pl.valkoisenNappulat.get(i).getX();
                int y = pl.valkoisenNappulat.get(i).getY();
                ArrayList<Koordinaatit> siirrot = pl.lauta[x][y].getSiirrot();
                for (int j = 0; j < siirrot.size(); j++) {
                    Lauta tama = pl.kopioi();
                    tama.teeSiirto(x, y, siirrot.get(j).getX(), siirrot.get(j).getY());
                    v = Math.max(v, minArvo(tama, alpha, beta, syvyys - 1));
                    alpha = Math.max(alpha, v);
                    if (alpha >= beta) return v;
                }
            }
            return v;
        }
        
        
    /**
     * Lautalle annetaan peliLauta, sekä nappuloiden sijainnit ja antaa arvion laudan tilanteesta.
     * Tällä hetkellä arvio on todella epätarkka.
     * @param peliLauta
     * @param valkoisenNappulat
     * @param mustanNappulat
     * @return 
     */
    public int lautaArvio(Nappula[][] peliLauta, ArrayList<Koordinaatit> valkoisenNappulat, ArrayList<Koordinaatit> mustanNappulat) {
        
        int arvio = 0;
        
        for (int i = 0; i < valkoisenNappulat.size(); i++) {
            int x = valkoisenNappulat.get(i).getX();
            int y = valkoisenNappulat.get(i).getY();
            if (x > 2 && x < 7) arvio++;
            if (x > 3 && x < 6) arvio += 4;
            if (y > 3) arvio += 3;
            if (x > 2 && x < 7 && y > 3) arvio += 5;
            
            arvio += peliLauta[x][y].getArvo();
        }
        
        for (int i = 0; i < mustanNappulat.size(); i++) {
            int x = mustanNappulat.get(i).getX();
            int y = mustanNappulat.get(i).getY();
            if (x > 2 && x < 7) arvio--;
            if (x > 3 && x < 6) arvio -= 4;
            if (y < 8) arvio -= 3;
            if (x > 2 && x < 7 && y < 8) arvio -= 5;
            arvio += peliLauta[x][y].getArvo();
        }
        
        return arvio;
    }
    
    
    
    
    
}
