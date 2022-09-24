
package shakki.domain;

import java.util.ArrayList;


/**
 * Luokka huolehtii pelilaudan toiminnasta.
 */
public class Lauta {
    
    int[][] lauta;
    
    ArrayList<Nappula> nappulat = new ArrayList<>();
    
    public Lauta() {
        
        lauta = new int[8][8];
        
    }
    
    public void asetaLauta() {
        
        // Asetetaan ensiksi valkoiset nappulat
        
        lauta[0][0] = 4; lauta [7][0] = 4; // Tornit
        lauta[1][0] = 2; lauta[6][0] = 2; // Ratsut
        lauta[2][0] = 3; lauta[5][0] = 3; // Lähetit
        lauta[3][0] = 5; // Kuningatar
        lauta[4][0] = 6; // Kuningas
        
        for (int i = 0; i < 8; i++) {
            lauta[i][1] = 1; // Sotilaat
        }
        
        // Nyt mustat nappulat laudan toiselle puolelle. Mustien nappuloiden numerot ovat negatiivisia
        
        lauta[0][7] = -4; lauta [7][7] = -4; // Tornit
        lauta[1][7] = -2; lauta[6][7] = -2; // Ratsut
        lauta[2][7] = -3; lauta[5][7] = -3; // Lähetit
        lauta[3][7] = -5; // Kuningatar
        lauta[4][7] = -6; // Kuningas
        
        for (int i = 0; i < 8; i++) {
            lauta[i][6] = -1; // Sotilaat
        }
    
    }
    
    public void teeSiirto() {
        
    }
    
}
