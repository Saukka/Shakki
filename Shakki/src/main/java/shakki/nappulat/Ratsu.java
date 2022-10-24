
package shakki.nappulat;

import shakki.domain.Koordinaatit;
import shakki.domain.Lauta;


public class Ratsu extends Nappula {
    
    public Ratsu(int id, int x, int y, int vari) {
        super(id, x, y, vari);
        
        this.paikanArvo = 0;
        
        if (vari == 0) {
            this.numero = 2;
            this.arvo = 30;
        } else {
            this.numero = -2;
            this.arvo = -30;
        }
    }
    
    @Override
    public void paivitaSiirrot(Lauta l, int kiinnitys) {
        this.blokit.clear();
        this.siirrot.clear();
        
        if (kiinnitys != 0) {
            return;
        }
        
        if (l.lauta[x + 1][y + 2] == null || (!omaNappula(l.lauta[x + 1][y + 2].getID()))) {
            this.siirrot.add(new Koordinaatit(x + 1, y + 2));
        } else if (l.lauta[x + 1][y + 2] != null) {
            this.blokit.add(new Koordinaatit(x + 1, y + 2));
        }
        
        if (x + 2 < 10 && (l.lauta[x + 2][y + 1] == null || !omaNappula(l.lauta[x + 2][y + 1].getID()))) {
            this.siirrot.add(new Koordinaatit(x + 2, y + 1));
        } else if (x + 2 < 10 && l.lauta[x + 2][y + 1] != null) {
            this.blokit.add(new Koordinaatit(x + 2, y + 1));
        }
        
        if (x + 2 < 10 && (l.lauta[x + 2][y - 1] == null || !omaNappula(l.lauta[x + 2][y - 1].getID()))) {
            this.siirrot.add(new Koordinaatit(x + 2, y - 1));
        } else if (x + 2 < 10 && l.lauta[x + 2][y - 1] != null) {
            this.blokit.add(new Koordinaatit(x + 2, y - 1));
        }
        
        if (l.lauta[x + 1][y - 2] == null || !omaNappula(l.lauta[x + 1][y - 2].getID())) {
            this.siirrot.add(new Koordinaatit(x + 1, y - 2));
        } else if (l.lauta[x + 1][y - 2] != null) {
            this.blokit.add(new Koordinaatit(x + 1, y - 2));
        }
        
        if (l.lauta[x - 1][y - 2] == null || !omaNappula(l.lauta[x - 1][y - 2].getID())) {
            this.siirrot.add(new Koordinaatit(x - 1, y - 2));
        } else if (l.lauta[x - 1][y - 2] != null) {
            this.blokit.add(new Koordinaatit(x - 1, y - 2));
        }
        
        if (x - 2 > 0 && (l.lauta[x - 2][y - 1] == null || !omaNappula(l.lauta[x - 2][y - 1].getID()))) {
            this.siirrot.add(new Koordinaatit(x - 2, y - 1));
        } else if (x - 2 > 0 && l.lauta[x - 2][y - 1] != null) {
            this.blokit.add(new Koordinaatit(x - 2, y - 1 ));
        }
        
        if (x - 2 > 0 && (l.lauta[x - 2][y + 1] == null || !omaNappula(l.lauta[x - 2][y + 1].getID()))) {
            this.siirrot.add(new Koordinaatit(x - 2, y + 1));
        } else if (x - 2 > 0 && l.lauta[x - 2][y + 1] != null) {
            this.blokit.add(new Koordinaatit(x - 2, y + 1 ));
        }
        
        if (l.lauta[x - 1][y + 2] == null || !omaNappula(l.lauta[x - 1][y + 2].getID())) {
            this.siirrot.add(new Koordinaatit(x - 1, y + 2));
        } else if (l.lauta[x - 1][y + 2] != null) {
            this.blokit.add(new Koordinaatit(x - 1, y + 2 ));
        }
        
        paivitaArvio(l.lauta);
    }   
    
    
    @Override
    public Ratsu kopioi() {
        
        Ratsu n = new Ratsu(id, x, y, vari);
        
        for (int i = 0; i < this.siirrot.size(); i++) {
            n.siirrot.add(new Koordinaatit(this.siirrot.get(i).getX(), this.siirrot.get(i).getY()));
        }
        
        for (int i = 0; i < this.siirrotShakissa.size(); i++) {
            n.siirrotShakissa.add(new Koordinaatit(this.siirrotShakissa.get(i).getX(), this.siirrotShakissa.get(i).getY()));
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
        
        if (this.x > 2 && this.x < 7 && this.y > 3 && this.y < 8) {
            this.paikanArvo += 10;
        }
        
        this.paikanArvo += this.siirrot.size() * 2;
        
        if (this.vari > 0) {
            this.paikanArvo *= -1;
        }
        this.paikanArvo += this.arvo;
    }

}