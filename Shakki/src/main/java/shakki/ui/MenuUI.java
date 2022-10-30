
package shakki.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import shakki.domain.Shakki;

public class MenuUI extends Application {
    
    @Override
    public void start(Stage ikkuna) {
        
        ikkuna.setTitle("Shakki");
        
        Button aloitusNappi = new Button("Aloita peli");
        aloitusNappi.setMinSize(300,60);
        aloitusNappi.relocate(400, 400);
        
        Pane pane = new Pane();
        pane.getChildren().add(aloitusNappi);
        
        Scene näkymä = new Scene(pane,1050,850);
        ikkuna.setScene(näkymä);
        ikkuna.show();
        
        String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
        
        aloitusNappi.setOnAction( e -> {
            Shakki shakki = new Shakki(ikkuna, fen);
            if (!shakki.asetaUI(fen)) {
                System.out.println("Virheellinen fen-merkkijono");
                return;
            }
            shakki.aloita();
        });        
        
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
