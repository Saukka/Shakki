
package shakki.domain;

import shakki.nappulat.*;

public class Siirto {
    
    Nappula nappula;
    Nappula torni; // jos linnoitus
    
    Nappula syotyNappula;
    
    int x;
    int y;
    
    boolean oliLiikkunut;
    
    // int uusX;
    // int uusY;
    
    public Siirto(int x, int y, Nappula nappula, Nappula torni, Nappula syotyNappula, boolean oliLiikkunut) {
        
        this.x = x;
        this.y = y;
        
        this.nappula = nappula;
        this.torni = torni;
        this.syotyNappula = syotyNappula;
        
        this.oliLiikkunut = oliLiikkunut;
    }
    
    
    
}
