
package shakki.domain;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class TekoAlyTest {
    
    
    public TekoAlyTest() {
        
    }
    
    Lauta lauta;
    TekoAly tekoAly;
    
    @Test
    public void lautaArvio() {
        lauta = new Lauta();
        
        tekoAly = new TekoAly();
        
        assertEquals(0, tekoAly.lautaArvio(lauta.lauta, lauta.valkoisenNappulat, lauta.mustanNappulat));
        
        lauta.teeSiirto(lauta.ulkoL + 4, lauta.ulkoP + 1, lauta.ulkoL + 4, lauta.ulkoP + 3);
        
        assertTrue(tekoAly.lautaArvio(lauta.lauta, lauta.valkoisenNappulat, lauta.mustanNappulat) > 0);
    }
    
    @Test
    public void tekoAlySiirto() {
        lauta = new Lauta();
        
        tekoAly = new TekoAly();
        
        
        ArrayList<Koordinaatit> siirto = tekoAly.LaskeSiirto(lauta);
        
        assertEquals(2, siirto.size());
    }
    
    
}
