package shakki.nappulat;

import java.util.ArrayList;
import shakki.domain.*;


/**
 * Nappula-olion luokka.
 */
public abstract class Nappula {
    
    int id = 0;
    int numero; // numero kertoo nappulan tyypin
    int arvo; // Arvo kertoo nappulan arvon
    Double paikanArvo; // paikanArvo kertoo kuinka hyvässä paikassa nappula on laudalla
    
    int vari;
    
    // Nappulan koordinaatit
    int x;
    int y;
    
    boolean onLiikkunut;
    
    ArrayList<Koordinaatit> siirrot;
    ArrayList<Koordinaatit> blokit;
    
    
    ArrayList<Integer> kiinnitetyt; 
    // Lista nappuloista jotka nappula on kiinnittänyt kuninkaaseen. (Eli jos nappula 
    // siirtyisi pois tieltä, kuninkaan voisi syödä. Joten siirto olisi laiton)
    
    int kiinnitetynID = 100;
    boolean tormannyt = false;
    
    /**
     * 
     * @param id jokaisella nappulalla on uniikki id
     * @param x nappulan x-koordinaatti.
     * @param y nappulan y-koordinaatti.
     * @param vari nappulan väri. 0 = valkoinen, 1 = musta, -1 = ei ole nappula
     */
    public Nappula(int id, int x, int y, int vari) {
        
        this.id = id;
        this.vari = vari;
        
        this.x = x;
        this.y = y;
        
        onLiikkunut = false;
        
        siirrot = new ArrayList<>();
        blokit = new ArrayList<>();
        kiinnitetyt = new ArrayList<>();
        

    }
    
    // Metodit joita käytetään siirtojen päivittämisessä
    
    public boolean omaNappula(int nid) {
        return (this.vari == 0 && nid < 17 && nid > 0) || ((this.vari == 1 && nid > 16)) || nid == -1;
    }
    
    public int katso(Nappula[][] lauta, int i, int j, boolean voiSiirtaa) {
        
        if (lauta[i][j] == null) {
            if (!tormannyt && voiSiirtaa) {
                this.siirrot.add(new Koordinaatit(i, j));
            }
            return 1;
            
        } else if (omaNappula(lauta[i][j].getID())) {
            tormannyt = false;
            blokit.add(new Koordinaatit(i, j));
            return 0;
        } else {
            // on vihollisen nappula
            if (lauta[i][j].getID() == 5 || lauta[i][j].getID() == 21) {
                if (tormannyt) {
                    kiinnitetyt.add(kiinnitetynID);
                    tormannyt = false;
                    return 0; // kiinnitys kuninkaaseen
                }
                this.siirrot.add(new Koordinaatit(i, j));
                return 2; // shakki
            }
            if (tormannyt) {
                tormannyt = false;
                return 0; // ei kiinnitystä
            }
            tormannyt = true;
            kiinnitetynID = lauta[i][j].getID();
            if (voiSiirtaa) {
                this.siirrot.add(new Koordinaatit(i, j));
            }
            return 1;
        }
        
    }
    
    public void paivitaSiirrot(Nappula[][] lauta, int kiinnitys) {
        
    }
        public int getNumero() {
        return this.numero;
    }
    
    public int getArvo() {
        return this.arvo;
    }
    
    public int getID() {
        if (this == null) return 0;
        return this.id;
    }
    
    public int getVari() {
        return this.vari;
    }
    
    public void SetKoordinaatit(int x, int y) {
        this.x = x;
        this.y = y;
        onLiikkunut = true;
    }
    
    public void lisaaRajoittava(int i) {
        kiinnitetyt.add(i);
    }
    
    public ArrayList<Koordinaatit> getSiirrot() {
        return this.siirrot;
    }
    
    public ArrayList<Koordinaatit> getBlokit() {
        return this.blokit;
    }
        
    public Nappula kopioi() {
        System.out.println("Ei pitäisi näkyä");
    return this;
    }
    
    
    
}
