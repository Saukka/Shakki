package shakki.nappulat;

import shakki.domain.Koordinaatit;
import shakki.domain.Lauta;
import shakki.domain.Siirto;
import shakki.domain.TehtySiirto;


public class Sotilas extends Nappula {
    
    public Sotilas(int id, int x, int y, int vari, Lauta lauta) {
        
        super(id, x, y, vari, lauta);
        
        this.tyyppi = TYYPPI.SOTILAS;
        this.arvo = 10;
        
        if (vari == 0) {
            this.numero = 1;
        } else {
            this.numero = -1;
        }
        if (vari == 0) {
                lauta.valkoisenHyökätyt[this.x - 1][this.y + 1]++;
                lauta.valkoisenHyökätyt[this.x + 1][this.y + 1]++;
            } else {
                lauta.mustanHyökätyt[this.x - 1][this.y - 1]++;
                lauta.mustanHyökätyt[this.x + 1][this.y - 1]++;
            }
    }
    
    @Override
    public void paivitaSiirrot () {
        
        if (vari == 0) {
            
            // jos valkoinen
            if (!onLiikkunut) {
                if (lauta.lauta[x][y + 2] == null && lauta.lauta[x][y + 1] == null) {
                    this.siirrot.add(new Siirto(x, y, x, y + 2, 4,0, false));
                } 
                else if ((lauta.lauta[x][y + 1] == null && lauta.lauta[x][y + 2] != null) && (kiinnitys == 0)) {
                    blokit.add(new Koordinaatit(x, y + 2));
                }
            }
                
            if (lauta.lauta[x][y + 1] == null) {
                this.siirrot.add(new Siirto(x, y, x, y + 1, 4, 0, false));
            } else {
                blokit.add(new Koordinaatit(x, y + 1));
            }
            
            if (lauta.lauta[x - 1][y + 1] != null && !omaNappula(lauta.lauta[x - 1][y + 1])) {
                this.siirrot.add(new Siirto(x, y, x - 1, y + 1, 6, 0, false));
                if (lauta.lauta[x - 1][y + 1].getTyyppi() == TYYPPI.KUNINGAS) {
                    shakita(10); 
                }
            }
            
            if (lauta.lauta[x + 1][y + 1] != null && !omaNappula(lauta.lauta[x + 1][y + 1])) {
                this.siirrot.add(new Siirto(x, y, x + 1, y + 1, 8, 0, false));
                if (lauta.lauta[x + 1][y + 1].getTyyppi() == TYYPPI.KUNINGAS) {
                    shakita(10); 
                }
            }
            
            // OhestaLyönti. Tässä siirto lisätään jos vastustajan sotilas on vieressä. 
            // Siirtoja haettaessa katsotaan saako ohestalyöntiä oikeasti suorittaa.
            if (lauta.lauta[x + 1][y + 1] == null && lauta.lauta[x + 1][y] != null && lauta.lauta[x + 1][y].getNumero() == -1) {
                this.siirrot.add(new Siirto (x, y, x + 1, y + 1, 8, 3, true));
            }
            
            if (lauta.lauta[x - 1][y + 1] == null && lauta.lauta[x - 1][y] != null && lauta.lauta[x - 1][y].getNumero() == -1) {
                this.siirrot.add(new Siirto (x, y, x - 1, y + 1, 6, 3, true));
            }
            
        } else {
            
            // jos musta
            if (!onLiikkunut) {
                if (lauta.lauta[x][y - 2] == null && lauta.lauta[x][y - 1] == null) {
                    this.siirrot.add(new Siirto(x, y, x, y - 2, 3, 0, false));
                } else if (lauta.lauta[x][y - 1] == null && lauta.lauta[x][y - 2] != null && (kiinnitys == 0 || kiinnitys == 2)) {
                    blokit.add(new Koordinaatit(x, y - 2));
                }
            }
                
            if (lauta.lauta[x][y - 1] == null) {
                this.siirrot.add(new Siirto(x, y, x, y - 1, 3, 0, false));
            } else {
                blokit.add(new Koordinaatit(x, y - 1));
            }
            
            if (lauta.lauta[x - 1][y - 1] != null && !omaNappula(lauta.lauta[x - 1][y - 1])) {
                this.siirrot.add(new Siirto(x, y, x - 1, y - 1, 7, 0, false));
                if (lauta.lauta[x - 1][y - 1].getTyyppi() == TYYPPI.KUNINGAS) {
                    shakita(10); 
                }
            }
            
            if (lauta.lauta[x + 1][y - 1] != null && !omaNappula(lauta.lauta[x + 1][y - 1])) {
                this.siirrot.add(new Siirto(x, y, x + 1, y - 1, 5, 0, false));
                if (lauta.lauta[x + 1][y - 1].getTyyppi() == TYYPPI.KUNINGAS) {
                    shakita(10); 
                }
            }
            
            // OhestaLyönti. Tässä siirto lisätään jos vastustajan sotilas on vieressä. 
            // Siirtoja haettaessa katsotaan saako ohestalyöntiä oikeasti suorittaa.
            if (lauta.lauta[x + 1][y - 1] == null && lauta.lauta[x + 1][y] != null && lauta.lauta[x + 1][y].getNumero() == 1) {
                this.siirrot.add(new Siirto (x, y, x + 1, y - 1, 5, 3, true));
            }
            
            if (lauta.lauta[x - 1][y - 1] == null && lauta.lauta[x - 1][y] != null && lauta.lauta[x - 1][y].getNumero() == 1) {
                this.siirrot.add(new Siirto (x, y, x - 1, y - 1, 7, 3, true));
            }
            
        }
        
    }
    
