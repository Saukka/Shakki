
package shakki.nappulat;

import java.util.ArrayList;
import shakki.domain.Koordinaatit;
import shakki.domain.Lauta;
import shakki.domain.Siirto;


public class Kuningas extends Nappula {
    
    public Kuningas (int id, int x, int y, int vari, Lauta lauta) {
        super(id, x, y, vari, lauta);
        this.tyyppi = TYYPPI.KUNINGAS;
        
        if (vari == 0) {
            this.numero = 6;
            this.arvo = 1000;
        } else {
            this.numero = -6;
            this.arvo = -1000;
        }
    }
    
    @Override
    public void paivitaSiirrot() {
        
        int[][] hyokatyt;
        if (vari == 0) {
            hyokatyt = lauta.mustanHyökätyt;
        } else {
            hyokatyt = lauta.valkoisenHyökätyt;
        }
        
        
        for (int i = x - 1; i < x + 2; i++) {
            lisaa(hyokatyt, i, y);
            lisaa(hyokatyt, i, y + 1);
            lisaa(hyokatyt, i, y - 1);
        }
        
        //linnoitus oikealle
        if (!this.onLiikkunut && lauta.lauta[x + 1][y] == null && hyokatyt[x + 1][y] < 1 && lauta.lauta[x + 2][y] == null && hyokatyt[x + 2][y] < 1 && lauta.lauta[x + 3][y] != null && lauta.lauta[x + 3][y].tyyppi == TYYPPI.TORNI &&!lauta.lauta[x + 3][y].onLiikkunut) {
            this.siirrot.add(new Siirto(x, y, x + 2, y, 0, 2));
        }
        //linnoitus vasemmalle
        if (!this.onLiikkunut && lauta.lauta[x - 1][y] == null && hyokatyt[x - 1][y] < 1 && lauta.lauta[x - 2][y] == null && hyokatyt[x - 2][y] < 1 && lauta.lauta[x - 3][y] == null && lauta.lauta[x - 4][y] != null && lauta.lauta[x - 4][y].tyyppi == TYYPPI.TORNI &&!lauta.lauta[x - 4][y].onLiikkunut) {
            this.siirrot.add(new Siirto(x, y, x - 2, y, 0,2));
        }
        
    }
    
    public void lisaa(int[][] hyokatyt, int x, int y) {
        if (hyokatyt[x][y] < 1) {
            if (lauta.lauta[x][y] == null) {
                this.siirrot.add(new Siirto(this.x, this.y, x, y, 0, 0));
            } else if (!omaNappula(lauta.lauta[x][y])) {
                this.siirrot.add(new Siirto(this.x, this.y, x, y, 0, 2));
            }
        } else if (omaNappula(lauta.lauta[x][y]) || hyokatyt[x][y] > 0) {
            blokit.add(new Koordinaatit(x, y));
        } 
    }
    
    @Override
    public void paivitaKunShakissa(ArrayList<Koordinaatit> ruudut, int[][] hyokatyt) {
        
        this.siirrotShakissa.clear();
        for (int i = 0; i < this.siirrot.size(); i++) {
            this.siirrotShakissa.add(this.siirrot.get(i));
        }
    }
    
    
}
