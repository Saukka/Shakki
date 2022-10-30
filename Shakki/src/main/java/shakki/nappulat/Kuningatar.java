
package shakki.nappulat;

import shakki.domain.Koordinaatit;
import shakki.domain.Lauta;
import shakki.domain.Siirto;


public class Kuningatar extends Nappula {
    
    public Kuningatar(int id, int x, int y, int vari, Lauta lauta) {
        super(id, x, y, vari, lauta);
        
        this.tyyppi = TYYPPI.KUNINGATAR;
        if (vari == 0) {
            this.numero = 5;
            this.arvo = 90;
            this.paikanArvo = 7;
        } else {
            this.numero = -5;
            this.arvo = -90;
            this.paikanArvo = -7;
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
    
    @Override
    public void paivitaArvio(Nappula[][] lauta) {
        this.paikanArvo = 0;
        if (this.syoty) {
            return;
        }
        
        if (this.y > 3 && this.y < 8) {
            this.paikanArvo += 10;
        }
        
        this.paikanArvo += this.siirrot.size();
        
        if (this.vari > 0) {
            this.paikanArvo *= -1;
        }
        
        this.paikanArvo += this.arvo;
    }
    
    
}
