
package shakki.domain;
import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.concurrent.atomic.AtomicReference;
import javafx.scene.layout.Pane;

import shakki.ui.PeliUI;


/**
 * Luokka katsoo pelaajan siirrot ja ilmoittaa ne lauta-luokalle, 
 * sekä päivittää laudan näkymän PeliUI-luokan avulla.
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
                    if (s > 50) {
                        if (x.get() < uusX) {
                            ui.siirräNappula(s - 50, uusX - 1 - lauta.ulkoL, 7 + lauta.ulkoP - uusY);
                        } else {
                            ui.siirräNappula(s - 50, uusX + 1 - lauta.ulkoL, 7 + lauta.ulkoP - uusY);
                        }
                    }
                    
                    tekoAlySiirra();
                    
                } else {
                }
                
                siirto.set(Boolean.FALSE);
            }
                       
        });
            
    }
    
    /**
     * Tekoälylle annetaan lauta ja palauttaa siirtonsa.
     */
    public void tekoAlySiirra() {
        
        Siirto siirto = tekoAly.LaskeSiirto();
        if (siirto == null) {
            System.out.println("Valkoisen voitto");
            return;
        }
        int x = siirto.getX();
        int y = siirto.getY();
        int uusX = siirto.getUusX();
        int uusY = siirto.getUusY();
        
        int id = lauta.lauta[x][y].getID();
        
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
        if (lauta.getSiirrot(0, lauta.shakitus).isEmpty()) {
            System.out.println("Mustan voitto");
        }
        
    }
    
 
}
