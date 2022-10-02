package shakki.nappulat;

import shakki.domain.Koordinaatit;


public class Sotilas extends Nappula {
    
    public Sotilas(int id, int x, int y, int vari) {
        super(id, x, y, vari);
        
        if (vari == 1) {
            this.numero = 1;
            this.arvo = 10;
        } else {
            this.numero = -1;
            this.arvo = -10;
        }
    }
    
    @Override
    public void paivitaSiirrot (Nappula[][] lauta, int kiinnitys) {
        this.siirrot.clear();
        
        if (id < 17) {
            
            // jos valkoinen
            if (!onLiikkunut) {
                if (lauta[x][y + 2] == null && lauta[x][y + 1] == null &&(kiinnitys == 0 || kiinnitys == 2)) {
                    this.siirrot.add(new Koordinaatit(x, y + 2));
                } 
                else if (lauta[x][y + 1] == null && omaNappula(lauta[x][y + 2].getID()) && (kiinnitys == 0)) {
                    blokit.add(new Koordinaatit(x, y + 2));
                }
            }
                
            if (lauta[x][y + 1] == null && (kiinnitys == 0 || kiinnitys == 2)) {
                this.siirrot.add(new Koordinaatit(x, y + 1));
            } else if (omaNappula(lauta[x][y + 1].getID()) && (kiinnitys == 0)){
                blokit.add(new Koordinaatit(x, y + 1));
            }
            
            if (lauta[x - 1][y + 1] != null) {
                if (!omaNappula(lauta[x - 1][y + 1].getID()) && (kiinnitys == 0 || kiinnitys == 3)) {
                    this.siirrot.add(new Koordinaatit(x - 1, y + 1));
                }
            }
            
            if (lauta[x + 1][y + 1] != null) {
                if (!omaNappula(lauta[x + 1][y + 1].getID()) && (kiinnitys == 0 || kiinnitys == 4)) {
                    this.siirrot.add(new Koordinaatit(x + 1, y + 1));
                }
            }
            
        } else {
            
            // jos musta
            if (!onLiikkunut) {
                if (lauta[x][y - 2] == null && lauta[x][y - 1] == null && (kiinnitys == 0 || kiinnitys == 2)) {
                    this.siirrot.add(new Koordinaatit(x, y - 2));
                }
            }
                
            if (lauta[x][y - 1] == null && (kiinnitys == 0 || kiinnitys == 2)) {
                this.siirrot.add(new Koordinaatit(x, y - 1));
            }
            
            if (lauta[x - 1][y - 1] != null) {
                System.out.println(" x: " + x + ", y: " + y);
                if (!omaNappula(lauta[x - 1][y - 1].getID()) && (kiinnitys == 0 || kiinnitys == 4)) {
                    this.siirrot.add(new Koordinaatit(x - 1, y - 1));
                }
            }
            
            if (lauta[x + 1][y - 1] != null) {
                if (!omaNappula(lauta[x + 1][y - 1].getID()) && (kiinnitys == 0 || kiinnitys == 3)) {
                    this.siirrot.add(new Koordinaatit(x + 1, y - 1));
                }
            }
            
            
            
            
        }
        
        
        
    }
    
}
