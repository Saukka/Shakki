
package shakki.domain;

/**
 * Nappula-olion luokka.
 */
public class Nappula {
    
    int id; // id kertoo mikä nappula on kyseessä
    int arvo; // Arvo kertoo nappulan arvon
    
    // Nappulan koordinaatit
    int x;
    int y;
    
    public Nappula(int id, int arvo, int x, int y) {
        this.id = id;
        this.arvo = arvo;
    }
    
    public int getNumero() {
        return this.id;
    }
    
    public int getArvo() {
        return this.arvo;
    }
    
}
