
package shakki.nappulat;

import shakki.domain.Koordinaatit;
import shakki.domain.Lauta;
import shakki.domain.Siirto;

public class Torni extends Nappula {
    
    public Torni (int id, int x, int y, int vari, Lauta lauta) {
        super(id, x, y, vari, lauta);
        
        this.tyyppi = TYYPPI.TORNI;
        
        if (vari == 0) {
            this.numero = 4;
            this.arvo = 50;
            this.paikanArvo = 2;
        } else {
            this.numero = -4;
            this.arvo = -50;
            this.paikanArvo = -2;
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
    public void paivitaArvio(Nappula[][] lauta) {
        this.paikanArvo = 0;
        if (this.syoty) {
            return;
        }
        this.paikanArvo = 0;
        if (this.x > 3 && this.x < 6) {
            this.paikanArvo += 7;
        }
        if (this.siirrot.size() > 4) {
            this.paikanArvo += 4;
        }
        
        if (this.vari > 0) {
            this.paikanArvo *= -1;
        }
        this.paikanArvo += this.arvo;
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
        if (hyökätyt[x][y] > 0) {
            arvio -= hyökätyt[x][y] * 8;
        }
        if (puolustetut[x][y] > 0) {
            arvio += 6;
        }
        
        arvio += this.siirrot.size() / 2;
        
        return arvio;
    }
    
}
