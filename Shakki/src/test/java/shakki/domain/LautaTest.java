
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
        lauta.asetaLauta();
        
        int summa = 0;
        
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 8; x++) {
                summa += lauta.lauta[x][y].getID();
            }
        }
        
        assertEquals(136, summa);
        
    }
    
    @Test
    public void mustatNappulat() {
        
        lauta = new Lauta();
        lauta.asetaLauta();
        
        int summa = 0;
        
        for (int y = 6; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                summa += lauta.lauta[x][y].getID();
            }
        }
        assertEquals(392, summa);
    }
    
    @Test
    public void siirtoTesti() {
        lauta = new Lauta();
        lauta.asetaLauta();
        lauta.teeSiirto(0, 1, 0, 2);
        
        assertEquals(0, lauta.lauta[0][1].getID());
        assertEquals(9, lauta.lauta[0][2].getID());
        
    }
        
    @Test
    public void nappulanOminaisuudet() {
        lauta = new Lauta();
        lauta.asetaLauta();
        
        
    }
    
    
}
