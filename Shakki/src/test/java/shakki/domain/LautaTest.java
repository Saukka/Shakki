
package shakki.domain;

import org.junit.Test;
import static org.junit.Assert.*;

public class LautaTest {
    
    
    public LautaTest() {
    }
    
    Lauta lauta;
    
    @Test
    public void valkoisetNappulat() {
        
        lauta = new Lauta();
        
        int summa = 0;
        
        for (int y = lauta.ulkoP; y < lauta.ulkoP + 2; y++) {
            for (int x = lauta.ulkoL; x < 8 + lauta.ulkoL; x++) {
                summa += lauta.lauta[x][y].getID();
            }
        }
        
        assertEquals(136, summa);
        
    }
    
    @Test
    public void mustatNappulat() {
        
        lauta = new Lauta();
        
        int summa = 0;
        
        for (int y = lauta.ulkoP + 6; y < lauta.ulkoP + 8; y++) {
            for (int x = lauta.ulkoL; x < 8 + lauta.ulkoL; x++) {
                summa += lauta.lauta[x][y].getID();
            }
        }
        
        assertEquals(392, summa);
    }
    
    @Test
    public void siirtoTesti() {
        lauta = new Lauta();
        lauta.teeSiirto(lauta.ulkoL, lauta.ulkoP + 1, lauta.ulkoL, lauta.ulkoP + 2);
        
        assertEquals(null, lauta.lauta[lauta.ulkoL][lauta.ulkoP + 1]);
        assertEquals(9, lauta.lauta[lauta.ulkoL][lauta.ulkoP + 2].getID());
        
    }
        
    @Test
    public void linnoitus() {
        lauta = new Lauta();
        int xU = lauta.ulkoL;
        int yU = lauta.ulkoP;
        
        //Siirretään valkoisen nappulat pois edestä
        lauta.teeSiirto(xU + 6, yU, xU + 5, yU + 2);
        lauta.teeSiirto(xU + 4, yU + 1, xU + 4, yU + 3);
        lauta.teeSiirto(xU + 5, yU, xU + 3, yU + 2);
        
        //Sitten linnoitus
        lauta.teeSiirto(xU + 4, yU, xU + 6, yU);
        
        assertEquals(5, lauta.lauta[xU + 6][yU].getID());
        assertEquals(8, lauta.lauta[xU + 5][yU].getID());
        assertEquals(null, lauta.lauta[xU + 4][yU]);
        assertEquals(null, lauta.lauta[xU + 7][yU]);
    }
    
    @Test
    public void poistaNappula() {
        lauta = new Lauta();
        assertEquals(16, lauta.valkoisenNappulat.size());
        lauta.poistaNappula(lauta.lauta[lauta.ulkoL][lauta.ulkoP]);
        assertEquals(15, lauta.valkoisenNappulat.size());
    }
    
    
}
