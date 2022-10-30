
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
        
        tekoAly = new TekoAly(lauta);
        
        assertEquals(0, tekoAly.lautaArvio());
        
        lauta.teeSiirto(lauta.ulkoL + 4, lauta.ulkoP + 1, lauta.ulkoL + 4, lauta.ulkoP + 3);
        
        assertTrue(tekoAly.lautaArvio() > 0);
    }
    
    @Test
    public void tekoAlySiirto() {
        lauta = new Lauta();
        
        tekoAly = new TekoAly(lauta);
        
        
        Siirto siirto = tekoAly.LaskeSiirto();
        
        
    }
    
    
}
