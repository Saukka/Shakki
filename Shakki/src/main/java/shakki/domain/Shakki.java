
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
    
    
    public Shakki(Stage ikkuna) {
        
        ui = new PeliUI();
        this.näkymä = ui.getPeliNäkymä();
        pane = ui.getPane();
        
        this.ikkuna = ikkuna;
        
        lauta = new Lauta();
        
        tekoAly = new TekoAly();
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
                
                int id = lauta.getID(x.get(), y.get());
                int s = lauta.teeSiirto(x.get(), y.get(), uusX, uusY);
                if (s >= 0) {
                    
                    if (s > 0 && s < 33) {
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
                    System.out.println("Siirto ei onnistunut!");
                }
                
                siirto.set(Boolean.FALSE);
            }
                       
        });
            
    }
    
    /**
     * Tekoälylle annetaan lauta ja palauttaa siirtonsa.
     */
    public void tekoAlySiirra() {
        
        ArrayList<Koordinaatit> taSiirto = tekoAly.LaskeSiirto(lauta);
        int taVanhaX = taSiirto.get(0).getX();
        int taVanhaY = taSiirto.get(0).getY();
        int taUusX = taSiirto.get(1).getX();
        int taUusY = taSiirto.get(1).getY();
        
        int id = lauta.getID(taVanhaX, taVanhaY);
        
        int s = lauta.teeSiirto(taVanhaX, taVanhaY, taUusX, taUusY);
        
        if (s > 0 && s < 33) {
            ui.poistaNappula(s);
        }
        ui.siirräNappula(id, taUusX - lauta.ulkoL, 7 + lauta.ulkoP - taUusY);
        
        if (s > 50) {
            if (taVanhaX < taVanhaY) {
                ui.siirräNappula(s - 50, taUusX - 1 - lauta.ulkoL, 7 + lauta.ulkoP - taUusY);
            } else {
                ui.siirräNappula(s - 50, taUusX + 1 - lauta.ulkoL, 7 + lauta.ulkoP - taUusY);
            }
        }
        
    }
    
 
}
