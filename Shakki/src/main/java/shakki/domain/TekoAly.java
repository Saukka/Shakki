package shakki.domain;

import java.util.ArrayList;
import java.util.HashMap;
import shakki.nappulat.Nappula;





/**
 * Luokka arvioi peliä ja tekee siirron minimax-algoritmin avulla. (Ei vielä kehitetty)
 */
public class TekoAly {
    
    int syvyys;
    
    Double tilanneArvio = 0.0;
    
    ArrayList<Koordinaatit> parasSiirto;
    
    HashMap<Integer, ArrayList<Koordinaatit>> minArvot;
    
    public TekoAly() {
    }
    
    
    public ArrayList<Koordinaatit> LaskeSiirto(Lauta l) {
        
        syvyys = 2;
        
        Lauta lauta = l;
        
        this.parasSiirto = new ArrayList<>();
        this.parasSiirto.clear();
        
        minArvot = new HashMap();
        minArvot.clear();
        
        int min = minArvo(lauta, -10000, 10000, syvyys);
        
        System.out.println(" hashmap koko " + minArvot.size());
        
        return minArvot.get(min);
    }
        
    
        public int minArvo(Lauta pl, int alpha, int beta, int syvyys) {
            syvyys --;
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
                    v = Math.min(v, maxArvo(tama, alpha, beta, syvyys));
                    beta = Math.min(beta, v);
                    if (syvyys == 1) {
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
            syvyys--;
            if (syvyys == 0) {
                return lautaArvio(pl.lauta, pl.valkoisenNappulat, pl.mustanNappulat);
            }
            
            int v = -10000;
            
            for (int i = 0; i < pl.valkoisenNappulat.size(); i++) {
                System.out.println("listan koko: " + pl.valkoisenNappulat.size());
                System.out.println("yritetään uutta nappulaa, i: " + i);
                int x = pl.valkoisenNappulat.get(i).getX();
                int y = pl.valkoisenNappulat.get(i).getY();
                ArrayList<Koordinaatit> siirrot = pl.lauta[x][y].getSiirrot();
                System.out.println("Siirtoja: " + siirrot.size());
                for (int j = 0; j < siirrot.size(); j++) {
                    Lauta tama = pl.kopioi();
                    tama.teeSiirto(x, y, siirrot.get(j).getX(), siirrot.get(j).getY());
                    v = Math.max(v, minArvo(tama, alpha, beta, syvyys));
                    alpha = Math.max(alpha, v);
                    if (alpha >= beta) return v;
                }
            }
            return v;
        }
        
//        System.out.println("nappulalistan koko: " + pl.valkoisenNappulat.size());
//                    System.out.println("siirrotlistan koko: " + siirrot.size());
//                    System.out.println("i: " + i);
//                    System.out.println("j: " + j);
        
        
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
