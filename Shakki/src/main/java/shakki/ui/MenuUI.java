
package shakki.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import shakki.domain.Shakki;

public class MenuUI extends Application {
    
    @Override
    public void start(Stage ikkuna) {
        
        ikkuna.setTitle("Shakki");
        
        Button aloitusNappi = new Button("Aloita peli");
        aloitusNappi.setMinSize(300,60);
        aloitusNappi.relocate(380, 405);
        
        TextField teksti = new TextField();
        teksti.setFont(Font.font("Verdana", 18));
        teksti.relocate(200, 200);
        teksti.setMinSize(600, 25);
        //teksti.resize(600, 25);
        teksti.setText("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
        
        Label ohje = new Label("Shakkilaudan asetus hyödyntää fen-merkkijonoa, \n jonka avulla laudan voi asettaa mihin tahansa tilanteeseen. \n "
                + "esim. 3k1r2/p5n1/1bp2n1p/3p2p1/1P5P/P4PK1/2B1N1P1/2R5");
        ohje.setFont(Font.font("Verdana", 18));
        ohje.relocate(200, 260);
        
        Pane pane = new Pane();
        pane.getChildren().addAll(aloitusNappi, teksti, ohje);
        
        
        
        Scene näkymä = new Scene(pane,1050,850);
        ikkuna.setScene(näkymä);
        ikkuna.show();
        
        aloitusNappi.setOnAction( e -> {
            Shakki shakki = new Shakki(ikkuna, teksti.getText());
            if (!shakki.asetaUI(teksti.getText())) {
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
