package shakki.domain;

import java.util.ArrayList;
import java.util.HashMap;
import shakki.nappulat.Nappula;




/**
 * Luokka arvioi peliä ja tekee siirron minimax-algoritmin avulla. (Ei vielä kehitetty)
 */
public class TekoAly {
    
    Double tilanneArvio = 0.0;
    
    ArrayList<Koordinaatit> parasSiirto;
    
    HashMap<Integer, ArrayList<Koordinaatit>> hashMap;
    
    public TekoAly() {
    }
    
    
    public ArrayList<Koordinaatit> LaskeSiirto(Lauta l) {
        
        Lauta lauta = l;
        
        this.parasSiirto = new ArrayList<>();
        this.parasSiirto.clear();
        
        hashMap = new HashMap();
        hashMap.clear();
        
        System.out.println(" mustat koko: " + lauta.mustanNappulat.size());
        
        int min = minArvo(lauta, -10000, 10000, 3);
        
        return hashMap.get(min);
    }
        
    
        public int minArvo(Lauta pl, int alpha, int beta, int syvyys) {
            syvyys --;
            if (syvyys == 0) {
                return lautaArvio(pl.lauta, pl.valkoisenNappulat, pl.mustanNappulat);
            }
            
            int v = 10000;
            
            for (int i = 0; i < pl.mustanNappulat.size(); i++) {
                System.out.println("mustat koko: " + pl.mustanNappulat.size());
                ArrayList<Koordinaatit> siirrot = pl.lauta[pl.mustanNappulat.get(i).getX()][pl.mustanNappulat.get(i).getY()].getSiirrot();
                System.out.println("siirrot koko: " + siirrot.size());
                for (int j = 0; j < siirrot.size(); j++) {
                    pl.teeSiirto(pl.mustanNappulat.get(i).getX(), pl.mustanNappulat.get(i).getY(), siirrot.get(j).getX(), siirrot.get(j).getY());
                    v = Math.min(v, maxArvo(pl, alpha, beta, syvyys));
                    beta = Math.min(beta, v);
                    if (syvyys == 2) {
                        ArrayList<Koordinaatit> koordit = new ArrayList<>();
                        koordit.add(new Koordinaatit(pl.mustanNappulat.get(i).getX(), pl.mustanNappulat.get(i).getY()));
                        koordit.add(new Koordinaatit(siirrot.get(j).getX(), siirrot.get(j).getY()));
                        hashMap.put(v, koordit);
                    }
                    
                    if (alpha >= beta) return v;
                    
                }
        }
            return v;
            
        }
        
        public int maxArvo(Lauta pl, int alpha, int beta, int syvyys) {
            syvyys--;
            if (syvyys == 0) {
                return lautaArvio(pl.lauta, pl.valkoisenNappulat, pl.mustanNappulat);
            }
            
            int v = -10000;
            
            for (int i = 0; i < pl.valkoisenNappulat.size(); i++) {
                ArrayList<Koordinaatit> siirrot = pl.lauta[pl.valkoisenNappulat.get(i).getX()][pl.valkoisenNappulat.get(i).getY()].getSiirrot();
                for (int j = 0; j < siirrot.size(); j++) {
                    pl.teeSiirto(pl.valkoisenNappulat.get(i).getX(), pl.valkoisenNappulat.get(i).getY(), siirrot.get(j).getX(), siirrot.get(j).getY());
                    v = Math.max(v, minArvo(pl, alpha, beta, syvyys));
                    alpha = Math.max(alpha, v);
                    if (alpha >= beta) return v;
                }
            }
            
            
            return v;
        }
        
        
    public int lautaArvio(Nappula[][] peliLauta, ArrayList<Koordinaatit> valkoisenNappulat, ArrayList<Koordinaatit> mustanNappulat) {
        
        int arvio = 0;
        
        for (int i = 0; i < valkoisenNappulat.size(); i++) {
            int x = valkoisenNappulat.get(i).getX();
            int y = valkoisenNappulat.get(i).getY();
            if (x > 2 && x < 7) arvio++;
            if (x > 3 && x < 6) arvio += 4;
            if (y > 3) arvio += 3;
            
            arvio += peliLauta[valkoisenNappulat.get(i).getX()][valkoisenNappulat.get(i).getY()].getArvo();
        }
        
        for (int i = 0; i < mustanNappulat.size(); i++) {
            System.out.println("Arviossa mustan koko: " + mustanNappulat.size());
            int x = mustanNappulat.get(i).getX();
            int y = mustanNappulat.get(i).getY();
            if (x > 2 && x < 7) arvio--;
            if (x > 3) arvio -= 4;
            if (y < 8) arvio -= 3;
            
            arvio += peliLauta[x][y].getArvo();
        }
        
        return arvio;
    }
    
    
    
    
    
}
