
package shakki.domain;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.concurrent.atomic.AtomicReference;
import javafx.scene.layout.Pane;
import shakki.nappulat.Nappula;

import shakki.ui.PeliUI;

/**
 * Luokka katsoo pelaajan siirrot ja ilmoittaa ne lauta-luokalle, sekä päivittää laudan näkymän PeliUI-luokan avulla.
 * Luokka myös suorittaa tekoälyn siirrot.
 */
public class Shakki {
    
    PeliUI ui;
    
    Stage ikkuna;
    Scene näkymä;
    
    Lauta lauta;
    
    Pane pane;
    
    TekoAly tekoAly;
    
    
    public Shakki(Stage ikkuna, String fen) {
        
        ui = new PeliUI();
        this.näkymä = ui.getPeliNäkymä();
        pane = ui.getPane();
        
        this.ikkuna = ikkuna;
        
        lauta = new Lauta();
        lauta.asetaLauta(fen);
        
        tekoAly = new TekoAly(lauta);
    }
    
    public boolean asetaUI(String fen) {
        return ui.lisääNappulat(fen);
    }
    
    /**
     * Metodi käsittelee pelaajan hiiren klikkailut ja välittää koordinaatit sovelluslogiikalle.
     * Jos siirto sallitaan, siirrettään nappulaa myös graafisesti.
     */
    public void aloita() {
        ikkuna.setScene(näkymä);
        
        AtomicReference<Integer> x = new AtomicReference<>();
        AtomicReference<Integer> y = new AtomicReference<>();
        AtomicReference<Boolean> siirto = new AtomicReference<>();
        siirto.set(Boolean.FALSE);
        
        näkymä.setOnKeyPressed(e -> {
            if (lauta.tehdytSiirrot.size() > 2) {
                // Perutaan kaksi viimeistä siirtoa
                peruSiirto();
                peruSiirto();
            }
            
                    
        });
        
        näkymä.setOnMouseClicked(e -> {
            
            if(!siirto.get()) {
                x.set((int) (e.getX() - 50) / 100 + lauta.ulkoL);
                y.set(7 + lauta.ulkoP - (int) e.getY() / 100);
                
                if (lauta.lauta[x.get()][y.get()] == null) {
                    return;
                }
                siirto.set(Boolean.TRUE);
            } else {
                int uusX = (int) (e.getX() - 50 ) / 100 + lauta.ulkoL;
                int uusY = 7 + lauta.ulkoP - (int) e.getY() / 100;
                
                int id = lauta.lauta[x.get()][y.get()].getID();
                int s = lauta.teeSiirto(x.get(), y.get(), uusX, uusY);
                if (s >= 0) {
                    if (s > 0 && s < 50) {
                        ui.poistaNappula(s);
                    } 
                    
                    ui.siirräNappula(id, uusX - lauta.ulkoL, 7 + lauta.ulkoP - uusY);
                    //System.out.println("Minun siirto: x: " + x.get() + ", y: " + y.get() + ", uus x: " + uusX + ", uus y: " + uusY + " " + lauta.lauta[x.get()][y.get()].getTyyppi());
                    if (s > 50) {
                        if (x.get() < uusX) {
                            ui.siirräNappula(s - 50, uusX - 1 - lauta.ulkoL, 7 + lauta.ulkoP - uusY);
                        } else {
                            ui.siirräNappula(s - 50, uusX + 1 - lauta.ulkoL, 7 + lauta.ulkoP - uusY);
                        }
                    }
                    CompletableFuture.delayedExecutor(0, TimeUnit.SECONDS).execute(() -> {
                        //tekoAlySiirra();
                    });
                    
//                    for (int i = 9; i > 1; i--) {
//                        System.out.print(i - 1 + ": ");
//                        for (int j = 1; j < 9; j++) {
//                            System.out.print(lauta.valkoisenHyökätyt[j][i]);
//                        }
//                        System.out.println("");
//                    }
//                    System.out.println("   ABCDEFGH");
                    
                } else {
                }
                
                siirto.set(Boolean.FALSE);
            }
                       
        });
            
    }
    
    /**
     * Metodi peruu siirron ja päivittää laudan näkymän.
     */
    public void peruSiirto() {
        TehtySiirto t = lauta.tehdytSiirrot.get(lauta.tehdytSiirrot.size() - 1);
        int x = t.x;
        int y = t.y;
        ui.siirräNappula(t.nappula.getID(), x - lauta.ulkoL, 7 + lauta.ulkoP - y);
        if (t.syotyNappula != null) {
            ui.siirräNappula(t.syotyNappula.getID(), t.syotyNappula.getX() - lauta.ulkoL, 7 + lauta.ulkoP - t.syotyNappula.getY());
        }
        lauta.peruSiirto();
    }
    
    /**
     * Metodi suorittaa tekoälyn laskeman siirron.
     */
    public void tekoAlySiirra() {
        
        Siirto siirto = tekoAly.LaskeSiirto();
        if (siirto == null) {
            if (lauta.shakitus != 0) {
                System.out.println("Valkoisen voitto");
            } else {
                System.out.println("Tasapeli");
            }
            return;
        }
        int x = siirto.getX();
        int y = siirto.getY();
        int uusX = siirto.getUusX();
        int uusY = siirto.getUusY();
        
        int id = lauta.lauta[x][y].getID();
        
        System.out.println("Tekoälyn siirto: x: " + x + ", y: " + y + ", uus x: " + uusX + ", uus y: " + uusY + " " + lauta.lauta[x][y].getTyyppi());
        
        int s = lauta.teeSiirto(x, y, uusX, uusY);
        
        if (s > 0 && s < 33) {
            ui.poistaNappula(s);
        }
        
        ui.siirräNappula(id, uusX - lauta.ulkoL, 7 + lauta.ulkoP - uusY);
        
        if (s > 50) {
            if (x < y) {
                ui.siirräNappula(s - 50, uusX - 1 - lauta.ulkoL, 7 + lauta.ulkoP - uusY);
            } else {
                ui.siirräNappula(s - 50, uusX + 1 - lauta.ulkoL, 7 + lauta.ulkoP - uusY);
            }
        }
        if (lauta.getSiirrot(0, false).isEmpty()) {
            if (lauta.shakitus != 0) {
                System.out.println("Mustan voitto");
            } else {
                System.out.println("Tasapeli");
            }
        }
        
    }
    
 
}
