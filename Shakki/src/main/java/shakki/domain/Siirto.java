
package shakki.domain;


public class Siirto {
    
    int x;
    int y;
    
    int uusX;
    int uusY;
    
    int vahvuus;
    
    int suunta;
    
    boolean ohestalyönti = false;
    
    public Siirto(int x, int y, int uusX, int uusY, int suunta, int vahvuus, boolean ohestaLyönti) {
        this.x = x;
        this.y = y;
        this.uusX = uusX;
        this.uusY = uusY;
        
        this.suunta = suunta;
        
        this.vahvuus = vahvuus;
        
        ohestalyönti = ohestaLyönti;
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
    
    public void asetaOhestaLyönti() {
        ohestalyönti = true;
    }
    
    public boolean getOhestalyönti() {
        return ohestalyönti;
    }
}
