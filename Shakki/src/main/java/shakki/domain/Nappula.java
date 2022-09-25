
package shakki.domain;

import java.util.ArrayList;

/**
 * Nappula-olion luokka.
 */
public class Nappula {
    
    int id; // jokaisella nappulalla on uniikki id
    int numero; // numero kertoo nappulan tyypin
    int arvo; // Arvo kertoo nappulan arvon
    Double paikanArvo; // paikanArvo kertoo kuinka hyvässä paikassa nappula on laudalla
    
    
    // Nappulan koordinaatit
    int x;
    int y;
    
    boolean onLiikkunut;
    
    
    public Nappula(int id, Double paikanArvo, int x, int y) {
        this.id = id;
        
        this.paikanArvo = paikanArvo;
        this.x = x;
        this.y = y;
        
        onLiikkunut = false;
        
        int mustaKerroin = 1; // -1 jos musta nappula
        
        if (id > 16) {
            id = id - 16;
            mustaKerroin = -1;
        }

        
        if (id == 1 || id == 8) {
            this.arvo = 5 * mustaKerroin;
            this.numero = 4 * mustaKerroin;
        } else if (id == 2 || id == 7) {
            this.arvo = 3 * mustaKerroin;
            this.numero =  2 * mustaKerroin;
        } else if (id == 3 || id == 6) {
            this.arvo = 3 * mustaKerroin;
            this.numero = 3 * mustaKerroin;
        } else if (id == 4) {
            this.arvo = 9 * mustaKerroin;
            this.numero = 5 * mustaKerroin;
        } else if (id == 5) {
            this.arvo = 200 * mustaKerroin;
            this.numero = 6 * mustaKerroin;
        } else {
            this.arvo = 1 * mustaKerroin;
            this.numero = 1 * mustaKerroin;
        }
    }
    
    public int getNumero() {
        return this.id;
    }
    
    public int getArvo() {
        return this.arvo;
    }
    
    public void SetKoordinaatit(int x, int y) {
        this.x = x;
        this.y = y;
        onLiikkunut = true;
    }
    
    /* public ArrayList<Koordinaatit> mahdollisetSiirrot(int[][] lauta) {
        
        
    } */
    
    
    
    public ArrayList<Koordinaatit> sotilasSiirrot(int[][] lauta) {
        
        ArrayList<Koordinaatit> siirrot = new ArrayList<>();
        if (lauta[x][y+1] == 0) {
            siirrot.add(new Koordinaatit(x,y+1));
        }
        if (!onLiikkunut) {
            if (lauta[x][y+2] == 0) {
                siirrot.add(new Koordinaatit(x,y+2));
            }
        }
        
        return siirrot;
        
    }
    
    public ArrayList<Koordinaatit> ratsuSiirrot(int[][] lauta) {
        ArrayList<Koordinaatit> siirrot = new ArrayList<>();
        
        return siirrot;
    }
    
    public ArrayList<Koordinaatit> lähettiSiirrot(int[][] lauta) {
        ArrayList<Koordinaatit> siirrot = new ArrayList<>();
        
        return siirrot;
    }
    
    public ArrayList<Koordinaatit> torniSiirrot(int[][] lauta) {
        ArrayList<Koordinaatit> siirrot = new ArrayList<>();
        
        return siirrot;
    }
    
    public ArrayList<Koordinaatit> kuningatarSiirrot(int[][] lauta) {
        ArrayList<Koordinaatit> siirrot = new ArrayList<>();
        
        return siirrot;
    }
    
    public ArrayList<Koordinaatit> kuningasSiirrot(int[][] lauta) {
        ArrayList<Koordinaatit> siirrot = new ArrayList<>();
        
        return siirrot;
    }
    
    
    
}
