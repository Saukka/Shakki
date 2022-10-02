
package shakki.domain;

public class Koordinaatit {
    int x;
    int y;
    
    public Koordinaatit(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void setKoordinaatit(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
}
