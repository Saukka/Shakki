
package shakki.nappulat;

public class Torni extends Nappula {
    
    public Torni (int id, int x, int y, int vari) {
        super(id, x, y, vari);
        
        if (vari == 1) {
            this.numero = 4;
            this.arvo = 50;
        } else {
            this.numero = -4;
            this.arvo = -50;
        }
        
    }
    

    @Override
    public void paivitaSiirrot(Nappula[][] lauta, int kiinnitys) {
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
        
        
    }
    
}
