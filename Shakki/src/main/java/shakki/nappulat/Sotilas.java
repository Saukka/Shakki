package shakki.nappulat;

import shakki.domain.Koordinaatit;
import shakki.domain.Lauta;
import shakki.domain.Siirto;


public class Sotilas extends Nappula {
    
    public Sotilas(int id, int x, int y, int vari, Lauta lauta) {
        
        super(id, x, y, vari, lauta);
        
        this.tyyppi = TYYPPI.SOTILAS;
        this.paikanArvo = 0;
        
        if (vari == 0) {
            this.numero = 1;
            this.arvo = 10;
        } else {
            this.numero = -1;
            this.arvo = -10;
        }
    }
    
    @Override
    public void paivitaSiirrot () {
        
        if (vari == 0) {
            
            // jos valkoinen
            if (!onLiikkunut) {
                if (lauta.lauta[x][y + 2] == null && lauta.lauta[x][y + 1] == null) {
                    this.siirrot.add(new Siirto(x, y, x, y + 2, 4,0));
                } 
                else if ((lauta.lauta[x][y + 1] == null && lauta.lauta[x][y + 2] != null) && (kiinnitys == 0)) {
                    blokit.add(new Koordinaatit(x, y + 2));
                }
            }
                
            if (lauta.lauta[x][y + 1] == null) {
                this.siirrot.add(new Siirto(x, y, x, y + 1, 4, 0));
            } else {
                blokit.add(new Koordinaatit(x, y + 1));
            }
            
            if (lauta.lauta[x - 1][y + 1] != null && !omaNappula(lauta.lauta[x - 1][y + 1])) {
                this.siirrot.add(new Siirto(x, y, x - 1, y + 1, 6, 0));
                if (lauta.lauta[x - 1][y + 1].getTyyppi() == TYYPPI.KUNINGAS) {
                    shakita(10); 
                }
            }
            
            if (lauta.lauta[x + 1][y + 1] != null && !omaNappula(lauta.lauta[x + 1][y + 1])) {
                this.siirrot.add(new Siirto(x, y, x + 1, y + 1, 8, 0));
                if (lauta.lauta[x + 1][y + 1].getTyyppi() == TYYPPI.KUNINGAS) {
                    shakita(10); 
                }
            }
            
        } else {
            
            // jos musta
            if (!onLiikkunut) {
                if (lauta.lauta[x][y - 2] == null && lauta.lauta[x][y - 1] == null) {
                    this.siirrot.add(new Siirto(x, y, x, y - 2, 3, 0));
                } else if (lauta.lauta[x][y - 1] == null && lauta.lauta[x][y - 2] != null && (kiinnitys == 0 || kiinnitys == 2)) {
                    blokit.add(new Koordinaatit(x, y - 2));
                }
            }
                
            if (lauta.lauta[x][y - 1] == null) {
                this.siirrot.add(new Siirto(x, y, x, y - 1, 3, 0));
            } else {
                blokit.add(new Koordinaatit(x, y - 1));
            }
            
            if (lauta.lauta[x - 1][y - 1] != null && !omaNappula(lauta.lauta[x - 1][y - 1])) {
                this.siirrot.add(new Siirto(x, y, x - 1, y - 1, 7, 0));
                if (lauta.lauta[x - 1][y - 1].getTyyppi() == TYYPPI.KUNINGAS) {
                    shakita(10); 
                }
            }
            
            if (lauta.lauta[x + 1][y - 1] != null && !omaNappula(lauta.lauta[x + 1][y - 1])) {
                this.siirrot.add(new Siirto(x, y, x + 1, y - 1, 5, 0));
                if (lauta.lauta[x + 1][y - 1].getTyyppi() == TYYPPI.KUNINGAS) {
                    shakita(10); 
                }
            }
            
        }
        
        paivitaArvio(lauta.lauta);
    }
    
    @Override
    public void paivitaArvio(Nappula[][] lauta) {
        this.paikanArvo = 0;
        if (this.syoty) {
            return;
        }
        arvo = 0;
        
        if (this.vari == 0) {
            if (this.y > 3) {
                arvo += 1;
                if (this.y > 4) {
                    arvo += 1;
                }
            }
            if (this.y > 3 && this.x > 3 && this.x < 6) {
                arvo += 3;
                if (this.y > 4) {
                    arvo += 4;
                }
            }
            for (int i = x - 1; i <  x + 2; i += 2) {
                if (lauta[i][y - 1] != null && lauta[i][y - 1].numero == 1) arvo += 2;
                if (lauta[i][y + 1] != null && lauta[i][y + 1].numero == 1) arvo += 2;
            }
        
        } else {
            if (this.y < 8) {
                arvo -= 1;
                if (this.y < 7) {
                    arvo -= 1;
                }
            }
            if (this.y < 8 && this.x > 3 && this.x < 6) {
                arvo -= 3;
                if (this.y < 7) {
                    arvo -= 4;
                }
            }
            
            for (int i = x - 1; i <  x + 2; i += 2) {
                if (lauta[i][y - 1] != null && lauta[i][y - 1].numero == -1) arvo -= 2;
                if (lauta[i][y + 1] != null && lauta[i][y + 1].numero == -1) arvo -= 2;
            }
        }
        
        this.paikanArvo = arvo + this.arvo;
    }
    
    @Override
    public void asetaKoordinaatit(int x, int y) {
        
        if (vari == 0) {
            lauta.valkoisenHyökätyt[this.x - 1][this.y + 1]--;
            lauta.valkoisenHyökätyt[this.x + 1][this.y + 1]--;
        } else {
            lauta.mustanHyökätyt[this.x - 1][this.y - 1]--;
            lauta.mustanHyökätyt[this.x + 1][this.y - 1]--;
        }
        this.x = x;
        this.y = y;
        
        if (vari == 0) {
            lauta.valkoisenHyökätyt[this.x - 1][this.y + 1]++;
            lauta.valkoisenHyökätyt[this.x + 1][this.y + 1]++;
        } else {
            lauta.mustanHyökätyt[this.x - 1][this.y - 1]++;
            lauta.mustanHyökätyt[this.x + 1][this.y - 1]++;
        }
    }
    
    public void syö() {
        if (vari == 0) {
            lauta.valkoisenHyökätyt[this.x - 1][this.y + 1]--;
            lauta.valkoisenHyökätyt[this.x + 1][this.y + 1]--;
        } else {
            lauta.mustanHyökätyt[this.x - 1][this.y - 1]--;
            lauta.mustanHyökätyt[this.x + 1][this.y - 1]--;
        }
        this.syoty = true;
    }
    public void tuoTakaisin() {
        if (vari == 0) {
            lauta.valkoisenHyökätyt[this.x - 1][this.y + 1]++;
            lauta.valkoisenHyökätyt[this.x + 1][this.y + 1]++;
        } else {
            lauta.mustanHyökätyt[this.x - 1][this.y - 1]++;
            lauta.mustanHyökätyt[this.x + 1][this.y - 1]++;
        }
        this.syoty = false;
    }
    
}
