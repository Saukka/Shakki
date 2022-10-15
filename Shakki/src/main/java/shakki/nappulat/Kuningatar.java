
package shakki.nappulat;

import shakki.domain.Koordinaatit;


public class Kuningatar extends Nappula {
    
    public Kuningatar(int id, int x, int y, int vari) {
        super(id, x, y, vari);
        
        if (vari == 0) {
            this.numero = 5;
            this.arvo = 90;
            this.paikanArvo = 7;
        } else {
            this.numero = -5;
            this.arvo = -90;
            this.paikanArvo = -7;
        }
    }

    @Override
    public void paivitaSiirrot(Nappula[][] lauta, int kiinnitys) {
        this.blokit.clear();
        this.siirrot.clear();
        
        boolean voiSiirtaa = true;
        
        if (kiinnitys < 4 && kiinnitys > 0) {
            voiSiirtaa = false;
        }
        
        for (int i = x + 1, j = y + 1; i < 9 && j < 10; i++, j++) {
            
            int p = katso(lauta, i, j, voiSiirtaa);
            if (p == 0) break;
            if (p == 1) continue;
            if (p == 2) {
                //shakki
            }
        }
        tormannyt = false;
        
        for (int i = x - 1, j = y - 1; i > 0 && j > 1; i--, j--) {
            
            int p = katso(lauta, i, j, voiSiirtaa);
            if (p == 0) break;
            if (p == 1) continue;
            if (p == 2) {
                //shakki
            }
        }
        tormannyt = false;
        
        if (kiinnitys == 4) {
            voiSiirtaa = false;
        } else if (kiinnitys == 3) {
            voiSiirtaa = true;
        }
        
        for (int i = x + 1, j = y - 1; i < 9 && j > 1; i++, j--) {
            
            int p = katso(lauta, i, j, voiSiirtaa);
            if (p == 0) break;
            if (p == 1) continue;
            if (p == 2) {
                //shakki
            }
        }
        tormannyt = false;
        
        for (int i = x - 1, j = y + 1; i > 0 && j < 10; i--, j++) {
            
            int p = katso(lauta, i, j, voiSiirtaa);
            if (p == 0) break;
            if (p == 1) continue;
            if (p == 2) {
                //shakki
            }
        }
        tormannyt = false;
        
        voiSiirtaa = true;
        
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
        
        if (kiinnitys == 1) {
            voiSiirtaa = false;
        } else if (kiinnitys == 2) {
            voiSiirtaa = true;
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
        
        paivitaArvio(lauta);
    }
    
    @Override
    public Kuningatar kopioi() {
        
        Kuningatar n = new Kuningatar(id, x, y, vari);
        
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
        
        if (this.y > 3 && this.y < 8) {
            this.paikanArvo += 10;
        }
        
        this.paikanArvo += this.siirrot.size();
        
        if (this.vari > 0) {
            this.paikanArvo *= -1;
        }
        
        this.paikanArvo += this.arvo;
    }
    
    
}
