package shakki.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import shakki.nappulat.Nappula;





/**
 * Luokka laskee minimax-algoritmin ja heuristisen arvion avulla suoritettavan siirron.
 */
public class TekoAly {
    
    Lauta lauta;
    
    int syvyys;
    
    HashMap<Integer, Siirto> minArvot;
    
    public TekoAly(Lauta l) {
        lauta = l;
    }
    
    /**
     * Tekoäly laskee minimax-algoritmia käyttäen siirron.
     * @return Suoritettava siirto
     */
    public Siirto LaskeSiirto() {

        syvyys = 4;
        
        minArvot = new HashMap();
        minArvot.clear();
        
        if (lauta.valkoisenNappulat.size() + lauta.mustanNappulat.size() < 10) {
            syvyys = 5;
            if (lauta.valkoisenNappulat.size() + lauta.mustanNappulat.size() < 6) {
                syvyys = 6;
            }
        } 
        
        int mini = minArvo(-1000000, 1000000, syvyys);
        
        return minArvot.get(mini);
    }
    
    /**
     * Minimax-algoritmin min-osa.
     * @param alpha
     * @param beta
     * @param syvyys haun syvyys
     * @return pelitilanteen arvio siirron jälkeen.
     */
    public int minArvo(int alpha, int beta, int syvyys) {
        if (syvyys == 0) {
            return lautaArvio();
        }
        
        int v = 100000;
        
        ArrayList<Siirto> siirrot = lauta.getSiirrot(1, false);
        if (siirrot.isEmpty()) {
            if (lauta.shakitus != 0) return 1000 * syvyys;
            return 0;
        }
        
        for (Siirto s : siirrot) {
            int x = s.getX();
            int y = s.getY();
            int uusX = s.getUusX();
            int uusY = s.getUusY();
            
            int p = lauta.teeSiirto(x, y, uusX, uusY);
            if (p != -1) {
                int max = maxArvo(alpha, beta, syvyys - 1);
                lauta.peruSiirto();
                v = Math.min(v, max);
                beta = Math.min(beta, v);
                if (syvyys == this.syvyys && v == max) {
                    minArvot.put(v, s);
                }
            }

            if (alpha > beta) return v;
        }
        return v;
    }
        
    /**
     * Minimax-algoritmin max-osa.
     * @param alpha suurin saavutettu arvio
     * @param beta pienin saavutettu arvio
     * @param syvyys haun syvyys
     * @return pelitilanteen arvio siirron jälkeen.
     */
    public int maxArvo(int alpha, int beta, int syvyys) {
        if (syvyys == 0) {
            return lautaArvio();
        }
        
        int v = -100000;
        
        ArrayList<Siirto> siirrot = lauta.getSiirrot(0, false);
        if (siirrot.isEmpty()) {
            if (lauta.shakitus != 0) return -1000 * syvyys;
            return 0;
        }
        for (Siirto s : siirrot) {
            int x = s.getX();
            int y = s.getY();
            int uusX = s.getUusX();
            int uusY = s.getUusY();
            
            int p = lauta.teeSiirto(x, y, uusX, uusY);
            if (p != -1) {
                int min = minArvo(alpha, beta, syvyys - 1);
                lauta.peruSiirto();
            
                v = Math.max(v, min);
                alpha = Math.max(alpha, v);
            }
            
            if (alpha > beta) return v;
        }
        return v;
        
    }
    

    /**
     * Lautalle annetaan peliLauta, sekä nappuloiden sijainnit ja antaa arvion laudan tilanteesta.
     * Tällä hetkellä arvio on todella epätarkka.
     * @return 
     */
    public int lautaArvio() {
        
        int arvio = 0;
        for (int i = 0; i < lauta.valkoisenNappulat.size(); i++) {
            arvio += lauta.valkoisenNappulat.get(i).nappulanArvio();
            
        }
        for (int i = 0; i < lauta.mustanNappulat.size(); i++) {
            arvio -= lauta.mustanNappulat.get(i).nappulanArvio();
        }
        
        if (lauta.valkoisenNappulat.size() + lauta.mustanNappulat.size() < 8) {
            arvio = arvio / 2;
            arvio -= loppuPelinArvio();
            
        }
        
        return arvio;
    }
    
    public int loppuPelinArvio() {
        double arvio = 0;
        int x = lauta.valkoisenNappulat.get(0).getX();
        int y = lauta.valkoisenNappulat.get(0).getY();
        
        // valkoisen kuninkaan etäisyys keskustaan
        int kuningasKeskustaan = (Math.abs(5 - x) + Math.abs(6 - y));
        arvio += 5 * kuningasKeskustaan;
        
        int omaX = lauta.mustanNappulat.get(0).getX();
        int omaY = lauta.mustanNappulat.get(0).getY();
        
        int kuninkaidenEtäisyys = Math.abs(x - omaX) + Math.abs(y - omaY);
        
        arvio += 3 * (14 - kuninkaidenEtäisyys);
        
        arvio = arvio * (2 / (double) (lauta.valkoisenNappulat.size() + lauta.mustanNappulat.size()));
        return (int) arvio;
        
    }
    
    
    
}
