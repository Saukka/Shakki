
package shakki.nappulat;

import shakki.domain.Koordinaatit;
import shakki.domain.Lauta;
import shakki.domain.Siirto;

public class Torni extends Nappula {
    
    public Torni (int id, int x, int y, int vari, Lauta lauta) {
        super(id, x, y, vari, lauta);
        
        this.tyyppi = TYYPPI.TORNI;
        this.arvo = 50;
        
        if (vari == 0) {
            this.numero = 4;
        } else {
            this.numero = -4;
        }
        
    }
    

    @Override
    public void paivitaSiirrot() {
        
        int kuninkaanSuunta = kuninkaanSuunta(this.vari - 1);
        
        katsoRuudut(1, 0, 1, kuninkaanSuunta);
        katsoRuudut(-1, 0, 2, kuninkaanSuunta);
        katsoRuudut(0, 1, 4, kuninkaanSuunta);
        katsoRuudut(0, -1, 3, kuninkaanSuunta);
    }
    
    @Override
    public int nappulanArvio() {
        int arvio = 50;
        
        int[][] puolustetut;
        int[][] hyökätyt;
        if (vari == 0) {
            puolustetut = lauta.valkoisenHyökätyt;
            hyökätyt = lauta.mustanHyökätyt;
            
        } else {
            puolustetut = lauta.mustanHyökätyt;
            hyökätyt = lauta.valkoisenHyökätyt;
        }
        if (hyökätyt[x][y] > puolustetut[x][y]) {
            arvio -= hyökätyt[x][y] - puolustetut[x][y];
        }
        if (puolustetut[x][y] > 0) {
            arvio += 6;
        }
        
        arvio += this.siirrot.size() / 2;
        
        return arvio;
    }
    
}
