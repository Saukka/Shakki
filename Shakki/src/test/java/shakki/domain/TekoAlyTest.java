
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
    public void tekoAlySiirto() {
        lauta = new Lauta();
        lauta.asetaLauta("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
        tekoAly = new TekoAly(lauta);
        
        lauta.teeSiirto(lauta.ulkoL + 4, lauta.ulkoP + 1, lauta.ulkoL + 4, lauta.ulkoP + 3);
        Siirto s = tekoAly.LaskeSiirto();
        lauta.teeSiirto(s.getX(), s.getY(), s.getUusX(), s.getUusY());
        
        // Tehtyjen siirtojen ensimmäinen arvo on tyhjä siirto joka lisätään lautaa asettaessa.
        assertEquals(3, lauta.tehdytSiirrot.size());
        
    }
    
    
}
