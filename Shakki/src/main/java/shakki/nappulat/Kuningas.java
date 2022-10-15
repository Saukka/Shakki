
package shakki.nappulat;

import shakki.domain.Koordinaatit;


public class Kuningas extends Nappula {
    
    public Kuningas (int id, int x, int y, int vari) {
        super(id, x, y, vari);
        
        if (vari == 0) {
            this.numero = 6;
            this.arvo = 1000;
        } else {
            this.numero = -6;
            this.arvo = -1000;
        }
    }
    
    @Override
    public void paivitaSiirrot(Nappula[][] lauta, int kiinnitys) {
        this.blokit.clear();
        this.siirrot.clear();
        
        for (int i = x - 1; i < x + 2; i++) {
            if (lauta[i][y] == null || !omaNappula(lauta[i][y].getID())) {
                this.siirrot.add(new Koordinaatit(i, y));
            } else if (omaNappula(lauta[i][y].getID())) {
                blokit.add(new Koordinaatit(i, y));
            }
            
            if (lauta[i][y + 1] == null || !omaNappula(lauta[i][y + 1].getID())) {
                this.siirrot.add(new Koordinaatit(i, y + 1));
            } else if (omaNappula(lauta[i][y + 1].getID())) {
                blokit.add(new Koordinaatit(i, y + 1));
            }
            
            if (lauta[i][y - 1] == null || !omaNappula(lauta[i][y - 1].getID())) {
                this.siirrot.add(new Koordinaatit(i, y - 1));
            } else if (omaNappula(lauta[i][y - 1].getID())) {
                blokit.add(new Koordinaatit(i, y - 1));
            }
        }
        if (!this.onLiikkunut) {
            blokit.add(new Koordinaatit(x + 2, y));
            blokit.add(new Koordinaatit(x + 3, y));
            blokit.add(new Koordinaatit(x - 2, y));
            blokit.add(new Koordinaatit(x - 3, y));
            blokit.add(new Koordinaatit(x - 4, y));
        }
        
        //linnoitus oikealle
        if (!this.onLiikkunut && lauta[x + 1][y] == null && lauta[x + 2][y] == null && lauta[x + 3][y] != null && !lauta[x + 3][y].onLiikkunut) {
            this.siirrot.add(new Koordinaatit(x + 2, y));
        }
        //linnoitus vasemmalle
        if (!this.onLiikkunut && lauta[x - 1][y] == null && lauta[x - 2][y] == null && lauta[x - 3][y] == null && lauta[x - 4][y] != null && !lauta[x - 4][y].onLiikkunut) {
            this.siirrot.add(new Koordinaatit(x - 2, y));
        }
        paivitaArvio(lauta);
    }
    
    
    @Override
    public Kuningas kopioi() {
        
        Kuningas n = new Kuningas(id, x, y, vari);
        
        for (int i = 0; i < this.siirrot.size(); i++) {
            n.siirrot.add(new Koordinaatit(this.siirrot.get(i).getX(), this.siirrot.get(i).getY()));
        }
        
        for (int i = 0; i < this.blokit.size(); i++) {
            n.blokit.add(new Koordinaatit(this.blokit.get(i).getX(), this.blokit.get(i).getX()));
        }
        
        n.onLiikkunut = this.onLiikkunut;
        n.paikanArvo = this.paikanArvo;
        
        return n;
        
    }
    
    @Override
    public void paivitaArvio(Nappula[][] lauta) {
        this.paikanArvo = 0;
        if (this.syoty) {
            return;
        }
        
        if (vari == 0) {
            if (this.y < 3) {
                this.paikanArvo += 10;
            }
            for (int i = x - 1; i < x + 2; i++) {
                if (lauta[i][y + 1] != null && lauta[i][y + 1].getNumero() == 1) this.paikanArvo += 8;
            }
        } else {
            if (this.y > 8) {
                this.paikanArvo -= 10;
            }
            for (int i = x - 1; i < x + 2; i++) {
                if (lauta[i][y - 1] != null && lauta[i][y - 1].getNumero() == -1) this.paikanArvo -= 8;
            }
        }
        
        
        
        this.paikanArvo += this.arvo;
    }
    
}
