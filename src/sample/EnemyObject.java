package sample;

import javafx.scene.input.KeyCode;

import java.util.ArrayList;

public class EnemyObject extends GameObject{

    public EnemyObject(int x, int y, int z, ImageFrame ... img){
        super(x,y,z,img);
        EnemyManager.instance.enemies.add(this);
    }

    public void Update(float deltaTime){
        super.Update(deltaTime);

    }

    public void Fight(){
        Main.clearUptadables();
        Main.setScene("/resources/fxml/sceneFight.fxml","/resources/style/styleFight.css");
        Defeat();
    }

    public void Defeat(){
        setActive(false);
        EnemyManager.instance.enemies.remove(this);
    }
}
