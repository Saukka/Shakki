package shakki.domain;

import java.util.ArrayList;


/**
 * Luokka huolehtii pelilaudan toiminnasta.
 */
public class Lauta {
    int[][] lauta;
    
    ArrayList<Nappula> valkoisenNappulat;
    ArrayList<Nappula> mustanNappulat;
    
    public Lauta() {

        lauta = new int[8][8];
        
        valkoisenNappulat = new ArrayList<>();
        mustanNappulat = new ArrayList<>();
        
        asetaLauta();
    }
    
    /**
     * Metodi asettaa nappulat laudalle.
     */
    
    public void asetaLauta() {
        
        // Asetetaan ensiksi valkoiset nappulat
        
        /* lauta[0][0] = 4; lauta [7][0] = 4; // Tornit
        lauta[1][0] = 2; lauta[6][0] = 2; // Ratsut
        lauta[2][0] = 3; lauta[5][0] = 3; // Lähetit
        lauta[3][0] = 5; // Kuningatar
        lauta[4][0] = 6; // Kuningas */
        
        for (int i = 0; i < 8; i++) {
            lauta[i][0] = i + 1;
            lauta[i][1] = i + 9; // Sotilaat laudalle
            valkoisenNappulat.add(new Nappula(i + 1, 0.0, i, 0)); // takarivin nappulat listaan
        }
        
        for (int i = 0; i < 8; i++) {
            valkoisenNappulat.add(new Nappula(i + 9, 0.0, i, 1)); // sotilaat listaan
        }
        
        // Nyt mustat nappulat laudan toiselle puolelle. Mustien nappuloiden numerot ovat negatiivisia
        
        /* lauta[0][7] = -4; lauta [7][7] = -4; // Tornit
        lauta[1][7] = -2; lauta[6][7] = -2; // Ratsut
        lauta[2][7] = -3; lauta[5][7] = -3; // Lähetit
        lauta[3][7] = -5; // Kuningatar
        lauta[4][7] = -6; // Kuningas */
        
        for (int i = 0; i < 8; i++) {
            lauta[i][6] = i + 25; // Sotilaat laudalle
            lauta[i][7] = i + 17;
            mustanNappulat.add(new Nappula(i + 17, 0.0, i, 7)); // takarivin mustat nappulat listaan
        }
        for (int i = 0; i < 8; i++) {
            mustanNappulat.add(new Nappula(i + 25, 0.0, 1, 6)); // mustat sotilaat listaan
        }

        
    
    }
    
    public void teeSiirto(int x, int y, int uusX, int uusY) {
        
        int id = lauta[x][y];
        lauta[x][y] = 0;
        lauta[uusX][uusY] = id;
        
        if (id < 17) {
            valkoisenNappulat.get(id - 1).SetKoordinaatit(uusX, uusY);
        } else {
            mustanNappulat.get(id - 17).SetKoordinaatit(uusX, uusY);
        }
        
        
    }
    
    
    public void mustanSiirto() {
        
    }
    
    public int getID (int x, int y) {
        return lauta[x][y];
    }
    
    
    
}
