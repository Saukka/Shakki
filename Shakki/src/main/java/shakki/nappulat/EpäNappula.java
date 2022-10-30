
package shakki.nappulat;

import shakki.domain.Lauta;



public class EpäNappula extends Nappula{
    
    public EpäNappula (int x, int y, Lauta lauta) {
        super(-1, x, y, -1, lauta);
        this.tyyppi = TYYPPI.EPANAPPULA;
        
        this.numero = 0;
        this.arvo = 0;
    }

    
    
}
