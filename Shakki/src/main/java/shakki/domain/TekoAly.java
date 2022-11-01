package shakki.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import shakki.nappulat.Nappula;





/**
 * Luokka arvioi peliä ja tekee siirron minimax-algoritmin avulla. 
 */
public class TekoAly {
    
    Lauta lauta;
    
    int syvyys;
    
    int mini;
    
    HashMap<Integer, Siirto> minArvot;
    
    public TekoAly(Lauta l) {
        lauta = l;
    }
    
    /**
     * Tekoäly laskee minimax-algoritmia käyttäen siirron.
     * @return Koordinaatit-lista, ensimmäinen arvo kertoo nappulan koordinaatit. Toinen, mihin nappula siirretään
     */
    public Siirto LaskeSiirto() {

        syvyys = 4;
        
        mini = 100000;
        
        minArvot = new HashMap();
        minArvot.clear();
        
        if (lauta.valkoisenNappulat.size() + lauta.mustanNappulat.size() < 10) {
            syvyys = 5;
            if (lauta.valkoisenNappulat.size() + lauta.mustanNappulat.size() < 6) {
                syvyys = 6;
            }
        } 
        
        int re = minArvo(-1000000, 1000000, syvyys);
        
        return minArvot.get(mini);
    }
    
    
    public int minArvo(int alpha, int beta, int syvyys) {
        if (syvyys == 0) {
            return lautaArvio();
        }
        
        int v = 100000;
        
        ArrayList<Siirto> siirrot = lauta.getSiirrot(1, false);
        if (syvyys == this.syvyys) {
            //System.out.println("Mustien siirtojen koko: " + siirrot.size());
            //System.out.println("Shakitus: " + lauta.shakitus);
        }
        if (siirrot.isEmpty()) {
            if (lauta.shakitus != 0) return 1000 * syvyys;
            return 0;
        }
        
        for (int i = 0; i < siirrot.size(); i++) {
            int x = siirrot.get(i).getX();
            int y = siirrot.get(i).getY();
            int uusX = siirrot.get(i).getUusX();
            int uusY = siirrot.get(i).getUusY();
            
            //System.out.println("Musta tekee siirron " + lauta.lauta[x][y].getTyyppi() +  " x: " + x + ", y: " + y + ", uusX:  " + uusX + ", uus Y: " + uusY);
            int s = lauta.teeSiirto(x, y, uusX, uusY);
            if (s != -1) {
                int max = maxArvo(alpha, beta, syvyys - 1);
                lauta.peruSiirto();
                v = Math.min(v, max);
                beta = Math.min(beta, v);
                if (syvyys == this.syvyys && v == max) {
                    mini = v;
                    minArvot.put(v, siirrot.get(i));
                }
            }

            if (alpha > beta) return v;
        }
        return v;
    }
        
    public int maxArvo(int alpha, int beta, int syvyys) {
        if (syvyys == 0) {
            return lautaArvio();
        }
        
        int v = -100000;
        
        ArrayList<Siirto> siirrot = lauta.getSiirrot(0, false);
        if (siirrot.isEmpty()) {
            System.out.println("syvyys: " + syvyys);
            if (lauta.shakitus != 0) return -1000 * syvyys;
            return 0;
        }
        for (int i = 0; i < siirrot.size(); i++) {
            int x = siirrot.get(i).getX();
            int y = siirrot.get(i).getY();
            int uusX = siirrot.get(i).getUusX();
            int uusY = siirrot.get(i).getUusY();
            
            //System.out.println("Valkoinen tekee siirron " + lauta.lauta[x][y].getTyyppi() +  " x: " + x + ", y: " + y + ", uusX:  " + uusX + ", uus Y: " + uusY);
            int s = lauta.teeSiirto(x, y, uusX, uusY);
            if (s != -1) {
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
        
        if (lauta.valkoisenNappulat.size() + lauta.mustanNappulat.size() < 10) {
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
        arvio += kuningasKeskustaan;
        
        int omaX = lauta.mustanNappulat.get(0).getX();
        int omaY = lauta.mustanNappulat.get(0).getY();
        
        int kuninkaidenEtäisyys = Math.abs(x - omaX) + Math.abs(y - omaY);
        
        arvio += 18 - (kuninkaidenEtäisyys * 2);
        
        arvio = arvio * (5 / (double) (lauta.valkoisenNappulat.size() + lauta.mustanNappulat.size()));
        //System.out.println("Loppu pelin arvio: " + (int) arvio);
        return (int) arvio;
        
    }
    
    
    
    
    
    
    
}
