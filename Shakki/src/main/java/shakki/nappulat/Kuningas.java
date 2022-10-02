
package shakki.nappulat;

import shakki.domain.Koordinaatit;


public class Kuningas extends Nappula {
    
    public Kuningas (int id, int x, int y, int vari) {
        super(id, x, y, vari);
        
        if (vari == 1) {
            this.numero = 60;
            this.arvo = 1000;
        } else {
            this.numero = -60;
            this.arvo = -1000;
        }
    }
    
    @Override
    public void paivitaSiirrot(Nappula[][] lauta, int kiinnitys) {
        
        this.siirrot.clear();
        
        for (int i = x - 1; i < x + 2; i++) {
            if (lauta[i][y] == null || !omaNappula(lauta[i][y].getID())) {
                this.siirrot.add(new Koordinaatit(i, y));
            }
            
            if (lauta[i][y + 1] == null || !omaNappula(lauta[i][y + 1].getID())) {
                this.siirrot.add(new Koordinaatit(i, y + 1));
            }
            
            if (lauta[i][y - 1] == null || !omaNappula(lauta[i][y - 1].getID())) {
                this.siirrot.add(new Koordinaatit(i, y - 1));
            }
        }
    }
    
}
