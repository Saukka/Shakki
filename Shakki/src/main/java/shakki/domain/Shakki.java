
package shakki.domain;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;
import javafx.scene.layout.Pane;

import shakki.ui.PeliUI;


/**
 * Luokka katsoo pelaajan siirrot ja ilmoittaa ne lauta-luokalle, 
 * sekä päivittää laudan näkymän PeliUI-luokan avulla.
 * 
 */
public class Shakki {
    
    PeliUI ui;
    
    Stage ikkuna;
    Scene näkymä;
    
    Lauta lauta;
    
    Pane pane;
    
    
    public Shakki(Stage ikkuna) {
        
        ui = new PeliUI();
        this.näkymä = ui.getPeliNäkymä();
        pane = ui.getPane();
        
        this.ikkuna = ikkuna;
        
        lauta = new Lauta();
    }
    
    public void aloita() {
        ikkuna.setScene(näkymä);
        
        AtomicReference<Integer> x = new AtomicReference<>();
        AtomicReference<Integer> y = new AtomicReference<>();
        AtomicReference<Boolean> siirto = new AtomicReference<>();
        siirto.set(Boolean.FALSE);
        
        näkymä.setOnMouseClicked(e -> {
            
            if(!siirto.get()) {
                x.set((int) (e.getX() - 50) / 100);
                y.set(7 - (int) e.getY() / 100);
                siirto.set(Boolean.TRUE);
            } else {
                int uusX = (int) (e.getX() - 50 ) / 100;
                int uusY = 7 - (int) e.getY() / 100;
                
                
                int id = lauta.getID(x.get(), y.get());
                lauta.teeSiirto(x.get(), y.get(), uusX, uusY);
                ui.siirräNappula(id, uusX, uusY);
                
                siirto.set(Boolean.FALSE);
            }
                       
        });
            
    }
 
}
