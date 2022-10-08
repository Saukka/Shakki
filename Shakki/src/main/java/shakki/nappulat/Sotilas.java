package shakki.nappulat;

import shakki.domain.Koordinaatit;


public class Sotilas extends Nappula {
    
    public Sotilas(int id, int x, int y, int vari) {
        super(id, x, y, vari);
        
        if (vari == 0) {
            this.numero = 1;
            this.arvo = 10;
        } else {
            this.numero = -1;
            this.arvo = -10;
        }
    }
    
    @Override
    public void paivitaSiirrot (Nappula[][] lauta, int kiinnitys) {
        this.blokit.clear();
        this.siirrot.clear();
        
        if (id < 17) {
            
            // jos valkoinen
            if (!onLiikkunut) {
                if (lauta[x][y + 2] == null && lauta[x][y + 1] == null &&(kiinnitys == 0 || kiinnitys == 2)) {
                    this.siirrot.add(new Koordinaatit(x, y + 2));
                } 
                else if ((lauta[x][y + 1] == null && lauta[x][y + 2] != null) && (kiinnitys == 0)) {
                    blokit.add(new Koordinaatit(x, y + 2));
                }
            }
                
            if (lauta[x][y + 1] == null && (kiinnitys == 0 || kiinnitys == 2)) {
                this.siirrot.add(new Koordinaatit(x, y + 1));
            } else if (lauta[x][y + 1] != null && (kiinnitys == 0) || kiinnitys == 2){
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
                } else if (lauta[x][y - 1] == null && lauta[x][y - 2] != null && (kiinnitys == 0 || kiinnitys == 2)) {
                    blokit.add(new Koordinaatit(x, y - 2));
                }
            }
                
            if (lauta[x][y - 1] == null && (kiinnitys == 0 || kiinnitys == 2)) {
                this.siirrot.add(new Koordinaatit(x, y - 1));
            } else if (lauta[x][y - 1] != null && (kiinnitys == 0 || kiinnitys == 2)){
                blokit.add(new Koordinaatit(x, y - 1));
            }
            
            if (lauta[x - 1][y - 1] != null) {
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
    
    @Override
    public Sotilas kopioi() {
        
        Sotilas n = new Sotilas(id, x, y, vari);
        
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
