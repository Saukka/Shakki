
package shakki.domain;

import org.junit.Test;
import static org.junit.Assert.*;
import shakki.nappulat.Nappula;

public class LautaTest {
    
    
    public LautaTest() {
    }
    
    Lauta lauta;
    
    
    @Test
    public void siirtojenMääräTesti() {
        lauta = new Lauta();
        lauta.asetaLauta("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
        
        int syvyysYksi = 0;
        int syvyysKaksi = 0;
        int syvyysKolme = 0;
        int syvyysNeljä = 0;
        int syvyysViisi = 0;
        int syvyysKuusi = 0;
        
        for (Siirto s : lauta.getSiirrot(0, true)) {
            lauta.teeSiirto(s.getX(), s.getY(), s.getUusX(), s.getUusY());
            syvyysYksi++;
            for (Siirto k : lauta.getSiirrot(1, true)) {
                lauta.teeSiirto(k.getX(), k.getY(), k.getUusX(), k.getUusY());
                syvyysKaksi++;
                for (Siirto j : lauta.getSiirrot(0, true)) {
                    syvyysKolme++;
                    lauta.teeSiirto(j.getX(), j.getY(), j.getUusX(), j.getUusY());
                    for (Siirto l : lauta.getSiirrot(1, true)) {
                        lauta.teeSiirto(l.getX(), l.getY(), l.getUusX(), l.getUusY());
                        syvyysNeljä++;
                        for (Siirto p : lauta.getSiirrot(0, true)) {
                            lauta.teeSiirto(p.getX(), p.getY(), p.getUusX(), p.getUusY());
                            syvyysViisi++;
                            
                            syvyysKuusi += lauta.getSiirrot(1, false).size();
                            
                            lauta.peruSiirto();
                        }
                        lauta.peruSiirto();
                    }
                    lauta.peruSiirto();
                }
                lauta.peruSiirto();
            }
            lauta.peruSiirto();
        }
        assertEquals(20, syvyysYksi);
        assertEquals(400, syvyysKaksi);
        assertEquals(8902, syvyysKolme);
        assertEquals(197281, syvyysNeljä);
        assertEquals(4865609, syvyysViisi);
        assertEquals(119060324, syvyysKuusi);
    }
    
    @Test
    public void valkoisetNappulat() {
        
        lauta = new Lauta();
        lauta.asetaLauta("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
        
        int summa = 0;
        
        for (int y = lauta.ulkoP; y < lauta.ulkoP + 2; y++) {
            for (int x = lauta.ulkoL; x < 8 + lauta.ulkoL; x++) {
                summa += lauta.lauta[x][y].getID();
            }
        }
        
        assertEquals(392, summa);
        
    }
    
    @Test
    public void mustatNappulat() {
        
        lauta = new Lauta();
        lauta.asetaLauta("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
        
        int summa = 0;
        
        for (int y = lauta.ulkoP + 6; y < lauta.ulkoP + 8; y++) {
            for (int x = lauta.ulkoL; x < 8 + lauta.ulkoL; x++) {
                summa += lauta.lauta[x][y].getID();
            }
        }
        
        assertEquals(136, summa);
    }
    
    @Test
    public void siirtoTesti() {
        lauta = new Lauta();
        lauta.asetaLauta("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
        lauta.teeSiirto(lauta.ulkoL, lauta.ulkoP + 1, lauta.ulkoL, lauta.ulkoP + 2);
        
        assertEquals(null, lauta.lauta[lauta.ulkoL][lauta.ulkoP + 1]);
        assertEquals(17, lauta.lauta[lauta.ulkoL][lauta.ulkoP + 2].getID());
        
    }
        
    @Test
    public void linnoitus() {
        lauta = new Lauta();
        lauta.asetaLauta("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
        int xU = lauta.ulkoL;
        int yU = lauta.ulkoP;
        
        //Siirretään valkoisen nappulat pois edestä ja siirretään mustan sotilasta
        lauta.teeSiirto(xU + 6, yU, xU + 5, yU + 2);
        lauta.teeSiirto(xU, yU + 6, xU, yU + 5);
        lauta.teeSiirto(xU + 4, yU + 1, xU + 4, yU + 3);
        lauta.teeSiirto(xU, yU + 5, xU, yU + 4);
        lauta.teeSiirto(xU + 5, yU, xU + 3, yU + 2);
        lauta.teeSiirto(xU, yU + 4, xU, yU + 3);
        
        //Sitten linnoitus
        lauta.teeSiirto(xU + 4, yU, xU + 6, yU);
        
        assertEquals(29, lauta.lauta[xU + 6][yU].getID());
        assertEquals(32, lauta.lauta[xU + 5][yU].getID());
        assertEquals(null, lauta.lauta[xU + 4][yU]);
        assertEquals(null, lauta.lauta[xU + 7][yU]);
    }
    
    @Test
    public void poistaNappula() {
        lauta = new Lauta();
        lauta.asetaLauta("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
        assertEquals(16, lauta.valkoisenNappulat.size());
        lauta.poistaNappula(lauta.lauta[lauta.ulkoL][lauta.ulkoP]);
        assertEquals(15, lauta.valkoisenNappulat.size());
    }
    
    
}
