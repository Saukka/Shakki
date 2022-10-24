
package shakki.nappulat;

import shakki.domain.Koordinaatit;
import shakki.domain.Lauta;

public class Torni extends Nappula {
    
    public Torni (int id, int x, int y, int vari) {
        super(id, x, y, vari);
        
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
    public void paivitaSiirrot(Lauta lauta, int kiinnitys) {
        this.blokit.clear();
        this.siirrot.clear();
        
        
        boolean voiSiirtaa = true;
        
        if (kiinnitys > 1) {
            voiSiirtaa = false;
        }
        
        for (int i = x + 1; i < 9; i++) {
            
            int p = katso(lauta, i, y, voiSiirtaa);
            if (p == 0) break;
            if (p == 1) continue;
            if (p == 2) {
                //shakki
            }
        }
        tormannyt = false;
        
        for (int i = x - 1; i > 0; i--) {
            
            int p = katso(lauta, i, y, voiSiirtaa);
            if (p == 0) break;
            if (p == 1) continue;
            if (p == 2) {
                //shakki
            }
        }
        tormannyt = false;
        
        if (kiinnitys == 2) {
            voiSiirtaa = true;
        } else if (kiinnitys == 1) {
            voiSiirtaa = false;
        }
        
        for (int j = y + 1; j < 10; j++) {
            
            int p = katso(lauta, x, j, voiSiirtaa);
            if (p == 0) break;
            if (p == 1) continue;
            if (p == 2) {
                //shakki
            }
        }
        tormannyt = false;
        
        for (int j = y - 1; j > 1; j--) {
            
            int p = katso(lauta, x, j, voiSiirtaa);
            if (p == 0) break;
            if (p == 1) continue;
            if (p == 2) {
                //shakki
            }
        }
        tormannyt = false;
        
        paivitaArvio(lauta.lauta);
    }
    
    @Override
    public Torni kopioi() {
        
        Torni n = new Torni(id, x, y, vari);
        
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
    
}
