package sample;

import java.util.ArrayList;

public class EnemyManager extends Updatable{

    public static EnemyManager instance;

    public ArrayList<EnemyObject> enemies;

    public void setActive(boolean active){
        super.setActive(active);
        for(EnemyObject enemy:enemies)
            enemy.setActive(active);
    }

    public EnemyManager(){
        enemies = new ArrayList<>();
        instance = this;
    }

    public EnemyObject getEnemy(int x, int y, int z){
        for(EnemyObject enemy: enemies){
            if(enemy.z == z && enemy.x == x && enemy.y == y){
                return enemy;
            }
        }
        return null;
    }
}
