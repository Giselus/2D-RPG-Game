package sample;

import javafx.scene.input.KeyCode;

import java.util.ArrayList;

public class EnemyObject extends InteractiveObject{

    public EnemyObject(int x, int y, int z, ImageFrame ... img){
        super("test",()->{},true,img);
    }

    public void Fight(){
        Main.clearUptadables();
        Main.setScene("/resources/fxml/sceneFight.fxml","/resources/style/styleFight.css");
        Defeat();
    }
    public void Defeat(){
        setActive(false);
    }
}
