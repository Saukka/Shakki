
package shakki.domain;


public class Siirto {
    
    int x;
    int y;
    
    int uusX;
    int uusY;
    
    int vahvuus;
    
    int suunta;
    
    
    public Siirto(int x, int y, int uusX, int uusY, int suunta, int vahvuus) {
        this.x = x;
        this.y = y;
        this.uusX = uusX;
        this.uusY = uusY;
        
        
        this.suunta = suunta;
        
        this.vahvuus = vahvuus;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getUusX() {
        return uusX;
    }
    
    public int getUusY() {
        return uusY;
    }
    
    public int getVahvuus() {
        return vahvuus;
    }
    
    public int getSuunta() {
        return suunta;
    }
}
