
package shakki.nappulat;

import shakki.domain.Koordinaatit;
import shakki.domain.Lauta;
import shakki.domain.Siirto;


public class Kuningatar extends Nappula {
    
    public Kuningatar(int id, int x, int y, int vari, Lauta lauta) {
        super(id, x, y, vari, lauta);
        
        this.tyyppi = TYYPPI.KUNINGATAR;
        this.arvo = 90;
        
        if (vari == 0) {
            this.numero = 5;
        } else {
            this.numero = -5;
        }
    }

    @Override
    public void paivitaSiirrot() {
        
        int kuninkaanSuunta = kuninkaanSuunta(this.vari - 1);
        
        katsoRuudut(1, 1, 8, kuninkaanSuunta);
        katsoRuudut(-1, -1, 7, kuninkaanSuunta);
        katsoRuudut(1, -1, 5, kuninkaanSuunta);
        katsoRuudut(-1, 1, 6, kuninkaanSuunta);
        katsoRuudut(1, 0, 1, kuninkaanSuunta);
        katsoRuudut(-1, 0, 2, kuninkaanSuunta);
        katsoRuudut(0, 1, 4, kuninkaanSuunta);
        katsoRuudut(0, -1, 3, kuninkaanSuunta);
        
    }
    
    public int nappulanArvio() {
        int arvio = 90;
        
        int[][] hyökätyt;
        if (vari == 0) {
            hyökätyt = lauta.mustanHyökätyt;
        } else {
            hyökätyt = lauta.valkoisenHyökätyt;
        }
        
        if (hyökätyt[x][y] > 0) {
            if (vari == 0) {
                if (lauta.valkoisenVuoro) {
                    arvio -= 5;
                } else {
                    arvio -= 30;
                }
            } else if (!lauta.valkoisenVuoro) {
                arvio -= 5;
            } else {
                arvio -= 30;
            }
        }
        
        if (vari == 0 && y > 3) {
            arvio += 3;
        } else if (vari == 1 && y < 8) {
            arvio += 3;
        }
        
        return arvio;
    }
    
    
}
