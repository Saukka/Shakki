
package shakki.nappulat;

import shakki.domain.Koordinaatit;
import shakki.domain.Lauta;
import shakki.domain.Siirto;


public class Ratsu extends Nappula {
    
    public Ratsu(int id, int x, int y, int vari, Lauta lauta) {
        super(id, x, y, vari, lauta);
        
        this.tyyppi = TYYPPI.RATSU;
        this.arvo = 30;
        
        if (vari == 0) {
            this.numero = 2;
        } else {
            this.numero = -2;
        }
    }
    
    @Override
    public void paivitaSiirrot() {
       
        int[][] hyokatyt;
        if (this.vari == 0) {
            hyokatyt = lauta.mustanHyökätyt;
        } else {
            hyokatyt = lauta.valkoisenHyökätyt;
        }
        
        katso(hyokatyt, x + 1, y + 2);
        
        if (x + 2 < 9) {
            katso(hyokatyt, x + 2, y + 1);
            katso(hyokatyt, x + 2, y - 1);
        }
        katso(hyokatyt, x + 1, y - 2);
        katso(hyokatyt, x - 1, y - 2);
        
        if (x - 2 > 0) {
            katso(hyokatyt, x - 2, y - 1);
            katso(hyokatyt, x - 2, y + 1);
        }
        katso(hyokatyt, x - 1, y + 2);
        
    }
    
    public void katso(int[][] hyokatyt, int x, int y) {
        
        if (lauta.lauta[x][y] != null && !omaNappula(lauta.lauta[x][y])) {
            // vastustajan nappula
            this.siirrot.add(new Siirto(this.x, this.y, x, y, 0, siirronVahvuus(x, y), false));
            
            if (lauta.lauta[x][y].getTyyppi() == TYYPPI.KUNINGAS) {
                shakita(10);
            }
            return;
        } 
        if (lauta.lauta[x][y] == null) {
            this.siirrot.add(new Siirto(this.x, this.y, x, y, 0, siirronVahvuus(x, y), false));
            return;
        }
        this.blokit.add(new Koordinaatit(x, y));
    }
    
    
    @Override
    public int nappulanArvio() {
        int arvio = 30;
        
        int[][] puolustetut;
        int[][] hyökätyt;
        if (vari == 0) {
            puolustetut = lauta.valkoisenHyökätyt;
            hyökätyt = lauta.mustanHyökätyt;
            
        } else {
            puolustetut = lauta.mustanHyökätyt;
            hyökätyt = lauta.valkoisenHyökätyt;
        }
        
        if (x > 2 && x < 7 && y > 3 && y < 8) {
            arvio += 1;
            if (hyökätyt[x][y] < 1) {
                arvio += 2;
            }
            if (puolustetut[x][y] < 1 && hyökätyt[x][y] > 1) {
                arvio -= 10;
            }
        }
        
        arvio += this.siirrot.size() / 4;
        
        return arvio;
    }

}