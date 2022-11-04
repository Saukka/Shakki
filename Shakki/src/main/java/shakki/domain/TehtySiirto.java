
package shakki.domain;

import java.util.ArrayList;
import java.util.HashMap;
import shakki.nappulat.*;

/**
 * Tehdyn siirron luokka. Luokka säilyttää kaikki oleelliset tiedot laudan palauttamiseen edelliseen tilaan.
 */
public class TehtySiirto {
    
    Nappula nappula;
    Nappula torni; // jos linnoitus
    
    Nappula syotyNappula;
    
    int x;
    int y;
    
    boolean oliLiikkunut;
    
    int shakitus;
    
    ArrayList<Nappula> nappulat;
    
    HashMap<Nappula, ArrayList<Siirto>> nappuloidenSiirrot;
    HashMap<Nappula, ArrayList<Koordinaatit>> nappuloidenBlokit;
    HashMap<Nappula, ArrayList<Siirto>> siirrotShakissa;
    
    HashMap<Nappula, Nappula> kiinnitetyt;
    HashMap<Nappula, Integer> kiinnitykset;
    
    HashMap<Nappula, Integer> viimeksiPaivitetty;
    
    HashMap<Nappula, Nappula> uudetKiinnitetyt;
    HashMap<Nappula, Integer> kiinnitysEnnen;
    
    int[][] valkoisenHyokatyt;
    int[][] mustanHyokatyt;
    
    
    public TehtySiirto(int x, int y, Nappula nappula, Nappula torni, Nappula syotyNappula, boolean oliLiikkunut, int shakitus) {
        
        this.x = x;
        this.y = y;
        
        this.nappula = nappula;
        this.torni = torni;
        this.syotyNappula = syotyNappula;
        
        this.oliLiikkunut = oliLiikkunut;
        
        this.shakitus = shakitus;
        
        nappulat = new ArrayList<>();
    
        nappuloidenSiirrot = new HashMap<>();
        nappuloidenBlokit = new HashMap<>();
        siirrotShakissa = new HashMap<>();
        
        kiinnitetyt = new HashMap<>();
        kiinnitykset = new HashMap<>();
        
        uudetKiinnitetyt = new HashMap<>();
        kiinnitysEnnen = new HashMap<>();
        
        viimeksiPaivitetty = new HashMap<>();
        
        valkoisenHyokatyt = new int[10][12];
        mustanHyokatyt = new int[10][12];
    }
    
    /**
     * Lisää nappulan siirron vaikuttaneiden nappuloiden listaan.
     * @param n lisättävä nappula
     */
    public void lisaaNappula(Nappula n) {
        nappulat.add(n);
    }
    
    /**
     * Lisää nappulan siirrot listaan.
     * @param n
     * @param siirrot 
     */
    public void lisaaSiirrot(Nappula n, ArrayList<Siirto> siirrot) {
        
        ArrayList <Siirto> siirrotKopio = new ArrayList<>();
        
        for (Siirto s : siirrot) {
            siirrotKopio.add(new Siirto(s.getX(), s.getY(), s.getUusX(), s.getUusY(), s.getSuunta(), s.getVahvuus(), s.getOhestalyönti()));
        }
        nappuloidenSiirrot.put(n, siirrotKopio);
    }
    
    public void lisaaBlokit(Nappula n, ArrayList<Koordinaatit> blokit) {
        ArrayList <Koordinaatit> blokitKopio = new ArrayList<>();
        
        for (Koordinaatit k : blokit) {
            blokitKopio.add(new Koordinaatit(k.getX(), k.getY()));
        }
        nappuloidenBlokit.put(n, blokitKopio);
    }
    
    public void lisaaSiirrotShakissa(Nappula n, ArrayList<Siirto> siirrot) {
        ArrayList <Siirto> siirrotKopio = new ArrayList<>();
        
        for (Siirto s : siirrot) {
            siirrotKopio.add(new Siirto(s.getX(), s.getY(), s.getUusX(), s.getUusY(), s.getSuunta(), s.getVahvuus(), s.getOhestalyönti()));
        }
        siirrotShakissa.put(n, siirrotKopio);
    }
    
    public void lisaaViimeksiPaivitetty(Nappula n, int i) {
        viimeksiPaivitetty.put(n, i);
    }
    
    public void lisaaVanhaKiinnitys(Nappula n, Nappula kiinnitetty, int kiinnitys) {
        kiinnitetyt.put(n, kiinnitetty);
        kiinnitykset.put(kiinnitetty, kiinnitys);
    }
    
    public void lisaaUusiKiinnitys (Nappula n, Nappula kiinnitetty, int kiinnitys) {
        uudetKiinnitetyt.put(n, kiinnitetty);
        kiinnitysEnnen.put(n, kiinnitys);
    }
    
    public int NappulanVanhaKiinnitys (Nappula n) {
        return kiinnitykset.getOrDefault(n, 0);
    }
    
}
