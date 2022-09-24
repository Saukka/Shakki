
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
                summa += lauta.lauta[x][y];
            }
        }
        
        assertEquals(37, summa);
        
        // nappuloiden tunnukset: 6 + 5 + 2x4 + 2x3 + 2x2 + 8x1 = 37
        
    }
    
    @Test
    public void mustatNappulat() {
        
        lauta = new Lauta();
        lauta.asetaLauta();
        
        int summa = 0;
        
        for (int y = 6; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                summa += lauta.lauta[x][y];
            }
        }
        assertEquals(-37, summa);
    }
        
    
}
