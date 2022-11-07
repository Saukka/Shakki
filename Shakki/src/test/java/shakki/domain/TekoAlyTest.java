
package shakki.domain;

import org.junit.Test;
import java.util.Random;
import static org.junit.Assert.*;

public class TekoAlyTest {
    
    
    public TekoAlyTest() {
        
    }
    
    Lauta lauta;
    TekoAly tekoAly;
    
    // Testataan että tekoäly saa siirron aikaiseksi
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
    
    // Testataan että tekoäly voittaa tornilla ja kuninkaalla ja että siirtoihin kestää alle 1.1 sekuntia.
    @Test
    public void tekoAlyVoittaa() {
        lauta = new Lauta();
        lauta.asetaLauta("8/8/4k2r/8/8/3K/8/8");
        tekoAly = new TekoAly(lauta);

        for (int i = 0; i < 24 && !lauta.valkoisenSiirrot.isEmpty(); i++) {
            Random x = new Random();
            Siirto s = lauta.valkoisenSiirrot.get(x.nextInt(lauta.valkoisenSiirrot.size()));
            lauta.teeSiirto(s.getX(), s.getY(), s.getUusX(), s.getUusY());
            long aloitus = System.currentTimeMillis();
            Siirto taS = tekoAly.LaskeSiirto();
            long loppu = System.currentTimeMillis();
            lauta.teeSiirto(taS.getX(), taS.getY(), taS.getUusX(), taS.getUusY());
            assertTrue(loppu - aloitus < 1100);
        }
        
        assertEquals(-1, lauta.tilanne);
    }
    
    // Testataan että tekoäly löytää matin kahdella siirrolla
    @Test
    public void mattiKahdella() {
        lauta = new Lauta();
        lauta.asetaLauta("6k1/2P3p1/4p3/3b4/p2Q4/6qP/6P1/1rR3K1");
        tekoAly = new TekoAly(lauta);
        
        lauta.teeSiirto(lauta.ulkoL + 3, lauta.ulkoP + 3, lauta.ulkoL + 3, lauta.ulkoP + 1);
        Siirto s = tekoAly.LaskeSiirto();
        lauta.teeSiirto(s.getX(), s.getY(), s.getUusX(), s.getUusY());
        lauta.teeSiirto(lauta.ulkoL + 3, lauta.ulkoP + 1, lauta.ulkoL + 2, lauta.ulkoP);
        Siirto matti = tekoAly.LaskeSiirto();
        lauta.teeSiirto(matti.getX(), matti.getY(), matti.getUusX(), matti.getUusY());
    
        assertEquals(-1, lauta.tilanne);
    }
        
    
}
