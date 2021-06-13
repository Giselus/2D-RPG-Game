package sample;

import sample.controllers.ControllerFight;

import java.util.ArrayList;

public class EnemySpawner extends InteractiveObject{

    private static float speed = 5f;

    public ArrayList<Integer> enemies;

    public boolean isEnemyAlive;
    private float counter;
    public EnemySpawner(String name, Integer ... enemies){
        super(name,null,false);
        this.enemies = new ArrayList<>();
        for(Integer enemyId: enemies)
            this.enemies.add(enemyId);
    }

    public EnemySpawner(EnemySpawner toCopy){
        super(toCopy);
        this.enemies = toCopy.enemies;
    }

    public static EnemySpawner clone(String name){
        if(interactiveObjectsMap.containsKey(name)){
            EnemySpawner toCopy = (EnemySpawner) FetchByName(name);
            EnemySpawner tmp = new EnemySpawner(toCopy);
            tmp.counter = speed;
            return tmp;
        }
        return null;
    }

    public static void Initialize(){
        new EnemySpawner("spawner1",1,2,3,4,5);
        new EnemySpawner("spawner2", 2, 4);
        new EnemySpawner("spawner2", 3);
    }

    public void Update(float deltaTime){
        super.Update(deltaTime);
        if(isEnemyAlive)
            return;
        counter -= deltaTime;
        CharacterManager character = CharacterManager.instance;
        if(counter < 0f && (character.x != x || character.y != y || character.z != z)){
            int rand = (int)(Math.random()*1000);
            rand %= enemies.size();
            Enemy tmp = new Enemy(enemies.get(rand),1);
            InteractiveObject enemyObj = InteractiveObject.clone(tmp.name);
            Map.setPosition(enemyObj,x,y,z);
            isEnemyAlive = true;
            enemyObj.action = ()->{
                isEnemyAlive = false;
                counter = speed;
                ControllerFight.opponent = tmp;
                Updatable.clearUpdatables();
                Main.setScene("/resources/fxml/sceneFight.fxml","/resources/style/styleFight.css");
            };
        }
    }
}
