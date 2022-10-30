package shakki.nappulat;

import shakki.domain.Koordinaatit;
import shakki.domain.Lauta;
import shakki.domain.Siirto;

public class Lähetti extends Nappula {
    
    public Lähetti(int id, int x, int y, int vari, Lauta lauta) {
        super(id, x, y, vari, lauta);
        this.tyyppi = TYYPPI.LAHETTI;
        if (vari == 0) {
            this.numero = 3;
            this.arvo = 30;
        } else {
            this.numero = -3;
            this.arvo = -30;
        }
    }
    
    
    @Override
    public void paivitaSiirrot() {
        
        int kuninkaanSuunta = kuninkaanSuunta(this.vari - 1);
        
        katsoRuudut(1, 1, 8, kuninkaanSuunta);
        katsoRuudut(-1, -1, 7, kuninkaanSuunta);
        katsoRuudut(1, -1, 5, kuninkaanSuunta);
        katsoRuudut(-1, 1, 6, kuninkaanSuunta);
        
    }
    
    @Override
    public void paivitaArvio(Nappula[][] lauta) {
        this.paikanArvo = 0;
        if (this.syoty) {
            return;
        }
        
        if (this.siirrot.size() > 3) {
            this.paikanArvo += 2;
        }
        
        if (vari > 0) {
            this.paikanArvo *= -1;
        }
        this.paikanArvo += this.arvo;
    }
    
}
