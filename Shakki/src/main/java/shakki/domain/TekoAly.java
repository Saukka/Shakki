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
        
        minArvo(-1000000, 1000000, syvyys);
        
        System.out.println("Tekoälyn siirron arvio: " + mini);
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
            return 100000;
        }
        
        for (int i = 0; i < siirrot.size(); i++) {
            int x = siirrot.get(i).getX();
            int y = siirrot.get(i).getY();
            int uusX = siirrot.get(i).getUusX();
            int uusY = siirrot.get(i).getUusY();
            
            //System.out.println("Musta tekee siirron " + lauta.lauta[x][y].getTyyppi() +  " x: " + x + ", y: " + y + ", uusX:  " + uusX + ", uus Y: " + uusY);
            lauta.teeSiirto(x, y, uusX, uusY);
            int max = maxArvo(alpha, beta, syvyys - 1);
            //System.out.println("Siirron arvio: " + max);
            lauta.peruSiirto();
            
            v = Math.min(v, max);
            beta = Math.min(beta, v);
            
            if (syvyys == this.syvyys && v == max) {
                mini = v;
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
        
        int v = -100000;
        
        ArrayList<Siirto> siirrot = lauta.getSiirrot(0, false);
        if (siirrot.isEmpty()) {
            return -100000;
        }
        for (int i = 0; i < siirrot.size(); i++) {
            int x = siirrot.get(i).getX();
            int y = siirrot.get(i).getY();
            int uusX = siirrot.get(i).getUusX();
            int uusY = siirrot.get(i).getUusY();
            
            //System.out.println("Valkoinen tekee siirron " + lauta.lauta[x][y].getTyyppi() +  " x: " + x + ", y: " + y + ", uusX:  " + uusX + ", uus Y: " + uusY);
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
        //System.out.println("Valkoisen nappuloiden arvot");
        for (int i = 0; i < lauta.valkoisenNappulat.size(); i++) {
            arvio += lauta.valkoisenNappulat.get(i).nappulanArvio();
            
            //System.out.println(lauta.valkoisenNappulat.get(i).getTyyppi() + " arvo: " + lauta.valkoisenNappulat.get(i).getPaikanArvo());
        }
        //System.out.println("Mustan nappuloiden arvot");
        for (int i = 0; i < lauta.mustanNappulat.size(); i++) {
            arvio -= lauta.mustanNappulat.get(i).nappulanArvio();
            //System.out.println(lauta.mustanNappulat.get(i).getTyyppi() + " arvo: " + lauta.mustanNappulat.get(i).getPaikanArvo());
        }
        
        return arvio;
    }
    
    
    
    
    
}
