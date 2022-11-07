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
    
    int id;
    
    String[] nappulat = new String[]{"torni", "ratsu", "lähetti", "kuningatar", "kuningas", "sotilas"};
    
    ImageView[] lisättävät;
    ImageView[] ikonit;
    
    boolean vKuningas = false;
    boolean mKuningas = false;
    
    public PeliUI() {
        pane = new Pane();
        pane.setBackground(new Background(new BackgroundFill(Color.web("#c7e4f8"), CornerRadii.EMPTY, Insets.EMPTY)));
        peliNäkymä = new Scene(pane, 1050, 850);
        
        lisättävät = new ImageView[50];
        id = 1;
        
        lisääKoordinaatit();
        maalaaRuudut();
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
    
    public boolean lisaaKirjaimella(char c, int x, int y) {
        
        String musta = "";
        
        if (Character.isLowerCase(c)) {
            musta += "M";
        }
        char n = Character.toLowerCase(c);
        
        if (id > 48) return false;
        
        switch (n) {
            case 'p':
                lisaaNappula(5, x, y, musta);
                return true;
            case 'r':
                lisaaNappula(0, x, y, musta);
                return true;
            case 'n':
                lisaaNappula(1, x, y, musta);
                return true;
            case 'b':
                lisaaNappula(2, x, y, musta);
                return true;
            case 'q':
                lisaaNappula(3, x, y, musta);
                return true;
            case 'k':
                if (musta.isBlank()) {
                    if (vKuningas) return false;
                    vKuningas = true;
                } else {
                    if (mKuningas) return false;
                    mKuningas = true;
                }
                lisaaNappula(4, x, y, musta);
                return true;
            default:
                break;
        }
        return false;
        
    }
    
    public void lisaaNappula(int i, int x, int y, String musta) {
        Image kuva = new Image("file:Kuvat/" + musta + nappulat[i] + ".png");
        ImageView ikoni = new ImageView(kuva);
        ikoni.relocate(60 + x * ruudunLeveys, y * ruudunLeveys + 10);
        ikoni.setFitHeight(ruudunLeveys - 20);
        ikoni.setFitWidth(ruudunLeveys - 20);
        lisättävät[id - 1] = ikoni;
        id++;
    }
    
    public boolean lisääNappulat(String fen) {
        
        int x = 0;
        int y = 0;
        
        for (int i = 0; i < fen.length(); i++) {
            System.out.println(x);
            if (x > 8 || y > 8) {
                System.out.println("yli 8");
                return false;
            }
            
            char c = fen.charAt(i);
            
            if (Character.isLetter(c)) {
                if (!lisaaKirjaimella(c, x, y)) {
                    return false;
                }
                x++;
            } else if (c == '/') {
                y ++;
                x = 0;
            } else if (Character.isDigit(c)) {
                x += Character.getNumericValue(c);
            } else {
                return false;
            }
        }
        
        if (!vKuningas || !mKuningas) {
            return false;
        }
        
        ikonit = new ImageView[id - 1];

        for (int i = 0; i < id - 1; i++) {
            ikonit[i] = lisättävät[i];
        }
        pane.getChildren().addAll(ikonit);
        
        return true;
    }
    
    public void tyhjennaIkonit() {
        for (int i = 0; i < ikonit.length; i++) {
            ikonit[i] = null;
        }
    }
    
    public void siirräNappula(int id, int x, int y) {
        ikonit[id - 1].relocate(60 + x * ruudunLeveys, 10 + y * ruudunLeveys);
    }
    
    public void poistaNappula(int id) {
        ikonit[id - 1].relocate(-100, -100);
    }
    
    public Scene getPeliNäkymä() {
        return peliNäkymä;
    }
    
    public Pane getPane() {
        return pane;
    }
    
}