    @Override
    public void asetaKoordinaatit(int x, int y, boolean päivitä) {
        
        onLiikkunut = true;
        
        if (päivitä) {
            if (vari == 0) {
                lauta.valkoisenHyökätyt[this.x - 1][this.y + 1]--;
                lauta.valkoisenHyökätyt[this.x + 1][this.y + 1]--;
            } else {
                lauta.mustanHyökätyt[this.x - 1][this.y - 1]--;
                lauta.mustanHyökätyt[this.x + 1][this.y - 1]--;
            }
        }
        this.x = x;
        this.y = y;
        
        if (päivitä) {
            if (vari == 0) {
                lauta.valkoisenHyökätyt[this.x - 1][this.y + 1]++;
                lauta.valkoisenHyökätyt[this.x + 1][this.y + 1]++;
            } else {
                lauta.mustanHyökätyt[this.x - 1][this.y - 1]++;
                lauta.mustanHyökätyt[this.x + 1][this.y - 1]++;
            }
        }
    }
    
    @Override
    public void syö() {
        if (vari == 0) {
            lauta.valkoisenHyökätyt[this.x - 1][this.y + 1]--;
            lauta.valkoisenHyökätyt[this.x + 1][this.y + 1]--;
        } else {
            lauta.mustanHyökätyt[this.x - 1][this.y - 1]--;
            lauta.mustanHyökätyt[this.x + 1][this.y - 1]--;
        }
        this.syoty = true;
        
        TehtySiirto tehtySiirto = lauta.tehdytSiirrot.get(lauta.tehdytSiirrot.size() - 1);
        tehtySiirto.lisaaNappula(this);
        tehtySiirto.lisaaSiirrot(this, siirrot);
        tehtySiirto.lisaaBlokit(this, blokit);
        tehtySiirto.lisaaViimeksiPaivitetty(this, viimeksiPaivitetty);
        
    }
    @Override
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
    
    @Override
    public int nappulanArvio() {
        int arvio = 10;
        
        int[][] puolustetut;
        int[][] hyökätyt;
        if (vari == 0) {
            puolustetut = lauta.valkoisenHyökätyt;
            hyökätyt = lauta.mustanHyökätyt;
            
        } else {
            puolustetut = lauta.mustanHyökätyt;
            hyökätyt = lauta.valkoisenHyökätyt;
        }
        if (hyökätyt[x][y] - 1 > puolustetut[x][y]) {
            arvio -= 2;
        } else if (hyökätyt[x][y] <= puolustetut[x][y]) {
            arvio += 2;
        }
        
        if (x > 2 && x < 7 && y > 3 && y < 8) {
            arvio++;
            if (puolustetut[x][y] >= hyökätyt[x][y]) {
                arvio ++;
                if (x > 3 && x < 6 && y > 4 && y < 7) {
                    arvio++;
                }
            }
            
        }
        
        if (vari == 0) {
            if (lauta.lauta[x - 1][y - 1] != null && lauta.lauta[x - 1][y - 1].getNumero() == 1) {
                arvio += 1;
            }
            if (lauta.lauta[x + 1][y - 1] != null && lauta.lauta[x + 1][y - 1].getNumero() == 1) {
                arvio += 1;
            }
            if (y > 4) arvio += 2;
        } else {
            if (lauta.lauta[x - 1][y + 1] != null && lauta.lauta[x - 1][y + 1].getNumero() == -1) {
                arvio += 1;
            }
            if (lauta.lauta[x + 1][y + 1] != null && lauta.lauta[x + 1][y + 1].getNumero() == -1) {
                arvio += 1;
            }
            if (y < 7) arvio += 2;
        }
        
        arvio += puolustetut[x][y];
        
        return arvio;
    }
    
}
