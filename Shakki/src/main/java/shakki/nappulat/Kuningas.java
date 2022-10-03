
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
    
    
    @Override
    public Kuningas kopioi() {
        
        Kuningas n = new Kuningas(id, x, y, vari);
        
        for (int i = 0; i < this.siirrot.size(); i++) {
            n.siirrot.add(this.siirrot.get(i));
        }
        
        for (int i = 0; i < this.blokit.size(); i++) {
            n.blokit.add(this.blokit.get(i));
        }
        
        n.onLiikkunut = this.onLiikkunut;
        
        return n;
        
    }
    
}
