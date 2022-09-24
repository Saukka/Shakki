
package shakki.domain;
import javafx.scene.Scene;
import javafx.stage.Stage;

import shakki.ui.PeliUI;




public class Shakki {
    
    PeliUI ui;
    
    Stage ikkuna;
    
    
    public Shakki(Stage ikkuna) {
        
        ui = new PeliUI();
        
        this.ikkuna = ikkuna;
        
    }
    
    public void aloita() {
        ikkuna.setScene(ui.getPeliNäkymä());
    }
    
    
    
}
