
package shakki.nappulat;

import shakki.domain.Koordinaatit;


public class Ratsu extends Nappula {
    
    public Ratsu(int id, int x, int y, int vari) {
        super(id, x, y, vari);
        
        if (vari == 0) {
            this.numero = 2;
            this.arvo = 30;
        } else {
            this.numero = -2;
            this.arvo = -30;
        }
    }
    
    @Override
    public void paivitaSiirrot(Nappula[][] lauta, int kiinnitys) {
        this.blokit.clear();
        this.siirrot.clear();
        
        if (kiinnitys != 0) {
            return;
        }
        
        if (lauta[x + 1][y + 2] == null || (!omaNappula(lauta[x + 1][y + 2].getID()))) {
            this.siirrot.add(new Koordinaatit(x + 1, y + 2));
        }
        
        if (x + 2 < 10 && (lauta[x + 2][y + 1] == null || !omaNappula(lauta[x + 2][y + 1].getID()))) {
            this.siirrot.add(new Koordinaatit(x + 2, y + 1));
        }
        
        if (x + 2 < 10 && (lauta[x + 2][y - 1] == null || !omaNappula(lauta[x + 2][y - 1].getID()))) {
            this.siirrot.add(new Koordinaatit(x + 2, y - 1));
        }
        
        if (lauta[x + 1][y - 2] == null || !omaNappula(lauta[x + 1][y - 2].getID())) {
            this.siirrot.add(new Koordinaatit(x + 1, y - 2));
        }
        
        if (lauta[x - 1][y - 2] == null || !omaNappula(lauta[x - 1][y - 2].getID())) {
            this.siirrot.add(new Koordinaatit(x - 1, y - 2));
        }
        
        if (x - 1 > 0 && (lauta[x - 2][y - 1] == null || !omaNappula(lauta[x - 2][y - 1].getID()))) {
            this.siirrot.add(new Koordinaatit(x - 2, y - 1));
        }
        
        if (x - 1 > 0 && (lauta[x - 2][y + 1] == null || !omaNappula(lauta[x - 2][y + 1].getID()))) {
            this.siirrot.add(new Koordinaatit(x - 2, y + 1));
        }
        
        if (lauta[x - 1][y + 2] == null || !omaNappula(lauta[x - 1][y + 2].getID())) {
            this.siirrot.add(new Koordinaatit(x - 1, y + 2));
        }
    }   
    
    
    @Override
    public Ratsu kopioi() {
        
        Ratsu n = new Ratsu(id, x, y, vari);
        
        for (int i = 0; i < this.siirrot.size(); i++) {
            n.siirrot.add(new Koordinaatit(this.siirrot.get(i).getX(), this.siirrot.get(i).getY()));
        }
        
        for (int i = 0; i < this.blokit.size(); i++) {
            n.blokit.add(new Koordinaatit(this.blokit.get(i).getX(), this.blokit.get(i).getX()));
        }
        
        n.onLiikkunut = this.onLiikkunut;
        
        return n;
        
    }

}