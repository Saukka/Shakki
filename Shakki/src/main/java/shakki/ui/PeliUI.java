package shakki.ui;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PeliUI {
    
    int ruudunLeveys = 100;
    
    Pane pane;
    Scene peliNäkymä;
    
    ImageView[] ikonit;
    
    public PeliUI() {
        pane = new Pane();
        pane.setBackground(new Background(new BackgroundFill(Color.web("#c7e4f8"), CornerRadii.EMPTY, Insets.EMPTY)));
        peliNäkymä = new Scene(pane, 1050, 850);
        
        ikonit = new ImageView[32];
        
        lisääKoordinaatit();
        maalaaRuudut();
        lisääNappulat();
        
    }
    
    public void lisääKoordinaatit() {
        String aakkoset = "abcdefgh";
        
        Label[] kirjaimet = new Label[8];
        for (int i = 0; i < 8; i++) {
            Label kirjain = new Label(String.valueOf(aakkoset.charAt(i)));
            kirjain.relocate(i * 100 + 90 , 820);
            kirjain.setTextFill(Color.web("#838587"));
            kirjaimet[i] = kirjain;
        }
        pane.getChildren().addAll(kirjaimet);

        Label[] numerot = new Label[8];
        for (int i = 1; i < 9; i++) {
            Label kirjain = new Label(String.valueOf(i));
            kirjain.relocate(20, 840 - i * 100);
            kirjain.setTextFill(Color.web("#838587"));
            numerot[i-1] = kirjain;
        }
        pane.getChildren().addAll(numerot);
        
    }
    
    public void maalaaRuudut() {
        ArrayList<Rectangle> ruudut = new ArrayList<>();
        
        Rectangle tausta = new Rectangle (50, 0, 800, 800);
        tausta.setFill(Color.web("#edfaf9"));
        ruudut.add(tausta);
        
        int ensimmäinen = 100; // Jos rivin ensimmäistä ruutua ei maalata, arvo on 100
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j = j + 2) {
                Rectangle ruutu = new Rectangle(j * ruudunLeveys + ensimmäinen + 50, i * ruudunLeveys, ruudunLeveys, ruudunLeveys);
                ruutu.setFill(Color.web("#749eb8"));
                ruudut.add(ruutu);
            }
            if (ensimmäinen == 100) {
                ensimmäinen = 0;
            } else {
                ensimmäinen = 100;
            }
        }
        pane.getChildren().addAll(ruudut);
    }
    
    public void lisääNappulat() {
        
        String[] nappulat = new String[]{"torni", "ratsu", "lähetti", "kuningatar", "kuningas", "lähetti", "ratsu", "torni"};
        
        
        // Valkoiset takarivin nappulat
        for (int i = 0; i < 8; i++) {
            Image kuva = new Image("file:kuvat/" + nappulat[i] + ".png");
            ImageView ikoni = new ImageView(kuva);
            ikoni.relocate(60 + i * ruudunLeveys, 710);
            ikoni.setFitHeight(ruudunLeveys - 20);
            ikoni.setFitWidth(ruudunLeveys - 20);
            ikonit[i] = ikoni;
        }
        // valkoiset sotilaat
        for (int i = 0; i < 8; i++) {
            Image kuva = new Image("file:kuvat/sotilas.png");
            ImageView ikoni = new ImageView(kuva);
            ikoni.relocate(60 + i * ruudunLeveys, 610);
            ikoni.setFitHeight(ruudunLeveys - 20);
            ikoni.setFitWidth(ruudunLeveys - 20);
            ikonit[i+8] = ikoni;
        }
        //mustat takarivin nappulat
        for (int i = 0; i < 8; i++) {
            Image kuva = new Image("file:kuvat/M" + nappulat[i] + ".png");
            ImageView ikoni = new ImageView(kuva);
            ikoni.relocate(60 + i * ruudunLeveys, 10);
            ikoni.setFitHeight(ruudunLeveys - 20);
            ikoni.setFitWidth(ruudunLeveys - 20);
            ikonit[i+16] = ikoni;
        }
        //mustat sotilaat
        for (int i = 0; i < 8; i++) {
            Image kuva = new Image("file:kuvat/Msotilas.png");
            ImageView ikoni = new ImageView(kuva);
            ikoni.relocate(60 + i * ruudunLeveys, 110);
            ikoni.setFitHeight(ruudunLeveys - 20);
            ikoni.setFitWidth(ruudunLeveys - 20);
            ikonit[i+24] = ikoni;
        }
        
        pane.getChildren().addAll(ikonit);
        
    }
    
    public void siirräNappula(int id, int x, int y) {
        int uusY = 7 - y;
        ikonit[id-1].relocate(60 + x * ruudunLeveys, 10 + uusY * ruudunLeveys);
    }
    
    public Scene getPeliNäkymä() {
        return peliNäkymä;
    }
    
    public Pane getPane() {
        return pane;
    }
    
}
