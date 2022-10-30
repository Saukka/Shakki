package shakki.domain;

import java.util.ArrayList;
import java.util.HashMap;
import shakki.nappulat.Nappula;





/**
 * Luokka arvioi peliä ja tekee siirron minimax-algoritmin avulla. 
 */
public class TekoAly {
    
    Lauta lauta;
    
    int syvyys;
    
    Double tilanneArvio = 0.0;
    
    HashMap<Integer, Siirto> minArvot;
    
    public TekoAly(Lauta l) {
        lauta = l;
    }
    
    /**
     * Tekoäly laskee minimax-algoritmia käyttäen siirron.
     * @param l Lauta jolle tekoäly laskee siirron
     * @return Koordinaatit-lista, ensimmäinen arvo kertoo nappulan koordinaatit. Toinen, mihin nappula siirretään
     */
    public Siirto LaskeSiirto() {
        
        syvyys = 2;
        
        int min = 10000;
        
        minArvot = new HashMap();
        minArvot.clear();
        
        minArvo(-10000, 10000, syvyys);
        
        return minArvot.get(min);
    }
    
    
    public int minArvo(int alpha, int beta, int syvyys) {
        if (syvyys == 0) {
            return lautaArvio();
        }
        
        int v = 10000;
        
        ArrayList<Siirto> siirrot = lauta.getSiirrot(1, lauta.shakitus);
        if (siirrot.isEmpty()) {
            return 100000;
        }
        
        for (int i = 0; i < siirrot.size(); i++) {
            int x = siirrot.get(i).getX();
            int y = siirrot.get(i).getY();
            int uusX = siirrot.get(i).getUusX();
            int uusY = siirrot.get(i).getUusY();
            
            System.out.println("MUSTAN SIIRTO");
            System.out.println("");
            System.out.println("");
            lauta.teeSiirto(x, y, uusX, uusY);
            int max = maxArvo(alpha, beta, syvyys - 1);
            lauta.peruSiirto();
            
            v = Math.min(v, max);
            beta = Math.min(beta, v);
            
            if (syvyys == this.syvyys && v == max) {
                minArvot.put(v, siirrot.get(i));
            }

            if (alpha >= beta) return v;
        }
        return v;
    }
        
    public int maxArvo(int alpha, int beta, int syvyys) {
        if (syvyys == 0) {
            return lautaArvio();
        }
        
        int v = -10000;
        
        ArrayList<Siirto> siirrot = lauta.getSiirrot(0, lauta.shakitus);
        
        if (siirrot.isEmpty()) {
            return -100000;
        }
         
        for (int i = 0; i < siirrot.size(); i++) {
            int x = siirrot.get(i).getX();
            int y = siirrot.get(i).getY();
            int uusX = siirrot.get(i).getUusX();
            int uusY = siirrot.get(i).getUusY();
            
            System.out.println("Valkoisen siirto");
            lauta.teeSiirto(x, y, uusX, uusY);
            int min = minArvo(alpha, beta, syvyys - 1);
            lauta.peruSiirto();
            
            v = Math.max(v, min);
            alpha = Math.max(alpha, v);
            
            if (alpha >= beta) return v;
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
            arvio += lauta.valkoisenNappulat.get(i).getArvo();
        }
        
        for (int i = 0; i < lauta.mustanNappulat.size(); i++) {
            arvio += lauta.mustanNappulat.get(i).getArvo();
        }
        
        return arvio;
    }
    
    
    
    
    
}
